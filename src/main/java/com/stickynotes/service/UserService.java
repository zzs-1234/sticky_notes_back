package com.stickynotes.service;

import com.stickynotes.dto.LoginDTO;
import com.stickynotes.dto.RegisterDTO;
import com.stickynotes.model.User;
import com.stickynotes.model.Category;
import com.stickynotes.repository.UserRepository;
import com.stickynotes.repository.CategoryRepository;
import com.stickynotes.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Transactional
    public User register(RegisterDTO registerDTO) {
        // 验证密码
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new CustomException("两次输入的密码不一致");
        }
        
        // 检查邮箱是否已存在
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new CustomException("该用户已被注册");
        }
        
        // 创建用户
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        user = userRepository.save(user);

        
        return user;
    }
    
    public User login(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername())
            .orElseThrow(() -> new CustomException("用户或密码错误"));
            
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new CustomException("用户或密码错误");
        }
        
        return user;
    }
} 