package com.xingoo.teddy.config;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/druid/*",
        initParams = {
                @WebInitParam(name = "allow",value="127.0.0.1"),
                @WebInitParam(name = "deny", value = ""),
                @WebInitParam(name = "loginUsername", value = "test"),
                @WebInitParam(name = "loginPassword", value = "test"),
                @WebInitParam(name = "resetEnable", value = "false")
        })
public class DruidStatViewServlet extends StatViewServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

}
