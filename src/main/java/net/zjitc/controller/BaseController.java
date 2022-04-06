package net.zjitc.controller;

import net.zjitc.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BaseController {
    @Autowired
    HttpServletRequest request;

    @Autowired
    RedisUtils redisUtils;
}
