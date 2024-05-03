package com.djawnstj.mvcframework.bean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.reflection.bean.UserRepository;
import study.reflection.bean.UserService;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ComponentScannerTest {

    @Test
    @DisplayName("특정 애너테이션이 붙은 클래스만 가져오기")
    void getClassInfo() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String basePackage = "study.reflection.bean";

        ComponentScanner componentScanner = new ComponentScanner();
        List<Class<?>> scanList = componentScanner.scan(basePackage);

//        List<Class<?>> classWithServiceAnnotationList = scanList.stream().filter(aClass -> {
//            return aClass.isAnnotationPresent(Service.class);
//        }).collect(Collectors.toList());
//
//        List<Class<?>> classWithRepositoryAnnotationList = scanList.stream().filter(aClass -> {
//            return aClass.isAnnotationPresent(Repository.class);
//        }).collect(Collectors.toList());

//        assertThat(classWithServiceAnnotationList).contains(UserService.class);
//        assertThat(classWithRepositoryAnnotationList).contains(UserRepository.class);
    }

    @Test
    @DisplayName("조회한 클래스 정보들을 이용해 빈 생성")
    void createObject() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<UserRepository> repository = UserRepository.class.getDeclaredConstructor();
        UserRepository userRepository = repository.newInstance();

        Constructor<UserService> service = UserService.class.getConstructor(UserRepository.class);
        UserService userService = service.newInstance(userRepository);

        assertThat(userService).isInstanceOf(UserService.class);
        assertThat(userService).hasNoNullFieldsOrProperties();
    }

}
