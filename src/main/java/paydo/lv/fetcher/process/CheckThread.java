package paydo.lv.fetcher.process;

import paydo.lv.fetcher.MainAppController;
import paydo.lv.fetcher.db.Person;
import paydo.lv.fetcher.db.PersonRepository;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class CheckThread extends Thread {
    PersonRepository pr = new PersonRepository();
    Filter filter = new Filter();
    private int realAllEmails;
    private int start;
    private int end;
    private Message[] msg;
    private int pos;
    private int couter = 0;
    CountDownLatch cd;
    private String login;

    public CheckThread(Message[] msg, int start, int end, int pos, CountDownLatch cd, String login) {
        this.start = start;
        this.end = end;
        this.msg = msg;
        this.pos = pos;
        this.cd = cd;
        this.login = login;
    }

    @Override
    public void run() {
        //check if need check email for the begining(new checkbox)
        if (!MainAppController.newCheckB) {
            Person p = pr.getByName(login);
            if ( p != null ) {
                ReadEmail.sendToDbCore(pos, "end", end);
                ReadEmail.sendToDbCore(MainAppController.allEmals);
                if ( pos == 0 ) {
                    start = p.getC0Start();
                    MainAppController.lastReaded0Date = p.getC0Date();
                    MainAppController.readed0Emails = start - 1;
                } else if ( pos == 1 ) {
                    start = p.getC1Start();
                    MainAppController.lastReaded0Date = p.getC1Date();
                    MainAppController.readed0Emails = start - 1;
                } else if ( pos == 2 ) {
                    start = p.getC2Start();
                    MainAppController.lastReaded0Date = p.getC2Date();
                    MainAppController.readed0Emails = start - 1;
                }
                MainAppController.addToLog("Was checked: " + start + "/" + end + " mails", "g");
            }
        }
        MainAppController.addToLog("Core" + pos + " started fetching ..", "y");
        for (int i = start; i < end;  i++) {
            MainAppController.btnVis = false;
            try {
                Message message = msg[i];
                String text = message.getContent().toString();
                if ( text.contains("MimeMultipart") ) {
                    couter++;
                    filter.filter(message);
                }
                if ( pos == 0 ) {
                    MainAppController.lastReaded0Date = message.getSentDate().toString();
                    MainAppController.readed0Emails++;
                } else if (pos == 1) {
                    MainAppController.lastReaded1Date = message.getSentDate().toString();
                    MainAppController.readed1Emails++;
                } else if (pos == 2) {
                    MainAppController.lastReaded2Date = message.getSentDate().toString();
                    MainAppController.readed2Emails++;
                }
                if (i%20 == 0 || i == end - 1) {
                    ReadEmail.sendToDbCore(pos, "start", (i + 1) );
                    ReadEmail.sendToDbCore(pos, "date", message.getSentDate().toString());
                }
            } catch (IOException e) {
                MainAppController.addToLog( "Can't get email msg", "r");
                e.printStackTrace();
            } catch (MessagingException e) {
                MainAppController.addToLog("Can't get info from email","r");
                e.printStackTrace();
            }
        }
        cd.countDown();
        MainAppController.addToLog("Core" + pos + " finished ..", "y");
        MainAppController.btnVis = true;
    }
}
