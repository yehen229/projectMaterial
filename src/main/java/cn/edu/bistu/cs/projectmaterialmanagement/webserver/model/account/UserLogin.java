package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account;

import org.springframework.stereotype.Component;

@Component
public class UserLogin extends User {
    private String captchaCode;
    private String captchaKey;

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }

    public String getCaptchaKey() {
        return captchaKey;
    }

    public void setCaptchaKey(String captchaKey) {
        this.captchaKey = captchaKey;
    }
}
