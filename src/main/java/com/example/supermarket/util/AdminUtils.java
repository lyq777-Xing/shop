package com.example.supermarket.util;

import com.example.supermarket.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

public class AdminUtils {

    public static Admin getCurrentAdmin() {

        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}