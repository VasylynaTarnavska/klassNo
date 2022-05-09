package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Teacher;
import kindgeek.school.klassno.exception.FileException;
import kindgeek.school.klassno.repository.TeacherRepository;
import kindgeek.school.klassno.service.FilesStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    private final Path root = Paths.get("C:/Users/Bohdan/Desktop/KlassNoFiles");

    private final TeacherRepository teacherRepository;

    @Override
    public void saveAvatar(MultipartFile avatar, Long teacherId){
        Teacher teacher = teacherRepository.getById(teacherId);
        try{
            Path path = this.root.resolve(Objects.requireNonNull(teacher.getLastName()+teacher.getFirstName()+"avatar"));
            Files.copy(avatar.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            teacher.setAvatar(path.toAbsolutePath().toString());
        } catch (Exception e){
            throw new FileException("Could not store the file.Error: " + e.getMessage());
        }
        teacherRepository.save(teacher);
    }

    @Override
    public Resource loadByTeacherId(Long teacherId){
        Teacher teacher = teacherRepository.getById(teacherId);
        try{
            Path avatar = Paths.get(teacher.getAvatar());
            Resource resource = new UrlResource(avatar.toUri());
            if(resource.exists()||resource.isReadable()){
                return resource;
            }else{
                throw new FileException("Could not read the file!");
            }
        }catch (MalformedURLException e){
            throw new FileException("Error: "+e.getMessage());
        }

    }

}
