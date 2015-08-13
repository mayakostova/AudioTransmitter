package com.odonataworkshop.audio.client;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.zip.GZIPOutputStream;

/**
 * Created by maya on 8.12.2014 г..
 */
public class AudioClient {
    private String hostname;
    private int port;
    private Socket socketClient;
    private SoundCapture mSoundCapture;
    private UpdateUIHandler mHandler;

    public AudioClient(String hostname, int port, UpdateUIHandler aHandler) {
        this.hostname = hostname;
        this.port = port;
        mHandler = aHandler;
    }

    public void connect() throws  IOException {
        mHandler.write("Attempting to connect to " + hostname + ":" + port);
        socketClient = new Socket(hostname, port);
        socketClient.setTcpNoDelay(true);
        socketClient.setKeepAlive(true);
        socketClient.setSendBufferSize(100*1024*100);
        mHandler.write("Connection Established");
    }

    public synchronized Socket getSocketClient() {
        return socketClient;
    }

    public void disconnect() {
        if (mSoundCapture != null) {
            mSoundCapture.stop();

        }
        if (socketClient != null && !socketClient.isClosed()) {
            try {
                socketClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                mHandler.write("Error disconnecting client...");
            }
        }
    }


    public void sendAudio() throws IOException {
        mSoundCapture = new SoundCapture(mHandler);
        mSoundCapture.capture(socketClient);
    }

}
