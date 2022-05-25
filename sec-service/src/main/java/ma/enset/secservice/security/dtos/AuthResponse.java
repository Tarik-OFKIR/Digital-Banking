package ma.enset.secservice.security.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.secservice.security.entities.AppRole;

import java.util.ArrayList;
import java.util.List;

@Data @AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private Long id;
    private String username;
    private String password;
    private List<AppRole> appRoles = new ArrayList<>();
}
