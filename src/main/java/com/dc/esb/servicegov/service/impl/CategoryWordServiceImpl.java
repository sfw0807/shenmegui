package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.CategoryWordDAOImpl;
import com.dc.esb.servicegov.entity.CategoryWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CategoryWordServiceImpl {
	
	@Autowired
	private CategoryWordDAOImpl categoryWordDAOImpl;
		
	public List<CategoryWord> getAllCategory(){
		return categoryWordDAOImpl.getAll();
	}
	
	public List<CategoryWord> getById(String Id){
		Map<String, String> params = new HashMap<String, String>();
		params.put("Id", Id);
		return categoryWordDAOImpl.findBy(params);	
	}
	
	public List<CategoryWord> getByEnglishWord(String englishWord) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("englishWord", englishWord);
		return categoryWordDAOImpl.findBy(params);	
	}

	public List<CategoryWord> getByChineseWord(String chineseWord) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("chineseWord", chineseWord);
		return categoryWordDAOImpl.findBy(params);
	}


	public List<CategoryWord> getByEsglisgAb(String esglisgAb) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("esglisgAb", esglisgAb);
		return categoryWordDAOImpl.findBy(params);
	}


	public List<CategoryWord> getByRemark(String remark) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("remark", remark);
		return categoryWordDAOImpl.findBy(params);
	}

	public List<CategoryWord> getByPotUser(String potUser) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("potUser", potUser);
		return categoryWordDAOImpl.findBy(params);
	}

	public List<CategoryWord> getByPotDate(String potDate) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("potDate", potDate);
		return categoryWordDAOImpl.findBy(params);
	}
	public boolean addCategoryWord(CategoryWord categoryWord){		
		categoryWordDAOImpl.save(categoryWord);
		return true;
	}
	
	public boolean modifyCategoryWord(CategoryWord categoryWord){
		categoryWordDAOImpl.save(categoryWord);	
		return true;
	}
	
	public void deleteCategoryWord(String id){
		categoryWordDAOImpl.delete(id);
	}
}
