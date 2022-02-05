package com.portal.security;

import com.portal.models.Roles;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
@Configuration
public class SuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res,
                                        Authentication auth) throws IOException, ServletException {
        UserDetailImp user = (UserDetailImp) auth.getPrincipal();
        System.out.println("->"+user.getUsername());
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        authorities.forEach(o -> {
            try {
                String authority = o.getAuthority();
                System.out.println("auth role= "+o);
                if (authority.equals(Roles.USER.name())) {
                    res.sendRedirect("/home");
                } else if (authority.equals(Roles.COMPANY.name())) {
                    System.out.println("hello");
                    res.sendRedirect("/company");
                } else if (authority.equals(Roles.ADMIN.name())) {
                    res.sendRedirect("/admin");
                }
            } catch (Exception ex) {
                try {
                    res.sendRedirect("/logfail");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
