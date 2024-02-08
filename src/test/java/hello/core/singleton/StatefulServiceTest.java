package hello.core.singleton;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {
    @Test
    void statefulServiceSingleton() {
        //ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        //StatefulService statefulService1 = ac.getBean(StatefulService.class);
       // StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA : A 사용자가 10000원 주문
        //statefulService1.order("userA", 10000);
        // ThreadB : B 사용자가 20000원 주문
       // statefulService2.order("userB", 20000);

        // ThreadA: 사용자A가 주문금액을 조회
       // int price = statefulService1.getPrice();
       // System.out.println("price = " + price); // A는 만원인데 2만원이 출력됨

       // Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);


        // ----------------------해결------------------------
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA : A 사용자가 10000원 주문
        int userAPrice = statefulService1.order("userA", 10000);
        // ThreadB : B 사용자가 20000원 주문
        int userBPrice = statefulService2.order("userB", 20000);
        // 지역변수는 공유되지 않기때문에 리턴값으로 받아서 따로 지역변수로 관리

        // ThreadA: 사용자A가 주문금액을 조회
        System.out.println("price = " + userAPrice); // A 가격 정상 출력



    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}