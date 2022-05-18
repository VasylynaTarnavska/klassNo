package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Attendance;
import kindgeek.school.klassno.entity.HomeworkFile;
import kindgeek.school.klassno.exception.FileException;
import kindgeek.school.klassno.repository.AttendanceRepository;
import kindgeek.school.klassno.repository.HomeworkFileRepository;
import kindgeek.school.klassno.service.HomeworkFileStorageService;
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
import java.util.Set;

@RequiredArgsConstructor
@Service
public class HomeworkFileStorageServiceImpl implements HomeworkFileStorageService {
    private final Path root = Paths.get("../klassno/src/main/resources/homework_files");

    private final AttendanceRepository attendanceRepository;

    private final HomeworkFileRepository homeworkFileRepository;

    @Override
    public void saveFiles(Set<MultipartFile> files, Long attendanceId) {
        Attendance attendance = attendanceRepository.getById(attendanceId);
        try {
            for (MultipartFile file : files) {
                Path path = this.root.resolve(attendance.getId() + attendance.getStudent().getLastName()+ "_"
                        + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                HomeworkFile homeworkFile = new HomeworkFile();
                homeworkFile.setAttendance(attendance);
                homeworkFile.setUrl(path.toAbsolutePath().toString());
                homeworkFile.setName(file.getOriginalFilename());
                attendance.getFiles().add(homeworkFile);
            }
        } catch (Exception e) {
            throw new FileException("Could not store the file.Error: " + e.getMessage());
        }
        homeworkFileRepository.saveAll(attendance.getFiles());
    }

    @Override
    public Resource loadFileById(Long id) {
        HomeworkFile file = homeworkFileRepository.getById(id);
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
