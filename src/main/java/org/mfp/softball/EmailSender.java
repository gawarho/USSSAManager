/**
 * 
 */
package org.mfp.softball;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author gawarho
 *
 */
public class EmailSender { 
	private Session session;
	final private String username = "glen.warholic@gmail.com";
	final private String password = "!Z2x#C4v%B";
	static private Logger logger = null;
	private int throttleSecs = 300;

	public EmailSender() {

		logger = LogManager.getLogger(EmailSender.class.getName());
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}

	/**
	 * 
	 * @param addrListFile
	 * @param subject
	 * @param htmlFile
	 * @param attachmentFile
	 * @return
	 */
	public int listSendMultipart(String addrListFile, String subject, String htmlFile, String attachmentFile) {
		int sent = 0;
		Map<String, String> m = new HashMap<String, String> ();

		try {
			BufferedReader br = new BufferedReader(new FileReader(addrListFile));
			String line = null;
			int lineCount = 0;
			while((line = br.readLine()) != null) {
				if(line.length() > 4) {
					m.put(line.toLowerCase(), line);
					lineCount++;
				}
			}
			br.close();
			logger.info("Lines in address file: " + lineCount + "  Unique Email Addresses: " + m.size());
			SortedSet<String> ts = new TreeSet<String>(m.keySet());
            for(String emailAddr : ts) {
				logger.debug("Sending email to " + emailAddr);
				boolean status = buildAndSendMultipart(emailAddr, subject, htmlFile, attachmentFile);
				if(status == true) {
					sent++;
					logger.debug("Sending successful to " + emailAddr);
				} else {
					logger.error("Failed sending to " + emailAddr);
				}
				if(throttleSecs > 0) {
					try {
						Thread.sleep(throttleSecs * 1000);
					} catch(Exception e) {
						;
					}
				}
			}
		} catch(Exception e) {
			logger.error("Exception: " + e);
		}

		logger.info("Sent " + sent + " emails successfully");

		return sent;
	}

	/**
	 * 
	 * @param toAddr
	 * @param subject
	 * @param htmlFile
	 * @param attachmentFile
	 * @return
	 */
	public boolean buildAndSendMultipart(String  toAddr, String subject, String htmlFile, String attachmentFile) {
		boolean retVal = false;
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("glen.warholic@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toAddr));
			message.setSubject(subject);
			Multipart mp = new MimeMultipart();

			if(htmlFile != null) {
				try {
					File file = new File(htmlFile);
					FileInputStream fis = new FileInputStream(file);
					byte[] data = new byte[(int) file.length()];
					fis.read(data);
					fis.close();

					String htmlBody = new String(data, "UTF-8");	        
					MimeBodyPart htmlPart = new MimeBodyPart();
					htmlPart.setContent(htmlBody, "text/html");
					mp.addBodyPart(htmlPart);
				} catch (Exception e ){
					logger.error("Exception building HTML part: " + e);
				}
			}

			if(attachmentFile != null) {
				try {
					File file = new File(attachmentFile);
					FileInputStream fis = new FileInputStream(file);
					byte[] attachmentData = new byte[(int) file.length()];
					fis.read(attachmentData);
					fis.close();

					MimeBodyPart attachment = new MimeBodyPart();
					attachment.setFileName(attachmentFile);
					attachment.setContent(attachmentData, "application/pdf");
					mp.addBodyPart(attachment);
				} catch (Exception e ){
					logger.error("Exception building PDF part: " + e);
				}
			}

			try {
				message.setContent(mp);

				Transport.send(message);
				retVal = true;
			} catch (Exception e) {
				logger.error("Exception sending email: " + e);
				retVal = false;
			} 

		} catch (MessagingException e) {
			logger.debug("Exception sending: " + e);
			throw new RuntimeException(e);
		}

		return retVal;		
	}


	public boolean buildAndSend() {
		boolean retVal = false;
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("glen.warholic@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("glen.warholic@gmail.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler,"
					+ "\n\n No spam to my email, please!");

			Transport.send(message);

			logger.debug("Sending successful");
			retVal = true;

		} catch (MessagingException e) {
			logger.debug("Exception sending: " + e);
			throw new RuntimeException(e);
		}

		return retVal;
	}



	public int getThrottleSecs() {
		return throttleSecs;
	}

	public void setThrottleSecs(int throttleSecs) {
		this.throttleSecs = throttleSecs;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EmailSender es = new EmailSender();
		if(args.length < 3) {
			System.err.println("usage: java org.mfp.EmailSender addressListFile subject bodyHtmlFile [pdfAttachmentFile]");
			System.exit(1);
		}
		String addrFile = args[0];
		String subject = args[1];
		String htmlFile = args[2];
		String pdfFile = null;
		if(args.length > 3) {
			pdfFile = args[3];
		}
		es.setThrottleSecs(30);
		int success = es.listSendMultipart(addrFile, subject, htmlFile, pdfFile);

		System.exit(success);
	}
}