package study.reflection.bean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class ComponentScannerTest {

    @Test
    @DisplayName("특정 애너테이션이 붙은 클래스만 가져오기")
    void getClassInfo() throws IOException, ClassNotFoundException {
        String basePackage = "study.reflection.bean";
//        @Repository, @Service 애너테이션이 붙은 클래스 정보들을 컬렉션으로 가지게 하기.

        ComponentScanner componentScanner = new ComponentScanner();
        List<Class<?>> scan = componentScanner.scan(basePackage);

        int size = scan.size();

    }

}
