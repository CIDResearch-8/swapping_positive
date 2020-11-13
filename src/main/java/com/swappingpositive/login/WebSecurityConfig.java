package com.swappingpositive.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()  // リクエストに対する許可設定
                .antMatchers("/").permitAll() // 「/」へのアクセスは全許可（ログイン必要なし）
                .anyRequest().authenticated()  // それ以外へのアクセスは認証（ログイン）必要
                .and() //設定を終え、次の設定を開始
                .formLogin() //ログイン処理にフォームを使用
                .loginPage("/login").permitAll() //ログインページ（パス）を「/login」にする
                .and() //設定を終え、次の設定を開始
                .logout()  // ログアウトを有効にする
                .logoutSuccessUrl("/")  // ログアウトしたら「/」にリダイレクトする
        ;
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        return manager;
    }
}