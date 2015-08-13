package com.odonataworkshop.util;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public final class SkinBuilder {

    private Point2D anchorPt;
    private Point2D previousLocation;
    private Stage primaryStage;

    private SkinBuilder(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public static SkinBuilder create(Stage primaryStage) {
        return new SkinBuilder(primaryStage);
    }

    public SkinBuilder addCloseButton(Group root, final boolean isExit) {
        Scene scene = primaryStage.getScene();
        final Group closeApp = new Group();
        Node closeButton = CircleBuilder.create().centerX(5).
                centerY(0).radius(7).fill(Color.rgb(255, 255, 255, .80)).build();
        Node closeXmark = new Text(2, 4, "X");
        closeApp.translateXProperty().bind(scene.widthProperty().subtract(15));
        closeApp.setTranslateY(10);
        closeApp.getChildren().addAll(closeButton, closeXmark);
        closeApp.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (isExit) {
                    Platform.exit();
                    System.exit(0);
                } else {
                    primaryStage.hide();
                }
            }
        });
        root.getChildren().add(closeApp);
        return this;
    }

    public SkinBuilder addShadow(Group root) {
       Scene scene = primaryStage.getScene();
       Rectangle applicationArea = RectangleBuilder.create()
                .arcWidth(20).arcHeight(20)
                .fill(Color.rgb(255, 255, 255, .10))
                .x(0).y(0)
                .strokeWidth(2)
                .effect(new BoxBlur(6, 6, 9))
                .strokeType(StrokeType.CENTERED)
                .stroke(Color.rgb(120, 120, 120, .70))
                .build();
        root.getChildren().add(0,applicationArea);
        
        applicationArea.widthProperty().bind(scene.widthProperty());
        applicationArea.heightProperty().bind(scene.heightProperty());
        return this;
    }

    public SkinBuilder addFullScreenButton(Group root) {
        Scene scene = primaryStage.getScene();
        final Group fullApp = new Group();
        Node closeButton = CircleBuilder.create().centerX(5).
                centerY(0).radius(7).fill(Color.rgb(255, 255, 255, .80)).build();
        Node closeXmark = new Text(1, 3, "[]");
        fullApp.translateXProperty().bind(scene.widthProperty().subtract(35));
        fullApp.setTranslateY(10);
        fullApp.getChildren().addAll(closeButton, closeXmark);
        fullApp.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                boolean fullScreen = primaryStage.isFullScreen();
                primaryStage.setFullScreen(!fullScreen);
            }
        });
        root.getChildren().add(fullApp);
        return this;
    }
    
    public SkinBuilder enableDragging(Node dragArea) {
        Scene scene = primaryStage.getScene();
        setLocation();
        // starting initial anchor point
        dragArea.setOnMousePressed(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                anchorPt = new Point2D(event.getScreenX(), event.getScreenY());
            }
        });
// dragging the entire stage
        dragArea.setOnMouseDragged(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                if (anchorPt != null && previousLocation != null) {
                    primaryStage.setX(previousLocation.getX() + event.getScreenX()
                            - anchorPt.getX());
                    primaryStage.setY(previousLocation.getY() + event.getScreenY()
                            - anchorPt.getY());
                }
            }
        });
// set the current location
        dragArea.setOnMouseReleased(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                previousLocation = new Point2D(primaryStage.getX(), primaryStage.getY());
            }
        });
        return this;
    }

    public void setLocation() {
        previousLocation = new Point2D(primaryStage.getX(), primaryStage.getY());
    }
}
