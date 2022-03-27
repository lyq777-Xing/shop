package com.example.supermarket.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@Api(tags = "验证码控制器")
public class CaptchaController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @ApiOperation("获取验证码图片")
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response){
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        /**
         * 1.生成随机的验证码的答案,将答案保存到session
         * 2.使用答案生成动态的图片
         * 3.将图片通过response对象写给客户端
         */
        String text = defaultKaptcha.createText();
        System.out.println(text);
        request.getSession().setAttribute("captcha",text);
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream=null;
        try {
            outputStream=response.getOutputStream();
            ImageIO.write(image,"jpg",outputStream);
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
