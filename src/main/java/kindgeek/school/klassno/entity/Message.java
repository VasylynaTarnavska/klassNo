package kindgeek.school.klassno.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String messageText;

    private LocalDate messageTime = LocalDate.now();

    @ManyToOne
    @NotNull
    private User sender;

    @ManyToOne
    @NotNull
    private Chat chat;
}
