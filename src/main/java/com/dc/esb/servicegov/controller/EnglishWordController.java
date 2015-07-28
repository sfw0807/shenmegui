package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.dao.support.Page;
import com.dc.esb.servicegov.entity.EnglishWord;
import com.dc.esb.servicegov.service.impl.EnglishWordServiceImpl;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/englishWord")
public class EnglishWordController {

    @Autowired
    private EnglishWordServiceImpl englishWordService;

    @RequiresPermissions({"metadata-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/getAll", headers = "Accept=application/json")
    public
    @ResponseBody
    Map<String, Object> getAll(@RequestParam("page") int pageNo, @RequestParam("rows") int rowCount) {
        Page page = englishWordService.getAll(rowCount);
        page.setPage(pageNo);
        List<EnglishWord> rows = englishWordService.getAll(page);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", page.getResultCount());
        result.put("rows", rows);
        return result;
    }

    @RequiresPermissions({"metadata-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/getById/{Id}", headers = "Accept=application/json")
    public
    @ResponseBody
    EnglishWord getEnglishWordById(@PathVariable(value = "Id") String Id) {
        return englishWordService.getById(Id);
    }

    @RequiresPermissions({"metadata-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/getByEnglishWord/{value}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<EnglishWord> getEnglishWordByEnglishWord(@PathVariable String value) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("englishWord", value);
        List<EnglishWord> words = englishWordService.findBy(params);
        return words;
    }

    @RequiresPermissions({"metadata-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/getByWordAb/{value}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<EnglishWord> getEnglishWordByWordAb(@PathVariable String value) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("wordAb", value);
        List<EnglishWord> words = englishWordService.findBy(params);
        return words;
    }

    @RequiresPermissions({"metadata-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/getByChineseWord/{value}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<EnglishWord> getEnglishWordByChineseWord(@PathVariable String value) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("chineseWord", value);
        List<EnglishWord> words = englishWordService.findBy(params);
        return words;
    }

    @RequiresPermissions({"metadata-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/getByPotUser/{value}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<EnglishWord> getEnglishWordByPotUser(@PathVariable String value) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("potUser", value);
        List<EnglishWord> words = englishWordService.findBy(params);
        return words;
    }

    @RequiresPermissions({"metadata-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/getByPotDate/{value}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<EnglishWord> getEnglishWordByPotDate(@PathVariable String value) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("potDate", value);
        List<EnglishWord> words = englishWordService.findBy(params);
        return words;
    }

    @RequiresPermissions({"metadata-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/getByRemark/{value}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<EnglishWord> getEnglishWordByRemark(@PathVariable String value) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("remark", value);
        List<EnglishWord> words = englishWordService.findBy(params);
        return words;
    }

    @RequiresPermissions({"metadata-get"})
    @RequestMapping(method = RequestMethod.POST, value = "/query", headers = "Accept=application/json" )
    @ResponseBody
    public Map<String, Object> query(@RequestBody Map<String, String> params) {
        List<EnglishWord> rows = englishWordService.findLikeAnyWhere(params);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", rows.size());
        result.put("rows", rows);
        return result;
    }

    @RequiresPermissions({"metadata-add"})
    @RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean save(@RequestBody
                 EnglishWord word) {
        englishWordService.save(word);
        return true;
    }

    @RequiresPermissions({"metadata-update"})
    @RequestMapping(method = RequestMethod.POST, value = "/modify", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean modify(@RequestBody
                   EnglishWord word) {
        englishWordService.save(word);
        return true;
    }

    @RequiresPermissions({"metadata-delete"})
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean delete(
            @PathVariable String id) {
        englishWordService.deleteById(id);
        return true;
    }

    @ExceptionHandler({UnauthenticatedException.class, UnauthorizedException.class})
    public String processUnauthorizedException() {
        return "403";
    }
}
