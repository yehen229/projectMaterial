package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.SysLogAnnotation;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.Role;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.UserLogin;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.UserLoginResult;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.Log;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IJWTTokenService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IRoleService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log.ILogService;
import com.google.code.kaptcha.Producer;
import io.netty.handler.codec.base64.Base64Encoder;
import org.apache.tomcat.util.codec.binary.Base64;
import jakarta.annotation.Resource;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user/v1")
@EnableMethodSecurity
public class UserController {
    private static final Logger log =
            LoggerFactory.getLogger(UserController.class);

    private final IUserService userService;
    private final IRoleService roleService;
    private final IJWTTokenService ijwtTokenService;

    private AuthenticationManager authenticationManager;
    private Producer producer;
    private final ILogService logService;
    @Resource
    private RedisTemplate<String, String> redisKeyTemplate;


    @Resource
    private RedisTemplate<String, org.springframework.security.core.userdetails.User> redisTemplate;


    UserController(IUserService userService, IRoleService roleService,
                   IJWTTokenService ijwtTokenService,
                   AuthenticationManager authenticationManager,
                   Producer producer,
                   ILogService logService) {
        this.userService = userService;
        this.roleService = roleService;
        this.ijwtTokenService = ijwtTokenService;
        this.authenticationManager = authenticationManager;
        this.producer = producer;
        this.logService = logService;
    }

    @GetMapping("publicKey")
    public String getPublicKey()
            throws NoSuchAlgorithmException {
        return ijwtTokenService.getPublicKey();

    }

    /**
     * 生成验证码
     *
     * @return
     * @throws IOException
     */
    @RequestMapping("captcha.jpg")
    public Map<String, String> captcha()
            throws IOException {

        String key = UUID.randomUUID().toString();

        //生成文字验证码
        String code = producer.createText();

        //生成图片验证码
        BufferedImage image = producer.createImage(code);


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        Base64Encoder encoder = new Base64Encoder();
        String str = "data:image/jpeg;base64,";
        String base64Imag = str + Base64.encodeBase64String(outputStream.toByteArray());


        //120秒有效期
        redisKeyTemplate.opsForValue().set(key, code, 120, TimeUnit.SECONDS);


        //uuid; 唯一标识code
        //code; 验证码图片的Base64串
        Map<String, String> kaptchaVoMap = new HashMap<>();
        kaptchaVoMap.put("key", key);
        kaptchaVoMap.put("code", base64Imag);

        return kaptchaVoMap;

    }


    /**
     * 用户登录
     *
     * @param sysUserLogin
     * @return
     */
    @SysLogAnnotation(opType = ILogService.SYS_LOG_OP_TYPE_LOGIN, opModule = "User")
    @PostMapping(value = "login")
    public UserLoginResult login(@RequestBody UserLogin sysUserLogin)
            throws UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        if (sysUserLogin == null) {
            throw new BusinessException("用户登录信息不全");
        }


        //前端传输过来的密码是加密的(采用RSA加密算法)，需要解密。注意，这个加密和数据库中存放的密码加密不是一回事
        //现在password字段变成了原始密码（明文）
        String rawPassword = ijwtTokenService.decodePwd(sysUserLogin.getPassword());
        // 创建用户名和密码认证令牌
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(sysUserLogin.getUserName(), rawPassword);
        // 执行身份认证
        Authentication authenticate = authenticationManager.authenticate(token);


        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }

        String captchaKey = sysUserLogin.getCaptchaKey();

        String code = redisKeyTemplate.opsForValue().get(captchaKey);
        redisKeyTemplate.delete(captchaKey);

        if (code == null || !code.equalsIgnoreCase(sysUserLogin.getCaptchaCode())) {
            throw new BusinessException("验证码不对");
        }


        //登录成功，返回JWT字符串
        String key = UUID.randomUUID().toString();
        System.out.println(key);

        //将用户信息存入redis中
        org.springframework.security.core.userdetails.User userInfo = (org.springframework.security.core.userdetails.User)
                authenticate.getPrincipal();
        redisTemplate.opsForValue().set(key, userInfo, 60, TimeUnit.HOURS);

        User sysUser = userService.getByUserName(userInfo.getUsername());
        if (sysUser == null) throw new BusinessException("不存在该用户");


        String token1 = ijwtTokenService.getToken(key);
        org.springframework.security.core.userdetails.User userInfo1 = redisTemplate.opsForValue().get(key);  //取缓存
        System.out.println("userInfo：" + userInfo1);

        UserLoginResult sysUserLoginResult = new UserLoginResult();
        sysUserLoginResult.setToken(ijwtTokenService.getToken(key));


        List<Role> sysRoleList = roleService.getRolesByUserId(sysUser.getId());
        if (sysRoleList != null && !sysRoleList.isEmpty()) {
            List<String> stringList = new ArrayList<>();
            for (Role sysRole : sysRoleList)
                stringList.add(sysRole.getName());

            sysUserLoginResult.setRoleList(stringList);
        }


        sysUserLoginResult.setUserName(sysUser.getUserName());
        sysUserLoginResult.setUserRealName(sysUser.getRealName());
        sysUserLoginResult.setUserId(sysUser.getId());

        Log log = new Log(sysUser.getId(), "123", "用户登录", 1, new Date());
        logService.add(log);
        return sysUserLoginResult;
    }

    /**
     * 用户登出
     *
     * @return
     */
    @PostMapping(value = "logout")
    public String logout() {
        return "登出成功";
    }

    @GetMapping(value = "get-by-id")
    public User getById(@RequestParam(value = "id") String id) {
        return userService.getById(id);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody User user) {
        User user1 = userService.getByUserName("admin");
        Log log = new Log(user1.getId(), "123", "添加用户：" + user.getRealName(), 1, new Date());
        logService.add(log);
        return userService.add(user);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody User user) {
        User user1 = userService.getByUserName("admin");
        Log log = new Log(user1.getId(), "123", "删除用户：" + user.getRealName(), 1, new Date());
        logService.add(log);
        return userService.delete(user);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody User user) {
        User user1 = userService.getByUserName("admin");
        Log log = new Log(user1.getId(), "123", "更新用户信息：" + user.getRealName(), 1, new Date());
        logService.add(log);
        return userService.update(user);
    }

    @PostMapping(value = "update-own-info")
    public int updateOwnInfo(@RequestBody User user) {
        Log log = new Log(user.getId(), "123", "更新用户信息：" + user.getRealName(), 1, new Date());
        logService.add(log);
        return userService.updateOwnInfo(user);
    }

    @PostMapping(value = "update-own-pwd")
    public int updateOwnPwd(@RequestBody User user) {
        Log log = new Log(user.getId(), "123", "更改用户密码：" + user.getRealName(), 1, new Date());
        logService.add(log);
        return userService.updateOwnPwd(user);
    }

    @PostMapping(value = "reset-pwd")
    @PreAuthorize("hasAnyRole('Admin','Teacher')")
    public int resetPassword(@RequestBody User user) {
        Log log = new Log(user.getId(), "123", "重置用户密码：" + user.getRealName(), 1, new Date());
        logService.add(log);
        return userService.resetPassword(user);
    }


    @GetMapping(value = "page")
    public Page<User> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return userService.getPage(pageNo, pageSize);
    }

}