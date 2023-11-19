package kindgeek.school.klassno.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarkDto {

    private Long id;

    private String level;

    private Byte value;

    private String comment;
}
