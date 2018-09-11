package paydo.lv.fetcher.process;

import paydo.lv.fetcher.MainAppController;

import javax.mail.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import paydo.lv.fetcher.db.FraudCBRepository;
import paydo.lv.fetcher.db.Person;
import paydo.lv.fetcher.db.PersonRepository;
import paydo.lv.fetcher.excel.CBExcel;
import paydo.lv.fetcher.excel.FraudExcel;
import paydo.lv.fetcher.fetched.CB;
import paydo.lv.fetcher.fetched.Fraud;

public class ReadEmail {
    public static PersonRepository pr = new PersonRepository();
    public static FraudCBRepository fr = new FraudCBRepository();
    public static ArrayList<Fraud> fraudArr;
    public static ArrayList<CB> cbArr;
    public static FraudExcel fe = new FraudExcel();
    public static CBExcel cbe = new CBExcel();

    public static void check(final String user, final String password, final int core) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //check fraud and cb in bd
                isTheDateInBD("fraud","-");
                isTheDateInBD("cb","-");
                MainAppController.fraudCount = fraudArr.size();
                MainAppController.cbCount = cbArr.size();
                try {
                    CountDownLatch cdl = new CountDownLatch(core);
                    Properties properties = new Properties();

                    properties.setProperty("mail.pop3.host", "pop.gmail.com");
                    properties.setProperty("mail.pop3.port", "995");
                    properties.setProperty("mail.pop3.starttls.enable", "true");
                    Session emailSession = Session.getDefaultInstance(properties);

                    Store store = emailSession.getStore("imaps");

                    store.connect("pop.gmail.com", user, password);

                    Folder emailFolder = store.getFolder("INBOX");
                    emailFolder.open(Folder.READ_ONLY);

                    Message[] messages = emailFolder.getMessages();
                    MainAppController.allEmals = messages.length;
                    MainAppController.lastReceivedDate = messages[MainAppController.allEmals - 1].getSentDate().toString();

                    //prepare to multy threading
                    int divAllEmails = (int)MainAppController.allEmals / core;
                    int progress = 0;
                    long start = System.currentTimeMillis();
                    for (int i = 0; i < core; i++) {
                        if (i != core - 1) {
                            new CheckThread(messages, progress, progress + divAllEmails, i, cdl, MainAppController.login).start();
                            progress += divAllEmails;
                        } else {
                            new CheckThread(messages, progress, MainAppController.allEmals, i, cdl, MainAppController.login).start();
                        }
                    }
                    MainAppController.addToLog("start fetching", "g");
                    MainAppController.addUser();
                    cdl.await();
                    MainAppController.addToLog("finish fetching - " + (System.currentTimeMillis() - start)/1000 + "sec.", "g,u");
                    emailFolder.close(false);

                    //write excel file
                    if (fe.isFileFraudExist())fe.updateFraud(fraudArr);
                    else fe.writeFraud(fraudArr);
                    if (cbe.isFileCBExist())cbe.updateCB(cbArr);
                    else cbe.writeCB(cbArr);

                    store.close();


                } catch (IllegalStateException e) {
                    MainAppController.addToLog("2 Not a FX thread", "r");
                    e.printStackTrace();
                } catch (AuthenticationFailedException e) {
                    MainAppController.addToLog("Wrong pass or email", "r");
                    e.printStackTrace();;
                } catch (InterruptedException e) {
                    MainAppController.addToLog("Trouble with join thread", "r");
                    e.printStackTrace();
                } catch (NoSuchProviderException e) {
                    MainAppController.addToLog("No such provider", "r");
                    e.printStackTrace();
                } catch (MessagingException e) {
                    MainAppController.addToLog("Problem with messages", "r");
                    e.printStackTrace();
                }
            }
        });
        t1.start();
    }

    public static void sendToDbCore(int core, String type, int value) {
        Person p = pr.getByName(MainAppController.login);
        if (core == 0) {
            if (type.equals("start")) {
                p.setC0Start(value);
                pr.update(p);
            } else if(type.equals("end")) {
                p.setC0End(value);
                pr.update(p);
            }
        } else if (core == 1) {
            if ( type.equals("start") ) {
                p.setC1Start(value);
                pr.update(p);
            } else if ( type.equals("end") ) {
                p.setC1End(value);
                pr.update(p);
            }
        } else if (core == 2) {
            if ( type.equals("start") ) {
                p.setC2Start(value);
                pr.update(p);
            } else if ( type.equals("end") ) {
                p.setC2End(value);
                pr.update(p);
            }
        }
    }

    public static void sendToDbCore(int core, String type, String value) {
        Person p = pr.getByName(MainAppController.login);
        if (core == 0) {
            if (type.equals("date")) {
                p.setC0Date(value);
                pr.update(p);
            }
        } else if (core == 1) {
            if ( type.equals("date") ) {
                p.setC1Date(value);
                pr.update(p);
            }
        } else if (core == 2) {
            if ( type.equals("date") ) {
                p.setC2Date(value);
                pr.update(p);
            }
        }
    }

    public static void sendToDbCore(int value) {
        Person p = pr.getByName(MainAppController.login);
        p.setAllEmail(value);
        pr.update(p);
    }

    public static boolean isTheDateInBD(String type, String subject) {
        if (type.equals("fraud")) {
            fraudArr = fr.getAllFraud();
            int fraudArrSize = fraudArr.size();
            for (int i = 0; i < fraudArrSize; i++) {
                if (subject.equals("-")) return false;
                else if (fraudArr.get(i).getARN().equals((subject.split(" "))[3])) return true;
            }
            return false;
        } else if(type.equals("cb")) {
            cbArr = fr.getAllCB();
            int cbArrSize = cbArr.size();
            for (int i = 0; i < cbArrSize; i++) {
                if (subject.equals("-")) return false;
                if (cbArr.get(i).getARN().equals((subject.split(" "))[3])) return true;
            }
            return false;
        } else return false;
    }
}

