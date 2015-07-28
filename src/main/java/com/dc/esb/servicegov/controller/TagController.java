package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.Tag;
import com.dc.esb.servicegov.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by vincentfxz on 15/7/20.
 */
@Controller
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagServiceImpl tagService;

    @RequestMapping(method = RequestMethod.GET, value = "/interface/{interfaceId}", headers = "Accept=application/json")
    public @ResponseBody List<Tag> getTagsForInterface(@PathVariable("interfaceId") String interfaceId){
        return tagService.getTagsForInterface(interfaceId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/interface/{interfaceId}", headers = "Accept=application/json")
    public @ResponseBody boolean addTagsForInterface(@PathVariable("interfaceId") String interfaceId, @RequestBody Tag[] tags){
        return tagService.addTagsForInterface(interfaceId, Arrays.asList(tags));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/service/{serviceId}", headers = "Accept=application/json")
    public @ResponseBody List<Tag> getTagsForService(@PathVariable("serviceId") String serviceId){
        return tagService.getTagsForService(serviceId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/service/{serviceId}", headers = "Accept=application/json")
    public @ResponseBody boolean addTagsForService(@PathVariable("serviceId") String serviceId, @RequestBody Tag[] tags){
        return tagService.addTagsForService(serviceId, Arrays.asList(tags));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/service/{serviceId}/operation/{operationId}", headers = "Accept=application/json")
    public @ResponseBody List<Tag> getTagsForOperation(@PathVariable("serviceId") String serviceId, @PathVariable("operationId") String operationId){
        return tagService.getTagsForOperation(serviceId, operationId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/service/{serviceId}/operation/{operationId}", headers = "Accept=application/json")
    public @ResponseBody boolean addTagsForService(@PathVariable("serviceId") String serviceId, @PathVariable("operationId") String operationId ,@RequestBody Tag[] tags){
        return tagService.addTagsForOperation(serviceId, operationId, Arrays.asList(tags));
    }




}
