package com.djawnstj.mvcframework.bean;

import com.djawnstj.mvcframework.annotation.AutoWired;
import com.djawnstj.mvcframework.annotation.Service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class BeanFactoryTest {

    private final String basePackage = "com.djawnstj.mvcframework";

    @Test
    @DisplayName("조회한 클래스 정보들을 이용해 빈 생성")
    void getBean() {
        final BeanFactory beanFactory = new BeanFactory(basePackage);
        beanFactory.init();

        Object userService1 = beanFactory.getBean(UserService.class);
        Object userService2 = beanFactory.getBean(UserService.class);

        assertThat(userService1).isSameAs(userService2);
    }

    @Test
    @DisplayName("@Autowired 애너테이션이 붙은 생성자의 파라미터 주입")
    void parameterInjectionInAutoWiredConstructor() {
        // Given
        final BeanFactory beanFactory = new BeanFactory(basePackage);
        Class<UserService> userServiceClass = UserService.class;
        Class<UserRepository> userRepositoryClass = UserRepository.class;

        // When
        beanFactory.init();

        // Then
        UserService userService = beanFactory.getBean(userServiceClass);
        UserRepository userRepository = beanFactory.getBean(userRepositoryClass);
        assertThat(userService.getUserRepository()).isEqualTo(userRepository);
    }

    @Test
    @DisplayName("컴포넌트들을 정상적으로 조회한다")
    void createAllComponent() {
        // Given
        final BeanFactory beanFactory = new BeanFactory(basePackage);
        Class<UserService> userServiceClass = UserService.class;
        Class<PayController> payControllerClass = PayController.class;
        beanFactory.init();

        // When
        UserService userService = beanFactory.getBean(userServiceClass);
        PayController payController = beanFactory.getBean(payControllerClass);

        // Then
        assertThat(userService).isNotNull();
        assertThat(payController).isNotNull();
    }

    @Test
    @DisplayName("@AutoWired 생성자가 없다면 기본 생성자를 가져오는 지 확인")
    void isNotExistAutoWiredConstructor() {
        // Given
        final BeanFactory beanFactory = new BeanFactory(basePackage);
        Class<UserServiceTest> userServiceTestClass = UserServiceTest.class;
        beanFactory.init();

        // When
        UserServiceTest userServiceTest = beanFactory.getBean(userServiceTestClass);

        // Then
//        assertThat(userServiceTest).isEqualTo(userServiceTestClass.getDeclaredConstructor());
    }

    @Test
    @DisplayName("AutoWired가 두개 이상인 경우 예외 던지는 지 확인")
    void autowiredException() {
        // Given
        final BeanFactory beanFactory = new BeanFactory(basePackage);

        try {
            // When
            beanFactory.init();
            fail("RuntimeException 예외가 발생해야 함");
        } catch (RuntimeException e) {
            // Then
//            assertThat(e.getMessage()).isEqualTo("There are more than one AutoWired annotation.");
            assertThat(e).isEqualTo(new RuntimeException());
        }
    }

    @Service
    static class UserServiceTest {
        private final UserRepository userRepository;

//        @AutoWired
        public UserServiceTest() {
            this.userRepository = new UserRepository();
        }

//        @AutoWired
        public UserServiceTest(final UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        public UserRepository getUserRepository() {
            return this.userRepository;
        }

        public void register(final String id, final String pw) {
            this.userRepository.save(id, new User(id, pw));
        }

        public List<User> getUsers() {
            return (List<User>) this.userRepository.findAll();
        }
    }

}
