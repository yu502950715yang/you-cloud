package com.you.auth.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.you.auth.captcha.RandomImage;
import com.you.auth.captcha.impl.RandomImageImpl;
import com.you.auth.domain.model.LoginForm;
import com.you.auth.domain.model.User;
import com.you.auth.service.LoginService;
import com.you.common.core.constant.CacheConstants;
import com.you.common.core.domain.R;
import com.you.common.core.exception.CommonException;
import com.you.common.core.utils.uuid.UUID;
import com.you.common.redis.service.RedisService;
import com.you.validation.ValidationGroups;
import org.springframework.validation.annotation.Validated;
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
    public R<User> login(@Validated(ValidationGroups.Common.class) @RequestBody LoginForm loginForm) {
        if (loginForm.getVerifyCode() == null || loginForm.getVerifyCode().isEmpty()) {
            throw new CommonException("验证码错误");
        }
        String redisKey = CacheConstants.CAPTCHA_CODE_KEY + loginForm.getVerifyCodeKey();
        Object codeObj = redisService.getCacheObject(redisKey);
        if (codeObj == null) {
            throw new CommonException("验证码错误");
        }
        String redisCode = codeObj.toString();
        if (!loginForm.getVerifyCode().equals(redisCode)) {
            throw new CommonException("验证码错误");
        }
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

    @GetMapping("/logout")
    public R<String> logout() {
        StpUtil.logout();
        return R.ok("登出成功");
    }
}
