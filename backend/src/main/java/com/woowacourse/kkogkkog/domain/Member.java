package com.woowacourse.kkogkkog.domain;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[가-힣a-zA-Z0-9]{2,6}$");
    private static final SecureRandom RANDOM_GENERATOR = new SecureRandom();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private MasterMember masterMember;

    @Column(nullable = false, unique = true)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String imageUrl;

    public Member(Long id, MasterMember masterMember, String userId, Workspace workspace,
                  String nickname, String email, String imageUrl) {
        this.id = id;
        this.masterMember = masterMember;
        this.userId = userId;
        this.workspace = workspace;
        this.nickname = nickname;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    // TODO: 테스트 깨지기 때문에 임시로 생성. Fixture 사용하여 기본 생성자로 대체하기
    public Member(Long id, String userId, Workspace workspace, String nickname, String email,
                  String imageUrl) {
        this(id, null, userId, workspace, nickname, email, imageUrl);
    }

    public static Member ofRandomNickname(String userId, MasterMember masterMember,
                                          Workspace workspace, String email, String imageUrl) {
        String randomNum = String.valueOf(RANDOM_GENERATOR.nextDouble());
        String nickname = String.format("익명%s", randomNum.substring(2, 6));
        return new Member(null, masterMember, userId, workspace, nickname, email, imageUrl);
    }

    public void updateNickname(String nickname) {
        Matcher matcher = NICKNAME_PATTERN.matcher(nickname);
        if (!matcher.matches()) {
            throw new InvalidRequestException("잘못된 닉네임 형식입니다. (한글, 숫자, 영문자로 구성된 2~6글자)");
        }
        this.nickname = nickname;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateImageURL(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
