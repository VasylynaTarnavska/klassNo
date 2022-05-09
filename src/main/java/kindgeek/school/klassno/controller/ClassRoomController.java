package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.ClassRoom;
import kindgeek.school.klassno.entity.dto.ClassRoomDto;
import kindgeek.school.klassno.entity.request.ClassRoomRequest;
import kindgeek.school.klassno.service.ClassRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/class-room")
public class ClassRoomController {


    private final ClassRoomService classRoomService;

    @PostMapping
    public void save(@RequestBody ClassRoomRequest classRoomRequest){
        classRoomService.create(classRoomRequest);
    }

    @GetMapping("/{id}")
    public ClassRoomDto findDtoById (@PathVariable Long id){
        return classRoomService.findDtoById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete (@PathVariable Long id){classRoomService.delete(id);}
}
