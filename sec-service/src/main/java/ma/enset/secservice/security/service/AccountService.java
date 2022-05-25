package ma.enset.secservice.security.service;

import ma.enset.secservice.security.entities.AppRole;
import ma.enset.secservice.security.entities.AppUser;

import java.util.List;

public interface AccountService {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username, String roleName);
    AppUser loadUserByUsername(String username);
    AppUser login(String username,String password) throws Exception;
    List<AppUser> listUsers();
}
