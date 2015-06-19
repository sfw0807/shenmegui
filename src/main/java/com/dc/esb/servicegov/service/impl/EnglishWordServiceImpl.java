package com.dc.esb.servicegov.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.EnglishWordDAOImpl;
import com.dc.esb.servicegov.entity.EnglishWord;
import com.dc.esb.servicegov.service.EnglishWordService;

@Service
@Transactional
public class EnglishWordServiceImpl implements EnglishWordService {

	@Autowired
	private EnglishWordDAOImpl englishWordDAOImpl;
	
	public void editEnglishWord(EnglishWord entity) {
		englishWordDAOImpl.save(entity);

	}
	public List<EnglishWord> getEnglishWordByName(String name, String value) {
		return englishWordDAOImpl.findBy(name, value);
	}
	
	public List<EnglishWord> getEnglistWordAll(){
		return englishWordDAOImpl.getAll();
	}
	

	public void removeEnglishWord(String id) {
		englishWordDAOImpl.delete(id);

	}

}
