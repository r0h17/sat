package com.sch.sat.service;

import com.sch.sat.core.UserGrades;
import org.apache.commons.lang3.text.StrSubstitutor;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by rn250152 on 11/29/16.
 */
public class MessageService {

    final Session session;

    public MessageService(String smtpHost, String smtpPort, String template) {
        this.template = template;
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        session = Session.getInstance(props);
    }

    final String template;

    public boolean sendEmail(UserGrades userGrade){
        Map<String, String> valuesMap = new HashMap<String, String>();
        valuesMap.put("firstName", userGrade.getFirstName());
        valuesMap.put("testName", userGrade.getCourseName());
        valuesMap.put("date", userGrade.getDate().toString());
        valuesMap.put("grade", userGrade.getGrade().toString());
        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("admin@sat.com"));
            message.setSubject("Test Score Update for "+userGrade.getCourseName()+" held on "+userGrade.getDate());
            message.setText(sub.replace(new String(template)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(userGrade.getUserName()));
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
