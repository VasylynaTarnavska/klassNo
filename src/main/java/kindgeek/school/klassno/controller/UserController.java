package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.dto.JwtDto;
import kindgeek.school.klassno.entity.request.LoginRequest;
import kindgeek.school.klassno.security.jwt.JwtUtils;
import kindgeek.school.klassno.security.service.UserDetailsImpl;
import kindgeek.school.klassno.service.AvatarStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final AvatarStorageService avatarStorageService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @PostMapping("/{id}/add/img")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public void addAvatar(@PathVariable Long id, @RequestParam("avatars") MultipartFile avatar){
        log.info("Adding avatar by users id: {}", id);
        avatarStorageService.saveAvatar(avatar, id);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtDto> authenticateUser(@RequestBody LoginRequest loginRequest) {
        log.info("Login");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow();

        JwtDto jwtDto = new JwtDto();
        jwtDto.setToken(jwt);
        jwtDto.setId(userDetails.getId());
        jwtDto.setUsername(userDetails.getUsername());
        jwtDto.setEmail(userDetails.getEmail());
        jwtDto.setRole(role);
        return ResponseEntity.ok(jwtDto);
    }

}
