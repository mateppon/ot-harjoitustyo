/*

 */
package timemanagementapp.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import java.io.FileInputStream;

import timemanagementapp.dao.SQLUserDao;
import timemanagementapp.dao.SQLProjectsDao;
import timemanagementapp.domain.TimeManagementService;

public class TimeManagementUi extends Application {

    private Scene scene1, createNewUserScene, loggedInScene;
    private TimeManagementService service;
    private ChoiceBox<String> choiceBoxProjects = new ChoiceBox<>();
    private List<String> projects = new ArrayList<String>();

    @Override
    public void init() throws Exception {

        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        String sqlDatabase = properties.getProperty("sqlFile");

        SQLUserDao userDao = new SQLUserDao(sqlDatabase);
        SQLProjectsDao projectsDao = new SQLProjectsDao(sqlDatabase, userDao);

        service = new TimeManagementService(userDao, projectsDao);
        service.createTables();
    }

    public void redrawProjectList() {
        choiceBoxProjects.getItems().clear();
        projects.clear();
        try {
            this.projects = service.getAllProjectNames();
            projects.forEach(project -> {
                choiceBoxProjects.getItems().add(project);
            });
        } catch (Exception e) {
            System.out.println("ui:ssa" + e);
        }
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Time Managemenet App");

        //layout1
        BorderPane layout = new BorderPane();

        FlowPane newUserPane = new FlowPane();
        Button newUserButton = new Button();
        newUserButton.setText("Create new user");
        newUserButton.setOnAction(e
                -> primaryStage.setScene(createNewUserScene));

        newUserPane.getChildren().add(newUserButton);

        FlowPane loginPane = new FlowPane();
        Label loginLabel = new Label("Sign in: ");
        TextField loginInput = new TextField();

        Label loginFailedMessage = new Label();

        Button loginButton = new Button();
        loginButton.setText("Sign in");
        loginButton.setOnAction(e -> {
            String username = loginInput.getText();
            if (service.findUser(username)) {
                primaryStage.setScene(loggedInScene);
                service.loggedIn();
                redrawProjectList();
            } else {
                loginFailedMessage.setText("Username does not exist.");
                loginFailedMessage.setTextFill(Color.RED);
            }
        });

        loginPane.getChildren().add(loginLabel);
        loginPane.getChildren().add(loginInput);
        loginPane.getChildren().add(loginButton);
        loginPane.getChildren().add(loginFailedMessage);

        layout.setCenter(newUserPane);
        layout.setBottom(loginPane);

        //layoutNewUser
        BorderPane createUserPane = new BorderPane();
        FlowPane namePane = new FlowPane();
        FlowPane userPane = new FlowPane();
        FlowPane buttons = new FlowPane();

        TextField nameInput = new TextField();
        TextField usernameInput = new TextField();

        Label ok = new Label("");

        Button createButton = new Button();
        createButton.setText("Create new account");
        createButton.setOnAction(e -> {
            String name = nameInput.getText();
            String username = usernameInput.getText();
            if (service.createNewUser(name, username)) {
                primaryStage.setScene(loggedInScene);
            } else {
                ok.setText("Username already exists.");
            }
        });

        namePane.getChildren().add(new Label("Name: "));
        namePane.getChildren().add(nameInput);
        userPane.getChildren().add(new Label("Username: "));
        userPane.getChildren().add(usernameInput);

        buttons.getChildren().add(createButton);
        buttons.getChildren().add(ok);

        createUserPane.setTop(namePane);
        createUserPane.setCenter(userPane);
        createUserPane.setBottom(buttons);

        //layout loggedInScene
        BorderPane loggedInPane = new BorderPane();

        FlowPane userLoggedInPane = new FlowPane();
        FlowPane createNewProjectPane = new FlowPane();
        FlowPane startNewSprintPane = new FlowPane();

        Label loggedInLabel = new Label("LOGGED IN");

        userLoggedInPane.getChildren().add(loggedInLabel);

        Label startNewSprintLabel = new Label("Start new sprint  ");
        choiceBoxProjects.setValue("select project");
        Label bookedTimeLabel = new Label("How many hours you plan to spend wiht your project:");
        TextField bookedTimeInput = new TextField();
        Button bookTimeButton = new Button("Add");

        startNewSprintPane.getChildren().add(startNewSprintLabel);
        startNewSprintPane.getChildren().add(choiceBoxProjects);
        startNewSprintPane.getChildren().add(bookedTimeLabel);
        startNewSprintPane.getChildren().add(bookedTimeInput);
        startNewSprintPane.getChildren().add(bookTimeButton);

        Label newProjectLabel = new Label("Add new project to your list.");
        TextField newProjectInput = new TextField("Name of the project:");

        Button createNewProjectButton = new Button("Add");
        createNewProjectButton.setOnAction(e -> {
            String projectname = newProjectInput.getText();
            if (service.createNewProject(projectname)) {
                System.out.println("ok");
                redrawProjectList();
            } else {
                System.out.println("Projekti on jo olemassa");
            }
        });

        createNewProjectPane.getChildren().add(newProjectLabel);
        createNewProjectPane.getChildren().add(newProjectInput);
        createNewProjectPane.getChildren().add(createNewProjectButton);

        loggedInPane.setTop(userLoggedInPane);
        loggedInPane.setLeft(startNewSprintPane);
        loggedInPane.setBottom(createNewProjectPane);

        createNewUserScene = new Scene(createUserPane, 500, 300);

        loggedInScene = new Scene(loggedInPane, 500, 300);

        scene1 = new Scene(layout, 300, 250);

        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
