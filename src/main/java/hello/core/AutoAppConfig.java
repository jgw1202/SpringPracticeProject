package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan ( // 다른 예제에 빈 코드를 그냥 남겨두기 위해 스캔 필터링
        //basePackages = "hello.core.member", // 해당 패키지 하위 클래스만 필터링
        //basePackageClasses = AutoAppConfig.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION, classes = Configuration.class))
public class AutoAppConfig {


}
