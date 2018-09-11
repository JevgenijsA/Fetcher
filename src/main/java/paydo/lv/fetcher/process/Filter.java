package paydo.lv.fetcher.process;

import paydo.lv.fetcher.MainAppController;
import paydo.lv.fetcher.fetched.TextConvertor;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import java.io.*;

public class Filter extends Thread {
    String subject;
    TextConvertor tc = new TextConvertor();

    public void filter(Message msg) {
        try {
            if ( msg.getSubject() != null ) {
                subject = msg.getSubject().toString();
                if ( subject.contains("TC40 - ARN") ) {
                    Object content = msg.getContent();
                    if ( content.getClass().getName().equals("javax.mail.internet.MimeMultipart") ) {
                        Multipart multiPart = (Multipart) content;
                        BodyPart bp = multiPart.getBodyPart(0);
                        String text = (bp.getContent().toString());
                        String date = msg.getSentDate().toString();
                        if (!ReadEmail.isTheDateInBD("fraud", subject)) {
                            tc.generateFraud(text, date, subject);
                            MainAppController.fraudCount++;
                        }

                    }
                } else if ( subject.contains("Dispute - ARN") ) {
                    Object content = msg.getContent();
                    if ( content.getClass().getName().equals("javax.mail.internet.MimeMultipart") ) {
                        Multipart multiPart = (Multipart) content;
                        BodyPart bp = multiPart.getBodyPart(0);
                        String text = (bp.getContent().toString());
                        String date = msg.getSentDate().toString();
                        if (!ReadEmail.isTheDateInBD("cb", subject)) {
                            tc.generateCB(text, date);
                            MainAppController.cbCount++;
                        }
                    } else return;
                } else return;
            }
        } catch (MessagingException ex) {
            MainAppController.addToLog("Can't get info from email", "r");
            ex.printStackTrace();
        } catch (IOException ex) {
            MainAppController.addToLog("Can't get info from fraud", "r");
            ex.printStackTrace();
        }
    }

}
