
package com.johnson.utility;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.johnson.model.EntityUser;

@Component
public class EmailConstructor {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;

	@Autowired
	private TemplateEngine templateEngine;

	public MimeMessage constructNewUserEmail(EntityUser user, String password) throws MessagingException {
		Context context = new Context();
		context.setVariable("user", user);
		context.setVariable("password", password);
		String text = templateEngine.process("newUserEmailTemplate", context);

		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper email = new MimeMessageHelper(message, true);
		email.setPriority(1);
		email.setTo(user.getEmail());
		email.setSubject("Welcome To Orchard");
		email.setText(text, true);
		email.setFrom(new InternetAddress(env.getProperty("support.email")));

		return message;
	}

	public MimeMessage constructResetPasswordEmail(EntityUser user, String password) throws MessagingException {
		Context context = new Context();
		context.setVariable("user", user);
		context.setVariable("password", password);
		String text = templateEngine.process("resetPasswordEmailTemplate", context);

		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper email = new MimeMessageHelper(message, true);
		email.setPriority(1);
		email.setTo(user.getEmail());
		email.setSubject("Welcome To Orchard");
		email.setText(text, true);
		email.setFrom(new InternetAddress(env.getProperty("support.email")));

		return message;
	}

	public MimeMessage constructUpdateUserProfileEmail(EntityUser user, String password) throws MessagingException {

		Context context = new Context();
		context.setVariable("user", user);
		context.setVariable("password", password);
		String text = templateEngine.process("updateUserProfileEmailTemplate", context);

		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper email = new MimeMessageHelper(message, true);
		email.setPriority(1);
		email.setTo(user.getEmail());
		email.setSubject("Welcome To Orchard");
		email.setText(text, true);
		email.setFrom(new InternetAddress(env.getProperty("support.email")));

		return message;
	}

}
