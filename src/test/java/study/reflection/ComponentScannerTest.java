package study.reflection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

class ComponentScannerTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("어노테이션 조회 테스트")
    void testAnnotation() throws IOException, ClassNotFoundException {
        ComponentScanner componentScanner = new ComponentScanner();
        String packageName = this.getClass().getPackage().getName();
        List<Class<?>> allClasses = componentScanner.basicScan(packageName);
        List<Class<?>> annotatedClasses = new ArrayList<>();

        for (Class<?> clazz : allClasses) {
            logger.debug(String.valueOf(clazz));
            if (clazz.isAnnotationPresent(Component.class) ||
                    clazz.isAnnotationPresent(Configuration.class) ||
                    clazz.isAnnotationPresent(Repository.class) ||
                    clazz.isAnnotationPresent(Service.class) ||
                    clazz.isAnnotationPresent(Controller.class)) {
                annotatedClasses.add(clazz);
            }
        }

//        Assertions.assertTrue(annotatedClasses.contains(UserService.class));
//        Assertions.assertTrue(annotatedClasses.contains(UserRepository.class));
    }

    @Test
    @DisplayName("빈 등록 및 조회 테스트 - 모든 객체는 싱글톤 패턴으로 구현")
    void testBean() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ComponentScanner componentScanner = new ComponentScanner();
        String packageName = this.getClass().getPackage().getName();
        List<Class<?>> allClasses = componentScanner.basicScan(packageName);
        List<Class<?>> annotatedClasses = new ArrayList<>();
        // bean의 역할을 함 - 생성된 객체를 담는 list
        Map<Class<?>, Object> instancesMap = new HashMap<>();

        for (Class<?> clazz : allClasses) {
            logger.debug(clazz.getName());
            if (clazz.isAnnotationPresent(Component.class) ||
                    clazz.isAnnotationPresent(Configuration.class) ||
                    clazz.isAnnotationPresent(Repository.class) ||
                    clazz.isAnnotationPresent(Service.class) ||
                    clazz.isAnnotationPresent(Controller.class)) {
                // 이렇게 되면 어노테이션이 4개가 들어감
                // 모두 같은 객체를 가르키는지 확인하기 위한 반복 코드
                annotatedClasses.add(clazz);
                annotatedClasses.add(clazz);
                annotatedClasses.add(clazz);
                annotatedClasses.add(clazz);
            }
        }

        for (Class<?> annotatedClass : annotatedClasses) {
            // 맵에서 인스턴스 확인 후 없으면 생성
            // 처음에는 그냥 리스트에 add로 넣어서 싱글톤으로 구현 못함.
            // hashmap으로 만들어놓고 위에 if문을 통과 했을 때, 등록해야하고
            // 등록하기 전에 먼저 hashmap안에 같은 인스턴스가 존재하는지 확인함
            // 존재하면 put안하고, 존재하지 않으면 put함
            // 이렇게 하면 중복된 클래스들은 hashmap에 등록되지 않아서 싱글톤 패턴으로 구현 한 것.
            Object instance = instancesMap.get(annotatedClass);

            logger.debug(instance.toString());
        }

        // 위에서 add로 4번추가해도 hashmap은 중복을 검증하도록 되어 있기 때문에
        // hashmap은 하나의 객체만 사용함.
//        Assertions.assertTrue(annotatedClasses.contains(UserRepository.class));
//        Assertions.assertTrue(annotatedClasses.contains(UserService.class));
    }
}
