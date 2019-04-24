package com.codingwolves.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This class essentially handles the get request from client and sends the page
 * to the client
 * @author Reubin George
 * @since 04/19/2019
 * @version 1.0
 */
@Controller
public class HandshakeController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String printWelcome (ModelMap model){
        return "index";
    }
}
