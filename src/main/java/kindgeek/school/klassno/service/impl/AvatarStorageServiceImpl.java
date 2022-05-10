package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Teacher;
import kindgeek.school.klassno.entity.User;
import kindgeek.school.klassno.exception.FileException;
import kindgeek.school.klassno.repository.TeacherRepository;
import kindgeek.school.klassno.repository.UserRepository;
import kindgeek.school.klassno.service.AvatarStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AvatarStorageServiceImpl implements AvatarStorageService {

    private final Path root = Paths.get("../klassno/src/main/resources/avatars");

    private final UserRepository userRepository;

    @Override
    public void saveAvatar(MultipartFile avatar, Long userId){
        User user= userRepository.getById(userId);
        if (user.getAvatar()!=null){
            File file = new File (user.getAvatar());
            file.delete();
        }
        try{
            Path path = this.root.resolve(user.getLastName()+user.getFirstName() + "_" + avatar.getOriginalFilename());
            Files.copy(avatar.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            user.setAvatar(path.toAbsolutePath().toString());
        } catch (Exception e){
            throw new FileException("Could not store the file.Error: " + e.getMessage());
        }
        userRepository.save(user);
    }

//    @Override
//    public Resource loadByTeacherId(Long teacherId){
//        Teacher teacher = teacherRepository.getById(teacherId);
//        try{
//            Path avatar = Paths.get(teacher.getAvatar());
//            Resource resource = new UrlResource(avatar.toUri());
//            if(resource.exists()||resource.isReadable()){
//                return resource;
//            }else{
//                throw new FileException("Could not read the file!");
//            }
//        }catch (MalformedURLException e){
//            throw new FileException("Error: "+e.getMessage());
//        }

//    }

}
