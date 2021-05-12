package com.bariser.cvapp.service.Impl;

import com.bariser.cvapp.model.User;
import com.bariser.cvapp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("Email " + email + " not found!");
        }
        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), getGrantedAuthority(user));
    }


    private List<GrantedAuthority> getGrantedAuthority(User user) {

        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_"+user.getRole().toString()));
        return authorities;

        /*
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

         */

        /*
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getRole().toString().equalsIgnoreCase("admin")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;

         */
    }

}