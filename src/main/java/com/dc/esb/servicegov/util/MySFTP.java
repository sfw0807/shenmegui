package com.dc.esb.servicegov.util;

/**
 * Created by Administrator on 2015/5/14.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;


public class MySFTP {

    public static  String host = "";// http://202.91.246.188/
    public static  String username = "";
    public static  String password = ";";
    public static  int port = 22;
    public static  String localPath = ""; //
    public static  String remotePath = "";
    public static  String seperator = "/";
    private ChannelSftp sftp = null;
    private List<File> fileList = new ArrayList<File>();

    /**
     * connect server via sftp
     */
    public void connect(String hosts,String usernames,String passwords,int ports,ChannelSftp sftps,String localPaths,String remotePaths,String seperators ) {


        host = hosts;
        username = usernames;
        password = passwords;
        port = 22;
        sftp = sftps;
        localPath = localPaths;
        remotePath = remotePaths;
        seperator = "/";


        try {
            if (sftp != null) {
                System.out.println("sftp is not null");
            }
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            System.out.println("Session created.");
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            System.out.println("Session connected.");
            System.out.println("Opening Channel.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            System.out.println("Connected to " + host + ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnect with server
     */
    public void disconnect() {
        if (this.sftp != null) {
            if (this.sftp.isConnected()) {
                this.sftp.disconnect();
            } else if (this.sftp.isClosed()) {
                System.out.println("sftp is closed already");
            }
        }

    }

    public void upload() {
        String filePathMetadata = "";
        String filePathInRemote = "";
        String filePathOutRemote = "";
        try {
            if (fileList.size() > 0) {
                for (File file : fileList) {
                    String remoteFile = null;
                    String filePath = file.getAbsolutePath();
                    filePathMetadata = file.getParentFile().getParentFile().getParentFile().getAbsolutePath();
                   // System.out.println(filePath);
                    if (filePath.contains("in_config")) {
                        int commonInPathStart = filePath.indexOf("in_config");
                        String inFile = filePath.substring(commonInPathStart);
                        inFile = inFile.replace("in_config", "in_conf");
                        remoteFile = this.remotePath + "/" + inFile;
                        remoteFile = remoteFile.replaceAll("\\\\", "/");

                    }else if(filePath.contains("out_config")){
                        int commonInPathStart = filePath.indexOf("out_config");
                        String inFile = filePath.substring(commonInPathStart);
                        inFile = inFile.replace("out_config", "out_conf");
                        remoteFile = this.remotePath + "/" + inFile;
                        remoteFile = remoteFile.replaceAll("\\\\", "/");
                    }
                   // System.out.println(remoteFile);
                    if(null != remoteFile){

                        this.sftp.put(new FileInputStream(file), remoteFile);
                    }
                }
            }

            File file = new File(filePathMetadata + "/metadata.xml");
            if(file.isFile())
            {
                filePathInRemote = this.remotePath + "/" + "in_conf" + "/" + "metadata" + "/" + file.getName();
                filePathOutRemote = this.remotePath + "/" + "out_conf" + "/"+ "metadata" + "/" + file.getName();
                this.sftp.put(new FileInputStream(file), filePathInRemote);
                this.sftp.put(new FileInputStream(file), filePathOutRemote);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SftpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void createFileList(File file) throws Exception {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File temp : files) {
                createFileList(temp);
            }
        } else {
            fileList.add(file);
        }
    }

    public void init() {


        try {
            String inConfigPath = localPath + File.separator + "in_config";

            createFileList(new File(this.localPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createDir(String filepath, ChannelSftp sftp) {
        boolean bcreated = false;
        boolean bparent = false;
        File file = new File(filepath);
        String ppath = file.getParent();
        try {
            this.sftp.cd(ppath);
            bparent = true;
        } catch (SftpException e1) {
            bparent = false;
        }
        try {
            if (bparent) {
                try {
                    this.sftp.cd(filepath);
                    bcreated = true;
                } catch (Exception e) {
                    bcreated = false;
                }
                if (!bcreated) {
                    this.sftp.mkdir(filepath);
                    bcreated = true;
                }
                return;
            } else {
                createDir(ppath, sftp);
                this.sftp.cd(ppath);
                this.sftp.mkdir(filepath);
            }
        } catch (SftpException e) {
            System.out.println("mkdir failed :" + filepath);
            e.printStackTrace();
        }

        try {
            this.sftp.cd(filepath);
        } catch (SftpException e) {
            e.printStackTrace();
            System.out.println("can not cd into :" + filepath);
        }

    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    /**
     * @param args
     */
//    public static void main(String[] args) throws Exception {
//        // TODO Auto-generated method stub
//        MySFTP ftp = new MySFTP();
//        ftp.connect();
//        ftp.init();
//        ftp.upload();
//        ftp.disconnect();
//        System.exit(0);
//		/*
//		 * SFTP sp=new SFTP(); try { sp.createFileList(new File("F:/test/"));
//		 * List list=sp.getFileList(); System.out.println("a"); } catch
//		 * (Exception e) { // TODO Auto-generated catch block
//		 * e.printStackTrace(); }
//		 */
//    }
}

