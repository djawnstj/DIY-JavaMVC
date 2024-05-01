package study.reflection;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.*;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {

        String carClassName = "study.reflection.Car";
        String carField1 = "name";
        String carField2 = "price";
        String carMethod1 = "getName";
        String carMethod2 = "printView";
        String carMethod3 = "getPrice";
        String carMethod4 = "testGetPrice";
        String carMethod5 = "testGetName";
        Class<Car> carClass = Car.class;


        Stream<String> fieldStream = Arrays.stream(carClass.getDeclaredFields())
                .map(Field::getName);
        List<String> fieldList = fieldStream.collect(Collectors.toList());

        Stream<String> methodStream = Arrays.stream(carClass.getDeclaredMethods())
                .map(Method::getName);
        List<String> methodList = methodStream.collect(Collectors.toList());

        assertThat(carClass.getName()).isEqualTo(carClassName);
        assertThat(fieldList)
                .hasSize(2)
                .containsExactlyInAnyOrder(carField1, carField2);
        assertThat(methodList)
                .hasSize(5)
                .containsExactlyInAnyOrder(carMethod1, carMethod2, carMethod3, carMethod4, carMethod5);
    }

    @Test
    @DisplayName("test로 시작하는 메서드 실행")
    void testMethodRun() throws IllegalArgumentException, SecurityException {

        String test = "test";
        Class<Car> carClass = Car.class;
        String carName = "BMW";
        int carPrice = 130000000;
        Car car = new Car(carName, carPrice);

        Stream<Object> stream = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith(test))
                .map(method -> {
                    try {
                        return method.invoke(car);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        List<Object> result = stream.collect(Collectors.toList());

        assertThat(result)
                .hasSize(2)
                .containsExactlyInAnyOrder("test : " + carName, "test : " + carPrice);

    }

    @Test
    @DisplayName("@PrintView 어노테이션 메서드 실행")
    void testAnnotationMethodRun() throws SecurityException {
        Class<Car> carClass = Car.class;
        Arrays.stream(carClass.getDeclaredMethods())
                .peek(method -> {
                    if (method.isAnnotationPresent(PrintView.class)) {
                        try {
                            method.invoke(new Car());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
                .collect(Collectors.toList());
    }

    @Test
    @DisplayName("private field에 값 할당")
    void privateFieldAccess() throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        String name = "name";
        String price = "price";
        String newName = "BMW";
        int newPrice = 130000000;

        Class<Car> carClass = Car.class;
        Car carInstance = carClass.getDeclaredConstructor().newInstance();

        Field nameField = carClass.getDeclaredField(name);
        Field priceField = carClass.getDeclaredField(price);
        nameField.setAccessible(true);
        priceField.setAccessible(true);
        nameField.set(carInstance, newName);
        priceField.set(carInstance, newPrice);

        assertThat(carInstance.getName()).isEqualTo(newName);
        assertThat(carInstance.getPrice()).isEqualTo(newPrice);
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String name = "carName";
        int price = 9900;
        Class<Car> carClass = Car.class;

        Constructor<Car> carConstructorWithParam = carClass.getConstructor(String.class, int.class);

        Car carInstance = carConstructorWithParam.newInstance(name, price);

        assertThat(carInstance.getName()).isEqualTo(name);
        assertThat(carInstance.getPrice()).isEqualTo(price);
    }

}
