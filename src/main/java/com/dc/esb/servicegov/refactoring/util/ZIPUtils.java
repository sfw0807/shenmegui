package com.dc.esb.servicegov.refactoring.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-24
 * Time: 上午8:50
 */
public class ZIPUtils {
    private static final Log log = LogFactory.getLog(ZIPUtils.class);
    private static int bufSize; // size of bytes
    private ZipOutputStream zipOut; // 压缩Zip
    private byte[] buf;
    private int readedBytes;
    // 用于压缩中。要去除的绝对父路路径，目的是将绝对路径变成相对路径。
    private String deleteAbsoluteParent;

    /**
     * 构造方法。默认缓冲区大小为512字节。
     */
    public ZIPUtils() {
        this(512);
    }

    /**
     * 构造方法。
     *
     * @param bufSize 指定压缩或解压时的缓冲区大小
     */
    public ZIPUtils(int bufSize) {
        this.bufSize = bufSize;
        this.buf = new byte[this.bufSize];
        deleteAbsoluteParent = null;
    }

    /**
     * 压缩文件夹内的所有文件和目录。
     *
     * @param zipDirectory 需要压缩的文件夹名
     */
    public void doZip(String zipDirectory) {
        File zipDir = new File(zipDirectory);
        doZip(new File[]{zipDir}, zipDir.getName());
    }

    /**
     * 压缩多个文件或目录。可以指定多个单独的文件或目录。而 <code>doZip(String zipDirectory)</code>
     * 则直接压缩整个文件夹。
     *
     * @param files       要压缩的文件或目录组成的<code>File</code>数组。
     * @param zipFileName 压缩后的zip文件名，如果后缀不是".zip"， 自动添加后缀".zip"。
     */
    public void doZip(File[] files, String zipFileName) {
        // 未指定压缩文件名，默认为"ZipFile"
        if (zipFileName == null || zipFileName.equals(""))
            zipFileName = "ZipFile";
        // 添加".zip"后缀
        if (!zipFileName.endsWith(".zip"))
            zipFileName += ".zip";
        try {
            this.zipOut = new ZipOutputStream(new BufferedOutputStream(
                    new FileOutputStream(zipFileName)));
            compressFiles(files, this.zipOut, true);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                this.zipOut.close();
            } catch (IOException e) {
                log.error("Fail to close zip output stream!", e);
            }
        }
    }

    /**
     * 压缩文件和目录。由doZip()调用
     *
     * @param files      要压缩的文件
     * @param zipOut     zip输出流
     * @param isAbsolute 是否是要去除的绝对路径的根路径。因为compressFiles()
     *                   会递归地被调用，所以只用deleteAbsoluteParent不行。必须用isAbsolute来指明
     *                   compressFiles()是第一次调用，而不是后续的递归调用。即如果要压缩的路径是
     *                   E:\temp，那么第一次调用时，isAbsolute=true，则deleteAbsoluteParent会记录
     *                   要删除的路径就是E:\ ，当压缩子目录E:\temp\folder时，isAbsolute=false，
     *                   再递归调用compressFiles()时，deleteAbsoluteParent仍然是E:\ 。从而保证了
     *                   将E:\temp及其子目录均正确地转化为相对目录。这样压缩才不会出错。不然绝对 路径E:\也会被写入到压缩文件中去。
     */
    private void compressFiles(File[] files, ZipOutputStream zipOut,
                               boolean isAbsolute) throws IOException {
        for (File file : files) {
            if (file == null)
                continue; // 空的文件对象
            // 删除绝对父路径
            if (file.isAbsolute()) {
                if (isAbsolute) {
                    deleteAbsoluteParent = file.getParentFile()
                            .getAbsolutePath();
                    deleteAbsoluteParent = appendSeparator(deleteAbsoluteParent);
                }
            } else
                deleteAbsoluteParent = "";
            if (file.isDirectory()) {// 是目录
                compressFolder(file, zipOut);
            } else {// 是文件
                compressFile(file, zipOut);
            }
        }
    }

    /**
     * 压缩文件或空目录。由compressFiles()调用。
     *
     * @param file   需要压缩的文件
     * @param zipOut zip输出流
     */
    public void compressFile(File file, ZipOutputStream zipOut)
            throws IOException {
        String fileName = file.toString();
        /* 去除绝对父路径。 */
        if (file.isAbsolute())
            fileName = fileName.substring(deleteAbsoluteParent.length());
        if (fileName == null || "".equals(fileName ))
            return;

		/*
		 * 因为是空目录，所以要在结尾加一个"/"。 不然就会被当作是空文件。 ZipEntry的isDirectory()方法中,目录以"/"结尾.
		 * org.apache.tools.zip.ZipEntry : public boolean isDirectory() { return
		 * getName().endsWith("/"); }
		 */
        if (file.isDirectory())
            fileName = fileName + "/";// 此处不能用"\\"
        zipOut.putNextEntry(new ZipEntry(fileName));
        // 如果是文件则需读;如果是空目录则无需读，直接转到zipOut.closeEntry()。
        if (file.isFile()) {
            FileInputStream fileIn = new FileInputStream(file);
            while ((this.readedBytes = fileIn.read(this.buf)) > 0) {
                zipOut.write(this.buf, 0, this.readedBytes);
            }
            fileIn.close();
        }
        zipOut.closeEntry();
    }

    /**
     * 递归完成目录文件读取。由compressFiles()调用。
     *
     * @param dir    需要处理的文件对象
     * @param zipOut zip输出流
     */
    private void compressFolder(File dir, ZipOutputStream zipOut)
            throws IOException {

        File[] files = dir.listFiles();
        if(null != files){
            if (files.length == 0)// 如果目录为空，则单独压缩空目录。
                compressFile(dir, zipOut);
            else
                // 如果目录不为空,则分别处理目录和文件.
                compressFiles(files, zipOut, false);
        }else{
            log.error("files to zip is null");
        }


    }


    /**
     * 给文件路径或目录结尾添加File.separator
     *
     * @param path 需要添加路径分割符的路径
     * @return 如果路径已经有分割符，则原样返回，否则添加分割符后返回。
     */
    private String appendSeparator(String path) {
        if (!path.endsWith(File.separator))
            path += File.separator;
        return path;
    }

    /**
     * 解压指定zip文件。
     *
     * @param unZipFile 需要解压的zip文件对象
     * @return
     */


    /**
     * 设置压缩或解压时缓冲区大小。
     *
     * @param bufSize 缓冲区大小
     */
    public void setBufSize(int bufSize) {
        this.bufSize = bufSize;
    }

    // 主函数，用于测试AntZip类
	/*
	 * public static void main(String[] args)throws Exception{
	 * if(args.length>=2){ AntZip zip = new AntZip();
	 * if(args[0].equals("-zip")){ //将后续参数全部转化为File对象 File[] files = new File[
	 * args.length - 1]; for(int i = 0;i < args.length - 1; i++){ files[i] = new
	 * File(args[i + 1]); } //将第一个文件名作为zip文件名 zip.doZip(files ,
	 * files[0].getName()); return ; } else if(args[0].equals("-unzip")){
	 * zip.unZip(args[1]); return ; } }
	 *
	 * System.out.println("Usage:");
	 * System.out.println("压缩:java AntZip -zip [directoryName | fileName]... ");
	 * System.out.println("解压:java AntZip -unzip fileName.zip"); }
	 */

    public void doZip(File[] files, String workDir, String fileName) {

    }
}
