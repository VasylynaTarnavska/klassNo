package kindgeek.school.klassno.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.util.Set;

public interface LessonFilesStorageService {
    void saveFiles(Set<MultipartFile> files, Long lessonId);

    Resource loadFileById(Long Id);
}
