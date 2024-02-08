package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        System.out.println("memberReposotiry -> memberRepository = " + memberRepository);

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
        // 객체 공유를 통해 싱글톤 패턴이 깨지지 않았음을 테스트
    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);
        System.out.println("bean = " + bean.getClass());
        // $$$$SpringCGLIB 가 붙어서 출력됨

        // CGGLIB 라는 바이트코드 조작 라이브러리를 사용하여
        // AppConfig를 상속받은 임의의 다른 클래스를 빈으로 등록함을 알 수 있다

        // CGLIB 내부 예상 코드!!
        // if XXX가 이미 스프링 컨테이너에 등록되어 있으면? -> return 스프링 컨테이너에서 찾아서 반환
        // else XXX가 스프링 컨테이너에 없으면? -> 기존 로직을 호출해서 XXX를 생성하고 스프링 컨테이너에 등록 후 반환

        // 이로 인하여 싱글톤이 보장됨
    }

}
