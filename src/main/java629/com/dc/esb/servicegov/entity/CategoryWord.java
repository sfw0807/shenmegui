package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CATEGORY_WORD")
public class CategoryWord {
	@Id
    @Column(name = "ID")
	private String id;
	
    @Column(name = "ENGLISH_WORD")
	private String englishWord;
	
	
    @Column(name = "CHINESE_WORD")
	private String chineseWord;

    @Column(name = "ESGLISG_AB")
	private String esglisgAb;
	
	@Column(name = "REMARK")
	private String remark;
	 
	@Column(name = "OPT_USER")
	private String potUser;
	
	@Column(name = "OPT_DATE")
	private String potDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEnglishWord() {
		return englishWord;
	}

	public void setEnglishWord(String englishWord) {
		this.englishWord = englishWord;
	}

	public String getChineseWord() {
		return chineseWord;
	}

	public void setChineseWord(String chineseWord) {
		this.chineseWord = chineseWord;
	}

	public String getEsglisgAb() {
		return esglisgAb;
	}

	public void setEsglisgAb(String esglisgAb) {
		this.esglisgAb = esglisgAb;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPotUser() {
		return potUser;
	}

	public void setPotUser(String potUser) {
		this.potUser = potUser;
	}

	public String getPotDate() {
		return potDate;
	}

	public void setPotDate(String potDate) {
		this.potDate = potDate;
	}
	 
	
	
}
