package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.dto.ClassRoomDto;
import kindgeek.school.klassno.entity.request.ClassRoomRequest;
import kindgeek.school.klassno.service.ClassRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/class-room")
public class ClassRoomController {


    private final ClassRoomService classRoomService;

    @PostMapping
    @PreAuthorize("hasAuthority('TEACHER')")
    public void save(@RequestBody ClassRoomRequest classRoomRequest){
        log.info("Creating new class room");
        classRoomService.create(classRoomRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public ClassRoomDto findDtoById (@PathVariable Long id){
        log.info("Getting class room by id: {}", id);
        return classRoomService.findDtoById(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void delete (@PathVariable Long id){
        log.info("Deleting class room by id: {}", id);
        classRoomService.delete(id);}
}
