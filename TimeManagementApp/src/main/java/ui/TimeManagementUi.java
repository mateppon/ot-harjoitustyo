/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Properties;
import java.io.FileInputStream;

import dao.SQLUserDao;
import dao.SQLProjectsDao;
import domain.TimeManagementService;
import domain.User;


public class TimeManagementUi extends Application {
    
    private Scene scene1, createNewUserScene, loggedInScene;
    
    private TimeManagementService service;
    
    @Override
    public void init()throws Exception{ 
    
    Properties properties = new Properties();
    properties.load(new FileInputStream("config.properties"));
    
    String sqlDatabase = properties.getProperty("sqlFile");
        
    SQLUserDao userDao = new SQLUserDao(sqlDatabase);
    SQLProjectsDao projectsDao = new SQLProjectsDao(sqlDatabase, userDao);
    
    service = new TimeManagementService(userDao, projectsDao);
    
    service.setForeignKeys();
    
    service.createTables();
    
    
    }
    
    
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Time Managemenet App"); 
        
        //layout1
        BorderPane layout = new BorderPane();
        FlowPane newUserPane = new FlowPane();
        
        Button newUserButton = new Button();
        newUserButton.setText("Create new user");
        newUserButton.setOnAction(e -> 
                primaryStage.setScene(createNewUserScene));

        newUserPane.getChildren().add(newUserButton);
        layout.setCenter(newUserPane);
  

        //layoutNewUser
        BorderPane createUserPane = new BorderPane();
        FlowPane namePane = new FlowPane();
        FlowPane userPane =new FlowPane();
        FlowPane buttons = new FlowPane();
        
        TextField nameInput = new TextField();
        TextField usernameInput = new TextField();
        
        Label ok = new Label("");
         
        Button createButton = new Button();
        createButton.setText("Create new account");
        createButton.setOnAction(e -> {
            String name = nameInput.getText();
            String username = usernameInput.getText();
            if(service.createNewUser(name,username)){
                primaryStage.setScene(loggedInScene);
            } else
                ok.setText("Username already exists.");
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
        
        
        Label loggedInLabel = new Label("Logged in");
        
        Label newProjectLabel = new Label(
                "Add new project to your list. "); 
       
        TextField newProjectInput = new TextField("Name of the project:");
        
        
        Button createNewProjectButton = new Button("Add");
        createNewProjectButton.setOnAction(e -> {
            String projectname = newProjectInput.getText();
            
            if(service.createNewProject(projectname)) {
                System.out.println("ok");
            }else System.out.println("failed");
            
            //primaryStage.setScene(??);
        });
        
        userLoggedInPane.getChildren().add(loggedInLabel);
        createNewProjectPane.getChildren().add(newProjectLabel);
        createNewProjectPane.getChildren().add(newProjectInput);
        createNewProjectPane.getChildren().add(createNewProjectButton);
        
        
        loggedInPane.setTop(userLoggedInPane);
        loggedInPane.setCenter(createNewProjectPane);
        
        createNewUserScene = new Scene(createUserPane, 300, 250);
        
        loggedInScene = new Scene(loggedInPane, 300, 250);
        
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

