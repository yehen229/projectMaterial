package cn.edu.bistu.cs.projectmaterialmanagement.webserver.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 图片验证码
 */
@Configuration
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha producer() {
        Properties properties = new Properties();

        //图片的宽度
        properties.setProperty("kaptcha.image.width", "100");

        //图片的高度
        properties.setProperty("kaptcha.image.height", "40");

        //字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "32");

        //字体颜色（RGB）
        properties.setProperty("kaptcha.textproducer.font.color", "0,0,0");

        //验证码字符的集合
        properties.setProperty("kaptcha.textproducer.char.string", "1234567890");

        //验证码长度（即在上面集合中随机选取几位作为验证码）
        properties.setProperty("kaptcha.textproducer.char.length", "4");

        //图片的干扰样式
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");

        //图片边框：无，如果为yes的话，则是有边框，则可以使用kaptcha.border.color设置边框颜色
        properties.put("kaptcha.border", "no");


        //文字间隔
        properties.put("kaptcha.textproducer.char.space", "5");

        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
