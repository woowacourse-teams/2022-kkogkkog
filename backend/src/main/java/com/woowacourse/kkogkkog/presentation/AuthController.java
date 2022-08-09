package com.woowacourse.kkogkkog.presentation;

import com.woowacourse.kkogkkog.application.AuthService;
import com.woowacourse.kkogkkog.application.WorkspaceService;
import com.woowacourse.kkogkkog.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.presentation.dto.InstallSlackAppRequest;
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
    private final WorkspaceService workspaceService;

    public AuthController(AuthService authService, WorkspaceService workspaceService) {
        this.authService = authService;
        this.workspaceService = workspaceService;
    }

    @GetMapping("/login/token")
    public ResponseEntity<TokenResponse> login(@RequestParam String code) {
        TokenResponse tokenResponse = authService.login(code);

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/install/bot")
    public ResponseEntity<Void> installSlackApp(@RequestBody InstallSlackAppRequest installSlackAppRequest) {
        workspaceService.installSlackApp(installSlackAppRequest.getCode());

        return ResponseEntity.ok().build();
    }
}
