package com.project.email_service.service;
import com.project.email_service.dto.EmailRequestDTO;
import com.project.email_service.response.ApiResponse;
public interface EmailService {
	
	ApiResponse sendMail(EmailRequestDTO emailRequestDTO) throws Exception;

}
