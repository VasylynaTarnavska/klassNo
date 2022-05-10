package kindgeek.school.klassno.service;

import org.springframework.web.multipart.MultipartFile;

public interface LogoAwardStorageService {
    void saveLogo(MultipartFile logo, Long awardId);
}
