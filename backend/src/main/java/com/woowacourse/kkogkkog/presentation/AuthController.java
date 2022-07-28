package com.woowacourse.kkogkkog.presentation;

import com.woowacourse.kkogkkog.application.AuthService;
import com.woowacourse.kkogkkog.application.dto.TokenOAuthResponse;
import com.woowacourse.kkogkkog.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.presentation.dto.TokenRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody TokenRequest tokenRequest) {
        TokenResponse tokenResponse = authService.login(tokenRequest);

        return ResponseEntity.ok(tokenResponse);
    }

    @GetMapping("/slack/login")
    public ResponseEntity<TokenOAuthResponse> loginByOAuth(@RequestParam String code) {
        TokenOAuthResponse tokenResponse = authService.loginByOAuth(code);

        return ResponseEntity.ok(tokenResponse);
    }
}
