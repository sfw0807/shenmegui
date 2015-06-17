package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.ConFilePath;
import com.dc.esb.servicegov.entity.InvokeInfo;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.resource.impl.ConfigFilesExport;
import com.dc.esb.servicegov.resource.impl.MetadataXMLGenerater;
import com.dc.esb.servicegov.service.LogManager;
import com.dc.esb.servicegov.service.impl.ConFilePathManagerImpl;
import com.dc.esb.servicegov.util.FileUtil;
import com.dc.esb.servicegov.util.MySFTP;
import com.dc.esb.servicegov.vo.ConFilePathVO;
import com.jcraft.jsch.ChannelSftp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.dc.esb.servicegov.resource.impl.ConfigFilesExport.merge_configFile_dir;
import static com.dc.esb.servicegov.resource.impl.ConfigFilesExport.merge_sqlFile_dir;

/**
 * Created by Administrator on 2015/5/13.
 */
@Controller
@RequestMapping("/relateVieww")
public class ConFilePathController {

    private Log log = LogFactory.getLog(ConFilePathController.class);

    @Autowired
    private MetadataXMLGenerater metadataXMLGenerate;
    @Autowired
    private ConFilePathManagerImpl conFilePathRelate;
    @Autowired
    private ConfigFilesExport cfe;

