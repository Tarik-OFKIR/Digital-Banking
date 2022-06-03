package com.ebanking.ebankingbackend.security.service;

import com.ebanking.ebankingbackend.security.dtos.AuthResponse;
import com.ebanking.ebankingbackend.security.entities.AppUser;
import com.ebanking.ebankingbackend.security.entities.AppRole;

import java.util.List;

public interface AccountService {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username, String roleName);
    AppUser loadUserByUsername(String username);
    AuthResponse login(String username, String password) throws Exception;
    List<AppUser> listUsers();
}
