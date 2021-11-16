package SpringSecurity.FirstSpringSecurity;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 누구나 허용
                .antMatchers("/login", "/signup", "/user")
                .permitAll()
                // USER, ADMIN만 허용
                .antMatchers("/")
                .hasRole("USER")
                // ADMIN만 허용
                .antMatchers("/admin")
                .hasRole("ADMIN")
                // 나머지 요청들은 권한 종류에 상관없이 권한이 있으면 접근 가능
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                // 로그인 성공 후 "/"로 리다이렉트
                .defaultSuccessUrl("/")
                .and()
                .logout()
                // 로그아웃 성공시 "login"으로 리다이렉트
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
