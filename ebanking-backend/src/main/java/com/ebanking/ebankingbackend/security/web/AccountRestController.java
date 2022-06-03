package com.ebanking.ebankingbackend.security.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ebanking.ebankingbackend.security.JWTUtil;
import com.ebanking.ebankingbackend.security.dtos.AuthRequest;
import com.ebanking.ebankingbackend.security.dtos.AuthResponse;
import com.ebanking.ebankingbackend.security.entities.AppRole;
import com.ebanking.ebankingbackend.security.entities.AppUser;
import com.ebanking.ebankingbackend.security.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AccountRestController {
    private AccountService accountService;

@GetMapping("/test")
@CrossOrigin(origins = "http://localhost:4200")
public void test(){
    System.out.println("test");
}
@GetMapping("/login")
@CrossOrigin(origins = "http://localhost:4200")
public AuthResponse login(@RequestBody AuthRequest authRequest) throws Exception {
   return this.accountService.login(authRequest.getUsername(),authRequest.getPassword());
}

    @GetMapping("/users")
    @PostAuthorize("hasAuthority('USER')")
    public List<AppUser> appUsers(){
        return accountService.listUsers();
    }

    @PostMapping("/users")
    @PostAuthorize("hasAuthority('ADMIN')")
    AppUser saveUser(@RequestBody AppUser appUser){
        return accountService.addNewUser(appUser);
    }

    @PostMapping("/roles")
    AppRole saveRole(@RequestBody AppRole appRole){
        return accountService.addNewRole(appRole);
    }

    @PostMapping("/addRoleToUser")
    public void addRoleToUser(RoleUserForm roleUserForm){
        accountService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRoleName());
    }



    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authToken = request.getHeader(JWTUtil.AUTH_HEADER);
        if (authToken!=null && authToken.startsWith(JWTUtil.PREFIX)){
            try {
                String jwt = authToken.substring(JWTUtil.PREFIX.length());
                Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                String username = decodedJWT.getSubject();
                AppUser appUser = accountService.loadUserByUsername(username);  // verify if user still has access
                String jwtAccessToken = JWT.create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+JWTUtil.EXPIRE_ACCESS_TOKEN))  // short period
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles", appUser.getAppRoles().stream().map(auth->auth.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm);


                Map<String, String> idToken = new HashMap<>();
                idToken.put("access-token", jwtAccessToken);
                idToken.put("refresh-token", jwt);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), idToken);
            }catch (Exception e){
                throw e;
            }
        }
        else {
            throw new RuntimeException("Refresh Token required");
        }
    }

    @GetMapping("/profile")
    @PostAuthorize("hasAuthority('USER')")
    public AppUser profile(Principal principal){
        return accountService.loadUserByUsername(principal.getName());
    }
}

@Data
class RoleUserForm{
    private String username;
    private String roleName;
}