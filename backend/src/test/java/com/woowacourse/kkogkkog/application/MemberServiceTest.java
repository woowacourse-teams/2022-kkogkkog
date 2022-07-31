package com.woowacourse.kkogkkog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.infrastructure.MemberCreateResponse;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("가입되지 않은 회원 정보를 받으면, 회원을 저장하고 저장된 Id와 회원가입 여부를 반환한다.")
    void save() {
        SlackUserInfo slackUserInfo = new SlackUserInfo("URookie", "T03LX3C5540", "루키", "image");

        MemberCreateResponse memberCreateResponse = memberService.saveOrFind(slackUserInfo);

        assertAll(
            () -> assertThat(memberCreateResponse.getId()).isNotNull(),
            () -> assertThat(memberCreateResponse.getIsCreated()).isTrue()
        );
    }

    @Test
    @DisplayName("가입된 회원 정보를 받으면, 해당 회원의 Id와 회원가입 여부를 반환한다.")
    void find() {
        SlackUserInfo slackUserInfo = new SlackUserInfo("URookie", "T03LX3C5540", "루키", "image");

        memberService.saveOrFind(slackUserInfo);
        MemberCreateResponse memberCreateResponse = memberService.saveOrFind(slackUserInfo);

        assertAll(
            () -> assertThat(memberCreateResponse.getId()).isNotNull(),
            () -> assertThat(memberCreateResponse.getIsCreated()).isFalse()
        );
    }

    @Test
    @DisplayName("회원 ID를 통해 회원 정보를 조회할 수 있다.")
    void findById() {
        SlackUserInfo slackUserInfo = new SlackUserInfo("URookie", "T03LX3C5540", "루키", "image");
        Long memberId = memberService.saveOrFind(slackUserInfo).getId();

        MemberResponse memberResponse = memberService.findById(memberId);

        assertThat(memberResponse).usingRecursiveComparison().ignoringFields("id").isEqualTo(
            new MemberResponse(null, "URookie", "T03LX3C5540", "루키", "image")
        );
    }

    @Test
    @DisplayName("회원가입된 회원들의 정보를 조회할 수 있다.")
    void findAll() {
        SlackUserInfo rookieUserInfo = new SlackUserInfo("URookie", "T03LX3C5540", "루키", "image");
        SlackUserInfo arthurUserInfo = new SlackUserInfo("UArthur", "T03LX3C5540", "아서", "image");

        memberService.saveOrFind(rookieUserInfo);
        memberService.saveOrFind(arthurUserInfo);

        List<MemberResponse> membersResponse = memberService.findAll();

        assertThat(membersResponse).hasSize(2);
    }
}
