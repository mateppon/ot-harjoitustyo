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

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import timemanagementapp.dao.SQLUserDao;
import timemanagementapp.dao.SQLProjectsDao;
import timemanagementapp.domain.TimeManagementService;

public class TimeManagementUi extends Application {

    private Scene scene1, createNewUserScene, loggedInScene, 
            newSprintScene, updateTimeSpentScene, statisticsScene;
    private TimeManagementService service;
    
    private ChoiceBox<String> choiceBoxProjects = new ChoiceBox<>();
    private List<String> projects = new ArrayList<String>();
    
    private ChoiceBox<String> choiceBoxForUpdateTimeSpent = new ChoiceBox<>();
    private List<String> projectsForUpdateTimeSpent = new ArrayList<String>();
    
    private TextField allocatedTimeInput;
    private TextField usedTimeInput;
    
    private int allocatedTimeForProject;
    private int timeUsedOnProject;
    
    private String choice;
    
    private Button statisticsButton, backToLoginButton;
    
    private Label allocateTimeMessage, timeSpentMessage;
    
    private String sqlDatabase = "jdbc:sqlite:timemanagement.db";

    @Override
    public void init() throws Exception {

//        Properties properties = new Properties();
//        properties.load(new FileInputStream("config.properties"));
//        String sqlDatabase = properties.getProperty("sqlFile");
        
        

        SQLUserDao userDao = new SQLUserDao(sqlDatabase);
        SQLProjectsDao projectsDao = new SQLProjectsDao(sqlDatabase, userDao);

        service = new TimeManagementService(userDao, projectsDao);
        service.createTables();
    }

    private void redrawProjectList() {
        choiceBoxProjects.getItems().clear();
        projects.clear();
        try {
            this.projects = service.getAllProjectNames();
            projects.forEach(project -> {
                choiceBoxProjects.getItems().add(project);
            });
        } catch (Exception e) {
        }
    }
    private void bookHours(ChoiceBox<String> choiceBoxProjects) {
        try {
            
        String choice = choiceBoxProjects.getValue();
        int allocatedHours = Integer.parseInt(allocatedTimeInput.getText());
        if(allocatedHours < 1) {
            this.allocateTimeMessage.setText("Allocated hours can not be negative or zero.");
            this.allocateTimeMessage.setTextFill(Color.RED);
        
        } else if(service.setBookedTimeForProject(choice, allocatedHours)) {
            this.allocateTimeMessage.setText("ok");
            this.allocateTimeMessage.setTextFill(Color.GREEN);
        }
        } catch (Exception e) {
            this.allocateTimeMessage.setText("Enter an integer.");
            this.allocateTimeMessage.setTextFill(Color.RED);

        }
    }
    private void updateSpentHours(ChoiceBox<String> choiceBoxProjects) {
        
        try {
            
        String choice = choiceBoxProjects.getValue();
        int timeSpent = Integer.parseInt(usedTimeInput.getText());
        if(timeSpent < 1) {
            this.timeSpentMessage.setText("Time spent can not be negative or zero.");
            this.timeSpentMessage.setTextFill(Color.RED);
        } else {
            service.setTimeUsed(choice, timeSpent);
            this.timeSpentMessage.setText("ok");
            this.timeSpentMessage.setTextFill(Color.GREEN); 
        }
            
        } catch (Exception e) {
            this.timeSpentMessage.setText("Enter an integer.");
            this.timeSpentMessage.setTextFill(Color.RED);
        }
    }
        
    


    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Time Managemenet App");

