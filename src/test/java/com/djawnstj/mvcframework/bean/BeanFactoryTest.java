package com.djawnstj.mvcframework.bean;

import com.djawnstj.common.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
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

    @RepeatedTest(value = 10)
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
        Class<ProductService> productServiceClass = ProductService.class;

        // When
        beanFactory.init();
        UserService userService = beanFactory.getBean(userServiceClass);
        ProductService productService = beanFactory.getBean(productServiceClass);

        // Then
        assertThat(userService).isNotNull();
        assertThat(productService).isNotNull();
    }

    @Test
    @DisplayName("@AutoWired 어노테이션이 두 개 이상인 경우 예외를 던져야 한다")
    void autowiredException() {
        // Given
        final String basePackage = "com.djawnstj.exception.autowired";
        final BeanFactory beanFactory = new BeanFactory(basePackage);

        // When Then
        assertThatThrownBy(beanFactory::init)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("There are more than one AutoWired annotation.");
    }

    @Test
    @DisplayName("@Bean으로 동적 빈 생성")
    void createBeanWithBeanAnnotation() {
        // Given
        final BeanFactory beanFactory = new BeanFactory(basePackage);
        Class<ProductService> productServiceClass = ProductService.class;
        Class<ProductRepository> productRepositoryClass = ProductRepository.class;

        // When
        beanFactory.init();
        ProductService productService = beanFactory.getBean(productServiceClass);
        ProductRepository productRepository = beanFactory.getBean(productRepositoryClass);

        // Then
        assertThat(productService).isNotNull();
        assertThat(productRepository).isNotNull();
    }

    @Test
    @DisplayName("@Bean으로 동적 빈 생성 시 빈 메서드의 파라미터가 빈 생성 대상이 아니면 예외 처리")
    void createBeanWithParameterException() {
        // Given
        final String basePackage = "com.djawnstj.exception.bean";
        final BeanFactory beanFactory = new BeanFactory(basePackage);

        // When Then
        assertThatThrownBy(beanFactory::init)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("ParameterType is not the target of creating Bean");
    }

}
