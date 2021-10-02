package spring.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import spring.domain.user.Role;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활정화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().headers().frameOptions().disable() // h2-console 화면을 사용하기 위한 설정
                .and()
                    .authorizeRequests() // URL별 권한 관리를 설정하는 옵션의 시작점
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll() // 전체 열람 권한
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // USER 권한을 가진 사람만
                    .anyRequest().authenticated() // anyReQuest(): 설정된 값들 이외 나머지 URL, authenticated
                .and()
                    .logout() // 로그아웃 시
                        .logoutSuccessUrl("/") /// 로 이동
                .and()
                    .oauth2Login() // oauth2 로그인
                        .userInfoEndpoint() // 로그인 이후 사용자 정보를 가져올 때 설정
                            .userService(customOAuth2UserService); // 로그인 성공 시 진행할 인터페이스 구현체 등록
    }
}
