package study.reflection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    String carName = "k5";
    int carPrice = 10000;

    @Test
    @DisplayName("요구사항 1 - 클래스 정보 출력")
    void showClass() {
        Class<Car> carClass = Car.class;
        // 요구사항 1 - 클래스 정보 출력
        logger.debug(Arrays.toString(carClass.getConstructors()));
        logger.debug(Arrays.toString(carClass.getFields()));
        logger.debug(Arrays.toString(carClass.getMethods()));

        Assertions.assertThat(carClass).isNotNull();
    }

    @Test
    @DisplayName("요구사항 2 - test로 시작하는 메소드 실행")
    void testMethodRun() throws IllegalAccessException, InvocationTargetException {
        Class<Car> carClass = Car.class;
        Method[] methods = carClass.getDeclaredMethods();
        Car car = new Car(carName, carPrice);

        Arrays.stream(methods).filter(method -> method.getName().startsWith("test"));

        for (Method method : methods) {
            String name = method.getName();
            if (name.startsWith("test")){
                Object invoke = method.invoke(car);
                assertTrue(invoke.toString().contains("test"));
            }
        }
    }

    @Test
    @DisplayName("요구사항 3 - @PrintView 애노테이션 메소드 실행")
    void testPrintViewInterface() throws InvocationTargetException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        Method[] methods = carClass.getDeclaredMethods();
        Car car = new Car(carName, carPrice);

        for (Method method : methods) {
            boolean isPresent = method.isAnnotationPresent(PrintView.class);
            if(isPresent){
                Object invoke = method.invoke(car);
                logger.debug((String) invoke);
                assertTrue(isPresent);
            }
        }
    }

    @Test
    @DisplayName("요구사항 4 - private field에 값 할당")
    void testPrivateFieldAccess() throws IllegalAccessException {
        Class<Car> carClass = Car.class;
        Field[] declaredFields = carClass.getDeclaredFields();
        Car car = new Car(carName, carPrice);

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("name",carName);
        hashMap.put("price",carPrice);
    }

    @Test
    @DisplayName("요구사항 5 - 인자를 가진 생성자의 인스턴스 생성")
    void testConstructorWithArgs() {
        Class<Car> carClass = Car.class;
        logger.debug(carClass.getName());


    }
}
