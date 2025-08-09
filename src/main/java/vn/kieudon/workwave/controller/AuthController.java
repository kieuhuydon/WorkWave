package vn.kieudon.workwave.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.kieudon.workwave.domain.dto.LoginDTO;
import vn.kieudon.workwave.domain.dto.TokenDTO;
import vn.kieudon.workwave.util.SecurityUtil;

@RestController
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder
    ,SecurityUtil securityUtil){
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
    }
    @PostMapping("/login")
    public ResponseEntity<TokenDTO > login(@Valid @RequestBody LoginDTO loginDTO) {
        // Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        // Xác thực người dùng => cần viết hàm loadUserByUsername trong
        // UserDetailsService, authentication không lưu password
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // Nạp thông tin (nếu xử lý thành công) vào SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //create  a token
        String access_token = this.securityUtil.createToken(authentication);
        TokenDTO token = new TokenDTO();
        token.setAccessToken(access_token);

        return ResponseEntity.ok().body(token);
    }
}
