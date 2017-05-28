package utils.creator;

import utils.actions.*;

/**
 * Created by Lenovo on 23/05/2017.
 */
public abstract class DataCreator {

    protected UsersActions usersActions = new UsersActions();

    protected CourseAction courseAction = new CourseAction();

    protected EnrollmentActions enrollmentManager = new EnrollmentActions();

    protected RecordingActions recordingActions = new RecordingActions();

    protected EditRecordingActions editRecordingActions = new EditRecordingActions();
}
