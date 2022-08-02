package com.woowacourse.kkogkkog.presentation;

import com.woowacourse.kkogkkog.application.MemberService;
import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.presentation.dto.SuccessResponse;
import com.woowacourse.kkogkkog.presentation.dto.MemberHistoriesResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> showMe(@LoginMember Long id) {
        MemberResponse memberResponse = memberService.findById(id);

        return ResponseEntity.ok(memberResponse);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<MemberResponse>>> showAll() {
        return ResponseEntity.ok(new SuccessResponse<>(memberService.findAll()));
    }

    @GetMapping("/me/history")
    public ResponseEntity<MemberHistoriesResponse> showHistory(@LoginMember Long id) {
        return ResponseEntity.ok(new MemberHistoriesResponse(memberService.findHistoryById(id)));
    }
}
