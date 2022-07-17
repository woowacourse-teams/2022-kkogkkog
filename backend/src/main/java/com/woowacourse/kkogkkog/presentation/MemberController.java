package com.woowacourse.kkogkkog.presentation;

import com.woowacourse.kkogkkog.application.MemberService;
import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.application.dto.MembersResponse;
import com.woowacourse.kkogkkog.presentation.dto.MemberCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody MemberCreateRequest memberCreateRequest) {
        memberService.save(memberCreateRequest);

        return ResponseEntity.created(null).build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> showMe(@LoginMember Long id) {
        MemberResponse memberResponse = memberService.findById(id);

        return ResponseEntity.ok(memberResponse);
        
    @GetMapping
    public ResponseEntity<MembersResponse> showAll() {
        MembersResponse membersResponse = memberService.findAll();

        return ResponseEntity.ok(membersResponse);
    }
}
