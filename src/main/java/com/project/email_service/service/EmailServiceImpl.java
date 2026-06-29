package com.project.email_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.project.email_service.dto.EmailRequestDTO;
import com.project.email_service.entity.EmailLog;
import com.project.email_service.repository.EmailLogRepository;
import com.project.email_service.response.ApiResponse;
import com.project.email_service.util.EmailUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final AmazonSimpleEmailService amazonSimpleEmailService;
    private final EmailLogRepository emailLogRepository;

    @Override
    public ApiResponse sendMail(EmailRequestDTO emailRequestDTO) throws Exception {

        List<String> toEmails = emailRequestDTO.getToEmails()
                .stream()
                .map(x -> x.getRecipientEmail())
                .collect(Collectors.toList());

        List<String> ccEmails = emailRequestDTO.getCcEmails() == null
                ? List.of()
                : emailRequestDTO.getCcEmails()
                        .stream()
                        .map(x -> x.getRecipientEmail())
                        .collect(Collectors.toList());

        List<String> bccEmails = emailRequestDTO.getBccEmails() == null
                ? List.of()
                : emailRequestDTO.getBccEmails()
                        .stream()
                        .map(x -> x.getRecipientEmail())
                        .collect(Collectors.toList());

        SendEmailResult result = EmailUtil.sendEmailWithCcAndBcc(
                amazonSimpleEmailService,
                toEmails,
                emailRequestDTO.getCcEmails(),
                bccEmails,
                emailRequestDTO.getSenderEmail(),
                emailRequestDTO.getSubject(),
                emailRequestDTO.getBodyText(),
                emailRequestDTO.getBodyHtml());

        EmailLog log = new EmailLog();

        log.setEmailFrom(emailRequestDTO.getSenderEmail());
        log.setEmailTo(String.join(",", toEmails));
        log.setEmailCc(String.join(",", ccEmails));
        log.setEmailBcc(String.join(",", bccEmails));
        log.setSubject(emailRequestDTO.getSubject());
        log.setBodyText(emailRequestDTO.getBodyText());
        log.setStatus("SUCCESS");
        log.setMessageId(result.getMessageId());
        log.setCreatedOn(LocalDateTime.now());

        emailLogRepository.save(log);

        return new ApiResponse(
                true,
                "Email sent successfully",
                result.getMessageId());
    }
}