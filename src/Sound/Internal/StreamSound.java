package Sound.Internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import Sound.Sound;

/**
 * The StreamSound class implements the Sound interface that streams audio data
 * from a temporary file.
 *
 * @author Carlos Martinez
 */
public class StreamSound implements Sound {

    private final int ID;
    
    private Mixer mixer;
    
    private URL dataURL;
    
    private long numBytesPerChannel;

    /**
     * @param dataURL URL of the temporary file containing the audio data.
     * @param nBytesPerChannel the total number of bytes for each channel.
     * @param mixer Mixer that will handle this StreamSound.
     * @param id unique ID of this StreamSound.
     * @throws IOException if a stream cannot be opened from the URL.
     */
    public StreamSound(URL dataURL, long nBytesPerChannel, Mixer mixer, int id) throws IOException {
        this.ID = id;
        this.mixer = mixer;
        this.dataURL = dataURL;
        this.numBytesPerChannel = nBytesPerChannel;
        
        //Check for immediate issues.
        InputStream temp = this.dataURL.openStream();
        temp.close();
    }

    /**
     * Plays this StreamSound.
     */
    @Override
    public void play() {
        this.play(1.0);
    }

    /**
     * Plays this StreamSound with a specified volume.
     *
     * @param volume the volume at which to play this StreamSound
     */
    @Override
    public void play(double volume) {
        this.play(volume, 0.0);
    }

    /**
     * Plays this MemSound with a specified volume and pan.
     *
     * @param volume the volume this MemSound will play at.
     * @param pan the pan this MemSound will play at, between -1.0 and 1.0.
     */
    @Override
    public void play(double volume, double pan) {
        SoundRef soundRef;
        try {
            soundRef = new StreamSoundRef(this.dataURL.openStream(), this.numBytesPerChannel, volume, pan, this.ID);
            this.mixer.registerSoundReference(soundRef);
        } catch (IOException ex) {
            System.err.println("Failed to open stream for Sound");
        }
    }

    /**
     * Stops all instances of this StreamSound from playing.
     */
    @Override
    public void stop() {
        this.mixer.unRegisterSoundReference(this.ID);
    }

    /**
     * Unloads this StreamSound from the system. 
     * Attempts to use this StreamSound after unloading will result in error.
     */
    @Override
    public void unload() {
        this.mixer.unRegisterSoundReference(this.ID);
        this.mixer = null;
        this.dataURL = null;
    }

    /**
     * Implements the SoundReference interface.
     *
     * @author Carlos Martinez
     */
    private static class StreamSoundRef implements SoundRef {

        public final int SOUND_ID;
        
        private InputStream data;
        
        private byte[] buffer;
        private byte[] skipBuffer;
        
        private double volume;
        private double pan;
        
        private long nBytesPerChannel;
        private long position;

        /**
         * @param data the stream of the audio data.
         * @param nBytesPerChannel the total number of bytes for each channel.
         * @param volume volume at which the sound will play.
         * @param pan pan at which the sound will play.
         * @param soundID ID of the StreamSound for which this is a reference.
         */
        public StreamSoundRef(InputStream data, long nBytesPerChannel, double volume, double pan, int soundID) {
            this.data = data;
            
            this.nBytesPerChannel = nBytesPerChannel;
            
            this.volume = (volume >= 0.0) ? volume : 1.0;
            
            this.pan = (pan >= -1.0 && pan <= 1.0) ? pan : 0.0;
            
            this.buffer = new byte[4];
            this.skipBuffer = new byte[20];
            this.position = 0;
            
            this.SOUND_ID = soundID;
        }

        /**
         * Get the ID of the StreamSound that produced this StreamSoundRef.
         *
         * @return the ID of this StreamSoundRef's parent StreamSound.
         */
        @Override
        public int getSoundID() {
            return this.SOUND_ID;
        }

        /**
         * Gets the volume of this StreamSoundRef.
         *
         * @return volume of this StreamSoundRef.
         */
        @Override
        public double getVolume() {
            return this.volume;
        }

        /**
         * Gets the pan of this StreamSoundRef.
         *
         * @return pan of this StreamSoundRef.
         */
        @Override
        public double getPan() {
            return this.pan;
        }

        /**
         * Get the number of bytes remaining for each channel.
         *
         * @return number of bytes remaining for each channel.
         */
        @Override
        public long bytesAvailable() {
            return this.nBytesPerChannel - this.position;
        }

        /**
         * Skip a specified number of bytes of the audio data.
         *
         * @param amount number of bytes to skip.
         */
        @Override
        public void skipBytes(long amount) {
            if (this.position + amount >= this.nBytesPerChannel) {
                this.position = this.nBytesPerChannel;
                return;
            }
            
            //Amount of bytes to skip per channel, so double it...
            long numSkip = amount * 2;
            
            int tmpRead = 0;
            long numRead = 0;
            
            try {
                while (numRead < numSkip && tmpRead != -1) {
                    //Determine safe length to read...
                    long remaining = numSkip - numRead;
                    int len = remaining > this.skipBuffer.length ? this.skipBuffer.length : (int) remaining;
                    
                    //Read it...
                    tmpRead = this.data.read(this.skipBuffer, 0, len);
                    numRead += tmpRead;
                }
            } catch (IOException ex) {
                this.position = this.nBytesPerChannel;
            }
            
            //Properly increment the position.
            if (tmpRead == -1) {
                this.position = this.nBytesPerChannel;
            } else {
                this.position += amount;
            }
        }

        /**
         * Get the next two bytes from the sound data in the specified endian.
         *
         * @param data length-2 array to write in next 2 bytes from each channel.
         * @param bigEndian true if the bytes should be read big endian.
         */
        @Override
        public void nextTwoBytes(int[] data, boolean bigEndian) {
            int tmpRead = 0;
            int numRead = 0;
            
            try {
                while (numRead < this.buffer.length && tmpRead != -1) {
                    tmpRead = this.data.read(this.buffer, numRead, this.buffer.length - numRead);
                    numRead += tmpRead;
                }
            } catch (IOException ex) {
                //this shouldn't happen if the bytes were written correctly...
                this.position = this.nBytesPerChannel;
                System.err.println("Failed reading bytes for stream sound");
            }
            
            //Copy the values into the buffer...
            if (bigEndian) {
                data[0] = ((this.buffer[0] << 8) | (this.buffer[1] & 0xFF));    //Left
                data[1] = ((this.buffer[2] << 8) | (this.buffer[3] & 0xFF));    //Right
            } else {
                data[0] = ((this.buffer[1] << 8) | (this.buffer[0] & 0xFF));    //Left
                data[1] = ((this.buffer[3] << 8) | (this.buffer[2] & 0xFF));    //Right
            }
            
            //Properly increment the position.
            if (tmpRead == -1) {
                this.position = this.nBytesPerChannel;
            } else {
                this.position += 2;
            }
        }

        /**
         * Dispose of resources in use by this StreamSoundReference.
         */
        @Override
        public void dispose() {
            this.position = this.nBytesPerChannel;
            
            try {
                this.data.close();
            } catch (IOException ex) {
                //If it gets here... well you're fucked :D
            }
            
            this.buffer = null;
            this.skipBuffer = null;
        }
    }
}