    @Autowired
    private LogManager logManager;
    private String functionId = "15";
    /**
     * get all export invoke interface infos
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/configExport", headers = "Accept=application/json")
    public
    @ResponseBody
    List<ConFilePathVO> getAllExportInfos() {
        return conFilePathRelate.getAllConFilePathInfo();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/publish/{metadatas}/{params}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean getPublishConFilePathInfo(HttpServletRequest request,
                                      HttpServletResponse response, @PathVariable(value = "params")
                                      String params, @PathVariable(value = "metadatas") String metadatas) throws Exception {

        //传输到ESB成功 删除临时文件  否则不删除
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        params = new String(params.getBytes("iso-8859-1"), "UTF-8");
        // log.info("....." + request.getRealPath("/"));

        File metadataXML = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            metadataXML = metadataXMLGenerate.generate();
            if (null == metadataXML) {
                try {
                    logManager.setLog("生成Excel不存在!", functionId, "error");
                } catch (Exception e1) {
                    log.error(e1, e1);
                }
                String errorMsg = "生成Excel不存在！";
                DataException dataException = new DataException(errorMsg);
                throw dataException;

            } else {
                response.setContentType("application/x-zip-compressed");
                response.addHeader("Content-Disposition",
                        "attachment;filename="
                                + new String(metadataXML.getName().getBytes(
                                "gbk"), "iso-8859-1"));
                in = new BufferedInputStream(new FileInputStream(metadataXML));
                out = new BufferedOutputStream(response.getOutputStream());
                long fileLength = metadataXML.length();
                byte[] cache = null;
                if (fileLength > Integer.MAX_VALUE) {
                    cache = new byte[Integer.MAX_VALUE];
                } else {
                    cache = new byte[(int) fileLength];
                }
                int i = 0;
                while ((i = in.read(cache)) > 0) {
                    out.write(cache, 0, i);
                }
                out.flush();
            }
        } catch (Exception e) {
            try {
                logManager.setLog("导出metadata.xml文件发生异常!", functionId, "error");
            } catch (Exception e1) {
                log.error(e1, e1);
            }
            log.error(e, e);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    try {
                        logManager.setLog("metadata.xml文件导出完成,关闭输入流过程发生异常", functionId, "error");
                    } catch (Exception e1) {
                        log.error(e1, e1);
                    }
                    log.error(e, e);
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    try {
                        logManager.setLog("metadata.xml文件导出完成,关闭输出流过程发生异常", functionId, "error");
                    } catch (Exception e1) {
                        log.error(e1, e1);
                    }
                    log.error(e, e);
                }
            }
        }

        synchronized(ResourceExportController.class) {
            log.info("export params :" + params);
            boolean success = true;
            List<InvokeInfo> list = new ArrayList<InvokeInfo>();
            if (params.indexOf(":") > 0) {
                String[] paramArr = params.split(":");
                for (String uniqueStr : paramArr) {
                    String strArr[] = uniqueStr.split(",");
                    InvokeInfo invoke = new InvokeInfo();
                    invoke.setEcode(strArr[0]);
                    invoke.setConsumeMsgType(strArr[1]);
                    invoke.setProvideMsgType(strArr[2]);
                    invoke.setThrough("0".equals(strArr[3]) ? "是" : "否");
                    invoke.setServiceId(strArr[4]);
                    invoke.setOperationId(strArr[5]);
                    invoke.setProvideSysId(strArr[6]);
                    invoke.setDirection(strArr[7]);
                    invoke.setConsumeSysId(strArr[8]);
                    list.add(invoke);
                }
            } else {
                String strArr[] = params.split(",");
                InvokeInfo invoke = new InvokeInfo();
                invoke.setEcode(strArr[0]);
                invoke.setConsumeMsgType(strArr[1]);
                invoke.setProvideMsgType(strArr[2]);
                invoke.setThrough("0".equals(strArr[3]) ? "是" : "否");
                invoke.setServiceId(strArr[4]);
                invoke.setOperationId(strArr[5]);
                invoke.setProvideSysId(strArr[6]);
                invoke.setDirection(strArr[7]);
                invoke.setConsumeSysId(strArr[8]);
                list.add(invoke);
            }
            // 配置文件导出返回Files
            File[] files = cfe.exportConfigFiles(list);
            files[0].getAbsolutePath();
            String filePath = files[0].getParentFile().getParentFile().getAbsolutePath();


            ConFilePath machineInfo = conFilePathRelate.getMachineInfoByName(metadatas);
            String host = metadatas;//"192.168.1.110";
            String username = machineInfo.getUsername();//"vincent";
            String password = machineInfo.getPassword();//"asdfjkl;";
            int port = 22;
            ChannelSftp sftp = null;
            String localPath = filePath;
            String remotePath = machineInfo.getFilePath();//"/home/vincent/Documents/configs";
            final String seperator = "/";
            // 传输文件到ESB
            MySFTP ftp = new MySFTP();
            ftp.connect(host, username, password, port, sftp, localPath, remotePath, seperator);
            ftp.init();
            ftp.upload();
            ftp.disconnect();


            //  String url = System.getProperty(configZip.getPath());
            // 删除文件的根目录
            String[] delPathArr = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                InvokeInfo tempInvoke = list.get(i);
                delPathArr[i] = tempInvoke.getServiceId() + tempInvoke.getOperationId() + "(" + tempInvoke.getConsumeMsgType()
                        + "-" + tempInvoke.getProvideMsgType() + ")";
                // 删除具有-SOP标志的操作对应的其他路径
                String tempOperationId = tempInvoke.getOperationId();
                if (tempOperationId.contains("-")) {
                    String tempPath = tempInvoke.getServiceId()
                            + tempOperationId.substring(0, tempOperationId
                            .indexOf("-")) + "("
                            + tempInvoke.getConsumeMsgType() + "-"
                            + tempInvoke.getProvideMsgType() + ")";
                    File delfile = new File(tempPath);
                    FileUtil.deleteFile(delfile);
                }
            }
            // 删除serviceId + operation文件及所有子文件
            for (String delPath : delPathArr) {
                File delfile = new File(delPath);
                FileUtil.deleteFile(delfile);
            }
            // 删除mergeConfigFiles
            File configFileDir = new File(merge_configFile_dir);
            if (null != configFileDir) {
                FileUtil.deleteFile(configFileDir);
            }
            // 删除mergeSqlFiles
            File sqlFileDir = new File(merge_sqlFile_dir);
            if (null != sqlFileDir) {
                FileUtil.deleteFile(sqlFileDir);
            }
            return success;
        }
    }

}

