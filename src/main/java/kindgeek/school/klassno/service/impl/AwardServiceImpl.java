package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Award;
import kindgeek.school.klassno.entity.dto.AwardDto;
import kindgeek.school.klassno.entity.request.AwardRequest;
import kindgeek.school.klassno.exception.NotFoundException;
import kindgeek.school.klassno.mapper.AwardMapper;
import kindgeek.school.klassno.repository.AwardRepository;
import kindgeek.school.klassno.service.AwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AwardServiceImpl implements AwardService {

    private final AwardRepository awardRepository;

    private final AwardMapper awardMapper;

    @Override
    public void create(AwardRequest awardRequest){
        Award award = awardMapper.toEntity(awardRequest);
        awardRepository.save(award);
    }

    @Override
    public AwardDto findDtoById(Long id){
        Award award =  awardRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Award with id:" + id + " does not exist"));
        return awardMapper.toDto(award);
    }

    @Override
    public List<AwardDto> findAll(){
        List<Award> awards = awardRepository.findAll();
        return awards.stream()
                .map(awardMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete (Long id){awardRepository.deleteById(id);}

}