        //scene1
        
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
            if (service.findIfUserExists(username)) {
                primaryStage.setScene(loggedInScene);
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

        
        
        
        
        // newUser scene
        BorderPane createUserPane = new BorderPane();
        FlowPane namePane = new FlowPane();
        FlowPane userPane = new FlowPane();
        FlowPane buttons = new FlowPane();

        TextField nameInput = new TextField();
        TextField usernameInput = new TextField();

        Label createUserMessage = new Label("");

        Button createButton = new Button();
        createButton.setText("Create new user");
        createButton.setOnAction(e -> {
            String name = nameInput.getText();
            String username = usernameInput.getText();
            
            if (name.length()<2 || username.length() <2) {
                createUserMessage.setText("Name or username is too short.");
                createUserMessage.setTextFill(Color.RED);
            } else if (service.createNewUser(name, username)) {
                primaryStage.setScene(loggedInScene);
            } else {
                createUserMessage.setText("Username already exists.");
                createUserMessage.setTextFill(Color.RED);
            }
        });

        namePane.getChildren().add(new Label("Name: "));
        namePane.getChildren().add(nameInput);
        userPane.getChildren().add(new Label("Username: "));
        userPane.getChildren().add(usernameInput);

        buttons.getChildren().add(createButton);
        buttons.getChildren().add(createUserMessage);

        createUserPane.setTop(namePane);
        createUserPane.setCenter(userPane);
        createUserPane.setBottom(buttons);

        
        
        
        
        //loggedInScene
        
        BorderPane loggedInPane = new BorderPane();

        FlowPane userLoggedInPane = new FlowPane();
        Label loggedInLabel = new Label("LOGGED IN. Please select a project from dropdown or add a new one to your list.");     
        userLoggedInPane.getChildren().add(loggedInLabel);
        
        FlowPane projectListPane = new FlowPane();
        this.choice = choiceBoxProjects.getValue();
        projectListPane.getChildren().add(choiceBoxProjects);
        
        FlowPane buttonsPane = new FlowPane();
        
        this.backToLoginButton = new Button("Back");
        this.backToLoginButton.setOnAction(e -> primaryStage.setScene(loggedInScene));
        
        Button bookTimeButton = new Button("Shedule time for your project");
        bookTimeButton.setOnAction(e -> 
                primaryStage.setScene(newSprintScene));  
        
        Button updateTimeSpentButton = new Button("Update the time you spent today on your project");
        updateTimeSpentButton.setOnAction(e -> 
                primaryStage.setScene(updateTimeSpentScene));
        
        Button deleteProjectButton = new Button("Delete project");
        deleteProjectButton.setOnAction(e -> {
            choice = choiceBoxProjects.getValue();      
            service.deleteProject(choice);
                });
        
      
        Label statisticsLabel= new Label();
        
        this.statisticsButton = new Button("Update and view statistics");
       // this.statisticsButton.setOnAction(e -> primaryStage.setScene(this.getStatistics()));
        this.statisticsButton.setOnAction(e -> {
            choice = choiceBoxProjects.getValue();
            allocatedTimeForProject =  service.getBookedHoursForProject(choice);
            timeUsedOnProject = service.getTimeSpentForProject(choice);
            String statisticsString = "Allocated hours: " +
                    Integer.toString(allocatedTimeForProject) + 
                    "; Time spent so far: " + 
                    Integer.toString(timeUsedOnProject)+ " .";
            statisticsLabel.setText(statisticsString);
        });
        
        
        buttonsPane.getChildren().add(bookTimeButton);
        buttonsPane.getChildren().add(updateTimeSpentButton);
        buttonsPane.getChildren().add(this.statisticsButton);
        buttonsPane.getChildren().add(statisticsLabel);
        buttonsPane.getChildren().add(deleteProjectButton);

        
        FlowPane createNewProjectPane = new FlowPane();
        
        Label newProjectLabel = new Label("Add new project to your list.");
        TextField newProjectInput = new TextField("Name of the project:");
        
        Label createNewProjectMessage = new Label();

        Button createNewProjectButton = new Button("Add");
        createNewProjectButton.setOnAction(e -> {
            String projectname = newProjectInput.getText();
            if(projectname.length()<2) {
                createNewProjectMessage.setText("Projectname is too short.");
                createNewProjectMessage.setTextFill(Color.RED);
            } else if (service.createNewProject(projectname)) {
                createNewProjectMessage.setText("");
                redrawProjectList();
            } else {
                createNewProjectMessage.setText("Project already exists in your projectlist.");
            }
        });
        
        

        createNewProjectPane.getChildren().add(newProjectLabel);
        createNewProjectPane.getChildren().add(newProjectInput);
        createNewProjectPane.getChildren().add(createNewProjectButton);
        createNewProjectPane.getChildren().add(createNewProjectMessage);

        loggedInPane.setTop(userLoggedInPane);
        loggedInPane.setRight(projectListPane);
        loggedInPane.setCenter(buttonsPane);
        loggedInPane.setBottom(createNewProjectPane);
        
        
        
        
        
        // AllocateTime scene
        BorderPane allocatePane = new BorderPane();
        
        FlowPane allocateHeadingPane = new FlowPane();
        FlowPane allocateProjectsPane = new FlowPane();
        
        FlowPane allocateExitPane =new FlowPane();
        
        allocateTimeMessage = new Label();
    
        
        Label allocateTimeLabel = new Label(
                "How many hours you plan to spend on your project during this sprint?");
        this.allocatedTimeInput = new TextField();
                
               
        
        Button updateAllocatedTimeButton = new Button("Add");
        updateAllocatedTimeButton.setOnAction(e -> {
            bookHours(choiceBoxProjects);});
        
        
        
        Button fromBookingToLoginButton = new Button("Back");
        fromBookingToLoginButton.setOnAction(e -> primaryStage.setScene(loggedInScene));

        allocateHeadingPane.getChildren().add(allocateTimeLabel);
        
 
        allocateExitPane.getChildren().add(fromBookingToLoginButton);
      
        allocateProjectsPane.getChildren().add(allocatedTimeInput);
        allocateProjectsPane.getChildren().add(updateAllocatedTimeButton);
        allocateProjectsPane.getChildren().add(allocateTimeMessage);
        
        
        allocatePane.setTop(allocateHeadingPane);
        allocatePane.setCenter(allocateProjectsPane);
        allocatePane.setBottom(allocateExitPane);
        
        
        
        // scene updateTimeSpent 
        BorderPane updateTimeSpentPane = new BorderPane();
        
        FlowPane timeSpentHeadingPane = new FlowPane();
        Label timeSpentLabel = new Label(
                "How many hours you have spent with your project today?");
        timeSpentHeadingPane.getChildren().add(timeSpentLabel);
        
        FlowPane timeSpentProjectsPane = new FlowPane();
        
        Button updateHoursSpentButton = new Button("Update");
        updateHoursSpentButton.setOnAction(e -> updateSpentHours(choiceBoxProjects));
        this.usedTimeInput = new TextField();
        
        
        Label choicedProjectTimeSpentLabel = new Label(this.choice);
        
        timeSpentMessage = new Label();
        
        timeSpentProjectsPane.getChildren().add(usedTimeInput);
        timeSpentProjectsPane.getChildren().add(updateHoursSpentButton);
        timeSpentProjectsPane.getChildren().add(choicedProjectTimeSpentLabel);
        timeSpentProjectsPane.getChildren().add(timeSpentMessage);
        
        FlowPane fromSpentTimeSceneToLogin = new FlowPane();
        Button fromUpdateSpentToLoginButton = new Button("Back");
        fromUpdateSpentToLoginButton.setOnAction(e -> primaryStage.setScene(loggedInScene));
        fromSpentTimeSceneToLogin.getChildren().add(fromUpdateSpentToLoginButton);
        
        updateTimeSpentPane.setTop(timeSpentHeadingPane);
        updateTimeSpentPane.setCenter(timeSpentProjectsPane);
        updateTimeSpentPane.setBottom(fromSpentTimeSceneToLogin);
        
        

        
        createNewUserScene = new Scene(createUserPane, 600, 220);

        loggedInScene = new Scene(loggedInPane, 600, 220);
        
        newSprintScene = new Scene(allocatePane, 600, 220);
        
        updateTimeSpentScene = new Scene(updateTimeSpentPane, 600, 220);
        

        scene1 = new Scene(layout, 600, 220);

        primaryStage.setScene(scene1);
        primaryStage.show();
    }
    
//    private Scene getStatistics() {
//        choice = choiceBoxProjects.getValue();
//        allocatedTimeForProject = service.getBookedHoursForProject(choice);
//        timeUsedOnProject = service.getTimeSpentForProject(choice);
//        
//        CategoryAxis xAxis = new CategoryAxis();
//        NumberAxis yAxis = new NumberAxis();
//        BarChart<String,Integer> barChart = new BarChart(xAxis, yAxis);
//        XYChart.Series series = new XYChart.Series();
//        series.getData().add(new XYChart.Data("Allocated Time", this.allocatedTimeForProject));
//        series.getData().add(new XYChart.Data("Time used on Project so far", this.timeUsedOnProject));
//        barChart.getData().add(series);
//        yAxis.setLabel("Hours");
//        barChart.setTitle(this.choice);
//        barChart.setLegendVisible(false);
//        
//        
//        FlowPane statisticsPane = new FlowPane();
//        statisticsPane.getChildren().add(barChart);
//        statisticsPane.getChildren().add(this.backToLoginButton);
//        statisticsScene = new Scene(statisticsPane, 500, 500);
//        return statisticsScene;
//    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
