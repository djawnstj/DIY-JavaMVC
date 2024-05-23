package bean;

import com.djawnstj.mvcframework.annotation.Component;
import com.djawnstj.mvcframework.bean.BeanFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reflection.ReflectionTest;

import java.util.Set;

public class BeanFactoryTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("빈 팩토리 테스트")
    void beanFactoryTest() {
        BeanFactory beanFactory = new BeanFactory("com.djawnstj.mvcframework");

        Set<Class<?>> classes = beanFactory.scanAnnotationClasses(Component.class);

        Assertions.assertThat(classes).isNotNull();
    }
}