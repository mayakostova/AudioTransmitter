package com.odonataworkshop.audio.client;

import com.odonataworkshop.util.MessageType;
import com.odonataworkshop.util.ModalDialog;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by maya on 15.12.2014 г..
 */
public class Controller {
    public TextArea txtInfo;

    public TextField txtHost;
    public TextField txtPort;
    public Button btnConnect;
    public Button btnDisconnect;

    private Stage mStage;
    private AudioClient mClient;


    private enum InputType {
        HOST_INPUT, PORT_INPUT
    }

    public void setStage(Stage aStage) {
        mStage = aStage;
    }

    public void onConnectActionPerformed(ActionEvent aActionEvent) {
        boolean isValid;
        String host = txtHost.getText();

        isValid = validate(host, InputType.HOST_INPUT);
        String port = txtPort.getText();
        isValid = isValid && validate(port, InputType.PORT_INPUT);
        if(!isValid){
            ModalDialog.showMessageDialog(mStage, "Input value for host and/or port is invalid. Please input valid values!", MessageType.ERROR_MESSAGE);
            return;
        }
        Config.setProperty(Config.HOST_PROPERTY_NAME,host);
        Config.setProperty(Config.PORT_PROPERTY_NAME,port);
        Config.saveProperties();
        final UpdateUIHandler handler = new UpdateUIHandler(txtInfo);
        mClient = new AudioClient(host, new Integer(port), handler);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mClient.connect();
                    mClient.sendAudio();
                    btnConnect.setDisable(true);
                    btnDisconnect.setDisable(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (!mClient.getSocketClient().isClosed()) {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                  //not important
                                }
                            }
                            btnConnect.setDisable(false);
                            btnDisconnect.setDisable(true);
                        }
                    }).start();
                } catch (UnknownHostException e1) {
                    btnConnect.setDisable(false);
                    btnDisconnect.setDisable(true);
                    handler.write("Host unknown. Cannot establish connection!");
                } catch (IOException e1) {
                    btnConnect.setDisable(false);
                    btnDisconnect.setDisable(true);
                    handler.write("Error connecting to server: " + e1.getMessage());
                } catch (Exception e){
                    btnConnect.setDisable(false);
                    btnDisconnect.setDisable(true);
                }
            }
        }).start();
    }

    public void onDisconnectActionPerformed(ActionEvent aActionEvent) {
        if (mClient != null) {
            mClient.disconnect();
            btnConnect.setDisable(false);
            btnDisconnect.setDisable(true);
        }
    }

    private boolean validate(String aStrValue, InputType aInputType) {
        if (aInputType == InputType.HOST_INPUT) {
            String validIpAddress = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
            String validHostname = "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";
            return aStrValue != null && (aStrValue.matches(validHostname) || aStrValue.matches(validIpAddress));
        }
        if (aInputType == InputType.PORT_INPUT) {
            if (aStrValue.matches("^[0-9]+$")) {
                Integer intPort = Integer.parseInt(aStrValue);
                return (intPort < 65536);
            }
            return false;
        }
        return false;
    }
}
