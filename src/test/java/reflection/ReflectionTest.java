package reflection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.*;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);



    @Test
    @DisplayName("요구사항 1 - 클래스 정보 출력")
    void showClass() {
        Class<Car> carClass = Car.class;
        // 요구사항 1 - 클래스 정보 출력
        // 필드 생성자 메서드 출력
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
        int testCount = 0;
        int testCase = 2;
        String carName = "k5";
        int carPrice = 10000;
        Car car = new Car(carName, carPrice);

        for (Method method : methods) {
            String name = method.getName();
            if (name.startsWith("test")) {
                Object invoke = method.invoke(car);
                logger.debug(invoke.toString());
                testCount++;
            }
        }

        Assertions.assertThat(testCount).isEqualTo(testCase);
    }

    @Test
    @DisplayName("요구사항 3 - @PrintView 애노테이션 메소드 실행")
    void testPrintViewInterface() throws InvocationTargetException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        Method[] methods = carClass.getDeclaredMethods();
        String carName = "k5";
        int carPrice = 10000;
        int testCase = 0;
        int testAnswer = 1;
        Car car = new Car(carName, carPrice);

        for (Method method : methods) {
            if(method.isAnnotationPresent(PrintView.class)){
                method.invoke(car);
                testCase++;
            }
        }

        Assertions.assertThat(testCase).isEqualTo(testAnswer);
    }

    @Test
    @DisplayName("요구사항 4 - private field에 값 할당")
    void testPrivateFieldAccess() throws IllegalAccessException {
        // 생성자 없이 private 필드에 값 넣기
        // Car.class 로 private 필드의 정보 가져옴
        Class<Car> carClass = Car.class;
        Field[] declaredFields = carClass.getDeclaredFields();
        String carName = "k5";
        int carPrice = 10000;
        // 생성자 X
        Car car = new Car();

        // private 필드에 값 할당
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if (declaredField.getName().equals("name")) {
                declaredField.set(car, carName);
            } else if (declaredField.getName().equals("price")) {
                declaredField.set(car, carPrice);
            }
        }

        // 검증
        Assertions.assertThat(car.getName()).isEqualTo(carName);
        Assertions.assertThat(car.getPrice()).isEqualTo(carPrice);
    }

    @Test
    @DisplayName("요구사항 5 - 인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        Constructor<Car> constructor = carClass.getDeclaredConstructor(String.class, int.class);
        String carName = "k5";
        int carPrice = 10000;
        Car car = constructor.newInstance(carName, carPrice);

        Assertions.assertThat(car.getName()).isEqualTo(carName);
        Assertions.assertThat(car.getPrice()).isEqualTo(carPrice);
    }
}
