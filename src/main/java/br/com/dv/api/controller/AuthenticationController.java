package br.com.dv.api.controller;

import br.com.dv.api.domain.user.authentication.AuthenticationData;
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

    @Autowired
    public AuthenticationController (AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @PostMapping
    public ResponseEntity<Void> authenticate(@RequestBody @Valid AuthenticationData authData) {
        var token = new UsernamePasswordAuthenticationToken(authData.login(), authData.password());
        var auth = authManager.authenticate(token);

        return ResponseEntity.ok().build();
    }

}
