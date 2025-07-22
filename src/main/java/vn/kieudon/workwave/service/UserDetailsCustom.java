package vn.kieudon.workwave.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;




@Component("userDetailsCustom")
public class UserDetailsCustom implements UserDetailsService{
    private final UserService userService;
    public UserDetailsCustom  (UserService userService){
        this.userService = userService;
    }


    @Override
    // thay vì return userdetails, thì return user của security
    // lấy username và password trong database để so sánh với cái client gửi lên 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       vn.kieudon.workwave.domain.User user = this.userService.handleGetUserByUsername(username);

       return new User(
        user.getEmail(),
        user.getPassword(),
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
       );
    }
    
}
