package com.order.manager.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Api(value = "Swagger order manager", description = "basic order manager", tags = "Orders")
public class HomeController {

    @GetMapping("/")
    @ApiOperation("show the home page of Taco order system")
    public String home(){
        return "home";
    }
}
