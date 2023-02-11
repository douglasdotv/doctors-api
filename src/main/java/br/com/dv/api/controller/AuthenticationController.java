package br.com.dv.api.controller;

import br.com.dv.api.domain.user.User;
import br.com.dv.api.domain.user.authentication.AuthenticationData;
import br.com.dv.api.infra.security.JsonWebTokenService;
import br.com.dv.api.infra.security.TokenData;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final AuthenticationManager authManager;
    private final JsonWebTokenService jwtService;

    @Autowired
    public AuthenticationController (AuthenticationManager authManager, JsonWebTokenService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<TokenData> authenticate(@RequestBody @Valid AuthenticationData authData) {
        var authToken = new UsernamePasswordAuthenticationToken(authData.login(), authData.password());
        var auth = authManager.authenticate(authToken);

        var jsonWebToken = jwtService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new TokenData(jsonWebToken));
    }

}
