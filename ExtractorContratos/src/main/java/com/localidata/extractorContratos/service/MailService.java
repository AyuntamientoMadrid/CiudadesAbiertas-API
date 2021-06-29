package com.localidata.extractorContratos.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.localidata.extractorContratos.util.ConfigExtractor;


public class MailService {
	
	private ConfigExtractor config= new ConfigExtractor();
	
	private Properties props=null;
	
	private static Logger log = Logger.getLogger(MailService.class);
	
	private static Session session = null;
	
	public MailService()
	{
		props = new Properties();
		props.put("mail.smtp.host", config.getMailHost());
		props.put("mail.smtp.socketFactory.port", config.getMailPort());
		//props.put("mail.smtp.protocol", "smtp");
		if (config.getMailAuth()) {
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");        
//	        props.put("mail.smtp.socketFactory.fallback", "false");
//			props.put("java.net.preferIPv4Stack", "true");
		}
		props.put("mail.smtp.port", config.getMailPort());
	}
	
	public void enviar(String asunto, String texto)
	{	
		log.info("[enviar] Send mail process...");
		//CMG CAMBIO PARA INCLUIR EL ENTORNO CONFIGURADO
		asunto += " [ENTORNO:"+config.getEntorno()+"]";
		log.debug("[enviar] [asunto:"+asunto+"] [texto:"+texto+"]");
		
		if (!config.getMailSimular()) {
			try {
				if (session==null) {
					if (config.getMailAuth()) {
						session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(config.getMailUser(), config.getMailPass());
							}
						});
					}
				}				

				Message message = new MimeMessage(session);
				
				InternetAddress internetAddress = new InternetAddress(config.getMailFrom());				
				message.setFrom(internetAddress);
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(config.getMailTo()));
				message.setSubject(asunto);
				message.setText(texto);

				Transport.send(message);
				log.info("[enviar] [asunto:" + asunto + "] Send mail process - OK");

			} catch (Exception e) {
				log.error("[enviar] Error sending mail", e);
			}
		}else {
			log.info("[enviar] [SIMULADO] Send mail process - OK");
		}
		
	}

}
