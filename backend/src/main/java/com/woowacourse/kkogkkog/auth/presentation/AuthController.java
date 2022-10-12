package com.woowacourse.kkogkkog.auth.presentation;

import com.woowacourse.kkogkkog.auth.application.AuthService;
import com.woowacourse.kkogkkog.auth.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.auth.presentation.dto.InstallSlackAppRequest;
import com.woowacourse.kkogkkog.member.application.dto.MemberCreateResponse;
import com.woowacourse.kkogkkog.member.presentation.dto.MemberCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/token")
    public ResponseEntity<TokenResponse> login(@RequestParam String code) {
        TokenResponse tokenResponse = authService.login(code);

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/install/bot")
    public ResponseEntity<Void> installSlackApp(@RequestBody InstallSlackAppRequest installSlackAppRequest) {
        authService.installSlackApp(installSlackAppRequest.getCode());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup/token")
    public ResponseEntity<MemberCreateResponse> save(@RequestBody MemberCreateRequest memberCreateRequest) {
        Long id = authService.signUp(memberCreateRequest);
        MemberCreateResponse memberCreateResponse = authService.loginByMemberId(id);

        return ResponseEntity.created(null).body(memberCreateResponse);
    }

    @GetMapping("/login/google")
    public ResponseEntity<TokenResponse> googleLogin(@RequestParam String code) {
        TokenResponse tokenResponse = authService.loginGoogle(code);

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/signup/google")
    public ResponseEntity<MemberCreateResponse> saveGoogle(
        @RequestBody MemberCreateRequest memberCreateRequest) {
        Long id = authService.signUpGoogle(memberCreateRequest);
        MemberCreateResponse memberCreateResponse = authService.loginByMemberId(id);

        return ResponseEntity.created(null).body(memberCreateResponse);
    }
}
