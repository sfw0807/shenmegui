package com.dc.esb.servicegov.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

import com.dc.esb.servicegov.dao.support.Page;
import com.dc.esb.servicegov.dao.support.SearchCondition;
import com.dc.esb.servicegov.entity.EnglishWord;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.dc.esb.servicegov.entity.CategoryWord;
import com.dc.esb.servicegov.service.impl.CategoryWordServiceImpl;
import com.dc.esb.servicegov.util.DateUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/categoryWord")
public class CategoryWordController {

    @Autowired
    private CategoryWordServiceImpl categoryWordService;

    @RequiresPermissions({"metadata-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/getAll", headers = "Accept=application/json")
    public
    @ResponseBody
    Map<String, Object> getAll(HttpServletRequest req) {
        int pageNo = Integer.parseInt(req.getParameter("page"));
        int rowCount = Integer.parseInt(req.getParameter("rows"));
        String englishWord = req.getParameter("englishWord");
        String chineseWord = req.getParameter("chineseWord");
        String esglisgAb = req.getParameter("esglisgAb");
        String remark = req.getParameter("remark");

        Page page = categoryWordService.getAll(rowCount);
        page.setPage(pageNo);
        List<SearchCondition> searchConds = new ArrayList<SearchCondition>();
        StringBuffer hql = new StringBuffer("select c from CategoryWord c where 1=1 ");
        if (null != englishWord && !"".equals(englishWord)) {
            hql.append(" and englishWord like ?");
            searchConds.add(new SearchCondition("englishWord", "%" + englishWord + "%"));
        }
        if (null != chineseWord && !"".equals(chineseWord)) {
            hql.append(" and chineseWord like ?");
            try {
                chineseWord = URLDecoder.decode(chineseWord, "utf-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            searchConds.add(new SearchCondition("chineseWord", "%" + chineseWord + "%"));
        }
        if (null != esglisgAb && !"".equals(esglisgAb)) {
            hql.append(" and esglisgAb like ?");
            searchConds.add(new SearchCondition("esglisgAb", "%" + esglisgAb + "%"));
        }
        if (null != remark && !"".equals(remark)) {
            hql.append(" and remark like ?");
            searchConds.add(new SearchCondition("remark", "%" + remark + "%"));
        }
        List<CategoryWord> list = categoryWordService.findBy(hql.toString(), page,searchConds);
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("total", page.getResultCount());
        map.put("rows", list);
        return map;
    }

    @RequiresPermissions({"metadata-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/getById/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    CategoryWord getById(@PathVariable(value = "id") String id) {
        return categoryWordService.getById(id);
    }

    @RequiresPermissions({"metadata-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/getByEnglishWord/{englishWord}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<CategoryWord> getByEnglishWord(@PathVariable(value = "englishWord") String englishWord) {
        return categoryWordService.getByEnglishWord(englishWord);
    }

    @RequiresPermissions({"metadata-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/getByChineseWord/{chineseWord}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<CategoryWord> getByChineseWord(@PathVariable(value = "chineseWord") String chineseWord) {
        return categoryWordService.getByChineseWord(chineseWord);
    }

    @RequiresPermissions({"metadata-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/getByEsglisgAb/{esglisgAb}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<CategoryWord> getByEsglisgAb(@PathVariable(value = "esglisgAb") String esglisgAb) {
        return categoryWordService.getByEsglisgAb(esglisgAb);
    }

    @RequiresPermissions({"metadata-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/getByRemark/{remark}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<CategoryWord> getByRemark(@PathVariable(value = "remark") String remark) {
        return categoryWordService.getByRemark(remark);
    }

    @RequiresPermissions({"metadata-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/getByPotUser/{potUser}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<CategoryWord> getByPotUser(@PathVariable(value = "potUser") String potUser) {
        return categoryWordService.getByPotUser(potUser);
    }

    @RequiresPermissions({"metadata-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/getByPotDate/{potDate}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<CategoryWord> getByPotDate(@PathVariable(value = "potDate") String potDate) {
        return categoryWordService.getByPotDate(potDate);
    }

    @RequiresPermissions({"metadata-add"})
    @RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean add(@RequestBody CategoryWord categoryWord) {
    	categoryWord.setOptDate(DateUtils.format(new Date()));
        categoryWordService.save(categoryWord);
        return true;
    }

    @RequiresPermissions({"metadata-update"})
    @RequestMapping(method = RequestMethod.POST, value = "/modify", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean modify(@RequestBody CategoryWord categoryWord) {
        categoryWordService.save(categoryWord);
        return true;
    }

    @RequiresPermissions({"metadata-delete"})
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{Id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean delete(@PathVariable String Id) {
        categoryWordService.deleteById(Id);
        return true;
    }

    @RequiresPermissions({"metadata-get"})
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

    @RequiresPermissions({"metadata-get"})
    @RequestMapping(method = RequestMethod.POST, value = "/query", headers = "Accept=application/json")
    public @ResponseBody Map<String, Object> query(@RequestBody Map<String, String> params){
        List<CategoryWord> rows = categoryWordService.findLikeAnyWhere(params);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", rows.size());
        result.put("rows", rows);
        return result;
    }

    @RequiresPermissions({"metadata-add"})
    @RequestMapping(method = RequestMethod.POST, value = "/saveCategoryWord", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean saveCategoryWord(@RequestBody List list) {
        for (int i = 0; i < list.size(); i++) {
            LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) list.get(i);
            Set<String> keySet = map.keySet();
            CategoryWord categoryWord = new CategoryWord();
            categoryWord.setId(map.get("id"));
            categoryWord.setChineseWord(map.get("chineseWord"));
            categoryWord.setEnglishWord(map.get("englishWord"));
            categoryWord.setEsglisgAb(map.get("esglisgAb"));
            categoryWord.setRemark(map.get("remark"));
            categoryWord.setOptDate(DateUtils.format(new Date()));
            categoryWord.setOptUser(SecurityUtils.getSubject().getPrincipal().toString());
            categoryWordService.save(categoryWord);
        }
        return true;
    }

    @RequiresPermissions({"metadata-delete"})
    @RequestMapping(method = RequestMethod.POST, value = "/deleteCategoryWord", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean deleteCategoryWord(@RequestBody List list) {
        for (int i = 0; i < list.size(); i++) {
            LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) list.get(i);
            Set<String> keySet = map.keySet();
            String id = map.get("id");
            categoryWordService.deleteById(id);
        }
        return true;
    }

    @ExceptionHandler({UnauthenticatedException.class, UnauthorizedException.class})
    public String processUnauthorizedException() {
        return "403";
    }

}
