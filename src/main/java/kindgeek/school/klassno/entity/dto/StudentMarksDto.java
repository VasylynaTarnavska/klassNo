package kindgeek.school.klassno.entity.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentMarksDto {

    private Long id;

    private String firstName;

    private String lastName;

    private List<AttendanceShortDto> attendances;
}
