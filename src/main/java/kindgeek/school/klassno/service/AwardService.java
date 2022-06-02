package kindgeek.school.klassno.service;

import kindgeek.school.klassno.entity.dto.AwardDto;
import kindgeek.school.klassno.entity.request.AwardRequest;

import java.util.List;

public interface AwardService {

    void create (AwardRequest awardRequest);

    AwardDto findDtoById(Long id);

   List<AwardDto> findAll();

    void delete(Long id);
}
