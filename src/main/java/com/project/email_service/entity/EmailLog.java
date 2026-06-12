package com.project.email_service.entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "email_log")
public class EmailLog {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    private String emailFrom;

	    @Column(columnDefinition = "TEXT")
	    private String emailTo;

	    @Column(columnDefinition = "TEXT")
	    private String emailCc;

	    @Column(columnDefinition = "TEXT")
	    private String emailBcc;

	    private String subject;

	    @Column(columnDefinition = "TEXT")
	    private String bodyText;

	    private String status;

	    private String messageId;

	    private LocalDateTime createdOn;

}
