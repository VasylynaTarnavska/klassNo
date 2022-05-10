package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Award;
import kindgeek.school.klassno.exception.FileException;
import kindgeek.school.klassno.repository.AwardRepository;
import kindgeek.school.klassno.service.LogoAwardStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RequiredArgsConstructor
@Service
public class LogoAwardStorageServiceImpl implements LogoAwardStorageService {

    private final Path root = Paths.get("../klassno/src/main/resources/logos");

    private final AwardRepository awardRepository;

    @Override
    public void saveLogo(MultipartFile logo, Long awardId){
        Award award= awardRepository.getById(awardId);
        if (award.getLogo()!=null){
            File file = new File(award.getLogo());
            file.delete();
        }
        try{
            Path path = this.root.resolve(award.getName() + "_" + logo.getOriginalFilename());
            Files.copy(logo.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            award.setLogo(path.toAbsolutePath().toString());
        } catch (Exception e){
            throw new FileException("Could not store the file.Error: " + e.getMessage());
        }
        awardRepository.save(award);
    }
}
