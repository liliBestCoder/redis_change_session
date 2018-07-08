package com.quxiaojun.test.common.filter;

import com.quxiaojun.test.common.request.RedisRequestWrapper;
import com.quxiaojun.test.common.util.CookieUtil;
import com.quxiaojun.test.common.constant.GlobalConstant;
import com.quxiaojun.test.common.util.StringUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(RedisRequestWrapper.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        //从cookie中获取sessionId，如果此次请求没有sessionId，重写为这次请求设置一个sessionId
        String sid = CookieUtil.getCookieValue(request, GlobalConstant.JSESSIONID);
        if(StringUtils.isEmpty(sid) || sid.length() != 36){
            sid = StringUtil.getUuid();
            CookieUtil.setCookie(request, response, GlobalConstant.JSESSIONID, sid, 60 * 60);
        }

        //交给自定义的HttpServletRequestWrapper处理
        filterChain.doFilter(new RedisRequestWrapper(sid, request, response), response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
