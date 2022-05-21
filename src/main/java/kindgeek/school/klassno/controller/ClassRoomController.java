package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.dto.AwardDto;
import kindgeek.school.klassno.entity.dto.ClassRoomDto;
import kindgeek.school.klassno.entity.request.ClassRoomRequest;
import kindgeek.school.klassno.enums.Grade;
import kindgeek.school.klassno.service.ClassRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/class-room")
public class ClassRoomController {


    private final ClassRoomService classRoomService;

    @PostMapping
    @PreAuthorize("hasAuthority('TEACHER')")
    public Long save(@RequestBody ClassRoomRequest classRoomRequest){
        log.info("Creating new class room");
        return classRoomService.create(classRoomRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public ClassRoomDto findDtoById (@PathVariable Long id){
        log.info("Getting class room by id: {}", id);
        return classRoomService.findDtoById(id);
    }

    @GetMapping("/grades")
    @PreAuthorize("hasAuthority('TEACHER')")
    public List<Grade> getAll(){
        log.info("Getting all grades");
        return classRoomService.getAll();
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('TEACHER')")
    public List<ClassRoomDto> findAll(){
        log.info("Getting all class room");
        return classRoomService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void delete (@PathVariable Long id){
        log.info("Deleting class room by id: {}", id);
        classRoomService.delete(id);}
}
