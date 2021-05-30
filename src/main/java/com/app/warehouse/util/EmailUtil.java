
package com.app.warehouse.util;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class EmailUtil {

	private static final Logger LOG = LoggerFactory.getLogger(EmailUtil.class);

	@Autowired
	private JavaMailSender mailSender;

	public boolean sendEmail(String to, String[] cc, String[] bcc, String subject, String text, MultipartFile file) {
		LOG.info("Inside sendEmail():");
		boolean flag = false;
		try {
			// 1. create one empty email (mime message)
			MimeMessage message = mailSender.createMimeMessage();

			// 2. fill details
			MimeMessageHelper helper = new MimeMessageHelper(message, file != null);

			helper.setTo(to);
			if (cc != null && cc.length > 0)
				helper.setCc(cc);
			if (bcc != null && bcc.length > 0)
				helper.setBcc(bcc);

			helper.setSubject(subject);
			helper.setText(text);

			// 3. add attachment
			if (file != null)
				helper.addAttachment(file.getOriginalFilename(), file);

			// 4. send email
			mailSender.send(message);
			flag = true;
		} catch (Exception e) {
			LOG.error("Exception inside sendEmail(): " + e.getMessage());
			e.printStackTrace();
		}

		return flag;
	}

	/***
	 * This method is used to send email with below details
	 * 
	 * @param to
	 * @param subject
	 * @param text
	 * @return
	 */
	public boolean sendEmail(String to, String subject, String text) {
		LOG.info("Inside sendEmail():");
		return sendEmail(to, null, null, subject, text, null);
	}

	/***
	 * This method is used to send email with below details
	 * 
	 * @param to
	 * @param subject
	 * @param text
	 * @param file
	 * @return
	 */
	public boolean sendEmail(String to, String subject, String text, MultipartFile file) {
		LOG.info("Inside sendEmail():");
		return sendEmail(to, null, null, subject, text, file);
	}

}