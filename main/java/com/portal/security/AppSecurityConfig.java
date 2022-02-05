package com.portal.security;

import com.portal.models.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    SuccessHandler successHandler;
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(encoder());
        return provider;
    }
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] unAuth={"/static/**","/applicantReg","/saveApplicant","/createCom","/saveCom"};

        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(unAuth).permitAll()
                .antMatchers("/home/**").hasAnyAuthority(Roles.USER.name(),Roles.ADMIN.name())
                .antMatchers("/company/**").hasAnyAuthority(Roles.COMPANY.name(),Roles.ADMIN.name())
                .antMatchers("**/edit/**","**/delete/**","/admin/**").hasAnyAuthority(Roles.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll()
                .loginPage("/login")
                .successHandler(successHandler).failureForwardUrl("/logfail")
                .and().exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException e) throws IOException, ServletException {
                        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                        if(auth==null){
                            res.sendRedirect("/accessdenied");
                        }
                    }
                }).and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/logoutSuccess")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
                    try {
                        http.headers().frameOptions().disable();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


//        http
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/","/home/**","/static/**","/applicantReg","/createCom","/saveCom","/saveApplicant")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .permitAll()
//                .loginPage("/login")
////                .defaultSuccessUrl("/home",true)
//                .loginProcessingUrl("/login")
////                .successHandler(securityHandler)
//                .defaultSuccessUrl("/home",true)
////                .successForwardUrl("/home")
//                .failureForwardUrl("/logfail")
//                .successHandler((httpServletRequest, httpServletResponse, authentication) -> httpServletResponse.sendRedirect("/home"))
//                .permitAll()
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/logoutSuccess")
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .deleteCookies("JSESSIONID")
//                .permitAll();
    }
}
