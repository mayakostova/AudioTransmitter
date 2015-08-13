package com.odonataworkshop.audio.client;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * Created by maya on 15.12.2014 г..
 */
public class UpdateUIHandler {

    private TextArea mTextArea;

    public UpdateUIHandler(TextArea aTextArea) {
        mTextArea = aTextArea;
    }


    public void write(final String aText) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mTextArea.appendText(aText + "\n");
            }
        });
    }
}
