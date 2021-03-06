# AudioTransmitter

An application for remote audio output streaming. Streams audio from a computer to a remote android device (via the [android application](https://github.com/mayakostova/RemoteAudioServer)), which transforms as remote audio speakers communicator. Should be used with a loopback device, or, preferably, with a virtual audio driver. There are plenty available both for Mac and Windows. For me, those are VB-Audio driver for windows ( http://vb-audio.pagesperso-orange.fr/Cable/index.htm  ) and SoundFlower (https://code.google.com/p/soundflower/downloads/list or https://rogueamoeba.com/freebies/soundflower/  ) for Mac. 

The advantage here is that you don't have to have the music. Just place your laptop on an accessible and comfortable space and listen to youtube from those pro speakers hidden in the corner without the need of cables all over the room.

#Distributives

windows: [link](https://sourceforge.net/projects/audiotransmitter/files/windows/) <br/>
osx: [link](https://sourceforge.net/projects/audiotransmitter/files/OSX/) <br/>
android application: [link](https://sourceforge.net/projects/audiotransmitter/files/android/) 

#User Guide

![screen](https://cloud.githubusercontent.com/assets/5616532/9252715/85efdb08-41e2-11e5-92be-f4202405aed9.jpg)
![screen2](https://cloud.githubusercontent.com/assets/5616532/9252623/1928a806-41e2-11e5-9e49-375540516911.jpg)

1. First start the virtual audio driver, You could use VB-Audio driver or SoundFlower as described above.
2. Set the **audio input** and **audio output** to be with the driver. 
3. Connect your android device to speakers via the audio jack connector. Start the device's wifi and connect to your router.
4. Start the android application. You will see your local ip and connection port.
5. Start the desktop application. In **host** field write the ip and in **port** the connection port (defaults to 9991).
6. Hit connect and you should see the text: <br/>
    "Connection Established  <br/> 
    Start sending...".

Now you could play any music from your laptop, for example, from youtube.

Enjoy!

