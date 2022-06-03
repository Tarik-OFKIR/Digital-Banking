package com.ebanking.ebankingbackend.security.dtos;

import com.ebanking.ebankingbackend.security.entities.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private AppUser user;
    private String jwtToken;
}
