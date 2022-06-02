package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.Award;
import kindgeek.school.klassno.entity.dto.AwardDto;
import kindgeek.school.klassno.entity.request.AwardRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AwardMapper {

    Award toEntity (AwardRequest awardRequest);

    AwardDto toDto (Award award);

    default Award fromId(Long id){
        if (id == null) {
            return null;
        }

        Award award = new Award();
        award.setId(id);
        return award;
    }
}
