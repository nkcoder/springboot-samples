package org.nkcoder.utils;

import org.springframework.stereotype.Component;

@Component
public class EmailManager {
    public void sendEmail(String uuid) {
        System.out.println("Sending email: " + uuid);
    }
}
