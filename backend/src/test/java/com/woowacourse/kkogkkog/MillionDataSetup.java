package com.woowacourse.kkogkkog;

import com.woowacourse.kkogkkog.coupon.domain.Coupon2;
import com.woowacourse.kkogkkog.coupon.domain.CouponEventType;
import com.woowacourse.kkogkkog.coupon.domain.CouponHistory2;
import com.woowacourse.kkogkkog.coupon.domain.CouponState;
import com.woowacourse.kkogkkog.coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Nickname;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.member.domain.WorkspaceUser;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.member.domain.repository.WorkspaceUserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MillionDataSetup {

    private static final int MILLION = 1000000;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private WorkspaceUserRepository workspaceUserRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    protected NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    @Transactional
    @Rollback(false)
    void saveCouponData() {
        String sql =
            "INSERT INTO coupon(sender_member_id, receiver_member_id, description, hashtag, coupon_type, coupon_status, meeting_date, created_time, updated_time) "
                + "VALUES (:senderId, :receiverId, :couponMessage, :couponTag, :couponType, :couponStatus, :meetingDate, :createdTime, :updatedTime)";
        jdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(saveCoupons()));

        String sql2 =
            "INSERT INTO member_history(host_member_id, target_member_id, coupon_id, coupon_event, meeting_date, message, is_read, created_time) "
                + "VALUES (:hostMemberId, :targetMemberId, :couponId, :couponEventType, :meetingDate, :message, :isRead, :createdTime)";
        jdbcTemplate.batchUpdate(sql2, SqlParameterSourceUtils.createBatch(saveCouponHistories()));
    }

    @Test
    @Transactional
    @Rollback(false)
    void saveMillionUsers() {
        for (int i = 0; i < MILLION; i++) {
            Workspace workspace = saveWorkspace(i);
            Member member = saveMember(workspace);
            saveWorkspaceUser(workspace, member);
        }
    }

    private Workspace saveWorkspace(int i) {
        String workspaceId = UUID.randomUUID().toString();
        String name = String.format("Workspace Name %s", workspaceId);
        String accessToken = null;
        if (i % 2 == 0) {
            accessToken = String.format("xoxb-%s", workspaceId);
        }
        return workspaceRepository.save(
            new Workspace(null, workspaceId, name, accessToken));
    }

    private Member saveMember(Workspace workspace) {
        String userId = UUID.randomUUID().toString();
        Nickname nickname = Nickname.ofRandom();
        String email = String.format("%s@gmail.com", userId);
        String imageUrl = String.format("http:/%s.com", userId);
        return memberRepository.save(new Member(userId, workspace, nickname, email, imageUrl));
    }

    private void saveWorkspaceUser(Workspace workspace, Member member) {
        workspaceUserRepository.save(new WorkspaceUser(member, member.getUserId(),
            workspace, member.getNickname(), member.getEmail(), member.getImageUrl()));
    }

    private List<Coupon2> saveCoupons() {
        List<Coupon2> coupons = new ArrayList<>();
        for (int i = 0; i < MILLION; i++) {
            Long senderId = (Math.round(i / 10) * 10L) + 1;
            Long receiverId = i + 1L;
            String description = "쿠폰 설명입니다.";
            String hashTag = getHashTag(i);
            CouponType couponType = getCouponType(i);
            coupons.add(new Coupon2(senderId, receiverId, description, hashTag, couponType,
                getCouponState(i)));
        }
        return coupons;
    }

    private String getHashTag(int i) {
        if (i % 4 == 0) {
            return "고마워요";
        }
        if (i % 4 == 1) {
            return "즐코!";
        }
        if (i % 4 == 2) {
            return "행복해요!";
        }
        return "축하해요";
    }

    private CouponType getCouponType(int i) {
        if (i % 3 == 0) {
            return CouponType.COFFEE;
        }
        if (i % 3 == 1) {
            return CouponType.MEAL;
        }
        return CouponType.DRINK;
    }

    private CouponState getCouponState(int i) {
        if (i % 100 < 50) {
            return new CouponState(CouponStatus.READY, null);
        }
        LocalDateTime meetingDate = LocalDate.of(2023, 1,
            (int) Math.round(1 + (Math.random() * 20))).atStartOfDay();
        if (i % 100 < 60) {
            return new CouponState(CouponStatus.REQUESTED, meetingDate);
        }
        if (i % 100 < 80) {
            return new CouponState(CouponStatus.ACCEPTED, meetingDate);
        }
        return new CouponState(CouponStatus.FINISHED, meetingDate);
    }

    private List<CouponHistory2> saveCouponHistories() {
        List<CouponHistory2> couponHistories = new ArrayList<>();
        for (int i = 0; i < MILLION; i++) {
            Long hostId = (Math.round(i / 80) * 80L) + 1L;
            Long targetId = i + 1L;
            Long couponId = i + 1L;
            couponHistories.add(new CouponHistory2(
                hostId, targetId, couponId, getCouponEventType(i), null, "쿠폰 메시지", getIsRead(i)));
        }
        return couponHistories;
    }

    private CouponEventType getCouponEventType(int i) {
        if (i % 150 < 100) {
            return CouponEventType.INIT;
        }
        return CouponEventType.ACCEPT;
    }

    private boolean getIsRead(int i) {
        if (i % 200 < 150) {
            return false;
        }
        return true;
    }
}
