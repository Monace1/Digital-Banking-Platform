package com.app.User_service.Controller;


import com.app.User_service.Dtos.AuthRequest;
import com.app.User_service.Dtos.AuthResponse;
import com.app.User_service.Entity.Role;
import com.app.User_service.Security.JwtUtil;
import com.app.User_service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam String username,
                                               @RequestParam String password,
                                               @RequestParam String email,
                                               @RequestParam Role role) {
        return ResponseEntity.ok(userService.registerUser(username, password,email, role));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password) {
        return ResponseEntity.ok(userService.login(username, password));
    }
}
