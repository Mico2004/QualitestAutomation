package utils.actions;

import java.util.ArrayList;
import java.util.List;

public class UsersActions extends ActionsParent {


    //compose list of past courses whiche active then moving them to past course
    public List<String> createNewUser(String[] users) throws InterruptedException {
        List<String> createdUsers = new ArrayList<>();

        login("Admin", true);
        admin_dashboard_page.clickOnTargetSubmenuUsers("Manage Ad-hoc Users (User Builder)");
        for (int i = 0; i < 5; i++) {
            try {
                driver.switchTo().frame(0);
                break;
            } catch (Exception msg) {
                Thread.sleep(1000);
            }
        }

        for (String userName : users) {

            userName = userName + "-" + System.currentTimeMillis();
            createdUsers.add(userName);
            manageAdhocUsersPage.clickOnNewUser();
            Thread.sleep(1000);
            createNewUserWindow.waitForVisibility(createNewUserWindow.ok_button);
            createNewUserWindow.createNewUser(userName, userName, userName + "@abdc.com", "111", "111");
            manageAdhocUsersPage.exitInnerFrame();
            manageAdhocUsersPage.getIntoFrame(0);
            Thread.sleep(1000);
        }
        driver.quit();
        return createdUsers;
    }

    public void disableEulaPage() {
        login("Admin", true);
        admin_dashboard_page.clickOnTargetSubmenuAdvancedServices("Advanced Service Settings");
        advancedServiceSettingsPage.waitForVisibility(advancedServiceSettingsPage.eula_checkbox);
        /// 12.disable checkbox
        advancedServiceSettingsPage.disableEulaCheckboxAndClickOk(confirmation_menu);
        driver.quit();

    }

}
