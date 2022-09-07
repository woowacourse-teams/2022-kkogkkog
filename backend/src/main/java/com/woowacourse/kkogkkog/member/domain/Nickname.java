package com.woowacourse.kkogkkog.member.domain;

import com.woowacourse.kkogkkog.common.exception.InvalidRequestException;
import java.security.SecureRandom;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Nickname {

    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[가-힣a-zA-Z0-9]{1,6}$");
    private static final SecureRandom RANDOM_GENERATOR = new SecureRandom();
    private static final List<String> ADJECTIVES = List.of(
        "귀여운", "작은", "졸린", "착한", "무서운", "작은", "예쁜", "상냥한", "해로운", "느린", "빠른", "커다란");
    private static final List<String> ANIMALS = List.of(
        "강아지", "다람쥐", "고라니", "고래", "고릴라", "고양이", "곰", "기린", "낙타", "너구리", "노루",
        "늑대", "다람쥐", "당나귀", "돌고래", "돼지", "두더지", "라마", "망아지", "매머드", "멧돼지", "물개",
        "미어캣", "박쥐", "반달곰", "범고래", "북극곰", "불여우", "비버", "보아뱀", "사슴", "산양", "사자",
        "산토끼", "살쾡이", "삵", "생쥐", "송아지", "수달", "순록", "스컹크", "스피츠", "승냥이", "시궁쥐",
        "알파카", "양", "얼룩말", "얼룩소", "여우", "염소", "영양", "오소리", "원숭이","이리", "자칼",
        "재규어", "쥐", "진돗개", "청서", "청설모", "치와와", "치타", "친칠라", "침팬지", "캥거루", "코끼리",
        "코뿔소", "코알라", "토끼", "판다", "표범", "푸들", "하마", "해달", "햄스터", "호랑이", "황소");

    @Column(name = "nickname", nullable = false)
    private String value;

    public Nickname(String value) {
        Matcher matcher = NICKNAME_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new InvalidRequestException("잘못된 닉네임 형식입니다. (한글, 숫자, 영문자로 구성된 1~6글자)");
        }
        this.value = value;
    }

    public static Nickname ofRandom() {
        double randomNum = RANDOM_GENERATOR.nextDouble();
        String adjective = ADJECTIVES.get((int) (ADJECTIVES.size() * randomNum));
        String animal = ANIMALS.get((int) (ANIMALS.size() * randomNum));
        return new Nickname(String.format("%s%s", adjective, animal));
    }
}
