package com.odonataworkshop.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public final class ModalDialog extends Stage {

    private MessageType mMessageType;

    private ModalDialog(StageStyle style) {
        super(style);
    }

    private ModalDialog() {
    }
    
    
    private ModalDialog(final Stage parentStage, Object message) {
        this(parentStage, message, null, MessageType.INFO_MESSAGE);
    }

    private ModalDialog(final Stage parentStage, Object message, MessageType aMessageType) {
        this(parentStage, message, null, aMessageType);
    }

    private ModalDialog(final Stage parentStage, Object message, String title, MessageType aMessageType) {
        mMessageType = aMessageType;
        if (title != null) {
            setTitle(title);
        } else {
            setTitle(mMessageType.getTitle());
        }
        this.initStyle(StageStyle.UNDECORATED);
        Group root = new Group();
        GridPane modalGroup = new GridPane();
        modalGroup.setAlignment(Pos.CENTER);
        modalGroup.setManaged(true);
        modalGroup.setPadding(new Insets(20));
        root.getChildren().add(modalGroup);
        Scene scene = new Scene(root);
        initModality(Modality.WINDOW_MODAL);
        initOwner(parentStage);
        Node node = null;
        if (message instanceof String) {
            node = new Text(10, 45, String.valueOf(message));
            ((Text) node).setWrappingWidth(190);
            ((Text) node).setBoundsType(TextBoundsType.LOGICAL);
        } else if (Node.class.isAssignableFrom(message.getClass())) {
            node = (Node) message;
        } else {
            return;
        }
        node.setManaged(true);
        Button btn = new Button();
        btn.setPrefSize(50, 20);
        btn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                hide();
            }
        });
        this.centerOnScreen();

        btn.setAlignment(Pos.CENTER);
        btn.setManaged(true);
        btn.setText("OK");

        Image icon = new Image(getClass().getClassLoader().getResourceAsStream("images/" + mMessageType.getImage()));
        ImageView iconView = new ImageView(icon);
        GridPane.setHalignment(iconView, HPos.LEFT);
        GridPane.setValignment(iconView, VPos.CENTER);
        GridPane.setMargin(iconView, new Insets(0, 10, 0, 0));
        modalGroup.add(iconView, 0, 0);
        GridPane.setHalignment(node, HPos.CENTER);
        GridPane.setValignment(node, VPos.CENTER);
        modalGroup.add(node, 1, 0);
        GridPane.setHalignment(btn, HPos.RIGHT);
        GridPane.setValignment(btn, VPos.BOTTOM);
        GridPane.setMargin(btn, new Insets(10, 0, 10, 0));
        modalGroup.add(btn, 1, 1);
        setScene(scene);
        SkinBuilder builder = SkinBuilder.create(this).addCloseButton(root, false).addShadow(root).enableDragging(root);
        show();
        builder.setLocation();
    }
    
   private ModalDialog(Stage parentStage, Object message,final Callback  onConfirm){
        this.initStyle(StageStyle.UNDECORATED);
        Group root = new Group();
        GridPane modalGroup = new GridPane();
        modalGroup.setAlignment(Pos.CENTER);
        modalGroup.setManaged(true);
        modalGroup.setPadding(new Insets(20));
        root.getChildren().add(modalGroup);
        Scene scene = new Scene(root);
        initModality(Modality.APPLICATION_MODAL);
        initOwner(parentStage);
        Node node = null;
        if (message instanceof String) {
            node = new Text(10, 45, String.valueOf(message));
            ((Text) node).setWrappingWidth(190);
            ((Text) node).setBoundsType(TextBoundsType.LOGICAL);
        } else if (Node.class.isAssignableFrom(message.getClass())) {
            node = (Node) message;
        } else {
            return;
        }
        node.setManaged(true);
        
        HBox buttons  = new HBox();
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.BASELINE_RIGHT);
        Button btn = new Button();
        btn.setPrefSize(70, 20);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                onConfirm.call(null);
                hide();
            }
        });
        btn.setAlignment(Pos.CENTER);
        btn.setManaged(true);
        btn.setText("OK");
        
        Button cancelBtn = new Button();
        cancelBtn.setPrefSize(60, 20);
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                hide();
            }
        });
        
        this.centerOnScreen();

        cancelBtn.setAlignment(Pos.CENTER);
        cancelBtn.setManaged(true);
        cancelBtn.setText("Cancel");

        GridPane.setHalignment(node, HPos.CENTER);
        GridPane.setValignment(node, VPos.CENTER);
        modalGroup.add(node, 0, 0);
        GridPane.setHalignment(buttons, HPos.RIGHT);
        GridPane.setValignment(buttons, VPos.BOTTOM);
        GridPane.setMargin(buttons, new Insets(10, 0, 10, 0));
        buttons.getChildren().add(btn);
        buttons.getChildren().add(cancelBtn);
        modalGroup.add(buttons, 0, 1);
        setScene(scene);
        SkinBuilder builder = SkinBuilder.create(this).addCloseButton(root, false).addShadow(root).enableDragging(root);
        show();
        builder.setLocation();
   }
    
    
    public static <P, T> void  showConfigrmDialog(Stage parentStage, Object message, Callback<P, T>  onConfirm){
        new ModalDialog(parentStage, message, onConfirm).show();
    }
    

    public static void showMessageDialog(Stage parentStage, Object message) {
        new ModalDialog(parentStage, message).show();
    }
    
    
    
    public static void showMessageDialog(Stage parentStage, Object message, MessageType aType) {
        new ModalDialog(parentStage, message, aType).show();
    }

    public static void showMessageDialog(Stage parentStage, Object message, String title, MessageType aType) {
        new ModalDialog(parentStage, message, title, aType).show();
    }
}
