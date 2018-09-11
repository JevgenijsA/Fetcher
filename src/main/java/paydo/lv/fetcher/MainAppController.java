package paydo.lv.fetcher;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import paydo.lv.fetcher.db.Person;
import paydo.lv.fetcher.db.PersonRepository;
import paydo.lv.fetcher.process.Calc;
import paydo.lv.fetcher.process.ReadEmail;

public class MainAppController {

    public ReadEmail re = new ReadEmail();
    public static PersonRepository pr = new PersonRepository();
    public static ObservableList<Text> logList = FXCollections.observableArrayList ();
    ObservableList<Integer> comVar = FXCollections.observableArrayList(1);
    ObservableList<Person> userCom = FXCollections.observableArrayList();
    public static int allEmals = 0;
    public static int readed0Emails = -1;
    public static int readed1Emails = -1;
    public static int readed2Emails = -1;
    public static String lastReaded0Date = "none";
    public static String lastReaded1Date = "none";
    public static String lastReaded2Date = "none";
    public static String lastReceivedDate = "none";
    public static int cbCount = 0;
    public static int fraudCount = 0;
    public static String login;
    public static String pass;
    public static boolean btnVis = true;
    public static boolean newCheckB = false;


    @FXML
    private Accordion accordionPane;

    @FXML
    private TitledPane loginPane;

    @FXML
    private TextField loginText;

    @FXML
    private TextField passText;

    @FXML
    private ComboBox<Integer> comboBox;

    @FXML
    private ComboBox<Person> usersCombo;

    @FXML
    private Button btnLogin;

    @FXML
    private ListView<Text> log;

    @FXML
    private TitledPane infoPane;

    @FXML
    private Label usingEmailLable;

    @FXML
    private Label allEmailLable;

    @FXML
    private Label readedEmail0Lable;

    @FXML
    private Label readedEmail1Lable;

    @FXML
    private Label readedEmail2Lable;

    @FXML
    private Label cbVisaLabel;

    @FXML
    private Label fraudLabel;

    @FXML
    private Label lastReadedEmail0Lable;

    @FXML
    private Label lastReadedEmail1Lable;

    @FXML
    private Label lastReadedEmail2Lable;

    @FXML
    private Label lastReveivedEmailLable;

    @FXML
    private CheckBox newCheckBox;

    @FXML
    void initialize() {
        //update UI
        update();
        log.setItems(logList);
        accordionPane.setExpandedPane(loginPane);
        comboBox.setItems(comVar);
        comboBox.getSelectionModel().selectFirst();
        usersCombo.valueProperty().addListener((userCom, oldValue, newValue) -> {
            loginText.setText(newValue.getLogin());
            passText.setText(Calc.unConP(newValue.getPass()));
        });

        newCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                newCheckB = newValue;
            }
        });


        //bd
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        userCom = pr.get();
        usersCombo.setItems(userCom);

        //first log
        addToLog("Waiting for login ..", "g");

        //login button
        btnLogin.setOnAction(event -> {
            login = loginText.getText();
            pass = passText.getText();

            action();
        });
    }

    public void update () {
            final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
                        allEmailLable.setText(String.valueOf(allEmals));
                        readedEmail0Lable.setText(String.valueOf(readed0Emails + 1));
                        readedEmail1Lable.setText(String.valueOf(readed1Emails + 1));
                        readedEmail2Lable.setText(String.valueOf(readed2Emails + 1));
                        cbVisaLabel.setText(String.valueOf(cbCount));
                        fraudLabel.setText(String.valueOf(fraudCount));
                        lastReadedEmail0Lable.setText(String.valueOf(lastReaded0Date));
                        lastReadedEmail1Lable.setText(String.valueOf(lastReaded1Date));
                        lastReadedEmail2Lable.setText(String.valueOf(lastReaded2Date));
                        lastReveivedEmailLable.setText(String.valueOf(lastReceivedDate));
                        if (btnVis) btnLogin.setDisable(false);
                        else btnLogin.setDisable(true);
                        })
            );
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
    }

    public static void addToLog(String str, String color) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (color.equals("r")) {
                    Text text = new Text(str);
                    text.setFill(Color.RED);
                    logList.add(0, text);
                } else if (color.equals("g")) {
                    Text text = new Text(str);
                    text.setFill(Color.GREEN);
                    logList.add(0, text);
                } else if (color.equals("y")) {
                    Text text = new Text(str);
                    text.setFill(Color.MAGENTA);
                    logList.add(0, text);
                } else if (color.equals("g,u")) {
                    Text text = new Text(str);
                    text.setFill(Color.GREEN);
                    text.setUnderline(true);
                    logList.add(0, text);
                } else {
                    Text text = new Text(str);
                    logList.add(0, text);
                }

            }
        });
    }

    public void action() {
        usingEmailLable.setText(loginText.getText());
        readed0Emails = -1;
        readed1Emails = -1;
        readed2Emails = -1;
        cbCount = 0;
        fraudCount = 0;

        Thread t1 = new Thread (new Runnable() {
            @Override
            public void run() {
                try {
                    re.check(loginText.getText(), passText.getText(), comboBox.getSelectionModel().getSelectedItem());
                } catch (IllegalStateException e) {
                    addToLog("1 Not a FX thread", "r");
                    e.printStackTrace();
                } catch (Exception e) {
                    addToLog("ReadEmail thread do not start", "r");
                    e.printStackTrace();
                }
            }
        });
        t1.setDaemon(true);
        t1.start();
    }

    public static void addUser() {
        if(pr.getByName(login) == null && !login.equals("") && !pass.equals("")) {
            String cPass = Calc.conP(pass);
            pr.insertUser(new Person(login, cPass));
        };
    }

}
