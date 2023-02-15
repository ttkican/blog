package com.ican.utils;


import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;

import java.util.HashMap;
import java.util.Map;

/**
 * 浏览器工具
 *
 * @author ican
 */

public class UserAgentUtils {

    private static final UserAgentAnalyzer USER_AGENT_ANALYZER;

    static {
        USER_AGENT_ANALYZER = UserAgentAnalyzer
                .newBuilder()
                .hideMatcherLoadStats()
                .withField(UserAgent.OPERATING_SYSTEM_NAME_VERSION_MAJOR)
                .withField(UserAgent.AGENT_NAME_VERSION)
                .build();
    }

    /**
     * 从User-Agent解析客户端操作系统和浏览器版本
     */
    public static Map<String, String> parseOsAndBrowser(String userAgent) {
        UserAgent agent = USER_AGENT_ANALYZER.parse(userAgent);
        String os = agent.getValue(UserAgent.OPERATING_SYSTEM_NAME_VERSION_MAJOR);
        String browser = agent.getValue(UserAgent.AGENT_NAME_VERSION);
        Map<String, String> map = new HashMap<>(2);
        map.put("os", os);
        map.put("browser", browser);
        return map;
    }

}