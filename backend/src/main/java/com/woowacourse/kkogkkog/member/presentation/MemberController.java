package com.woowacourse.kkogkkog.member.presentation;

import com.woowacourse.kkogkkog.common.presentation.LoginMemberId;
import com.woowacourse.kkogkkog.member.application.MemberService;
import com.woowacourse.kkogkkog.member.application.dto.MyProfileResponse;
import com.woowacourse.kkogkkog.member.presentation.dto.MemberHistoriesResponse;
import com.woowacourse.kkogkkog.member.presentation.dto.MemberUpdateMeRequest;
import com.woowacourse.kkogkkog.member.presentation.dto.MembersResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MembersResponse> showAll() {
        return ResponseEntity.ok(new MembersResponse(memberService.findAll()));
    }

    @GetMapping("/me")
    public ResponseEntity<MyProfileResponse> showMe(@LoginMemberId Long id) {
        MyProfileResponse memberResponse = memberService.findById(id);

        return ResponseEntity.ok(memberResponse);
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateMe(@LoginMemberId Long id,
                                         @Valid @RequestBody MemberUpdateMeRequest memberUpdateMeRequest) {
        memberService.updateNickname(memberUpdateMeRequest.toMemberUpdateRequest(id));

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/histories")
    public ResponseEntity<MemberHistoriesResponse> showHistory(@LoginMemberId Long id) {
        return ResponseEntity.ok(new MemberHistoriesResponse(memberService.findHistoryById(id)));
    }

    @PutMapping("/me/histories")
    public ResponseEntity<Void> updateAllMemberHistories(@LoginMemberId Long id) {
        memberService.updateAllIsReadMemberHistories(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/me/histories/{historyId}")
    public ResponseEntity<Void> updateMemberHistory(@PathVariable Long historyId) {
        memberService.updateIsReadMemberHistory(historyId);

        return ResponseEntity.noContent().build();
    }
}
