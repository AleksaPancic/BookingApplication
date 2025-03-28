package com.DeskBooking.DeskBooking.service;

import java.nio.charset.StandardCharsets;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.DeskBooking.DeskBooking.model.Mail;
import org.thymeleaf.spring6.SpringTemplateEngine;


@Service
public class EmailSenderServiceImpl implements EmailSenderService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	private final static Logger log = LoggerFactory.getLogger(EmailSenderServiceImpl.class);
	
	public void send(Mail mail) {
		try {
			log.info("Sending email...");
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, 
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());			
			helper.addAttachment("deskbooking.png", new ClassPathResource("/static/deskbookIng1.png"));

			Context context = new Context();
			context.setVariables(mail.getProps());
			
			String html = templateEngine.process("mailTemplate", context);
			
			helper.setTo(mail.getMailTo());
			helper.setText(html, true);
			helper.setSubject("Deskbooking email confirmation");
			helper.setFrom("helpdesk@enjoying.rs");
			
			mailSender.send(mimeMessage);
			log.info("Email sent successfuly!");
			
		} catch (MessagingException e) {
			log.error("Failed to send email", e);
			throw new IllegalStateException("Failed to send email");
		}
	}

	@Override
	public void sendResetPasswordMail(Mail mail) {
		try {
			log.info("Sending email...");
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, 
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());			
			helper.addAttachment("deskbooking.png", new ClassPathResource("/static/deskbookIng1.png"));

			Context context = new Context();
			context.setVariables(mail.getProps());
			
			String html = templateEngine.process("mailResetPasswordTemplate", context);
			
			helper.setTo(mail.getMailTo());
			helper.setText(html, true);
			helper.setSubject("Deskbooking email confirmation");
			helper.setFrom("helpdesk@enjoying.rs");
			
			mailSender.send(mimeMessage);
			log.info("Email sent successfuly!");
			
		} catch (MessagingException e) {
			log.error("Failed to send email", e);
			throw new IllegalStateException("Failed to send email");
		}
		
	}
}
