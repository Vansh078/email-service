package com.project.email_service.util;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.project.email_service.dto.UsersEmail;

public class EmailUtil {

    private EmailUtil() {
    }

    public static SendEmailResult sendEmailWithCcAndBcc(
            AmazonSimpleEmailService amazonSimpleEmailService,
            List<String> toEmails,
            List<UsersEmail> ccEmails,
            List<String> bccEmails,
            String from,
            String subject,
            String bodyText,
            String bodyHtml) {

        List<String> ccEmailList =
                Optional.ofNullable(ccEmails)
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(UsersEmail::getRecipientEmail)
                        .collect(Collectors.toList());

        Destination destination = new Destination()
                .withToAddresses(toEmails)
                .withCcAddresses(ccEmailList)
                .withBccAddresses(bccEmails);

        Body body = new Body();

        if (bodyText != null && !bodyText.isBlank()) {
            body.withText(
                    new Content().withData(bodyText)
            );
        }

        if (bodyHtml != null && !bodyHtml.isBlank()) {
            body.withHtml(
                    new Content().withData(bodyHtml)
            );
        }

        Message message = new Message()
                .withSubject(
                        new Content().withData(subject)
                )
                .withBody(body);

        SendEmailRequest request = new SendEmailRequest()
                .withSource(from)
                .withDestination(destination)
                .withMessage(message)
                .withConfigurationSetName("my-first-configuration-set");

        SendEmailResult result = amazonSimpleEmailService.sendEmail(request);

        System.out.println("SES Message ID = " + result.getMessageId());

        return result;
    }
}