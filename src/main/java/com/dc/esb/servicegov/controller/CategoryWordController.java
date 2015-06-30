package com.dc.esb.servicegov.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.entity.CategoryWord;
import com.dc.esb.servicegov.service.impl.CategoryWordServiceImpl;

@Controller
@RequestMapping("/categoryWord")
public class CategoryWordController {

    @Autowired
    private CategoryWordServiceImpl categoryWordService;

    @RequestMapping(method = RequestMethod.GET, value = "/getAll", headers = "Accept=application/json")
    public
    @ResponseBody
    List<CategoryWord> getAll() {
        return categoryWordService.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getById/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    CategoryWord getById(@PathVariable(value = "id") String id) {
        return categoryWordService.getById(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getByEnglishWord/{englishWord}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<CategoryWord> getByEnglishWord(@PathVariable(value = "englishWord") String englishWord) {
        return categoryWordService.getByEnglishWord(englishWord);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getByChineseWord/{chineseWord}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<CategoryWord> getByChineseWord(@PathVariable(value = "chineseWord") String chineseWord) {
        return categoryWordService.getByChineseWord(chineseWord);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getByEsglisgAb/{esglisgAb}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<CategoryWord> getByEsglisgAb(@PathVariable(value = "esglisgAb") String esglisgAb) {
        return categoryWordService.getByEsglisgAb(esglisgAb);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getByRemark/{remark}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<CategoryWord> getByRemark(@PathVariable(value = "remark") String remark) {
        return categoryWordService.getByRemark(remark);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getByPotUser/{potUser}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<CategoryWord> getByPotUser(@PathVariable(value = "potUser") String potUser) {
        return categoryWordService.getByPotUser(potUser);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getByPotDate/{potDate}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<CategoryWord> getByPotDate(@PathVariable(value = "potDate") String potDate) {
        return categoryWordService.getByPotDate(potDate);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean add(@RequestBody CategoryWord categoryWord) {
        categoryWordService.save(categoryWord);
        return true;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/modify", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean modify(@RequestBody CategoryWord categoryWord) {
        categoryWordService.save(categoryWord);
        return true;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{Id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean delete(@PathVariable String Id) {
        categoryWordService.deleteById(Id);
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get/EnglishWord/{englishWord}/ChineseWord/{chineseWord}/EsglisgAb/{esglisgAb}/Remark/{remark}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<CategoryWord> getByParams(@PathVariable(value = "englishWord") String englishWord, @PathVariable(value = "chineseWord") String chineseWord, @PathVariable(value = "esglisgAb") String esglisgAb, @PathVariable(value = "remark") String remark) {
        Map<String, String> params = new HashMap<String, String>();
        if (!"isNull".equals(englishWord))
            params.put("englishWord", englishWord);
        if (!"isNull".equals(chineseWord))
            params.put("chineseWord", chineseWord);
        if (!"isNull".equals(esglisgAb))
            params.put("esglisgAb", esglisgAb);
        if (!"isNull".equals(remark))
            params.put("remark", remark);
        List<CategoryWord> words = categoryWordService.findBy(params);
        return words;
    }

}
