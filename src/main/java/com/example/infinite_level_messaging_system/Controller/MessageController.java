package com.example.infinite_level_messaging_system.Controller;

import com.example.infinite_level_messaging_system.DTO.MessageDTO;
import com.example.infinite_level_messaging_system.Entity.Message;
import com.example.infinite_level_messaging_system.Entity.User;
import com.example.infinite_level_messaging_system.Exceptions.BadRequestException;
import com.example.infinite_level_messaging_system.Response.ApiResponse;
import com.example.infinite_level_messaging_system.Services.AuthenticationService;
import com.example.infinite_level_messaging_system.Services.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final AuthenticationService authenticationService;

    @GetMapping("")
    public ResponseEntity<?> getAllMessage(){
        try {
            List<Message> messages = messageService.getAll();
            return ResponseEntity.ok(ApiResponse
                    .builder()
                            .status(200)
                            .message("Get all message success!")
                            .data(messages)
                            .success(true)
                    .build());
        } catch (Exception e){
            return ResponseEntity.ok(ApiResponse
                    .builder()
                            .error(e.getMessage())
                            .status(400)
                            .message("Get all message fail!")
                            .success(false)
                    .build());
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getMessageByUserId(@PathVariable String username){
        try {
            List<Message> messages = messageService.getMessageByUsername(username);
            return ResponseEntity.ok(ApiResponse
                    .builder()
                    .status(200)
                    .message("Get all message success!")
                    .data(messages)
                    .success(true)
                    .build());
        } catch (BadRequestException e){
            return ResponseEntity.ok(ApiResponse
                    .builder()
                    .error(e.getMessage())
                    .status(400)
                    .message("Get all message fail!")
                    .success(false)
                    .build());
        }
    }

    @PostMapping("/post")
    public ResponseEntity<?> postMessage(@RequestBody MessageDTO messageDTO, HttpServletRequest request){
        try {

            User user = authenticationService.checkLogin(request);
            if (user == null || !user.getUsername().equals(messageDTO.getUsername())){
                System.out.println("username is wrong");
                return ResponseEntity.ok(ApiResponse
                        .builder()
                        .error("Unauthorized")
                        .status(401)
                        .message("Login to post message!")
                        .success(false)
                        .build());
            }

            Message message = messageService.saveMessage(messageDTO);
            return ResponseEntity.ok(ApiResponse
                    .builder()
                    .status(200)
                    .message("post message success!")
                    .data(message)
                    .success(true)
                    .build());

        } catch (BadRequestException e){
            return ResponseEntity.ok(ApiResponse
                    .builder()
                    .error(e.getMessage())
                    .status(400)
                    .message("post message fail!")
                    .success(false)
                    .build());
        }
    }


}
