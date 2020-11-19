package controller;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Coursemodel;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author katie
 */

//code modified from source
public class DetailedModelViewController {
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML 
    //fx:id="backButton"
    private Button backButton; 

    @FXML 
    //fx:id="labelID"
    private Label labelID; 

    @FXML 
    //fx:id="labelValue"
    private Label labelValue;

    @FXML 
    //fx:id="image"
    private ImageView image;

    //backbutton   
    @FXML
    void backButtonAction(ActionEvent event) {
        //get current stage from event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       
        if (previousScene != null) {
            stage.setScene(previousScene);
        }

    }

    model.Coursemodel selectedModel;
    Scene previousScene;

    public void setPreviousScene(Scene scene) {
        previousScene = scene;
        backButton.setDisable(false);

    }

    public void initData(model.Coursemodel model) {
        selectedModel = model;
        labelID.setText(model.getCourseid().toString());
        labelValue.setText(model.getCoursename());

        try {
            //points to /resource/images/
            String imagename = "/resource/images/course.png";
            Image profile = new Image(getClass().getResourceAsStream(imagename));
            image.setImage(profile);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML 
    //called by FXMLLoader when initialization is complete
    void initialize() {
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'DetailedModelView.fxml'.";
        assert labelID != null : "fx:id=\"labelID\" was not injected: check your FXML file 'DetailedModelView.fxml'.";
        assert labelValue != null : "fx:id=\"labelValue\" was not injected: check your FXML file 'DetailedModelView.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'DetailedModelView.fxml'.";

        backButton.setDisable(true);

    }
    
}
