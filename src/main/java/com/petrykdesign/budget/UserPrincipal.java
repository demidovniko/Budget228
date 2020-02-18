package com.petrykdesign.budget;

import java.util.Collection;
import java.util.Collections;

import com.petrykdesign.budget.Repositories.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

/**
 *
 * @author Nick
 */
public class UserPrincipal implements UserDetails{
    private User user;
    final private String PASSWORD = "Justin_Jas_Pidoras_Ohuel2019_!_nePlAtIt";
    final private String salt = "a0fa1e9dd2befa92";
    final TextEncryptor encryptor = Encryptors.text(PASSWORD, salt);
    public UserPrincipal(User user) {
        System.out.println("user: "+user);
        this.user = user;

    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        try {
            if (user.getPassword().equals("error")){
                System.out.println("user: PASS: "+"error");
                return "error";
            }else{
                System.out.println("user: PASS: "+user.getPassword());
                return encryptor.decrypt(user.getPassword());
            }
        }catch (Exception ex){
            return "error";
        }

    }

    @Override
    public String getUsername() {
        if (user.getEmail().equals("error")){
            return "error";
        }else{
            return user.getEmail();
        }


    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

