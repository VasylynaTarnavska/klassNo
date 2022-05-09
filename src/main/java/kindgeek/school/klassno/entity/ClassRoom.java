package kindgeek.school.klassno.entity;

import kindgeek.school.klassno.enums.Grade;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BINARY(16)" )
    private UUID roomKey = UUID.randomUUID();

    @NotNull
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @OneToMany(mappedBy = "classRoom")
    private List<Student> students;

    @OneToMany(mappedBy = "classRoom")
    private List<Lesson> lessons;


}
