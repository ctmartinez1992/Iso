package Sound;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import Util.List.ByteList;
import Sound.Internal.MemoryMusic;
import Sound.Internal.MemorySound;
import Sound.Internal.Mixer;
import Sound.Internal.StreamInfo;
import Sound.Internal.StreamMusic;
import Sound.Internal.StreamSound;
import Sound.Internal.UpdateRunner;
import Util.Math.MyMath;

/**
 * MainSound is the main class of the Sound system. Initialize -> Load/Unload
 * Sounds and Music -> Shutdown.
 *
 * @author Carlos Martinez
 */
public class MainSound {

    public static final String SYSTEM_VERSION = "0.1";
    
    //The internal format used.
    public static final AudioFormat FORMAT = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED,                                    //linear signed PCM.
            44100,                                                              //44.1kHz Sampling Rate.
            16,                                                                 //16-bit.
            2,                                                                  //2 channels.
            4,                                                                  //Frame Size: 4 bytes.
            44100,                                                              //Same as Sampling Rate.
            false);                                                             //Small Indian (hehehe).
    
    //The System has only 1 Mixer... This one.
    private static Mixer mixer;
    
    //Line to the Speakers.
    private static SourceDataLine speakersLine;
    
    //Auto-Updater for the System.
    private static UpdateRunner autoUpdater;
    
    //Counter for unique Sound IDs.
    private static int soundCount = 0;
    
    //Was the System initialized.
    private static boolean initialized = false;

    /**
     * Initialize the main System... Must be called before anything else and
     * should only be called once.
     */
    public static void init() {
        //Prevention from more than 1 initialization.
        if (MainSound.initialized) {
            return;
        }

        //Open a line to the speakers.
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, MainSound.FORMAT);
        if (!AudioSystem.isLineSupported(info)) {
            System.err.println("Unsupported output format!");
            return;
        }

        MainSound.speakersLine = MainSound.tryGetLine();
        if (MainSound.speakersLine == null) {
            System.err.println("Output line unavailable!");
            return;
        }

        //Start the line.
        MainSound.speakersLine.start();

        //Finishing the initialization.
        MainSound.finishInit();
    }

    /**
     * Alternative function to initialize TinySound which should only be used by
     * those very familiar with the Java Sound API. This function allows the
     * line that is used for audio playback to be opened on a specific Mixer.
     *
     * @param info The Mixer.Info representing the desired Mixer.
     * @throws LineUnavailableException If a Line is not available from the
     * specified Mixer.
     * @throws SecurityException If the specified Mixer or Line are unavailable
     * due to security restrictions.
     * @throws IllegalArgumentException If the specified Mixer is not installed
     * on the system.
     */
    public static void init(javax.sound.sampled.Mixer.Info info) throws LineUnavailableException, SecurityException, IllegalArgumentException {
        //Prevention from more than 1 initialization.
        if (MainSound.initialized) {
            return;
        }

        //Try to open a line to the speakers,
        javax.sound.sampled.Mixer sampledMixer = AudioSystem.getMixer(info);
        DataLine.Info lineInfo = new DataLine.Info(SourceDataLine.class, MainSound.FORMAT);
        
        MainSound.speakersLine = (SourceDataLine) sampledMixer.getLine(lineInfo);
        MainSound.speakersLine.open(MainSound.FORMAT);
        
        //Start the line.
        MainSound.speakersLine.start();

        //Finishing the initialization.
        MainSound.finishInit();
    }

    /**
     * Initializes the Mixer and Updater, and marks the System as initialized.
     */
    private static void finishInit() {
        //Initialize the Mixer.
        MainSound.mixer = new Mixer();
        
        //Initialize and start the Updater.
        MainSound.autoUpdater = new UpdateRunner(MainSound.mixer, MainSound.speakersLine);
        
        Thread updateThread = new Thread(MainSound.autoUpdater);
        updateThread.setDaemon(true);
        updateThread.setPriority(Thread.MAX_PRIORITY);
        
        MainSound.initialized = true;
        
        updateThread.start();
        
        //Yield to give the Updater some time.
        Thread.yield();
    }

    /**
     * Shutdown the System.
     */
    public static void shutdown() {
        //Prevention from more than 1 initialization.
        if (!MainSound.initialized) {
            return;
        }
        
        //Stop the Auto Updater;
        MainSound.autoUpdater.stop();
        MainSound.autoUpdater = null;
        
        //Cut the line with the Speakers;
        MainSound.speakersLine.stop();
        MainSound.speakersLine.flush();
        
        //Clean the Mixer of all sounds and music.
        MainSound.mixer.clearMusic();
        MainSound.mixer.clearSounds();
        MainSound.mixer = null;
        
        //Dead...
        MainSound.initialized = false;
    }

    /**
     * Load a Music by a resource name. The resource must be on the classpath
     * for this to work. This will store audio data in memory.
     *
     * @param name name of the Music resource.
     * @return Music resource as specified, null if not found/loaded.
     */
    public static Music loadMusic(String name) {
        return MainSound.loadMusic(name, false);
    }

    /**
     * Load a Music by a resource name. The resource must be on the classpath
     * for this to work.
     *
     * @param name name of the Music resource.
     * @param streamFromFile true if this Music should be streamed from a
     * temporary file to reduce memory overhead.
     * @return Music resource as specified, null if not found/loaded.
     */
    public static Music loadMusic(String name, boolean streamFromFile) {
        //Check if the system is initialized.
        if (!MainSound.initialized) {
            System.err.println("Sound System not initialized!");
            return null;
        }
        
        //Check for empty String.
        if (name == null) {
            return null;
        }
        
        //Check for correct naming.
        if (!name.startsWith("/")) {
            name = "/" + name;
        }
        
        URL url = MainSound.class.getResource(name);
        
        //Check if the resource was found.
        if (url == null) {
            System.err.println("Unable to find resource " + name + "!");
            return null;
        }
        
        return MainSound.loadMusic(url, streamFromFile);
    }

    /**
     * Load a Music by a File. This will store audio data in memory.
     *
     * @param file the Music file to load
     * @return Music from file as specified, null if not found/loaded
     */
    public static Music loadMusic(File file) {
        return MainSound.loadMusic(file, false);
    }

    /**
     * Load a Music by a File.
     *
     * @param file the Music file to load
     * @param streamFromFile true if this Music should be streamed from a
     * temporary file to reduce memory overhead
     * @return Music from file as specified, null if not found/loaded
     */
    public static Music loadMusic(File file, boolean streamFromFile) {
        //Check if the system is initialized.
        if (!MainSound.initialized) {
            System.err.println("Sound System not initialized!");
            return null;
        }
        
        //Check for empty File.
        if (file == null) {
            return null;
        }
        
        URL url;
        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            System.err.println("Unable to find file " + file + "!");
            return null;
        }
        
        return MainSound.loadMusic(url, streamFromFile);
    }

    /**
     * Load a Music by a URL. This will store audio data in memory.
     *
     * @param url the URL of the Music.
     * @return Music from URL as specified, null if not found/loaded.
     */
    public static Music loadMusic(URL url) {
        return MainSound.loadMusic(url, false);
    }

    /**
     * Load a Music by a URL.
     *
     * @param url the URL of the Music.
     * @param streamFromFile true if this Music should be streamed from a
     * temporary file to reduce memory overhead.
     * @return Music from URL as specified, null if not found/loaded.
     */
    public static Music loadMusic(URL url, boolean streamFromFile) {
        //Check if the system is initialized.
        if (!MainSound.initialized) {
            System.err.println("Sound System not initialized!");
            return null;
        }
        
        //Check for empty String.
        if (url == null) {
            return null;
        }
        
        //Obtain a Stream.
        AudioInputStream audioStream = MainSound.getValidAudioStream(url);
        
        //Did it fail?
        if (audioStream == null) {
            return null;
        }
        
        //Read ALL the bytes!
        byte[][] data = MainSound.readAllBytes(audioStream);
        
        //Did it fail?
        if (data == null) {
            return null;
        }
        
        //Stream it...
        if (streamFromFile) {
            StreamInfo streamInfo = MainSound.createFileStream(data);
            if (streamInfo == null) {
                return null;
            }
            
            //Attempt to create the Stream.
            StreamMusic stream = null;
            try {
                stream = new StreamMusic(streamInfo.URL, streamInfo.NUM_BYTES_PER_CHANNEL, MainSound.mixer);
            } catch (IOException ex) {
                System.err.println("Failed to create Stream of Music!");
            }
            
            return stream;
        }
        
        return new MemoryMusic(data[0], data[1], MainSound.mixer);
    }

    /**
     * Load a Sound by a resource name. The resource must be on the classpath
     * for this to work. This will store audio data in memory.
     *
     * @param name name of the Sound resource.
     * @return Sound resource as specified, null if not found/loaded.
     */
    public static Sound loadSound(String name) {
        return MainSound.loadSound(name, false);
    }

    /**
     * Load a Sound by a resource name. The resource must be on the classpath
     * for this to work.
     *
     * @param name name of the Sound resource.
     * @param streamFromFile true if this Music should be streamed from a
     * temporary file to reduce memory overhead.
     * @return Sound resource as specified, null if not found/loaded.
     */
    public static Sound loadSound(String name, boolean streamFromFile) {
        //Check if the system is initialized.
        if (!MainSound.initialized) {
            System.err.println("TinySound not initialized!");
            return null;
        }
        
        //Check for empty String.
        if (name == null) {
            return null;
        }
        
        //Check for correct naming.
        if (!name.startsWith("/")) {
            name = "/" + name;
        }
        
        URL url = MainSound.class.getResource(name);
        
        //Check if the resource was found.
        if (url == null) {
            System.err.println("Unable to find resource " + name + "!");
            return null;
        }
        
        return MainSound.loadSound(url, streamFromFile);

    }

    /**
     * Load a Sound by a File. This will store audio data in memory.
     *
     * @param file the Sound file to load.
     * @return Sound from file as specified, null if not found/loaded.
     */
    public static Sound loadSound(File file) {
        return MainSound.loadSound(file, false);
    }

    /**
     * Load a Sound by a File.
     *
     * @param file the Sound file to load
     * @param streamFromFile true if this Music should be streamed from a
     * temporary file to reduce memory overhead
     * @return Sound from file as specified, null if not found/loaded
     */
    public static Sound loadSound(File file, boolean streamFromFile) {
        //Check if the system is initialized.
        if (!MainSound.initialized) {
            System.err.println("TinySound not initialized!");
            return null;
        }
        
        //Check for empty File.
        if (file == null) {
            return null;
        }
        
        URL url;
        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            System.err.println("Unable to find file " + file + "!");
            return null;
        }
        
        return MainSound.loadSound(url, streamFromFile);
    }

    /**
     * Load a Sound by a URL. This will store audio data in memory.
     *
     * @param url the URL of the Sound.
     * @return Sound from URL as specified, null if not found/loaded.
     */
    public static Sound loadSound(URL url) {
        return MainSound.loadSound(url, false);
    }

    /**
     * Load a Sound by a URL. This will store audio data in memory.
     *
     * @param url the URL of the Sound.
     * @param streamFromFile true if this Music should be streamed from a
     * temporary file to reduce memory overhead.
     * @return Sound from URL as specified, null if not found/loaded.
     */
    public static Sound loadSound(URL url, boolean streamFromFile) {
        //Check if the system is initialized.
        if (!MainSound.initialized) {
            System.err.println("TinySound not initialized!");
            return null;
        }
        
        //Obtain a Stream.
        AudioInputStream audioStream = MainSound.getValidAudioStream(url);
        
        //Did it fail?
        if (audioStream == null) {
            return null;
        }
        
        //Read ALL the bytes!
        byte[][] data = MainSound.readAllBytes(audioStream);
        
        //Did it fail?
        if (data == null) {
            return null;
        }
        
        //Stream it...
        if (streamFromFile) {
            StreamInfo streamInfo = MainSound.createFileStream(data);
            if (streamInfo == null) {
                return null;
            }
            
            //Attempt to create the Stream.
            StreamSound stream = null;
            try {
                stream = new StreamSound(streamInfo.URL, streamInfo.NUM_BYTES_PER_CHANNEL, MainSound.mixer, MainSound.soundCount);
                MainSound.soundCount++;
            } catch (IOException ex) {
                System.err.println("Failed to create Stream Sound!");
            }
            
            return stream;
        }
        
        MainSound.soundCount++;
        
        return new MemorySound(data[0], data[1], MainSound.mixer, MainSound.soundCount);
    }

    /**
     * Reads all of the bytes from an AudioInputStream.
     *
     * @param stream the stream to read.
     * @return all bytes from the stream, null if error.
     */
    private static byte[][] readAllBytes(AudioInputStream stream) {
        //Left and Right Channels;
        byte[][] data = null;
        int nChannels = stream.getFormat().getChannels();
        
        //Handle 1-Channel.
        if (nChannels == 1) {
            byte[] left = MainSound.readAllBytesOneChannel(stream);
            if (left == null) {
                return null;
            }
            
            data = new byte[2][];
            data[0] = left;
            data[1] = left;
        } //Handle 2-Channel.
        else if (nChannels == 2) {
            data = MainSound.readAllBytesTwoChannel(stream);
        } else {
            System.err.println("Unable to read " + nChannels + " channels!");
        }
        
        return data;
    }

    /**
     * Reads all of the bytes from a 1-channel AudioInputStream.
     *
     * @param stream the stream to read.
     * @return all bytes from the stream, null if error.
     */
    private static byte[] readAllBytesOneChannel(AudioInputStream stream) {
        //Assuming 1-Channel.
        byte[] data = null;
        try {
            data = MainSound.getBytes(stream);
        } catch (IOException e) {
            System.err.println("Error reading all bytes from stream!");
            return null;
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                System.err.println("Failed to close Audio Input Stream!");
            }
        }
        
        return data;
    }

    /**
     * Reads all of the bytes from a 2-channel AudioInputStream.
     *
     * @param stream the stream to read.
     * @return all bytes from the stream, null if error.
     */
    private static byte[][] readAllBytesTwoChannel(AudioInputStream stream) {
        //Assuming 2-Channel.
        byte[][] data = null;
        try {
            byte[] allBytes = MainSound.getBytes(stream);
            byte[] left = new byte[allBytes.length / 2];
            byte[] right = new byte[allBytes.length / 2];
            
            for (int i = 0, j = 0; i < allBytes.length; i += 4, j += 2) {
                left[j] = allBytes[i];
                left[j + 1] = allBytes[i + 1];
                right[j] = allBytes[i + 2];
                right[j + 1] = allBytes[i + 3];
            }
            
            data = new byte[2][];
            data[0] = left;
            data[1] = right;
        } catch (IOException ex) {
            System.err.println("Error reading all bytes from stream!");
            return null;
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                System.err.println("Failed to close Audio Input Stream!");
            }
        }
        
        return data;
    }

    /**
     * Gets an AudioInputStream in the system format.
     *
     * @param url URL of the resource.
     * @return the specified stream as an AudioInputStream stream, null if
     * failure.
     */
    private static AudioInputStream getValidAudioStream(URL url) {
        AudioInputStream audioStream;
        try {
            audioStream = AudioSystem.getAudioInputStream(url);
            AudioFormat streamFormat = audioStream.getFormat();
            
            //1-Channel can also be treated as stereo.
            AudioFormat mono16 = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 1, 2, 44100, false);
            
            AudioFormat mono8 = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 8, 1, 1, 44100, false);
            AudioFormat stereo8 = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 8, 2, 2, 44100, false);
            
            //Check formats.
            if (streamFormat.matches(MainSound.FORMAT) || streamFormat.matches(mono16)) {
                return audioStream;
            } //Check conversion to System format.
            else if (AudioSystem.isConversionSupported(MainSound.FORMAT, streamFormat)) {
                audioStream = AudioSystem.getAudioInputStream(MainSound.FORMAT, audioStream);
            } //Check conversion to mono alternate.
            else if (AudioSystem.isConversionSupported(mono16, streamFormat)) {
                audioStream = AudioSystem.getAudioInputStream(mono16, audioStream);
            } //Try convert from 8-bit (2-Channel).
            else if (streamFormat.matches(stereo8) || AudioSystem.isConversionSupported(stereo8, streamFormat)) {
                //Convert to 8-bit stereo first.
                if (!streamFormat.matches(stereo8)) {
                    audioStream = AudioSystem.getAudioInputStream(stereo8, audioStream);
                }
                
                audioStream = MainSound.convertStereo8Bit(audioStream);
            } //Try convert from 8-bit (1-channel).
            else if (streamFormat.matches(mono8) || AudioSystem.isConversionSupported(mono8, streamFormat)) {
                //Convert to 8-bit mono first.
                if (!streamFormat.matches(mono8)) {
                    audioStream = AudioSystem.getAudioInputStream(mono8, audioStream);
                }
                
                audioStream = MainSound.convertMono8Bit(audioStream);
            } //Fucking shit is not CONVERTABLE!!!
            else {
                System.err.println("Unable to convert audio resource!");
                System.err.println(url);
                System.err.println(streamFormat);
                
                audioStream.close();
                return null;
            }
            
            //Check the frame length.
            long frameLength = audioStream.getFrameLength();
            
            if (frameLength > Integer.MAX_VALUE) {
                System.err.println("Audio resource too long!");
                return null;
            }
        } catch (UnsupportedAudioFileException ex) {
            System.err.println("Unsupported audio resource!\n" + ex.getMessage());
            return null;
        } catch (IOException ex) {
            System.err.println("Error getting resource stream!\n" + ex.getMessage());
            return null;
        }
        
        return audioStream;
    }

    /**
     * Converts an 8-bit, signed, 1-channel AudioInputStream to 16-bit, signed,
     * 1-channel.
     *
     * @param stream stream to convert.
     * @return converted stream.
     */
    private static AudioInputStream convertMono8Bit(AudioInputStream stream) {
        //Assuming 8-bit (1-Channel), to 16-bit (1-Channel).
        byte[] newData = null;
        try {
            byte[] data = MainSound.getBytes(stream);
            int newNumBytes = data.length * 2;
            
            //Check if size has overflowed.
            if (newNumBytes < 0) {
                System.err.println("Audio resource too damn high!!!");
                return null;
            }
            
            newData = new byte[newNumBytes];
            
            //Convert bytes one-by-one to int, and then to 16-bit.
            for (int i = 0, j = 0; i < data.length; i++, j += 2) {
                double floatVal = (double) data[i];
                floatVal /= (floatVal < 0) ? 128 : 127;
                floatVal = MyMath.interval(floatVal, -1.0, 1.0);
                
                int val = (int) (floatVal * Short.MAX_VALUE);
                
                newData[j + 1] = (byte) ((val >> 8) & 0xFF);                    //MSB
                newData[j] = (byte) (val & 0xFF);                               //LSB
            }
        } catch (IOException ex) {
            System.err.println("Error reading all bytes from stream!");
            return null;
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                System.err.println("Failed to close Audio Input Stream!");
            }
        }
        
        AudioFormat mono16 = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 1, 2, 44100, false);
        
        return new AudioInputStream(new ByteArrayInputStream(newData), mono16, newData.length / 2);
    }

    /**
     * Converts an 8-bit, signed, 2-channel AudioInputStream to 16-bit, signed,
     * 2-channel.
     *
     * @param stream stream to convert.
     * @return converted stream.
     */
    private static AudioInputStream convertStereo8Bit(AudioInputStream stream) {
        //Assuming 8-bit (2-channel), to 16-bit (2-channel).
        byte[] newData = null;
        try {
            byte[] data = MainSound.getBytes(stream);
            int newNumBytes = data.length * 2 * 2;
            
            //Check if size has overflowed.
            if (newNumBytes < 0) {
                System.err.println("Audio resource too damn high!!!");
                return null;
            }
            
            newData = new byte[newNumBytes];
            for (int i = 0, j = 0; i < data.length; i += 2, j += 4) {
                double leftFloatVal = (double) data[i];
                double rightFloatVal = (double) data[i + 1];
                
                leftFloatVal /= (leftFloatVal < 0) ? 128 : 127;
                rightFloatVal /= (rightFloatVal < 0) ? 128 : 127;
                leftFloatVal = MyMath.interval(leftFloatVal, -1.0, 1.0);
                rightFloatVal = MyMath.interval(rightFloatVal, -1.0, 1.0);
                
                int leftVal = (int) (leftFloatVal * Short.MAX_VALUE);
                int rightVal = (int) (rightFloatVal * Short.MAX_VALUE);
                
                //Left channel bytes.
                newData[j + 1] = (byte) ((leftVal >> 8) & 0xFF);                //MSB
                newData[j] = (byte) (leftVal & 0xFF);                           //LSB
                
                //Right channel bytes.
                newData[j + 3] = (byte) ((rightVal >> 8) & 0xFF);               //MSB
                newData[j + 2] = (byte) (rightVal & 0xFF);                      //LSB
            }
        } catch (IOException ex) {
            System.err.println("Error reading all bytes from stream!");
            return null;
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                System.err.println("Failed to close Audio Input Stream!");
            }
        }
        
        AudioFormat stereo16 = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
        return new AudioInputStream(new ByteArrayInputStream(newData), stereo16, newData.length / 4);
    }

    /**
     * Read all of the bytes from an AudioInputStream.
     *
     * @param stream the stream from which to read bytes.
     * @return all bytes read from the AudioInputStream.
     * @throws IOException.
     */
    private static byte[] getBytes(AudioInputStream stream) throws IOException {
        //Buffer 1-sec at a time.
        int bufferSize = (int) MainSound.FORMAT.getSampleRate() * MainSound.FORMAT.getChannels() * MainSound.FORMAT.getFrameSize();
        byte[] buffer = new byte[bufferSize];
        ByteList list = new ByteList(bufferSize);
        int nReads;
        
        while ((nReads = stream.read(buffer)) > -1) {
            for (int i = 0; i < nReads; i++) {
                list.add(buffer[i]);
            }
        }
        
        return list.asArray();
    }

    /**
     * Dumps audio data to a temporary file for streaming and returns a
     * StreamInfo for the stream.
     *
     * @param data the audio data to write to the temporary file.
     * @return a StreamInfo for the stream.
     */
    private static StreamInfo createFileStream(byte[][] data) {
        //Try to create a file for the data.
        File tmp;
        try {
            tmp = File.createTempFile("tiny", "sound");
            tmp.deleteOnExit();
        } catch (IOException ex) {
            System.err.println("Failed to create file for streaming!");
            return null;
        }
        
        //Get the file.
        URL url;
        try {
            url = tmp.toURI().toURL();
        } catch (MalformedURLException ex) {
            System.err.println("Failed to get URL for stream file!");
            return null;
        }
        
        //Write to the file.
        OutputStream outStream;
        try {
            outStream = new BufferedOutputStream(new FileOutputStream(tmp), (512 * 1024));
        } catch (FileNotFoundException ex) {
            System.err.println("Failed to open stream file for writing!");
            return null;
        }
        
        //Write the bytes...
        try {
            for (int i = 0; i < data[0].length; i += 2) {
                try {
                    outStream.write(data[0], i, 2);                             //Left
                    outStream.write(data[1], i, 2);                             //Right
                } catch (IOException ex) {
                    System.err.println("Failed writing bytes to stream file!");
                    return null;
                }
            }
        } finally {
            try {
                outStream.close();
            } catch (IOException ex) {
                //凸ಠ益ಠ)凸
                System.err.println("Failed closing stream file after writing!");
            }
        }
        
        return new StreamInfo(url, data[0].length);
    }

    /**
     * Iterates through available Mixers looking for one that can provide a 
     * line to the speakers.
     *
     * @return an opened SourceDataLine to the speakers.
     */
    private static SourceDataLine tryGetLine() {
        //Build our Line Info.
        DataLine.Info lineInfo = new DataLine.Info(SourceDataLine.class, MainSound.FORMAT);
        javax.sound.sampled.Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
        
        //Let's try to find a Line.
        for (int i = 0; i < mixerInfos.length; i++) {
            javax.sound.sampled.Mixer sampledMixer = null;
            try {
                //Get the mixer,
                sampledMixer = AudioSystem.getMixer(mixerInfos[i]);
            } catch (SecurityException | IllegalArgumentException ex) {
                System.err.println("Failed to obtain a Mixer for our Line.");
            }
            
            //Check if we got a mixer and our Line is supported.
            if (sampledMixer == null || !sampledMixer.isLineSupported(lineInfo)) {
                continue;
            }
            
            //Let's get a Line, shall we?
            SourceDataLine line = null;
            try {
                line = (SourceDataLine) sampledMixer.getLine(lineInfo);
                if (!line.isOpen()) {
                    line.open(MainSound.FORMAT);
                }
            } catch (LineUnavailableException | SecurityException e) {
                System.err.println("Failed to access a Line.");
            }
            
            //Oh Goodie... We got a line...
            if (line != null && line.isOpen()) {
                return line;
            }
        }
        
        //Oh Hells Blazin'...
        return null;
    }

    /**
     * Set the global volume.
     *
     * @param volume the global volume to set.
     */
    public static void setGlobalVolume(double volume) {
        if (!MainSound.initialized) {
            return;
        }
        
        MainSound.mixer.setVolume(volume);
    }

    /**
     * Get the global volume for all audio.
     *
     * @return the global volume for all audio.
     */
    public static double getGlobalVolume() {
        if (!MainSound.initialized) {
            return -1.0;
        }
        
        return MainSound.mixer.getVolume();
    }

    /**
     * Is the System fully initialized.
     *
     * @return true if it's initialized, false if it has not been initialized or
     * has subsequently been shutdown
     */
    public static boolean isInitialized() {
        return MainSound.initialized;
    }
}
