package Sound.Internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import Sound.Music;
import Sound.MainSound;

/**
 * The StreamMusic class implements the Music interface that streams audio data
 * from a temporary file.
 *
 * @author Carlos Martinez
 */
public class StreamMusic implements Music {

    private URL dataURL;
    
    private Mixer mixer;
    
    private MusicRef musicRef;

    /**
     * @param dataURL URL of the temporary file containing audio data.
     * @param numBytesPerChannel the total number of bytes for each channel.
     * @param mixer Mixer that will handle this StreamSound.
     * @throws IOException if a stream cannot be opened from the URL.
     */
    public StreamMusic(URL dataURL, long numBytesPerChannel, Mixer mixer) throws IOException {
        this.dataURL = dataURL;
        this.mixer = mixer;
        this.musicRef = new StreamMusicRef(this.dataURL, false, false, 0, 0, numBytesPerChannel, 1.0, 0.0);
        
        this.mixer.registerMusicReference(this.musicRef);
    }

    /**
     * Play this StreamMusic and loop if specified.
     *
     * @param loop loop it!
     */
    @Override
    public void play(boolean loop) {
        this.musicRef.setLoop(loop);
        this.musicRef.setPlaying(true);
    }

    /**
     * Play this StreamMusic at the specified volume and loop if specified.
     *
     * @param loop loop it!
     * @param volume volume it!
     */
    @Override
    public void play(boolean loop, double volume) {
        this.setLoop(loop);
        this.setVolume(volume);
        this.musicRef.setPlaying(true);
    }

    /**
     * Play this StreamMusic at the specified volume, pan and loop.
     *
     * @param loop loop it!
     * @param volume volume it!
     * @param pan pan it!
     */
    @Override
    public void play(boolean loop, double volume, double pan) {
        this.setLoop(loop);
        this.setVolume(volume);
        this.setPan(pan);
        this.musicRef.setPlaying(true);
    }

    /**
     * Stop playing this StreamMusic and rewind it.
     */
    @Override
    public void stop() {
        this.musicRef.setPlaying(false);
        this.rewind();
    }

    /**
     * Stop playing this StreamMusic and keep its current position.
     */
    @Override
    public void pause() {
        this.musicRef.setPlaying(false);
    }

    /**
     * Play this StreamMusic from its current position.
     */
    @Override
    public void resume() {
        this.musicRef.setPlaying(true);
    }

    /**
     * Set this StreamMusic's position to the beginning.
     */
    @Override
    public void rewind() {
        this.musicRef.setPosition(0);
    }

    /**
     * Set this StreamMusic's position to the loop position.
     */
    @Override
    public void rewindToLoopPosition() {
        long byteIndex = this.musicRef.getLoopPosition();
        this.musicRef.setPosition(byteIndex);
    }

    /**
     * Determine if this StreamMusic is playing.
     *
     * @return true if this StreamMusic is playing.
     */
    @Override
    public boolean playing() {
        return this.musicRef.getPlaying();
    }

    /**
     * Determine if this StreamMusic has reached its end and stopped playing.
     *
     * @return true if this StreamMusic has reached the end and is done playing.
     */
    @Override
    public boolean done() {
        return this.musicRef.done();
    }

    /**
     * Set whether this StreamMusic will loop.
     *
     * @param loop whether this StreamMusic will loop.
     */
    @Override
    public void setLoop(boolean loop) {
        this.musicRef.setLoop(loop);
    }

    /**
     * Determine if this StreamMusic will loop.
     *
     * @return true if this StreamMusic will loop.
     */
    @Override
    public boolean getLoop() {
        return this.musicRef.getLoop();
    }

