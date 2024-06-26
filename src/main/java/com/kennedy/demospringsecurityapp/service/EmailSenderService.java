package com.kennedy.demospringsecurityapp.service;

import com.kennedy.demospringsecurityapp.dto.EmailDetails;

public interface EmailSenderService {
    void sendEmailAlert(EmailDetails emailDetails);
}
