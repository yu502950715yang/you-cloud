package com.you.auth.controller;

import com.you.auth.model.LoginForm;
import com.you.auth.model.User;
import com.you.auth.service.LoginService;
import com.you.common.core.model.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description on this file, you will change here.
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-20 13:30
 */
@RestController
public class AuthController {

    private final LoginService loginService;

    public AuthController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody LoginForm loginForm) {
        User user = loginService.login(loginForm.getUsername(), loginForm.getPassword());
        return R.ok(user);
    }
}
