package com.example.infinite_level_messaging_system.Controller;


import com.example.infinite_level_messaging_system.DTO.LoginDTO;
import com.example.infinite_level_messaging_system.DTO.UserDTO;
import com.example.infinite_level_messaging_system.Entity.User;
import com.example.infinite_level_messaging_system.Mapper.UserMapper;
import com.example.infinite_level_messaging_system.Response.ApiResponse;
import com.example.infinite_level_messaging_system.Response.LoginResponse;
import com.example.infinite_level_messaging_system.Services.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.example.infinite_level_messaging_system.Contant.Constant.MAX_AGE_COOKIE;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO){
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())){
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .error("Password not match")
                            .status(400)
                            .success(false)
                            .build()
            );
        }

        User user = authenticationService.registration(userDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                .data(UserMapper.INSTANCE.userToUserDTO(user))
                .message("Register success!")
                .status(200)
                .success(true)
                .build());
    }




    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response, HttpSession session){
        try {
            System.out.println(loginDTO.toString());

            LoginResponse loginResponse = authenticationService.login(loginDTO);
            String token = loginResponse.getAccessToken();
            System.out.println("token: "+token);

            //Add token to cookie login
            if (loginDTO.isRememberMe()){
                Cookie cookie = new Cookie("JWT_TOKEN",token);
                cookie.setMaxAge(MAX_AGE_COOKIE);
                cookie.setPath("/");
                response.addCookie(cookie);
                System.out.println("cookie: "+cookie.getValue());
                System.out.println("cookie: "+cookie.getName());
            }
            else {
                session.setAttribute("username", loginDTO.getUsername());
                session.setAttribute("JWT_TOKEN", token);
            }

            response.setHeader("Authorization", "Bearer "+loginResponse.getAccessToken());

            System.out.println("Header: "+response.getHeaderNames());
            System.out.println("Header: "+response.getHeader("Set-Cookie"));
            System.out.println("Header Access-Control-Allow-Credentials: "+response.getHeader("Access-Control-Allow-Credentials"));

            return ResponseEntity.ok(ApiResponse.builder()
                            .data(loginResponse)
                            .message("Login success!")
                            .status(200)
                            .success(true)
                    .build());

        }catch (Exception e){
            return ResponseEntity.status(401).body(ApiResponse.builder()
                    .error(e.getMessage())
                    .message("Login fail! Check your username and password!")
                    .status(401)
                    .success(false)
                    .build());
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
        HttpSession session = request.getSession(false); // false: không tạo mới session nếu không tồn tại
        if (session != null) {
            session.removeAttribute("JWT_TOKEN"); // Xóa JWT trong session
            session.removeAttribute("username");
        }
        return ResponseEntity.ok(ApiResponse
                .builder()
                .message("Logout success!")
                .status(200)
                .success(true)
                .build());
    }

    @GetMapping("/check-login")
    public ResponseEntity<?> checkLogin(HttpServletRequest request) {
        System.out.println("checkLogin AuthController");
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Enumeration<String> headerNames = request.getHeaderNames();

        if (headerNames != null) {
            int count = 0;
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                System.out.println(count+": "+headerName + ": " + request.getHeader(headerName));
                count++;
            }
        } else {
            System.out.println("No headers found in request.");
        }

        if (authenticationService.checkLogin(request)!=null) {
            System.out.println("true");
            Map<String, Object> responseMap = new HashMap<>();
            // Thêm các cặp khóa-giá trị vào Map
            responseMap.put("loggedIn", true);
            responseMap.put("user", UserMapper.INSTANCE.userToUserDTO(authenticationService.getUserLoggedIn()));
            System.out.println("user: " +authenticationService.getUserLoggedIn());
            System.out.println("userdto: "+ UserMapper.INSTANCE.userToUserDTO(authenticationService.getUserLoggedIn()).getUsername());


            // Is login
            return ResponseEntity.ok(responseMap);
        } else {
            System.out.println("false");
            // Isn't login
            return ResponseEntity.ok(Collections.singletonMap("loggedIn", false));
        }
    }
}
