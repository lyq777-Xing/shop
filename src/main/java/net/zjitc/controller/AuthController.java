package net.zjitc.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.zjitc.common.Const;
import net.zjitc.common.ResponseResult;
import net.zjitc.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static net.zjitc.common.Const.CAPTCHA_KEY;

@CrossOrigin
@RestController
@Api(tags = "登录验证码",description = "提供验证码API")
public class AuthController extends BaseController{
    @Autowired
    private Producer producer;

    /**
     * 生成图片验证码
     */
//    @PreAuthorize("hasAnyRole()")
    @ApiOperation(value = "获取验证码")
    @GetMapping("/captcha")
    public ResponseResult captcha() throws IOException {
//        生成key
        String key = UUID.randomUUID().toString();
//        生成code(验证码)
        String code = producer.createText();
//        将其导成图片
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image,"jpg",outputStream);

//        将key转化为64位的数据
        String str = "data:image/jpeg;base64,";
        String s = str + Base64Encoder.encode(outputStream.toByteArray());

        if(redisUtils.hmGet(Const.CAPTCHA_KEY,key)!=null){
            redisUtils.hdel(Const.CAPTCHA_KEY,key);
        }
        redisUtils.hmSet(Const.CAPTCHA_KEY,key,code);
        ResponseResult<Object> result = new ResponseResult<>();
        result.Success("获取验证码成功",MapUtil.builder()
                .put("key", key)
                .put("base64Img", s)
                .build());
        return result;
    }
}
