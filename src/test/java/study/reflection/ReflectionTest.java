package study.reflection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.reflection.code.Car;

import java.util.Arrays;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        // 요구사항 1. Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력
        Class<Car> carClass = Car.class;
        logger.debug(carClass.getName());
        logger.debug("Field : {}", Arrays.toString(carClass.getDeclaredFields()));
        logger.debug("Method : {}", Arrays.toString(carClass.getDeclaredMethods()));
        logger.debug("Constructor : {}", (Object) carClass.getDeclaredConstructors());
    }
}
