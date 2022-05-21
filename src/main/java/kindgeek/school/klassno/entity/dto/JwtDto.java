package kindgeek.school.klassno.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class JwtDto {

    private Long id;
    private String token;
    private String username;
    private String email;
    private String role;

}
