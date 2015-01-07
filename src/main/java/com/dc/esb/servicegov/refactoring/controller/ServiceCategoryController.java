package com.dc.esb.servicegov.refactoring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.refactoring.entity.ServiceCategory;
import com.dc.esb.servicegov.refactoring.service.impl.ServiceCategoryManagerImpl;
import com.dc.esb.servicegov.refactoring.vo.ServiceTotalVO;

@Controller
@RequestMapping("/serviceCategory")
public class ServiceCategoryController {	
	@Autowired
	private ServiceCategoryManagerImpl categoryManager;
	
	/**
	 * get all list
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
	public @ResponseBody List<ServiceCategory> getAllOperationInfo(){
		
		return categoryManager.getAllCategoryInfo();
	}
	
	/**
	 * get all service Total infos
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/total", headers = "Accept=application/json")
	public @ResponseBody List<ServiceTotalVO> getServiceTotalInfos(){
		
		return categoryManager.getServiceTotalInfos();
	}
    
	/**
	 * get first level list
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/first", headers = "Accept=application/json")
	public @ResponseBody List<ServiceCategory> getFirstLevelInfo(){
		
		return categoryManager.getFirstLevelInfo();
	}
	
	/**
	 * get second level list
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/second", headers = "Accept=application/json")
	public @ResponseBody List<ServiceCategory> getSecondLevelInfo(){
		
		return categoryManager.getSecondLevelInfo();
	}
	
	/**
     * delete serviceCategory
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/delete/{id}", headers = "Accept=application/json")
     public
     @ResponseBody
     boolean delete(@PathVariable String id) {
    	boolean flag = true;
    	try{
    		if(id.contains(",")){
    			String[] idArr = id.split(",");
    			int i =0;
    			while(i<idArr.length){
    				categoryManager.delServiceCategory(idArr[i]);
    				i++;
    			}
    		}
    		else{
    			categoryManager.delServiceCategory(id);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		flag = false;
    	}
    	return flag;
    }
    
    /**
     * get serviceCategory by id
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getServiceCategoryById/{id}", headers = "Accept=application/json")
	public @ResponseBody ServiceCategory getServiceCategoryById
	(@PathVariable String id){
		return categoryManager.getServiceCategoryById(id);
	}
	
    /**
     * update or insert serviceCategory
     * @param request
     * @param response
     * @param service
     * @return
     */
	@RequestMapping(method = RequestMethod.POST, value = "/insertOrUpdate", headers = "Accept=application/json")
     public
     @ResponseBody
     boolean insertOrUpdate(HttpServletRequest request,
 			HttpServletResponse response,@RequestBody ServiceCategory serviceCategory) {
    	boolean flag = true;
    	try{
		    categoryManager.insertOrUpdate(serviceCategory);
    	}catch(Exception e){
    		flag = false;
    		e.printStackTrace();
    	}
    	return flag;
    }
	
	/**
     * check category used by service
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/checkIsUsed/{id}", headers = "Accept=application/json")
	public @ResponseBody boolean checkIsUsed
	(@PathVariable String id){
    	boolean flag = false;
    	try{
    		if(id.contains(",")){
    			String[] idArr = id.split(",");
    			int i =0;
    			while(i<idArr.length){
    				if(categoryManager.checkIsUsed(idArr[i])){
    					flag = true;
    					break;
    				}
    				i++;
    			}
    		}
    		else{
    			if(categoryManager.checkIsUsed(id)){
					flag = true;
				}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return flag;
	}
    
    /**
     * check category is or not Father
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/checkIsFather/{id}", headers = "Accept=application/json")
	public @ResponseBody String checkIsFather
	(@PathVariable String id){
    	try{
    		if(id.contains(",")){
    			String[] idArr = id.split(",");
    			int i =0;
    			while(i<idArr.length){
    				if(categoryManager.checkIsFather(idArr[i])){
    					return idArr[i];
    				}
    				i++;
    			}
    		}
    		else{
    			if(categoryManager.checkIsFather(id)){
					return id;
				}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
	}
}
