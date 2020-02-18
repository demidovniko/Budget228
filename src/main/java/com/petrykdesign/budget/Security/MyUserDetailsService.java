package com.petrykdesign.budget.Security;

import com.petrykdesign.budget.Repositories.User;
import com.petrykdesign.budget.Repositories.UserRepo;
import com.petrykdesign.budget.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nick
 */
@Service
public class MyUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepo repo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = repo.findByEmail(email);
        if(user==null){
            user = repo.findByEmail(email);
            if(user==null){
                //user = repo.findByUsername("error");
                System.out.println("user: "+user);
            }
        }
        //throw new UsernameNotFoundException("User 404");

        return new UserPrincipal(user);
    }

}
