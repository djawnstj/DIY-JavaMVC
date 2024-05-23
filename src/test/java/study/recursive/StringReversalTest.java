package study.recursive;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringReversalTest {
    @Test
    @DisplayName("문자열 뒤집기 테스트")
    public void reverseStringTest() {
        //given
        String input = "hello";

        //when
        String reverseString = reverseString(input); // 구현

        //then
        assertThat(reverseString).isEqualTo("olleh");
    }

    private String reverseString(String input) {

        if (input.length() == 1) {
            return input;
        }

        char ch = input.charAt(input.length() - 1);

        return ch + reverseString(input.substring(0, input.length() - 1));
    }
}
