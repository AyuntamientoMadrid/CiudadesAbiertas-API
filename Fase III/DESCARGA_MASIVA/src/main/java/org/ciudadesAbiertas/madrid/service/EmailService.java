package org.ciudadesAbiertas.madrid.service;

import org.ciudadesAbiertas.madrid.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService
{
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    
    @Autowired
    private JavaMailSender javaMailSender;
    
   
    public void sendSimpleMessage( String to, String subject, String text, String from) {
	       
        SimpleMailMessage message = new SimpleMailMessage(); 
        if (to.contains(","))
        {
            String[] split = to.split(",");
            for (int i = 0; i < split.length; i++)
	    {
        	split[i]=split[i].trim();
	    }
            message.setTo(split);
        }
        else
        {
            message.setTo(to); 
        }
                
        message.setSubject(subject); 
        message.setText(text);
        if (Util.validValue(from))
        {
            message.setFrom(from);
        }
        
        Thread thread = new Thread()
	{
	    public void run()
	    {
		try
		{
		    javaMailSender.send(message);
		}
		catch (Exception e)
		{
		    log.error("Error sending mail",e);
		}
		
	    }
	};

	thread.start();
    }
    
    
    public void sendSimpleMessage( String to, String subject, String text) {
	       
	sendSimpleMessage(  to,  subject,  text,  null);
    }
}
