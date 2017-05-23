package utils.actions;

import com.automation.main.page_helpers.*;
import com.automation.main.ping.helper.LogInAsAnotherUser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Lenovo on 21/05/2017
 */

public abstract class ActionsParent {

    protected WebDriver driver;
    protected LoginHelperPage tegrity;
    protected AdminDashboardPage admin_dashboard_page;
    protected WebDriverWait wait;
    protected ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
    ConfirmationMenu confirmation_menu;


    public ActionsParent(WebDriver driver) {
        this.driver = driver;
        initPageObjects();
    }

    public ActionsParent() {

    }

    protected void initPageObjects(){

        tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
        admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
        mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
        confirmation_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
    }

    protected LogInAsAnotherUser login (String userName,boolean thisUserFromPropFile){
        LogInAsAnotherUser anotherUser = new LogInAsAnotherUser();
        anotherUser.openAnotherSession(userName, thisUserFromPropFile);
        anotherUser.getDriver().navigate().back();
        return anotherUser;
    }

}