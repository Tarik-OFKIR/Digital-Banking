package com.ebanking.ebankingbackend.security.service;

import com.ebanking.ebankingbackend.security.JWTUtil;
import com.ebanking.ebankingbackend.security.dtos.AuthResponse;
import com.ebanking.ebankingbackend.security.entities.AppRole;
import com.ebanking.ebankingbackend.security.entities.AppUser;
import com.ebanking.ebankingbackend.security.repositories.AppRoleRepository;
import com.ebanking.ebankingbackend.security.repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(String username, String password) throws Exception {

        try {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//           AuthResponse authResponse = (AuthResponse) authenticate.getPrincipal();
            System.out.println( authenticate.getPrincipal());
            UserDetails userDetails =(UserDetails) authenticate.getPrincipal();
            AppUser appUser = loadUserByUsername(userDetails.getUsername());
            String token = jwtUtil.generateToken(userDetails);
            return new AuthResponse(appUser,token);
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    @Override
    public AppUser addNewUser(AppUser appUser) {
        String pw = appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(pw));
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        appUser.getAppRoles().add(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }



    @Override
    public List<AppUser> listUsers() {
        return appUserRepository.findAll();
    }
}
