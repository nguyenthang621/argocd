package com.isttmicroservice.userservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isttmicroservice.userservice.dto.ResponseDTO;
import com.isttmicroservice.userservice.dto.UserDTO;
import com.isttmicroservice.userservice.entity.LoginRequest;
import com.isttmicroservice.userservice.entity.Role;
import com.isttmicroservice.userservice.entity.User;
import com.isttmicroservice.userservice.repository.RoleRepo;
import com.isttmicroservice.userservice.repository.UserRepo;
import com.isttmicroservice.userservice.service.JwtTokenService;
import com.isttmicroservice.userservice.service.UserService;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthAPIController {
    @Autowired
    UserRepo userRepository;
    @Autowired
    RoleRepo roleRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtTokenService jwtTokenService;
    @Autowired
    UserService userService;

//    @PostMapping("/signin")
//    public ResponseDTO<UserDTO> login(@Valid @RequestBody LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        
//        // Lấy thông tin người dùng từ cơ sở dữ liệu dựa trên tên người dùng đã xác thực
//        UserDTO userDTO = userService.findByUsername(loginRequest.getUsername());
//        if (userDTO == null) {
//            // Người dùng không tồn tại
//            return ResponseDTO.<UserDTO>builder()
//                    .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
//                    .message("User not found")
//                    .build();
//        }
//
//        // Tạo mã thông báo JWT
//        String token = jwtTokenService.createToken(loginRequest.getUsername(), authentication.getAuthorities());
//
//        // Tạo đối tượng ResponseDTO và gán thông tin người dùng vào thuộc tính data
//        ResponseDTO<UserDTO> responseDTO = ResponseDTO.<UserDTO>builder()
//                .code(String.valueOf(HttpStatus.OK.value()))
//                .data(userDTO)
//                .token(token)
//                .build();
//        System.out.println(token);
//        return responseDTO;
//    }

    @PostMapping("/signin")
    public ResponseDTO<String> login(@Valid @RequestBody LoginRequest loginRequest) {
 
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        return ResponseDTO.<String>builder().code(String.valueOf(HttpStatus.OK.value())).data(jwtTokenService.createToken(loginRequest.getUsername(),authentication.getAuthorities())).build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?>  register(@Valid @RequestBody User userSignUp) {
        User user = new User();
        user.setEmail(userSignUp.getEmail());
        user.setUsername(userSignUp.getUsername());
        user.setPassword(encoder.encode(userSignUp.getPassword()));
        System.out.println(encoder.encode(userSignUp.getPassword()));
        List<Role> strRoles = new ArrayList<Role>();
        strRoles.add(roleRepository.findById(1).orElseThrow(null));

        user.setRoles(strRoles);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}
