package com.project.email_service.dto;
import java.util.List;

import lombok.Data;

@Data
public class EmailRequestDTO {
	private String senderEmail;

    private String subject;

    private String bodyText;

    private String bodyHtml;

    private List<UsersEmail> toEmails;

    private List<UsersEmail> ccEmails;

    private List<UsersEmail> bccEmails;

}
