package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    // @Bean memberService -> new MemoryMemberRepository()
    // @Bean orderService -> new MemoryMemberRepository()
    // 2개의 객체가 만들어지면서 싱글톤 패턴이 깨진다??
    // 그렇지 않다.


    // 자바 코드에 의해서는 아래처럼 호출
    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.memberRepository
    // call AppConfig.orderService
    // call AppConfig.memberRepository


    // but 아래처럼 싱글톤 적용되어 호출
    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.orderService

    // ConfigurationSingletonTest.java  CGLIB 내부 예상 코드!!
    // if XXX가 이미 스프링 컨테이너에 등록되어 있으면? -> return 스프링 컨테이너에서 찾아서 반환
    // else XXX가 스프링 컨테이너에 없으면? -> 기존 로직을 호출해서 XXX를 생성하고 스프링 컨테이너에 등록 후 반환

    // 이로 인하여 싱글톤이 보장됨

    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy()
        );
    }
    @Bean
    public DiscountPolicy discountPolicy() {
        // return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
