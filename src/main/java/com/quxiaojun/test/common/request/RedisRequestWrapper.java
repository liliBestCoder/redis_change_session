package com.quxiaojun.test.common.request;


import com.quxiaojun.test.common.session.HttpSessionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RedisRequestWrapper extends HttpServletRequestWrapper {
    private static Logger log = LoggerFactory.getLogger(RedisRequestWrapper.class);

    private HttpSession session;

    private HttpServletRequest request;

    private HttpServletResponse response;

    private String sid = "";

    public RedisRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public RedisRequestWrapper(String sid, HttpServletRequest request) {
        super(request);
        this.sid = sid;
    }

    public RedisRequestWrapper(String sid, HttpServletRequest request,
                                     HttpServletResponse response) {
        super(request);
        this.request = request;
        this.response = response;
        this.sid = sid;
        if (this.session == null) {
            this.session = new HttpSessionWrapper(sid, super.getSession(false),
                    request, response);
        }
    }

    @Override
    public HttpSession getSession(boolean create) {
        if (this.session == null) {
            if (create) {
                this.session = new HttpSessionWrapper(this.sid,
                        super.getSession(create), this.request, this.response);
                return this.session;
            } else {
                return null;
            }
        }
        return this.session;
    }

    @Override
    public HttpSession getSession() {
        if (this.session == null) {
            this.session = new HttpSessionWrapper(this.sid, super.getSession(),
                    this.request, this.response);
        }
        return this.session;
    }


}
