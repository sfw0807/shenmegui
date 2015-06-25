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

import com.dc.esb.servicegov.entity.EnglishWord;
import com.dc.esb.servicegov.service.impl.EnglishWordServiceImpl;

@Controller
@RequestMapping("/englishWord")
public class EnglishWordController {

    @Autowired
    private EnglishWordServiceImpl englishWordService;

    @RequestMapping(method = RequestMethod.GET, value = "/getAll", headers = "Accept=application/json")
    public
    @ResponseBody
    List<EnglishWord> getAll() {
        List<EnglishWord> words = englishWordService.getEnglistWordAll();
        return words;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getById/{value}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<EnglishWord> getEnglishWordById(@PathVariable String value) {
        String name = "id";
        List<EnglishWord> words = englishWordService.getEnglishWordByName(name, value);
        return words;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getByEnglishWord/{value}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<EnglishWord> getEnglishWordByEnglishWord(@PathVariable String value) {
        String name = "englishWord";
        List<EnglishWord> words = englishWordService.getEnglishWordByName(name, value);
        return words;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getByWordAb/{value}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<EnglishWord> getEnglishWordByWordAb(@PathVariable String value) {
        String name = "wordAb";
        List<EnglishWord> words = englishWordService.getEnglishWordByName(name, value);
        return words;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getByChineseWord/{value}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<EnglishWord> getEnglishWordByChineseWord(@PathVariable String value) {
        String name = "chineseWord";
        List<EnglishWord> words = englishWordService.getEnglishWordByName(name, value);
        return words;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getByPotUser/{value}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<EnglishWord> getEnglishWordByPotUser(@PathVariable String value) {
        String name = "potUser";
        List<EnglishWord> words = englishWordService.getEnglishWordByName(name, value);
        return words;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getByPotDate/{value}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<EnglishWord> getEnglishWordByPotDate(@PathVariable String value) {
        String name = "potDate";
        List<EnglishWord> words = englishWordService.getEnglishWordByName(name, value);
        return words;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getByRemark/{value}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<EnglishWord> getEnglishWordByRemark(@PathVariable String value) {
        String name = "remark";
        List<EnglishWord> words = englishWordService.getEnglishWordByName(name, value);
        return words;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get/EnglishWord/{englishWord}/ChineseWord/{chineseWord}/WordAb/{wordAb}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<EnglishWord> getEnglishWordByRemark(@PathVariable(value = "englishWord") String englishWord, @PathVariable(value = "chineseWord") String chineseWord, @PathVariable(value = "wordAb") String wordAb) {
        Map<String, String> params = new HashMap<String, String>();
        if (!"itisanuniquevaluethatneverbeexisted".equals(englishWord))
            params.put("englishWord", englishWord);
        if (!"itisanuniquevaluethatneverbeexisted".equals(chineseWord))
            params.put("chineseWord", chineseWord);
        if (!"itisanuniquevaluethatneverbeexisted".equals(wordAb))
            params.put("wordAb", wordAb);
        List<EnglishWord> words = englishWordService.findBy(params);
        return words;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean save(@RequestBody
                 EnglishWord word) {
        englishWordService.editEnglishWord(word);
        return true;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/modify", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean modify(@RequestBody
                   EnglishWord word) {
        englishWordService.editEnglishWord(word);
        return true;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean delete(
            @PathVariable String id) {
        englishWordService.removeEnglishWord(id);
        return true;
    }


}
