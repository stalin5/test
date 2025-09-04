// package com.examly.springapp.controller;

// import com.examly.springapp.configuration.JWTUtil;
// import com.examly.springapp.model.User;
// import com.examly.springapp.service.UserService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import java.util.Map;
// import java.util.Optional;

// @RestController
// @RequestMapping("/api/auth")
// @CrossOrigin(origins = "http://localhost:3000")
// public class AuthController {

//     @Autowired
//     private UserService userService;

//     @Autowired
//     private JWTUtil jwtUtil;

//     @PostMapping("/signup")
//     public User signup(@RequestBody User user) {
//         return userService.registerUser(user);
//     }

//     @PostMapping("/login")
//     public Map<String, Object> login(@RequestBody User user) {
//         Optional<User> loggedUser = userService.loginUser(user.getEmail(), user.getPassword());

//         if (loggedUser.isEmpty()) {
//             throw new RuntimeException("Invalid Credentials");
//         }

//         User u = loggedUser.get();
//         // Generate JWT
//         String token = jwtUtil.generateToken(u.getEmail(), null);

//         return Map.of(
//                 "id", u.getId(),
//                 "username", u.getUsername(),
//                 "email", u.getEmail(),
//                 "token", token
//         );
//     }
// }
