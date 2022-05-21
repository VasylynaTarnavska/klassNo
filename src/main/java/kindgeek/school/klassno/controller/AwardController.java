package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.dto.AwardDto;
import kindgeek.school.klassno.entity.request.AwardRequest;
import kindgeek.school.klassno.service.AwardService;
import kindgeek.school.klassno.service.LogoAwardStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/award")
@RequiredArgsConstructor
public class AwardController {

    private final AwardService awardService;

    private final LogoAwardStorageService logoAwardStorageService;

    @PostMapping
    @PreAuthorize("hasAuthority('TEACHER')")
    public void create(@RequestBody AwardRequest awardRequest){
        log.info("Creating new award");
        awardService.create(awardRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public AwardDto findById (@PathVariable Long id){
        log.info("Getting award by id: {}", id);
        return awardService.findDtoById(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public List<AwardDto> findAll(){
        log.info("Getting all awards");
        return awardService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void delete(@PathVariable Long id){
        log.info("Deleting award by id: {}", id);
        awardService.delete(id);
    }

    @PostMapping("/{id}/add/img")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void addAvatar(@PathVariable Long id, @RequestParam("logo") MultipartFile logo){
        log.info("Adding log by award`s id: {}", id);
        logoAwardStorageService.saveLogo(logo, id);
    }
}
