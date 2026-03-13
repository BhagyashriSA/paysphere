package com.test.cph.servioceimpl;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendUserCreatedMail(String toEmail, String username) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

//            helper.setTo("bhagyashrisay@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Welcome! Your Account Has Been Created");

            String htmlContent =
                    "<div style='font-family:Arial,sans-serif;max-width:600px;margin:auto;border:1px solid #ddd;padding:20px'>" +
                            "<h2 style='color:#4CAF50;'>Welcome to Our Platform 🎉</h2>" +
                            "<p>Hello <b>" + username + "</b>,</p>" +
                            "<p>Your account has been <b>successfully created</b>.</p>" +
                            "<p>You can now log in and start using the system.</p>" +
                            "<br>" +
                            "<a href='http://localhost:4200/login' " +
                            "style='background:#4CAF50;color:white;padding:10px 20px;text-decoration:none;border-radius:5px'>Login Now</a>" +
                            "<br><br>" +
                            "<p>If you did not request this account, please contact support.</p>" +
                            "<hr>" +
                            "<p style='font-size:12px;color:gray'>© 2026 PaySphere. All rights reserved.</p>" +
                            "</div>";

            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendUserUpdatedMail(String toEmail, String username) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

//            helper.setTo("bhagyashrisay@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Welcome! Your Account Has Been Updated");

            String htmlContent =
                    "<div style='font-family:Arial,sans-serif;max-width:600px;margin:auto;border:1px solid #ddd;padding:20px'>" +
                            "<h2 style='color:#4CAF50;'>Welcome to Our Platform 🎉</h2>" +
                            "<p>Hello <b>" + username + "</b>,</p>" +
                            "<p>Your account has been <b>successfully updated</b>.</p>" +
                            "<p>You can now log in and start using the system.</p>" +
                            "<br>" +
                            "<a href='http://localhost:4200/login' " +
                            "style='background:#4CAF50;color:white;padding:10px 20px;text-decoration:none;border-radius:5px'>Login Now</a>" +
                            "<br><br>" +
                            "<p>If you did not request this account, please contact support.</p>" +
                            "<hr>" +
                            "<p style='font-size:12px;color:gray'>© 2026 PaySphere. All rights reserved.</p>" +
                            "</div>";

            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendResetEmail(String toEmail, String resetLink) throws Exception {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

//        helper.setTo("bhagyashrisay@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject("Reset Your Password");

        String htmlContent = """
        <div style="font-family:Arial,sans-serif;padding:20px">
            <h2 style="color:#333;">Reset Your Password</h2>
            
            <p>Hello,</p>
            
            <p>You requested to reset your password. Click the button below to reset it.</p>
            
            <a href="%s" 
               style="display:inline-block;padding:12px 20px;
               background-color:#4CAF50;color:white;
               text-decoration:none;border-radius:5px;
               font-weight:bold;">
               Reset Password
            </a>
            
            <p style="margin-top:20px;">This link will expire in <b>30 minutes</b>.</p>
            
            <p>If you did not request this, please ignore this email.</p>
            
            <hr>
            <p style="font-size:12px;color:gray;">
                © 2026 PaySphere
            </p>
        </div>
        """.formatted(resetLink);

        helper.setText(htmlContent, true);

        mailSender.send(message);
    }
}

