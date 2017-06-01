package utils.actions;

import com.automation.main.ping.helper.LogInAsAnotherUser;

/**
 * Created by Lenovo on 29/05/2017.
 */
public class UserActivatorRunnable implements Runnable {

    private String userName;
    private Thread t;

    UserActivatorRunnable(String userName) {
        this.userName = userName;
    }

    public void run() {
        LogInAsAnotherUser logInAsAnotherUser = new LogInAsAnotherUser();
        logInAsAnotherUser.openAnotherSession(userName, false);
        logInAsAnotherUser.killWebDriver();
    }

    public void start () {
        if (t == null) {
        }
            t = new Thread (this);
            t.start ();
        }
}
