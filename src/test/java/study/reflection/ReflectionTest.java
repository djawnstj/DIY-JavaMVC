package study.reflection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;


public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        // 요구사항 1 - 클래스 정보 출력
        logger.debug(Arrays.toString(carClass.getConstructors()));
        logger.debug(Arrays.toString(carClass.getFields()));
        logger.debug(Arrays.toString(carClass.getMethods()));

        Assertions.assertThat(carClass).isNotNull();
    }

    @Test
    void testMethodRun() throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<Car> carClass = Car.class;
        Method[] declaredMethods = carClass.getDeclaredMethods();

        for (Method declaredMethod : declaredMethods) {
            String name = declaredMethod.getName();
            if (name.startsWith("test")){
                Object invoke = declaredMethod.invoke(carClass.newInstance());
                logger.debug(invoke.toString());
            }
        }


    }
}
