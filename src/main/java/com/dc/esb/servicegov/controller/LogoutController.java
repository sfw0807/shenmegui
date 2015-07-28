package com.dc.esb.servicegov.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by vincentfxz on 15/7/24.
 */
@Controller
@RequestMapping("/logout")
public class LogoutController {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ModelAndView logout() {
        SecurityUtils.getSubject().logout();
        ModelAndView mv = new ModelAndView("/login/login");
        return mv;
    }
}