    /**
     * Set the loop position of this StreamMusic by sample frame.
     *
     * @param frameIndex sample frame loop position to set.
     */
    @Override
    public void setLoopPositionByFrame(int frameIndex) {
        int bytesPerChannelForFrame = MainSound.FORMAT.getFrameSize() / MainSound.FORMAT.getChannels();
        long byteIndex = (long) (frameIndex * bytesPerChannelForFrame);
        this.musicRef.setLoopPosition(byteIndex);
    }

    /**
     * Get the loop position of this StreamMusic by sample frame.
     *
     * @return loop position by sample frame.
     */
    @Override
    public int getLoopPositionByFrame() {
        int bytesPerChannelForFrame = MainSound.FORMAT.getFrameSize() / MainSound.FORMAT.getChannels();
        long byteIndex = this.musicRef.getLoopPosition();
        return (int) (byteIndex / bytesPerChannelForFrame);
    }

    /**
     * Set the loop position of this StreamMusic by seconds.
     *
     * @param seconds loop position to set by seconds.
     */
    @Override
    public void setLoopPositionBySeconds(double seconds) {
        int bytesPerChannelForFrame = MainSound.FORMAT.getFrameSize() / MainSound.FORMAT.getChannels();
        long byteIndex = (long) (seconds * MainSound.FORMAT.getFrameRate() * bytesPerChannelForFrame);
        this.musicRef.setLoopPosition(byteIndex);
    }

    /**
     * Get the loop position of this StreamMusic by seconds.
     *
     * @return loop position by seconds.
     */
    @Override
    public double getLoopPositionBySeconds() {
        int bytesPerChannelForFrame = MainSound.FORMAT.getFrameSize() / MainSound.FORMAT.getChannels();
        long byteIndex = this.musicRef.getLoopPosition();
        return (byteIndex / (MainSound.FORMAT.getFrameRate() * bytesPerChannelForFrame));
    }

    /**
     * Set the volume of this StreamMusic.
     *
     * @param volume the volume of this StreamMusic.
     */
    @Override
    public void setVolume(double volume) {
        if (volume >= 0.0) {
            this.musicRef.setVolume(volume);
        }
    }

    /**
     * Get the volume of this StreamMusic.
     *
     * @return volume of this StreamMusic.
     */
    @Override
    public double getVolume() {
        return this.musicRef.getVolume();
    }

    /**
     * Set the pan of this StreamMusic, between -1.0 and 1.0.
     *
     * @param pan the pan of this StreamMusic.
     */
    @Override
    public void setPan(double pan) {
        if (pan >= -1.0 && pan <= 1.0) {
            this.musicRef.setPan(pan);
        }
    }

    /**
     * Get the pan of this StreamMusic.
     *
     * @return pan of this StreamMusic.
     */
    @Override
    public double getPan() {
        return this.musicRef.getPan();
    }

    /**
     * Unload this MemMusic from the system. Attempts to use this MemMusic after
     * unloading will result in error.
     */
    @Override
    public void unload() {
        this.mixer.unRegisterMusicReference(this.musicRef);
        this.musicRef.dispose();
        this.mixer = null;
        this.dataURL = null;
        this.musicRef = null;
    }

    /**
     * The StreamMusicReference implements the MusicReference interface.
     */
    private static class StreamMusicRef implements MusicRef {

        private URL url;
        
        private InputStream data;
        
        private byte[] buf;
        private byte[] skipBuf;
        
        private double volume;
        private double pan;
        private long nBytesPerChannel;
        private long loopPosition;
        private long position;
        
        private boolean playing;
        private boolean loop;

        /**
         * @param dataURL URL of the temporary file containing audio data.
         * @param playing true if the music should be playing.
         * @param loop true if the music should loop.
         * @param loopPosition byte index of the loop position in music data.
         * @param position byte index position in music data.
         * @param nBytesPerChannel the total number of bytes for each channel.
         * @param volume volume to play the music.
         * @param pan pan to play the music.
         * @throws IOException if a stream cannot be opened from the URL.
         */
        public StreamMusicRef(URL dataURL, boolean playing, boolean loop, long loopPosition, long position, long nBytesPerChannel, double volume, double pan) throws IOException {
            this.url = dataURL;
            
            this.data = this.url.openStream();
            
            this.buf = new byte[4];
            this.skipBuf = new byte[50];
            
            this.volume = volume;
            this.pan = pan;
            
            this.nBytesPerChannel = nBytesPerChannel;
            this.loopPosition = loopPosition;
            this.position = position;
            
            this.playing = playing;
            this.loop = loop;
        }

