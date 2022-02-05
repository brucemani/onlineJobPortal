package com.portal.security;

import com.portal.models.Applicant;
import com.portal.models.Users;
import com.portal.repositories.UsersRepository;
import com.portal.services.PortalService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users usersByUserName = usersRepository.getUsersByUserName(s);
        System.out.println("***"+usersByUserName);
        if(usersByUserName!=null){
            return new UserDetailImp(usersByUserName);
        }
        throw new UsernameNotFoundException("User name not found");
    }

}
