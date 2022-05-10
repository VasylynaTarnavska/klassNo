package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.Award;
import kindgeek.school.klassno.entity.dto.AwardDto;
import kindgeek.school.klassno.entity.request.AwardRequest;
import kindgeek.school.klassno.service.AwardService;
import kindgeek.school.klassno.service.LogoAwardStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/award")
@RequiredArgsConstructor
public class AwardController {

    private final AwardService awardService;

    private final LogoAwardStorageService logoAwardStorageService;

    @PostMapping
    public void create(@RequestBody AwardRequest awardRequest){
        awardService.create(awardRequest);
    }

    @GetMapping("/{id}")
    public AwardDto findById (@PathVariable Long id){
        return awardService.findDtoById(id);
    }

    @GetMapping("/all")
    public List<AwardDto> findAll(){
        return awardService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        awardService.delete(id);
    }

    @PostMapping("/{id}/add/img")
    public void addAvatar(@PathVariable Long id, @RequestParam("logo") MultipartFile logo){
        logoAwardStorageService.saveLogo(logo, id);
    }
}
