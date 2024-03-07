package com.you.system.controller;

import com.you.common.core.domain.R;
import com.you.system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/permission/{userId}")
    public R<List<String>> getPermissions(@PathVariable Long userId) {
        return R.ok(authService.getPermissions(userId));
    }

    @GetMapping("/role/{userId}")
    public R<List<String>> getRoles(@PathVariable Long userId) {
        return R.ok(authService.getRoles(userId));
    }
}
