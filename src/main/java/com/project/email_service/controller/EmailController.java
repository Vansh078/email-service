package com.project.email_service.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.email_service.dto.EmailRequestDTO;
import com.project.email_service.response.ApiResponse;
import com.project.email_service.service.EmailService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
	private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse> sendMail(@RequestBody EmailRequestDTO request) throws Exception {

        return ResponseEntity.ok(emailService.sendMail(request));
    }

}
