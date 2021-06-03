
package com.app.warehouse.util;

import java.io.InputStream;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.app.warehouse.model.WhUserType;

@Component
public class EmailUtil {

	private static final Logger log = LoggerFactory.getLogger(EmailUtil.class);

	@Autowired
	private JavaMailSender mailSender;

	/***
	 * This method is used to send email with below details
	 * 
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param text
	 * @param file
	 * @return
	 */
	public boolean sendEmail(String to, String[] cc, String[] bcc, String subject, String text, MultipartFile file) {
		log.info("Inside sendEmail():");
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
			helper.setText(text, true);

			// add attachment
			if (file != null)
				helper.addAttachment(file.getOriginalFilename(), file);

			// 3. send email
			mailSender.send(message);
			flag = true;
		} catch (Exception e) {
			log.error("Exception inside sendEmail():" + e.getMessage());
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
		log.info("Inside sendEmail():");
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
		log.info("Inside sendEmail():");
		return sendEmail(to, null, null, subject, text, file);
	}

	public String getWhUserTemplateData(WhUserType whUserType) {
		log.info("Inside getWhUserTemplateData():");
		try {
			// loading file
			ClassPathResource file = new ClassPathResource("whusermt.txt");
			// read data as Stream
			InputStream fis = file.getInputStream();
			// load file data into byte[]
			byte[] arr = new byte[fis.available()];
			fis.read(arr);
			// convert byte[] to String
			String text = new String(arr);

			text = text.replace("{{Username}}:", whUserType.getUserCode())
					.replace("{{Usertype}}:", whUserType.getUserType())
					.replace("{{Usercontact}}:", whUserType.getUserContact())
					.replace("{{UserIDNumber}}:", whUserType.getUserIdNum());
			return text;
		} catch (Exception e) {
			log.error("Exception inside getWhUserTemplateData():" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}