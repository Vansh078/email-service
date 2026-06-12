package com.project.email_service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersEmail {
	private String recipientEmail;

    private String firstName;

    private String fullName;

}
