package com.swappingpositive.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountService userDetailsService;

    @Autowired
    private AuthenticationProviderImpl authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                    .frameOptions()
                        .disable()
                    .xssProtection()
                        .and()
                    .cacheControl()
                        .and()
                    .contentTypeOptions()
                        .and()
                    .and()
                .authorizeRequests()
                    // 認証対象外のパスを設定する
                    .antMatchers("/", "/register", "/register-error", "/h2-console/**","/registration/**", "/css/**", "/js/**", "/img/**")
                    // 上記パスへのアクセスを許可する
                    .permitAll()
                    // その他のリクエストは認証が必要
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    // ログインフォームのパス
                    .loginPage("/login")
                    // ログイン処理のパス
                    .loginProcessingUrl("/login")
                    // ログイン成功時の遷移先
                    .defaultSuccessUrl("/user/home")
                    // ログイン失敗時の遷移先
                    .failureUrl("/login-error")
                    // ログインフォームで使用するユーザー名のinput name
                    .usernameParameter("userId")
                    // ログインフォームで使用するパスワードのinput name
                    .passwordParameter("password")
                    .permitAll()
                    .and()
                .rememberMe()
                    .tokenValiditySeconds(86400) // 1ヶ月（秒）
                    .and()
                .logout()
                    // ログアウトがパス(GET)の場合設定する（CSRF対応）
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    // ログアウトがPOSTの場合設定する
                    //.logoutUrl("/logout")
                    // ログアウト後の遷移先
                    .logoutSuccessUrl("/")
                    // セッションを破棄する
                    .invalidateHttpSession(true)
                    // ログアウト時に削除するクッキー名
                    .deleteCookies("JSESSIONID", "remember-me")
                    .permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // 独自認証クラスを設定する
        auth
                .authenticationProvider(authenticationProvider)
                .userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}