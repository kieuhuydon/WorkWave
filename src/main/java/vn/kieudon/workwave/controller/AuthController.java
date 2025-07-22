package vn.kieudon.workwave.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vn.kieudon.workwave.domain.dto.LoginDTO;

@RestController
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder){
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody LoginDTO loginDTO) {
        // Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        // Xác thực người dùng => cần viết hàm loadUserByUsername trong
        // UserDetailsService
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // Nạp thông tin (nếu xử lý thành công) vào SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok().body(loginDTO);
    }
}
