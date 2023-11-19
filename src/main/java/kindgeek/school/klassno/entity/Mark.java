package kindgeek.school.klassno.entity;

import kindgeek.school.klassno.enums.Level;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity

public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Min(1)
    @Max(12)
    private Byte value;

    private String comment;

    @ManyToOne
    private Student student;

    @OneToOne
    private Attendance attendance;


}
