package com.dc.esb.servicegov.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.entity.SLAView;
import com.dc.esb.servicegov.excel.impl.SLAExcelGenerator;
import com.dc.esb.servicegov.service.impl.SLAViewManagerImpl;
import com.dc.esb.servicegov.vo.SLAViewInfoVO;

@Controller
@RequestMapping("/slaview")
public class SLAViewController {
    private Log log = LogFactory.getLog(SLAViewController.class);
    @Autowired
    private SLAViewManagerImpl slaManager;
    @Autowired
    private SLAExcelGenerator slaExcelGenerator;

    @RequestMapping(method = RequestMethod.GET, value = "/allslaview", headers = "Accept=application/json")
    public
    @ResponseBody
    List<SLAViewInfoVO> getAllSLAInfo() {
        List<SLAViewInfoVO> returnList = new ArrayList<SLAViewInfoVO>();
        List<SLAView> slaList = slaManager.getAllSLAInfo();
        for (SLAView slaView : slaList) {
            SLAViewInfoVO slaVO = new SLAViewInfoVO();
            slaVO.setServiceInfo(slaView.getServiceId() + "/" + slaView.getServiceName());
            slaVO.setOperationInfo(slaView.getOperationId() + "/" + slaView.getOperationName());
            slaVO.setInterfaceInfo(slaView.getInterfaceId() + "/" + slaView.getInterfaceName());
            slaVO.setProvideSysInfo(slaView.getSysAB() + "/" + slaView.getSysName());
            slaVO.setAverageTime(slaView.getAverageTime());
            slaVO.setCurrentCount(slaView.getCurrentCount());
            slaVO.setSuccessRate(slaView.getSuccessRate());
            slaVO.setTimeOut(slaView.getTimeOut());
            returnList.add(slaVO);
        }
        return returnList;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET, value = "/export/{params}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportSlaExcel(HttpServletRequest request,
                           HttpServletResponse response,
                           @PathVariable String params) throws UnsupportedEncodingException {
//		try {
//			request.setCharacterEncoding("UTF-8");
//			response.setCharacterEncoding("UTF-8");
//			params = new String(params.getBytes("iso-8859-1"),"UTF-8");
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
        params = java.net.URLDecoder.decode(params, "UTF-8");
        InputStream in = null;
        OutputStream out = null;
        boolean success = false;
        File slaExcel = null;
        try {
            if (log.isInfoEnabled()) {
                log.info("conditon params:" + params);
            }
            Map<String, String> conditions = null;
            ObjectMapper objectMapper = new ObjectMapper();
//			mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
            conditions = objectMapper.readValue(params, Map.class);
            slaExcel = slaExcelGenerator.generate(conditions);
            if (slaExcel == null) {
                String errorMsg = "生成excel失败";
                DataException dataException = new DataException(errorMsg);
                throw dataException;
            } else {
                response.setContentType("application/zip");
                response.addHeader("Content-Disposition",
                        "attachment;filename="
                                + new String(slaExcel.getName().getBytes(
                                "gbk"), "iso-8859-1"));
                in = new BufferedInputStream(new FileInputStream(slaExcel));
                out = new BufferedOutputStream(response.getOutputStream());
                long fileLength = slaExcel.length();
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
                success = true;
            }
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(e, e);
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error(e, e);
                }

            }
        }
        return success;
    }
}
