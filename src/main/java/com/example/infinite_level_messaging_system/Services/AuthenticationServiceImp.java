package com.example.infinite_level_messaging_system.Services;


import com.example.infinite_level_messaging_system.DTO.LoginDTO;
import com.example.infinite_level_messaging_system.DTO.UserDTO;
import com.example.infinite_level_messaging_system.Entity.User;
import com.example.infinite_level_messaging_system.Exceptions.BadRequestException;
import com.example.infinite_level_messaging_system.Exceptions.DuplicateException;
import com.example.infinite_level_messaging_system.JWT.JwtService;
import com.example.infinite_level_messaging_system.Mapper.UserMapper;
import com.example.infinite_level_messaging_system.Repository.RoleRepository;
import com.example.infinite_level_messaging_system.Repository.UserRepository;
import com.example.infinite_level_messaging_system.Response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import static com.example.infinite_level_messaging_system.Contant.Constant.USER_ROLE;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserServices userServices;

    @Override
    public LoginResponse login(LoginDTO loginDTO) {
        //Kiem tra dang nhap
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword())
        );
        User user = userRepository.findByEmail(loginDTO.getUsername())
                .or(()->userRepository.findByUsername(loginDTO.getUsername()))
                .orElseThrow(() -> new BadRequestException("Username is no exist!"));

         //save online status
        userRepository.save(user);

        return LoginResponse.builder()
                .accessToken(jwtService.generateToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .phone(user.getPhone())
                .user(UserMapper.INSTANCE.userToUserDTO(user))
                .build();
    }

    @Override
    public User registration(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            throw new DuplicateException("Email have been used!");
        }
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()){
            throw new DuplicateException("Phone have been used!");
        }

        User user = User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(roleRepository.getRoleByName(USER_ROLE))
                .build();
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUserLoggedIn(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            System.out.println("Toi dang bi loi"+username+"   "+userServices.getUser(username).getEmail());

            return userServices.getUser(username);
        }
        System.out.println("hu hu hu");
        return null;
    }

    @Override
    public User checkLogin(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);

        // Chek if user is authenticated (not be anonymous)
        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {

            return getUserLoggedIn();
            // Is login

        } else {
            System.out.println("false");
            // Isn't login
            return null;
        }

    }
}
