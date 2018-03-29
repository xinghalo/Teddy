package com.xingoo.teddy.manager;

import com.xingoo.teddy.utils.TeddyConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailSender {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Transport transport = null;
    private Session session = null;


    public EmailSender(){}

    private void init(){
        if (transport == null || session == null) {
            Properties properties = System.getProperties();
            // 设置邮件服务器
            properties.setProperty("mail.host", TeddyConf.get("mail.host"));
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.setProperty("mail.smtp.auth", "true");

            try {
                session = Session.getInstance(properties);
                transport = session.getTransport();
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
    }

    public void send(String email, String subject, String text) {

        init();

        try {
            // create message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(TeddyConf.get("mail.from")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject);
            message.setText(text);

            // transport send
            transport.connect(TeddyConf.get("mail.host"), TeddyConf.get("mail.from"), TeddyConf.get("mail.passwd"));
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            logger.info("email success：-->"+email+"["+subject+"]"+text);
        }catch(Exception e){
            logger.error("email error，"+e.getMessage());
        }
    }
}
