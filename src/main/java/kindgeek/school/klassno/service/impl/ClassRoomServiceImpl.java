package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Award;
import kindgeek.school.klassno.entity.ClassRoom;
import kindgeek.school.klassno.entity.dto.ClassRoomDto;
import kindgeek.school.klassno.entity.request.ClassRoomRequest;
import kindgeek.school.klassno.enums.Grade;
import kindgeek.school.klassno.exception.NotFoundException;
import kindgeek.school.klassno.mapper.ClassRoomMapper;
import kindgeek.school.klassno.repository.ClassRoomRepository;
import kindgeek.school.klassno.service.ClassRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class ClassRoomServiceImpl implements ClassRoomService {

    private final ClassRoomRepository classRoomRepository;

    private final ClassRoomMapper classRoomMapper;

    @Override
    public Long create(ClassRoomRequest classRoomRequest) {
        ClassRoom classRoom = classRoomMapper.toEntity(classRoomRequest);
        classRoomRepository.save(classRoom);
        return classRoom.getId();
    }

    @Override
    public List<ClassRoomDto> findAll(){
        List<ClassRoom> classRooms = classRoomRepository.findAll();
        return classRooms.stream()
                .map(classRoomMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
        public List<Grade> getAll(){
        List<Grade> grades = Arrays.asList(Grade.values());
        return grades;
    }

    @Override
    public ClassRoomDto findDtoById(Long id) {
        ClassRoom classRoom = classRoomRepository.findById(id)
                .orElseThrow(()->new NotFoundException("ClassRoom with id:" + id + " does not exist"));
        return classRoomMapper.toDto(classRoom);
    }

    @Override
    public ClassRoom findById(Long id) {
        return classRoomRepository.findById(id)
                .orElseThrow(()->new NotFoundException("ClassRoom with id:" + id + " does not exist"));
    }

    @Override
    public void delete(Long id){
        classRoomRepository.deleteById(id);
    }

}