        /**
         * Get the playing setting of this StreamMusicRef.
         *
         * @return true if this StreamMusicRef is set to play.
         */
        @Override
        public synchronized boolean getPlaying() {
            return this.playing;
        }

        /**
         * Get the loop setting of this StreamMusicRef.
         *
         * @return true if this StreamMusicRef is set to loop.
         */
        @Override
        public synchronized boolean getLoop() {
            return this.loop;
        }

        /**
         * Get the byte index of this StreamMusicRef.
         *
         * @return byte index of this StreamMusicRef.
         */
        @Override
        public synchronized long getPosition() {
            return this.position;
        }

        /**
         * Get the loop position byte index of this StreamMusicRef.
         *
         * @return loop position byte index of this StreamMusicRef.
         */
        @Override
        public synchronized long getLoopPosition() {
            return this.loopPosition;
        }

        /**
         * Get the volume of this StreamMusicRef.
         *
         * @return volume of this StreamMusicRef.
         */
        @Override
        public synchronized double getVolume() {
            return this.volume;
        }

        /**
         * Get the pan of this StreamMusicRef.
         *
         * @return pan of this StreamMusicRef.
         */
        @Override
        public synchronized double getPan() {
            return this.pan;
        }

        /**
         * Set whether this StreamMusicRef is playing.
         *
         * @param playing whether this StreamMusicRef is playing.
         */
        @Override
        public synchronized void setPlaying(boolean playing) {
            this.playing = playing;
        }

        /**
         * Set whether this StreamMusicRef will loop.
         *
         * @param loop whether this StreamMusicRef will loop.
         */
        @Override
        public synchronized void setLoop(boolean loop) {
            this.loop = loop;
        }

        /**
         * Set the byte index of this StreamMusicRef.
         *
         * @param position the byte index to set.
         */
        @Override
        public synchronized void setPosition(long position) {
            if (position >= 0 && position < this.nBytesPerChannel) {
                if (position >= this.position) {
                    this.skipBytes(position - this.position);
                } else {
                    //Close our current stream...
                    try {
                        this.data.close();
                    } catch (IOException e) {
                        //whatever...
                    }
                    
                    //New Stream...
                    try {
                        this.data = this.url.openStream();
                        this.position = 0;
                        this.skipBytes(position);
                    } catch (IOException e) {
                        System.err.println("Failed to open stream for StreamMusic");
                        this.playing = false;
                    }
                }
            }
        }

        /**
         * Set the loop position byte index of this StreamMusicRef.
         *
         * @param loopPosition the loop position byte index to set.
         */
        @Override
        public synchronized void setLoopPosition(long loopPosition) {
            if (loopPosition >= 0 && loopPosition < this.nBytesPerChannel) {
                this.loopPosition = loopPosition;
            }
        }

        /**
         * Set the volume of this StreamMusicRef.
         *
         * @param volume the volume of this StreamMusicR.
         */
        @Override
        public synchronized void setVolume(double volume) {
            this.volume = volume;
        }

        /**
         * Set the pan of this StreamMusicRef, between -1.0 and 1.0.
         *
         * @param pan the pan of this StreamMusicRef.
         */
        @Override
        public synchronized void setPan(double pan) {
            this.pan = pan;
        }

        /**
         * Get the number of bytes remaining for each channel.
         *
         * @return number of bytes remaining for each channel.
         */
        @Override
        public synchronized long bytesAvailable() {
            return this.nBytesPerChannel - this.position;
        }

