package paydo.lv.fetcher.db;

public class Person {
    private String login;
    private String pass;
    private int allEmail;
    private String c0Date = null;
    private String c1Date = null;
    private String c2Date = null;
    private int c0Start;
    private int c0End;
    private int c1Start;
    private int c1End;
    private int c2Start;
    private int c2End;

    public Person(String login, String pass) {
        this.login = login;
        this.pass = pass;;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getAllEmail() {
        return allEmail;
    }

    public void setAllEmail(int allEmail) {
        this.allEmail = allEmail;
    }

    public String getC0Date() {
        return c0Date;
    }

    public void setC0Date(String c0Date) {
        this.c0Date = c0Date;
    }

    public String getC1Date() {
        return c1Date;
    }

    public void setC1Date(String c1Date) {
        this.c1Date = c1Date;
    }

    public String getC2Date() {
        return c2Date;
    }

    public void setC2Date(String c2Date) {
        this.c2Date = c2Date;
    }

    public int getC0Start() {
        return c0Start;
    }

    public void setC0Start(int c0Start) {
        this.c0Start = c0Start;
    }

    public int getC0End() {
        return c0End;
    }

    public void setC0End(int c0End) {
        this.c0End = c0End;
    }

    public int getC1Start() {
        return c1Start;
    }

    public void setC1Start(int c1Start) {
        this.c1Start = c1Start;
    }

    public int getC1End() {
        return c1End;
    }

    public void setC1End(int c1End) {
        this.c1End = c1End;
    }

    public int getC2Start() {
        return c2Start;
    }

    public void setC2Start(int c2Start) {
        this.c2Start = c2Start;
    }

    public int getC2End() {
        return c2End;
    }

    public void setC2End(int c2End) {
        this.c2End = c2End;
    }

    @Override
    public String toString() {
        return this.login;
    }
}
