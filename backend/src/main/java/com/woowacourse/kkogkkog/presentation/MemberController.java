package com.woowacourse.kkogkkog.presentation;

import com.woowacourse.kkogkkog.application.MemberService;
import com.woowacourse.kkogkkog.application.dto.MyProfileResponse;
import com.woowacourse.kkogkkog.auth.presentation.LoginMember;
import com.woowacourse.kkogkkog.presentation.dto.MemberHistoriesResponse;
import com.woowacourse.kkogkkog.presentation.dto.MemberUpdateMeRequest;
import com.woowacourse.kkogkkog.presentation.dto.MembersResponse;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping
    public ResponseEntity<MembersResponse> showAll() {
        return ResponseEntity.ok(new MembersResponse(memberService.findAll()));
    }

    @GetMapping("/me")
    public ResponseEntity<MyProfileResponse> showMe(@LoginMember Long id) {
        MyProfileResponse memberResponse = memberService.findById(id);

        return ResponseEntity.ok(memberResponse);
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateMe(@LoginMember Long id,
                                         @Valid @RequestBody MemberUpdateMeRequest memberUpdateMeRequest) {
        memberService.update(memberUpdateMeRequest.toMemberUpdateRequest(id));

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/histories")
    public ResponseEntity<MemberHistoriesResponse> showHistory(@LoginMember Long id) {
        return ResponseEntity.ok(new MemberHistoriesResponse(memberService.findHistoryById(id)));
    }

    @PutMapping("/me/histories")
    public ResponseEntity<Void> updateAllMemberHistories(@LoginMember Long id) {
        memberService.updateAllIsReadMemberHistories(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/me/histories/{historyId}")
    public ResponseEntity<Void> updateMemberHistory(@PathVariable Long historyId) {
        memberService.updateIsReadMemberHistory(historyId);

        return ResponseEntity.noContent().build();
    }
}
