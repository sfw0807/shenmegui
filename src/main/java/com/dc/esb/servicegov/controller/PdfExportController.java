package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.entity.ServiceCategory;
import com.dc.esb.servicegov.service.PdfGenerator;
import com.dc.esb.servicegov.service.impl.PlatformPdfGenerator;
import com.dc.esb.servicegov.service.impl.ServiceCategoryManagerImpl;
import com.dc.esb.servicegov.service.impl.ServiceCategoryPdfGenerator;
import com.dc.esb.servicegov.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.vo.SDA;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-27
 * Time: 下午2:41
 */
@Controller
@RequestMapping("/export")
public class PdfExportController {

    private static final Log log = LogFactory.getLog(PdfExportController.class);

    @Autowired
    private ServiceManagerImpl serviceManager;
    @Autowired
    private ServiceCategoryManagerImpl serviceCategoryManager;
    @Autowired
    @Qualifier("servicePdfGenerator")
    private PdfGenerator<List<Service>> servicePdfGenerator;
    @Autowired
    @Qualifier("serviceCategoryPdfGenerator")
    private ServiceCategoryPdfGenerator serviceCategoryPdfGenerator;
    @Autowired
    @Qualifier("platformPdfGenerator")
    private PlatformPdfGenerator platformPdfGenerator;


    @RequestMapping(method = RequestMethod.GET, value = "/servicepdf/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportService(@PathVariable String id) {
        try{
            List<Service> services = serviceManager.getServiceById(id);
            servicePdfGenerator.generate(services);
        }catch(Exception e){
            log.error(e,e);
        }
        return true;

    }

    @RequestMapping(method = RequestMethod.GET, value = "/categorypdf/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportCategory(@PathVariable String id) {
        try{
            ServiceCategory category = serviceCategoryManager.getCategoryById(id);
            List<ServiceCategory> categories = new ArrayList<ServiceCategory>();
            categories.add(category);
            serviceCategoryPdfGenerator.generate(categories);
        }catch(Exception e){
            log.error(e,e);
        }
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/allpdf", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportAll() {
        try{
            platformPdfGenerator.generate(null);
        }catch(Exception e){
            log.error(e,e);
        }
        return true;
    }



}
