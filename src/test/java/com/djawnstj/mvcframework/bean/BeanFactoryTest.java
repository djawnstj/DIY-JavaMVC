package com.djawnstj.mvcframework.bean;

import com.djawnstj.common.PayController;
import com.djawnstj.common.UserRepository;
import com.djawnstj.common.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class BeanFactoryTest {

    private final String basePackage = "com.djawnstj.common";

    @Test
    @DisplayName("조회한 클래스 정보들을 이용해 빈 생성")
    void getBean() {
        // Given
        final BeanFactory beanFactory = new BeanFactory(basePackage);

        // When
        beanFactory.init();

        // Then
        Object userService1 = beanFactory.getBean(UserService.class);
        Object userService2 = beanFactory.getBean(UserService.class);
        assertThat(userService1).isSameAs(userService2);
    }

    @Test
    @DisplayName("@Autowired 어노테이션이 붙은 생성자의 파라미터 주입")
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
    @DisplayName("@Component들을 정상적으로 조회한다")
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
    @DisplayName("@AutoWired 어노테이션이 두 개 이상인 경우 예외를 던져야 한다")
    void autowiredException() {
        // Given
        final String basePackage = "com.djawnstj.exception";
        final BeanFactory beanFactory = new BeanFactory(basePackage);

        // When Then
        assertThatThrownBy(beanFactory::init)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("There are more than one AutoWired annotation.");
    }

}