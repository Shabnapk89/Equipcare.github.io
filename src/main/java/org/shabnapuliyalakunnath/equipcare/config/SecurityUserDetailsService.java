package org.shabnapuliyalakunnath.equipcare.config;

import org.shabnapuliyalakunnath.equipcare.entity.User;
import org.shabnapuliyalakunnath.equipcare.exceptions.UserNotFoundException;
import org.shabnapuliyalakunnath.equipcare.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if(user == null)
            throw new UserNotFoundException("User with email: " +username +" not found !");
        else {
            List<SimpleGrantedAuthority> granted = new ArrayList<>();
            granted.add( new SimpleGrantedAuthority(user.getRole()));
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    granted
            );
        }
    }
}