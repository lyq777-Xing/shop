package net.zjitc.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.introspector.Property;

import java.util.Properties;

@Configuration
public class KaptchaConfig {
    @Bean
    DefaultKaptcha producer(){

    //配置相关信息
    /*        1 kaptcha.border  是否有边框  默认为true  我们可以自己设置yes，no
            2 kaptcha.border.color   边框颜色   默认为Color.BLACK
            3 kaptcha.border.thickness  边框粗细度  默认为1
            4 kaptcha.producer.impl   验证码生成器  默认为DefaultKaptcha
            5 kaptcha.textproducer.impl   验证码文本生成器  默认为DefaultTextCreator
            6 kaptcha.textproducer.char.string   验证码文本字符内容范围  默认为abcde2345678gfynmnpwx
            7 kaptcha.textproducer.char.length   验证码文本字符长度  默认为5
            8 kaptcha.textproducer.font.names    验证码文本字体样式  默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
            9 kaptcha.textproducer.font.size   验证码文本字符大小  默认为40
            10 kaptcha.textproducer.font.color  验证码文本字符颜色  默认为Color.BLACK
            11 kaptcha.textproducer.char.space  验证码文本字符间距  默认为2
            12 kaptcha.noise.impl    验证码噪点生成对象  默认为DefaultNoise
            13 kaptcha.noise.color   验证码噪点颜色   默认为Color.BLACK
            14 kaptcha.obscurificator.impl   验证码样式引擎  默认为WaterRipple
            15 kaptcha.word.impl   验证码文本字符渲染   默认为DefaultWordRenderer
            16 kaptcha.background.impl   验证码背景生成器   默认为DefaultBackground
            17 kaptcha.background.clear.from   验证码背景颜色渐进   默认为Color.LIGHT_GRAY
            18 kaptcha.background.clear.to   验证码背景颜色渐进   默认为Color.WHITE
            19 kaptcha.image.width   验证码图片宽度  默认为200
            20 kaptcha.image.height  验证码图片高度  默认为50*/

        Properties properties = new Properties();
        properties.put("kaptcha.border","no");
        properties.put("kaptcha.textproducer.char.space","4");
        properties.put("kaptcha.image.height","40");
        properties.put("kaptcha.image.width","120");
        properties.put("kaptcha.textproducer.font.size","30");
        Config config = new Config(properties);
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
