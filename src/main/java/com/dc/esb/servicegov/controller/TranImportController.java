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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.dao.impl.FunctionDAOImpl;
import com.dc.esb.servicegov.excel.impl.TranConsumerCountExcelGenerator;
import com.dc.esb.servicegov.excel.impl.TranProviderCountExcelGenerator;
import com.dc.esb.servicegov.tranlink.DBUtil;
import com.dc.esb.servicegov.tranlink.ExcelParse;
import com.dc.esb.servicegov.vo.TranCountVO;

/**
 * 存量交易导入
 *
 * @author xiayn
 */
@Controller
@RequestMapping("/tranlink")
public class TranImportController {
    private Log log = LogFactory.getLog(TranImportController.class);
    @Autowired
    private FunctionDAOImpl functionDAO;
    @Autowired
    private TranProviderCountExcelGenerator tranProviderGenerator;
    @Autowired
    private TranConsumerCountExcelGenerator tranConsumerGenerator;

    @RequestMapping(method = RequestMethod.POST, value = "/provider")
    public
    @ResponseBody
    String parseProvider(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam("file")
                         MultipartFile file) throws Exception {
        response.setContentType("text/html");
        response.setCharacterEncoding("GB2312");
        String fileName = file.getOriginalFilename();
        if (fileName.toLowerCase().endsWith("xlsx")
                || fileName.toLowerCase().endsWith("xlsm")
                || fileName.toLowerCase().endsWith("xls")) {
            try {
                InputStream is = file.getInputStream();
                ExcelParse parser = new ExcelParse();
                parser.parseProvider(is);
                return "SUCCESS";
            } catch (Exception e) {
                log.error(e, e);
                return "FAILED:" + e.getMessage();
            }
        } else {
            log.info("file type is wrong!");
            java.io.PrintWriter writer = response.getWriter();
            writer.println("<script language=\"javascript\">");
            writer.println("alert('导入失败,文件类型不支持!');");
            writer.println("window.parent.top.location.href=\""
                    + request.getContextPath() + "/jsp/tranimport.jsp" + "\";");
            writer.println("</script>");
            writer.flush();
            writer.close();
            return "导入失败,文件类型不支持";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/consumer")
    public
    @ResponseBody
    String parseConsumer(HttpServletRequest request,
                         HttpServletResponse response, @RequestParam("file")
    MultipartFile file) throws Exception {
        response.setContentType("text/html");
        response.setCharacterEncoding("GB2312");
        String fileName = file.getOriginalFilename();
        if (fileName.toLowerCase().endsWith("xlsx")
                || fileName.toLowerCase().endsWith("xlsm")
                || fileName.toLowerCase().endsWith("xls")) {
            try {
                InputStream is = file.getInputStream();
                ExcelParse parser = new ExcelParse();
                parser.parseConsumer(is);
                return "SUCCESS";
            } catch (Exception e) {
                log.error(e, e);
                return "FAILED:" + e.getMessage();
            }
        } else {
            log.info("file type is wrong!");
            java.io.PrintWriter writer = response.getWriter();
            writer.println("<script language=\"javascript\">");
            writer.println("alert('导入失败,文件类型不支持!');");
            writer.println("window.parent.top.location.href=\""
                    + request.getContextPath() + "/jsp/tranimport.jsp"
                    + "\";");
            writer.println("</script>");
            writer.flush();
            writer.close();
            return "FAILED";
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAll")
    public
    @ResponseBody
    List getTranLink() throws Exception {
        List<Map> list = functionDAO.getTranLink();
        return list;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveTranprovider")
    public
    @ResponseBody
    boolean saveTranProvider(@RequestBody String[] param) throws Exception {
        //var param = [trancode,tranname,provider,providerMsgType,charger,remark];
        String trancode = param[0];
        if (!DBUtil.isTranExist("TRANPROVIDER", trancode)) {
            String tranname = param[1];
            String provider = param[2];
            String providerMsgType = param[3];
            String charger = param[4];
            String remark = param[5];
            StringBuffer sql = new StringBuffer("INSERT INTO ESB.TRANPROVIDER(TRANCODE, TRANNANE, PROVIDER) ");
            sql.append("VALUES('");
            sql.append(trancode);
            sql.append("', '");
            sql.append(tranname);
            sql.append("', '");
            sql.append(provider);
            sql.append("')");
            List<String> sqlList = new ArrayList<String>();
            sqlList.add(sql.toString());
            return DBUtil.batchExcute(sqlList);
        } else {
            return false;
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveTranconsumer")
    public
    @ResponseBody
    boolean saveTranconsumer(@RequestBody String[] param) throws Exception {
        //var param = [trancode,tranname,consumer,provider,consumerMsgType,fontTrancode,charger,remark];
        String trancode = param[0];
        if (!DBUtil.isTranExist("TRANCONSUMER", trancode)) {
            String tranname = param[1];
            String consumer = param[2];
            String provider = param[3];
            String consumerMsgType = param[4];
            String fontTrancode = param[5];
            String charger = param[6];
            String remark = param[7];
            StringBuffer sql = new StringBuffer("INSERT INTO ESB.TRANCONSUMER(TRANCODE, TRANNANE, CONSUMER) ");
            sql.append("VALUES('");
            sql.append(trancode);
            sql.append("', '");
            sql.append(tranname);
            sql.append("', '");
            sql.append(consumer);
            sql.append("')");
            List<String> sqlList = new ArrayList<String>();
            sqlList.add(sql.toString());
            return DBUtil.batchExcute(sqlList);
        } else {
            return false;
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/getTranCount")
    public
    @ResponseBody
    List<TranCountVO> getTranCount() throws Exception {
        Map<String, TranCountVO> countMap = new HashMap<String, TranCountVO>();
        List<Map> pcountlist = functionDAO.getProviderCount();
        int countAllProvider = 0;
        for (Map map : pcountlist) {
            String provider = (String) map.get("PROVIDER");
            String pcount = map.get("PCOUNT") + "";
            TranCountVO countvo = new TranCountVO();
            countvo.setName(" " + provider);
            countvo.setPtrancount(pcount);
            countvo.setCtrancount("0");
            countMap.put(provider, countvo);
            countAllProvider += Integer.parseInt(pcount);
        }
        List<Map> ccountlist = functionDAO.getConsumerCount();
        int countAllConsumer = 0;
        for (Map map : ccountlist) {
            String consumer = (String) map.get("CONSUMER");
            String ccount = map.get("CCOUNT") + "";
            TranCountVO countvo = countMap.get(consumer);
            if (countvo != null) {
                countvo.setCtrancount(ccount);
            } else {
                TranCountVO countvotemp = new TranCountVO();
                countvotemp.setName(" " + consumer);
                countvotemp.setCtrancount(ccount);
                countvotemp.setPtrancount("0");
                countMap.put(consumer, countvotemp);
            }
            countAllConsumer += Integer.parseInt(ccount);
        }
        Set keySet = countMap.keySet();
        List<TranCountVO> volist = new LinkedList<TranCountVO>();
        for (Iterator iterator = keySet.iterator(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            TranCountVO vo = countMap.get(key);
            if (null != vo.getName() && !" null".equals(vo.getName())) {
                volist.add(vo);
            }
        }
        TranCountVO countAllvo = new TranCountVO();
        countAllvo.setName("总计");
        countAllvo.setPtrancount(countAllProvider + "");
        countAllvo.setCtrancount(countAllConsumer + "");
        volist.add(countAllvo);
        return volist;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllTranProvider")
    public
    @ResponseBody
    List getAllTranProvider() throws Exception {
        List<Map> list = functionDAO.getAllTranProvider();
        return list;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllTranConsumer")
    public
    @ResponseBody
    List getAllTranConsumer() throws Exception {
        List<Map> list = functionDAO.getAllTranConsumer();
        return list;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET, value = "/exportTranProvider/{params}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportTranProviderExcel(HttpServletRequest request,
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
        File tranProviderExcel = null;
        try {
            if (log.isInfoEnabled()) {
                log.info("conditon params:" + params);
            }
            Map<String, String> conditions = null;
            ObjectMapper objectMapper = new ObjectMapper();
//			mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
            conditions = objectMapper.readValue(params, Map.class);
            tranProviderExcel = tranProviderGenerator.generate(conditions);
            if (tranProviderExcel == null) {
                String errorMsg = "生成excel失败";
                DataException dataException = new DataException(errorMsg);
                throw dataException;
            } else {
                response.setContentType("application/zip");
                response.addHeader("Content-Disposition",
                        "attachment;filename="
                                + new String(tranProviderExcel.getName().getBytes(
                                "gbk"), "iso-8859-1"));
                in = new BufferedInputStream(new FileInputStream(tranProviderExcel));
                out = new BufferedOutputStream(response.getOutputStream());
                long fileLength = tranProviderExcel.length();
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

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET, value = "/exportTranConsumer/{params}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportTranConsumerExcel(HttpServletRequest request,
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
        File tranConsumerExcel = null;
        try {
            if (log.isInfoEnabled()) {
                log.info("conditon params:" + params);
            }
            Map<String, String> conditions = null;
            ObjectMapper objectMapper = new ObjectMapper();
//			mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
            conditions = objectMapper.readValue(params, Map.class);
            tranConsumerExcel = tranConsumerGenerator.generate(conditions);
            if (tranConsumerExcel == null) {
                String errorMsg = "生成excel失败";
                DataException dataException = new DataException(errorMsg);
                throw dataException;
            } else {
                response.setContentType("application/zip");
                response.addHeader("Content-Disposition",
                        "attachment;filename="
                                + new String(tranConsumerExcel.getName().getBytes(
                                "gbk"), "iso-8859-1"));
                in = new BufferedInputStream(new FileInputStream(tranConsumerExcel));
                out = new BufferedOutputStream(response.getOutputStream());
                long fileLength = tranConsumerExcel.length();
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
