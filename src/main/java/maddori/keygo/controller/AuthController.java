package maddori.keygo.controller;

import lombok.RequiredArgsConstructor;
import maddori.keygo.service.AuthService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/teams")
public class AuthController {
    private final AuthService authService;

}