        /**
         * Determine if there are no bytes remaining and play has stopped.
         *
         * @return true if there are no bytes remaining and the reference is no
         * longer playing.
         */
        @Override
        public synchronized boolean done() {
            long available = this.nBytesPerChannel - this.position;
            return available <= 0 && !this.playing;
        }

        /**
         * Skip a specified number of bytes of the audio data.
         *
         * @param amount number of bytes to skip.
         */
        @Override
        public synchronized void skipBytes(long amount) {
            if ((this.position + amount) >= this.nBytesPerChannel) {
                if (!this.loop) {
                    this.position += amount;
                    this.playing = false;
                    return;
                } else {
                    //Calculate the next position.
                    long loopLength = this.nBytesPerChannel - this.loopPosition;
                    long bytesOver = (this.position + amount) - this.nBytesPerChannel;
                    long nextPosition = this.loopPosition + (bytesOver % loopLength);
                    
                    this.setPosition(nextPosition);
                    return;
                }
            }
            
            //Amount of bytes to skip per channel, so double it...
            long numSkip = amount * 2;
            
            int tmpRead = 0;
            int numRead = 0;
            try {
                while (numRead < numSkip && tmpRead != -1) {
                    //Calculate length to read...
                    long remaining = numSkip - numRead;
                    int len = remaining > this.skipBuf.length ? this.skipBuf.length : (int) remaining;
                    
                    //Read it...
                    tmpRead = this.data.read(this.skipBuf, 0, len);
                    numRead += tmpRead;
                }
            } catch (IOException ex) {
                this.position = this.nBytesPerChannel;
                this.playing = false;
            }
            
            //Properly increment the position.
            if (tmpRead == -1) {
                this.position = this.nBytesPerChannel;
                this.playing = false;
            } else {
                this.position += amount;
            }
        }

        /**
         * Get the next two bytes from the music data in the specified endian.
         *
         * @param data length-2 array to write in next 2 bytes from each channel.
         * @param bigEndian true if the bytes should be read big endian.
         */
        @Override
        public synchronized void nextTwoBytes(int[] data, boolean bigEndian) {
            //Attempt to read the audion data.
            int tmpRead = 0;
            int numRead = 0;
            try {
                while (numRead < this.buf.length && tmpRead != -1) {
                    tmpRead = this.data.read(this.buf, numRead, this.buf.length - numRead);
                    numRead += tmpRead;
                }
            } catch (IOException ex) {
                //If the bytes were written correctly, you won't get here.
                this.position = this.nBytesPerChannel;
                System.err.println("Failed reading bytes for stream sound");
            }
            
            //copy the values to the buffer...
            if (bigEndian) {
                data[0] = ((this.buf[0] << 8) | (this.buf[1] & 0xFF));          //Left.
                data[1] = ((this.buf[2] << 8) | (this.buf[3] & 0xFF));          //Right.
            } else {
                data[0] = ((this.buf[1] << 8) | (this.buf[0] & 0xFF));          //Left.
                data[1] = ((this.buf[3] << 8) | (this.buf[2] & 0xFF));          //Right.
            }
            
            //Properly increment the position.
            if (tmpRead == -1) { 
                this.position = this.nBytesPerChannel;
            } else {
                this.position += 2;
            }
            
            //wrap if looping, stop otherwise...
            if (this.position >= this.nBytesPerChannel) {
                if (this.loop) {
                    this.setPosition(this.loopPosition);
                } else {
                    this.playing = false;
                }
            }
        }

        /**
         * Dispose of resources in use by this StreamMusicRef.
         */
        @Override
        public synchronized void dispose() {
            this.playing = false;
            this.position = this.nBytesPerChannel;
            this.url = null;
            try {
                this.data.close();
            } catch (IOException ex) {
                //Dang it man...
            }
        }
    }
}