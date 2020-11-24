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

import dao.SQLUserDao;
import domain.TimeManagementService;


public class TimeManagementUi extends Application {
    
    private Scene scene1, createNewUserScene;
    
    private TimeManagementService service;
    
    @Override
    public void init()throws Exception{ 
        
    SQLUserDao uDao = new SQLUserDao();
    service = new TimeManagementService(uDao);
    }
    
    
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Time Managemenet App"); 
        BorderPane layout = new BorderPane();
        
        FlowPane newUserPane = new FlowPane();
        
        
        //layout 1
        Button newUserButton = new Button();
        newUserButton.setText("Create new user");
        newUserButton.setOnAction(e -> primaryStage.setScene(createNewUserScene));

        newUserPane.getChildren().add(newUserButton);
      
        layout.setCenter(newUserPane);
        

        //layoutNewUser
        BorderPane createUser = new BorderPane();
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
                ok.setText("ok!");
            }else
                ok.setText("failed");
        });
        
     
        
        namePane.getChildren().add(new Label("Name: "));
        namePane.getChildren().add(nameInput);
        userPane.getChildren().add(new Label("Username: "));
        userPane.getChildren().add(usernameInput);
        
        buttons.getChildren().add(createButton);
        buttons.getChildren().add(ok);
        
        
        createUser.setTop(namePane);
        createUser.setCenter(userPane);
        createUser.setBottom(buttons);
        
        createNewUserScene = new Scene(createUser, 300, 250);
        
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

