package com.you.auth.controller;

import com.you.auth.captcha.RandomImage;
import com.you.auth.captcha.impl.RandomImageImpl;
import com.you.auth.model.LoginForm;
import com.you.auth.model.User;
import com.you.auth.service.LoginService;
import com.you.common.core.constant.CacheConstants;
import com.you.common.core.model.R;
import com.you.common.core.utils.uuid.UUID;
import com.you.common.redis.service.RedisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 登录相关接口
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-20 13:30
 */
@RestController
public class AuthController {

    private final LoginService loginService;
    private final RedisService redisService;

    public AuthController(LoginService loginService, RedisService redisService) {
        this.loginService = loginService;
        this.redisService = redisService;
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody LoginForm loginForm) {
        User user = loginService.login(loginForm.getUsername(), loginForm.getPassword());
        return R.ok(user);
    }

    @GetMapping("/verifyCode")
    public void captcha(HttpServletResponse response) throws IOException {
        // 禁止缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.addHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        RandomImage randomImage = new RandomImageImpl(4, 125, 50);
        String code = randomImage.getValidateString();
        String uuid = UUID.randomUUID().toString();
        String redisKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        // 验证码以key，value的性质缓存到redis中，时间为两分钟
        redisService.setCacheObject(redisKey, code, CacheConstants.CAPTCHA_EXPIRATION, TimeUnit.SECONDS);
        Cookie cookie = new Cookie("verifyCodeKey", uuid);
        cookie.setPath("/");
        cookie.setMaxAge(Math.toIntExact(CacheConstants.CAPTCHA_EXPIRATION));
        /*key写入cookie，验证时获取*/
        response.addCookie(cookie);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(randomImage.getValidateImage(), "jpeg", outputStream);
        outputStream.flush();
        outputStream.close();
    }
}
