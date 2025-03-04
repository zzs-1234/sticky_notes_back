package com.stickynotes.controller;

import com.stickynotes.dto.LoginDTO;
import com.stickynotes.dto.RegisterDTO;
import com.stickynotes.model.User;
import com.stickynotes.service.UserService;
import com.stickynotes.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        User user = userService.register(registerDTO);
        return ResponseEntity.ok(Map.of("message", "注册成功"));
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

        System.out.println(loginDTO.toString());
        User user = userService.login(loginDTO);
        String token = jwtUtil.generateToken(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", Map.of(
            "id", user.getId(),
            "username", user.getUsername()
        ));
        
        return ResponseEntity.ok(response);
    }
} 