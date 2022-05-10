package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.service.AvatarStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final AvatarStorageService avatarStorageService;

    @PostMapping("/{id}/add/img")
    public void addAvatar(@PathVariable Long id, @RequestParam("avatars") MultipartFile avatar){
        avatarStorageService.saveAvatar(avatar, id);
    }

//    @GetMapping("/{id}/img")
//    public ResponseEntity<Resource> getAvatar(@PathVariable Long id){
//        Resource avatar = avatarStorageService.loadByTeacherId(id);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + avatar.getFilename()+ "\"")
//                .body(avatar);
//    }

}
