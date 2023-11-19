package kindgeek.school.klassno.entity;

import kindgeek.school.klassno.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @Email
    private String login;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String avatar;

    @Enumerated(EnumType.STRING)
    private UserRole role;

}
