package com.ebanking.ebankingbackend.security.service;

import com.ebanking.ebankingbackend.security.entities.AppUser;
import com.ebanking.ebankingbackend.security.repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        Collection<GrantedAuthority> grantedAuthorities = appUser.getAppRoles().stream()
                .map( r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList());
        return new User(appUser.getUsername(), appUser.getPassword(), grantedAuthorities);
    }
}
