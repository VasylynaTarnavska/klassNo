package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.dto.AttendanceDto;
import kindgeek.school.klassno.entity.request.AttendanceRequest;
import kindgeek.school.klassno.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping
    public void create(@RequestBody AttendanceRequest attendanceRequest){

        attendanceService.create(attendanceRequest);
    }

    @GetMapping("/{id}")
    public AttendanceDto findDtoById(@PathVariable Long id){
        return attendanceService.findDtoById(id);
    }

    @PutMapping("/present/{id}")
    public void editPresent(@PathVariable Long id, @RequestParam Boolean isPresent){
        attendanceService.editPresent(id, isPresent);
    }

    @GetMapping("/by-lesson/{lessonId}")
    public List<AttendanceDto> findByLessonId (@PathVariable Long lessonId){
        return attendanceService.findByLessonId(lessonId);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        attendanceService.delete(id);
    }

//todo add works with files
}
