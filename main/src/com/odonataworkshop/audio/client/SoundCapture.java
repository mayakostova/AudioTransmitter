package com.odonataworkshop.audio.client;

import javax.sound.sampled.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by maya on 12/7/14.
 */
public class SoundCapture {
    private static final Logger log = Logger.getLogger(SoundCapture.class.getName());

    private TargetDataLine mLine;
    private UpdateUIHandler mHandler;
    private Socket mSocket;

    public SoundCapture(UpdateUIHandler aHandler) {
        mHandler = aHandler;
    }

    public void capture(Socket aSocket) {

        try {
            mSocket = aSocket;
            final OutputStream objectOutputStream = mSocket.getOutputStream();
            final AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                mHandler.write("Line is not supported: " + format.toString());
            }
            mLine = (TargetDataLine) AudioSystem.getLine(info);

            mLine.open(format);
            mHandler.write("Start sending...");
            mLine.start();
            Thread captureThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        BufferedOutputStream outputStream = new BufferedOutputStream(objectOutputStream);

                        int bufferSize = 1024;
                        byte buffer[] = new byte[bufferSize];
                        AudioInputStream is = new AudioInputStream(mLine);
                        while (mLine.isOpen()) {
                            int count = is.read(buffer, 0, buffer.length);
                                outputStream.write(buffer, 0, count);
                                objectOutputStream.flush();
                        }
                    } catch (FileNotFoundException e) {
                        log.log(Level.SEVERE,e.getMessage(),e);
                        mHandler.write(e.getMessage());
                    } catch (SocketException e) {
                        //connection closed by the other end, not important...
                        log.log(Level.INFO,e.getMessage(),e);
                    } catch (IOException e) {
                        log.log(Level.SEVERE,e.getMessage(),e);
                        mHandler.write(e.getMessage());
                    } catch (Exception e) {
                        log.log(Level.SEVERE,e.getMessage(),e);
                        mHandler.write(e + e.getMessage());
                    } finally {
                        try {
                            if (objectOutputStream != null) {
                                objectOutputStream.flush();
                                objectOutputStream.close();
                            }
                            mLine.stop();
                            mLine.close();
                        } catch (IOException e) {
                            log.log(Level.SEVERE,e.getMessage(),e);
                            mHandler.write(e.getMessage());
                        }
                        mHandler.write("Client stopped!");
                    }

                }
            });
            captureThread.start();

        } catch (LineUnavailableException e) {
            log.log(Level.SEVERE,e.getMessage(),e);
            mHandler.write(e + e.getMessage());
        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage(),e);
            mHandler.write(e + e.getMessage());
        } catch (Exception e){
            log.log(Level.SEVERE,e.getMessage(),e);
            mHandler.write(e + e.getMessage());
        }
    }


    public synchronized void stop() {
        mHandler.write("Stopping client...");
        try
        {
            mSocket.close();
        } catch (IOException e) {
            log.log(Level.SEVERE,"Error closing client. Reason: " + e.getMessage(),e);
        }
        mLine.stop();
        mLine.close();
    }
}
