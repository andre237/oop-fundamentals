package com.utility.service.impl;

import com.utility.common.dto.EmailRequestDTO;
import com.utility.common.exceptions.BusinessException;
import com.utility.service.contracts.PlaceholderResolverService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class EmailSendingService {

    private static final String BODY_RESOLVE_ERROR = "Failed to build email body";

    private final PlaceholderResolverService resolverService;

    @Autowired
    public EmailSendingService(PlaceholderResolverService resolverService) {
        this.resolverService = resolverService;
    }

    public void processEmailRequest(EmailRequestDTO emailRequestDTO) {
        String emailBody = resolverService.resolve(emailRequestDTO.getType(), emailRequestDTO.getData());
        if (StringUtils.trimToNull(emailBody) == null) throw new BusinessException(BODY_RESOLVE_ERROR);

        CompletableFuture.runAsync(() -> this.sendEmail("", emailBody, emailRequestDTO.getRecipients()));
    }

    // encapsulated method to send email, currently mocked
    private void sendEmail(String header, String body, List<String> recipients) {
        System.out.printf("Sending email to %s\n", String.join(",", recipients));
        System.out.printf("Body = \n%s \n", body);
    }

}
