package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService LoginUserDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
    @Override
    public void configure(WebSecurity web) throws Exception {
    	//セキュリティ設定を無視するパスを指定
        web.ignoring().antMatchers("/webjars/**", "/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       
    	http.authorizeRequests()
                    .antMatchers("/login","/register").permitAll()
                    .anyRequest().authenticated()
            .and()
            .formLogin().loginProcessingUrl("/login")
            		//ログイン時のURLを指定
                    .loginPage("/login")
                    .failureUrl("/login?error")
                    //認証後にリダイレクトする場所を指定
                    .defaultSuccessUrl("/", true)
                    .usernameParameter("username")
                    .passwordParameter("password")
            .and()
            .logout()
                    .logoutSuccessUrl("/login");
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        // userDetailsServiceを使用して、DBからユーザを参照できるようする
        auth.userDetailsService(LoginUserDetailsService)
            .passwordEncoder(passwordEncoder());
    }
    
}
