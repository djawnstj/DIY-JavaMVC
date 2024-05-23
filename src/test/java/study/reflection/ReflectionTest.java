package study.reflection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.reflection.code.Car;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        // 요구사항 1. Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력
        //given
        Class<Car> carClass = Car.class;

        //when
        String name = carClass.getName();
        logger.debug(name);
        Field[] fields = carClass.getDeclaredFields();
        logger.debug("Field : {}", Arrays.toString(fields));
        Method[] methods = carClass.getDeclaredMethods();
        logger.debug("Method : {}", Arrays.toString(methods));
        Constructor<?>[] constructors = carClass.getDeclaredConstructors();
        logger.debug("Constructor : {}", (Object) constructors);

        //then
        assertThat(name).isEqualTo("Car");
        assertThat(fields).isNotEmpty();
        assertThat(methods).isNotEmpty();
        assertThat(constructors).isNotEmpty();
    }
}
