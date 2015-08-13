package com.odonataworkshop.audio.client;

import com.odonataworkshop.util.SkinBuilder;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by maya on 15.12.2014 г..
 */
public class StartApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties properties = new Properties();
        String userHome = System.getProperty("user.home");
        String propsLocation = userHome + File.separator + "audio_transmitter.properties";
        File propsFile = new File(propsLocation);
        Config.setFile(propsLocation);
        if (propsFile.exists()) {
            try {
                properties.load(new FileInputStream(propsFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Config.setProperties(properties);

        primaryStage.setTitle("Audio Transmitter");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader fxmlLoader = new FXMLLoader(Thread.currentThread().getContextClassLoader().getResource("main.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Group rootGroup = new Group(root);
        String image = StartApplication.class.getResource("/images/background.jpg").toExternalForm();
        root.setStyle("-fx-background-image: url('" + image + "');display:block");
        rootGroup.setStyle("-fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");
        primaryStage.setScene(new Scene(rootGroup, 500, 400));
        primaryStage.getIcons().add(new Image(StartApplication.class.getResource("/images/icon1.png").toExternalForm()));
        SkinBuilder builder = SkinBuilder.create(primaryStage).addCloseButton(rootGroup, true).addShadow(rootGroup).enableDragging(root);
        Object controller = fxmlLoader.getController();
        if (controller instanceof Controller) {
            ((Controller) controller).setStage(primaryStage);
            if(Config.getProperty(Config.HOST_PROPERTY_NAME) != null && !"".equals(Config.getProperty(Config.HOST_PROPERTY_NAME))){
                ((Controller)controller).txtHost.textProperty().bindBidirectional(new SimpleStringProperty(Config.getProperty(Config.HOST_PROPERTY_NAME)));
            }
            if(Config.getProperty(Config.PORT_PROPERTY_NAME) != null && !"".equals(Config.getProperty(Config.PORT_PROPERTY_NAME))){
                ((Controller)controller).txtPort.textProperty().bindBidirectional(new SimpleStringProperty(Config.getProperty(Config.PORT_PROPERTY_NAME)));
            }

        }

        primaryStage.show();
        builder.setLocation();
    }

    public static void main(String[] args) {

        launch(args);

    }
}
