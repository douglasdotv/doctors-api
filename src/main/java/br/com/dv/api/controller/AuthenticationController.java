package br.com.dv.api.controller;

import br.com.dv.api.domain.user.User;
import br.com.dv.api.domain.user.authentication.AuthenticationDto;
import br.com.dv.api.infra.security.JsonWebTokenService;
import br.com.dv.api.infra.security.TokenDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager manager;
    private final JsonWebTokenService jwtService;

    public AuthenticationController (AuthenticationManager manager, JsonWebTokenService jwtService) {
        this.manager = manager;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<TokenDto> authenticate(@RequestBody @Valid AuthenticationDto dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        var auth = manager.authenticate(authToken);

        var jsonWebToken = jwtService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new TokenDto(jsonWebToken));
    }

}
