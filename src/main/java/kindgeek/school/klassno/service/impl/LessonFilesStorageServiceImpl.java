package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Lesson;
import kindgeek.school.klassno.entity.LessonFile;
import kindgeek.school.klassno.entity.Teacher;
import kindgeek.school.klassno.entity.User;
import kindgeek.school.klassno.exception.FileException;
import kindgeek.school.klassno.repository.LessonFileRepository;
import kindgeek.school.klassno.repository.LessonRepository;
import kindgeek.school.klassno.repository.UserRepository;
import kindgeek.school.klassno.service.LessonFilesStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class LessonFilesStorageServiceImpl implements LessonFilesStorageService {

    private final Path root = Paths.get("../klassno/src/main/resources/lesson_files");

    private final LessonRepository lessonRepository;

    private final LessonFileRepository lessonFileRepository;

    @Override
    public void saveFiles(Set<MultipartFile> files, Long lessonId) {
        Lesson lesson = lessonRepository.getById(lessonId);
        try {
            for (MultipartFile file : files) {
                Path path = this.root.resolve(lesson.getId() + "_" + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                LessonFile lessonFile = new LessonFile();
                lessonFile.setLesson(lesson);
                lessonFile.setUrl(path.toString());
                lessonFile.setName(file.getOriginalFilename());
                lesson.getFiles().add(lessonFile);
            }
        } catch (Exception e) {
            throw new FileException("Could not store the file.Error: " + e.getMessage());
        }
        lessonFileRepository.saveAll(lesson.getFiles());
    }

    @Override
    public Resource loadFileById(Long id) {
        LessonFile file = lessonFileRepository.getById(id);
        try {
            Path url = Paths.get(file.getUrl());
            Resource resource = new UrlResource(url.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new FileException("Error: " + e.getMessage());
        }
    }

}

