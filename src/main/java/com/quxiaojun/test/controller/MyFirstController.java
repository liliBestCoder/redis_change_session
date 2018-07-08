package com.quxiaojun.test.controller;


import com.quxiaojun.test.common.services.SessionService;
import com.quxiaojun.test.common.util.CookieUtil;
import com.quxiaojun.test.common.constant.GlobalConstant;
import com.quxiaojun.test.common.util.SessionServiceHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/session")
public class MyFirstController {

    @RequestMapping("/show")
    @ResponseBody
    public Map<String, Object> sayHello(HttpServletRequest request){
        //local
        HttpSession session = request.getSession();
        session.setAttribute("age","28");
        session.setAttribute("weight","78kg");

        //redis
        SessionService sessionService = SessionServiceHolder.getSessionService();
        String sId = CookieUtil.getCookieValue(request, GlobalConstant.JSESSIONID);
        Map<String, Object> sessionMap = sessionService.getSession(sId);
        return sessionMap;
    }
}
