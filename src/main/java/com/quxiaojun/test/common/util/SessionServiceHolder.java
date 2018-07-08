package com.quxiaojun.test.common.util;

import com.quxiaojun.test.common.services.SessionService;

public class SessionServiceHolder {
    public static SessionService SESSION_SERVICE = null;

    public static SessionService getSessionService() {
        return SESSION_SERVICE;
    }

    public static void setSessionService(SessionService sessionService) {
        SESSION_SERVICE = sessionService;
    }
}
