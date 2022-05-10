package kindgeek.school.klassno.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface AvatarStorageService {
    void saveAvatar(MultipartFile avatar, Long teacherId);

//    Resource loadByTeacherId(Long teacherId);
}
