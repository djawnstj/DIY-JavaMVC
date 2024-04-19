package study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChargeService {
    
    // 로그인 한 이용자가 자신의 페이 계좌에 잔액을 충전할 수 있도록 기능을 구현한다.
    // (페이 계좌는 회원가입 시 자동으로 생성된다고 가정한다.) User 객체 안의 Pay(계좌?, 잔액)

    // Given : 잔액 충전을 요청한 유저를 찾고
    // 유저를 찾기 위한 유저리포지토리? 필요

    private long userNo = 1;
    private long account = 123456789;
    private long initBalance = 0;
    private Pay initPay = new Pay(account, initBalance);

    User user = new User(userNo, initPay);

    // When : 유저를 찾았으면
    // 해당 유저의 계좌에 충전한 금액만큼 잔액을 추가하는 역할

    // Then : 해당 유저의 계좌에 제대로 충전되었는지 확인

    @Test
    void charge(User user, Pay pay, long money) {

        pay.charge(money);
        user.setPay(pay);

        int num1 = 123;
        int num2 = 345;

        Assertions.assertThat(num1 == num2);
    }

}
