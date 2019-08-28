package edu.udacity.java.nano.chat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * Login Page
     */
    @GetMapping("/")
    public ModelAndView login() {

        logger.debug("Login page");
        return new ModelAndView("login");
    }

    /**
     * Chatroom Page
     */
    @GetMapping("/index")
    public ModelAndView index(String username, HttpServletRequest request) throws UnknownHostException {

        logger.debug("Login user=" + username);
        return new ModelAndView("chat").addObject("userName",username);
    }

}
