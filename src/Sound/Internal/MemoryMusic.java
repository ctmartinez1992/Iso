package Sound.Internal;

import Sound.Music;
import Sound.MainSound;

/**
 * The MemoryMusic class implements the Music interface that stores all audio
 * data in memory for low latency.
 *
 * @author Carlos Martinez
 */
public class MemoryMusic implements Music {

    private Mixer mixer;
    
    private MusicRef reference;
    
    private byte[] left;
    private byte[] right;

    /**
     * @param left left channel of music data.
     * @param right right channel of music data.
     * @param mixer Mixer with which this Music is registered.
     */
    public MemoryMusic(byte[] left, byte[] right, Mixer mixer) {
        this.left = left;
        this.right = right;
        this.mixer = mixer;
        this.reference = new MemoryMusicRef(this.left, this.right, false, false, 0, 0, 1.0, 0.0);

        this.mixer.registerMusicReference(this.reference);
    }

    /**
     * Play this Music and loop if specified.
     *
     * @param loop if this Music should loop.
     */
    @Override
    public void play(boolean loop) {
        this.reference.setLoop(loop);
        this.reference.setPlaying(true);
    }

    /**
     * Play this Music at the specified volume and loop if specified.
     *
     * @param loop if this Music should loop.
     * @param volume the volume to play this Music.
     */
    @Override
    public void play(boolean loop, double volume) {
        this.setLoop(loop);
        this.setVolume(volume);
        this.reference.setPlaying(true);
    }

    /**
     * Play this Music at the specified volume, pan and loop.
     *
     * @param loop if this Music should loop.
     * @param volume the volume this Music will play at.
     * @param pan the pan this Music will play at, between -1.0 and 1.0.
     */
    @Override
    public void play(boolean loop, double volume, double pan) {
        this.setLoop(loop);
        this.setVolume(volume);
        this.setPan(pan);
        this.reference.setPlaying(true);
    }

    /**
     * Stop playing this Music and rewind it.
     */
    @Override
    public void stop() {
        this.reference.setPlaying(false);
        this.rewind();
    }

    /**
     * Stop playing this Music and keep its current position.
     */
    @Override
    public void pause() {
        this.reference.setPlaying(false);
    }

    /**
     * Play this Music from its current position.
     */
    @Override
    public void resume() {
        this.reference.setPlaying(true);
    }

    /**
     * Set this Music's position to the beginning.
     */
    @Override
    public void rewind() {
        this.reference.setPosition(0);
    }

    /**
     * Set this Music's position to the loop position.
     */
    @Override
    public void rewindToLoopPosition() {
        long byteIndex = this.reference.getLoopPosition();
        this.reference.setPosition(byteIndex);
    }

    /**
     * Determine if this Music is playing.
     *
     * @return true if this Music is playing.
     */
    @Override
    public boolean playing() {
        return this.reference.getPlaying();
    }

    /**
     * Determine if this Music has reached its end and is done playing.
     *
     * @return true if this Music has reached the end and is done playing.
     */
    @Override
    public boolean done() {
        return this.reference.done();
    }

    /**
     * Set whether this Music will loop.
     *
     * @param loop whether this Music will loop.
     */
    @Override
    public void setLoop(boolean loop) {
        this.reference.setLoop(loop);
    }

    /**
     * Determine if this Music will loop.
     *
     * @return true if this Music will loop.
     */
    @Override
    public boolean getLoop() {
        return this.reference.getLoop();
    }

    /**
     * Set the volume of this Music.
     *
     * @param volume the volume of this Music.
     */
    @Override
    public void setVolume(double volume) {
        if (volume >= 0.0) {
            this.reference.setVolume(volume);
        }
    }

    /**
     * Get the volume of this Music.
     *
     * @return volume of this Music.
     */
    @Override
    public double getVolume() {
        return this.reference.getVolume();
    }

    /**
     * Set the pan of this Music, between -1.0 and 1.0.
     *
     * @param pan the pan of this Music.
     */
    @Override
    public void setPan(double pan) {
        if (pan >= -1.0 && pan <= 1.0) {
            this.reference.setPan(pan);
        }
    }

    /**
     * Get the pan of this Music.
     *
     * @return pan of this Music.
     */
    @Override
    public double getPan() {
        return this.reference.getPan();
    }

    /**
     * Set the loop position of this MemMusic by sample frame.
     *
     * @param frameIndex sample frame loop position to set.
     */
    @Override
    public void setLoopPositionByFrame(int frameIndex) {
        int bytesPerChannelForFrame = MainSound.FORMAT.getFrameSize() / MainSound.FORMAT.getChannels();
        long byteIndex = (long) (frameIndex * bytesPerChannelForFrame);
        this.reference.setLoopPosition(byteIndex);
    }

    /**
     * Get the loop position of this Music by sample frame.
     *
     * @return loop position by sample frame.
     */
    @Override
    public int getLoopPositionByFrame() {
        int bytesPerChannelForFrame = MainSound.FORMAT.getFrameSize() / MainSound.FORMAT.getChannels();
        long byteIndex = this.reference.getLoopPosition();
        return (int) (byteIndex / bytesPerChannelForFrame);
    }

    /**
     * Set the loop position of this Music by seconds.
     *
     * @param seconds loop position to set by seconds.
     */
    @Override
    public void setLoopPositionBySeconds(double seconds) {
        int bytesPerChannelForFrame = MainSound.FORMAT.getFrameSize() / MainSound.FORMAT.getChannels();
        long byteIndex = (long) (seconds * MainSound.FORMAT.getFrameRate() * bytesPerChannelForFrame);
        this.reference.setLoopPosition(byteIndex);
    }

    /**
     * Get the loop position of this Music by seconds.
     *
     * @return loop position by seconds.
     */
    @Override
    public double getLoopPositionBySeconds() {
        int bytesPerChannelForFrame = MainSound.FORMAT.getFrameSize() / MainSound.FORMAT.getChannels();
        long byteIndex = this.reference.getLoopPosition();
        return (byteIndex / (MainSound.FORMAT.getFrameRate() * bytesPerChannelForFrame));
    }

    /**
     * Unload this Music from the system. Attempts to use this Music after
     * unloading will result in error.
     */
    @Override
    public void unload() {
        this.mixer.unRegisterMusicReference(this.reference);
        this.reference.dispose();
        this.mixer = null;
        this.left = null;
        this.right = null;
        this.reference = null;
    }

    /**
     * The MemoryMusicRef implements the MusicReference interface.
     *
     * @author Carlos Martinez
     */
    private static class MemoryMusicRef implements MusicRef {

        private byte[] left;
        private byte[] right;
        
        private double volume;
        private double pan;
        
        private int loopPosition;
        private int position;
        
        private boolean playing;
        private boolean loop;

        /**
         * @param left left channel of music data.
         * @param right right channel of music data.
         * @param playing true if the music should be playing.
         * @param loop true if the music should loop.
         * @param loopPosition byte index of the loop position in music data.
         * @param position byte index position in music data.
         * @param volume volume to play the music.
         * @param pan pan to play the music.
         */
        public MemoryMusicRef(byte[] left, byte[] right, boolean playing, boolean loop, int loopPosition, int position, double volume, double pan) {
            this.left = left;
            this.right = right;
            this.volume = volume;
            this.pan = pan;
            this.loopPosition = loopPosition;
            this.position = position;
            this.playing = playing;
            this.loop = loop;
        }

        /**
         * Set whether this MemoryMusicRef is playing.
         *
         * @param playing whether this MemoryMusicRef is playing.
         */
        @Override
        public synchronized void setPlaying(boolean playing) {
            this.playing = playing;
        }

        /**
         * Get the playing setting of this MemoryMusicRef.
         *
         * @return true if this MemoryMusicRef is set to play.
         */
        @Override
        public synchronized boolean getPlaying() {
            return this.playing;
        }

        /**
         * Set whether this MemoryMusicRef will loop.
         *
         * @param loop whether this MemoryMusicRef will loop.
         */
        @Override
        public synchronized void setLoop(boolean loop) {
            this.loop = loop;
        }

        /**
         * Get the loop setting of this MemoryMusicRef.
         *
         * @return true if this MemoryMusicRef is set to loop.
         */
        @Override
        public synchronized boolean getLoop() {
            return this.loop;
        }

        /**
         * Set the byte index of this MemoryMusicRef.
         *
         * @param position the byte index to set.
         */
        @Override
        public synchronized void setPosition(long position) {
            if (position >= 0 && position < this.left.length) {
                this.position = (int) position;
            }
        }

        /**
         * Get the byte index of this MemoryMusicRef.
         *
         * @return byte index of this MemoryMusicRef.
         */
        @Override
        public synchronized long getPosition() {
            return this.position;
        }

        /**
         * Set the loop position byte index of this MemoryMusicRef.
         *
         * @param loopPosition the loop position byte index to set.
         */
        @Override
        public synchronized void setLoopPosition(long loopPosition) {
            if (loopPosition >= 0 && loopPosition < this.left.length) {
                this.loopPosition = (int) loopPosition;
            }
        }

        /**
         * Get the loop position byte index of this MemoryMusicRef.
         *
         * @return loop position byte index of this MemoryMusicRef.
         */
        @Override
        public synchronized long getLoopPosition() {
            return this.loopPosition;
        }

        /**
         * Set the volume of this MemoryMusicRef.
         *
         * @param volume the volume of this MemoryMusicRef.
         */
        @Override
        public synchronized void setVolume(double volume) {
            this.volume = volume;
        }

        /**
         * Get the volume of this MemoryMusicRef.
         *
         * @return volume of this MemoryMusicRef.
         */
        @Override
        public synchronized double getVolume() {
            return this.volume;
        }

        /**
         * Set the pan of this MemoryMusicRef, between -1.0 and 1.0.
         *
         * @param pan the pan of this MemoryMusicRef.
         */
        @Override
        public synchronized void setPan(double pan) {
            this.pan = pan;
        }

        /**
         * Get the pan of this MemoryMusicRef.
         *
         * @return pan of this MemoryMusicRef.
         */
        @Override
        public synchronized double getPan() {
            return this.pan;
        }

        /**
         * Get the number of bytes remaining for each channel.
         *
         * @return number of bytes remaining for each channel.
         */
        @Override
        public synchronized long bytesAvailable() {
            return this.left.length - this.position;
        }

        /**
         * Determine if there are no bytes remaining and play has stopped.
         *
         * @return true if there are no bytes remaining and it's no longer playing.
         */
        @Override
        public synchronized boolean done() {
            long available = this.left.length - this.position;
            return available <= 0 && !this.playing;
        }

        /**
         * Skip a specified number of bytes of the audio data.
         *
         * @param amount number of bytes to skip.
         */
        @Override
        public synchronized void skipBytes(long amount) {
            for (int i = 0; i < amount; i++) {
                this.position++;
                
                //Wrap if looping, stop otherwise...
                if (this.position >= this.left.length) {
                    if (this.loop) {
                        this.position = this.loopPosition;
                    } else {
                        this.playing = false;
                    }
                }
            }
        }

        /**
         * Get the next two bytes from the music data in the specified endian.
         *
         * @param data length-2 array to write in next 2 bytes from each channel.
         * @param bigEndian true if the bytes should be read big endian
         */
        @Override
        public synchronized void nextTwoBytes(int[] data, boolean bigEndian) {
            if (bigEndian) {
                data[0] = ((this.left[this.position] << 8) | (this.left[this.position + 1] & 0xFF));            //Left.
                data[1] = ((this.right[this.position] << 8) | (this.right[this.position + 1] & 0xFF));          //Right.
            } else {
                data[0] = ((this.left[this.position + 1] << 8) | (this.left[this.position] & 0xFF));            //Left.
                data[1] = ((this.right[this.position + 1] << 8) | (this.right[this.position] & 0xFF));          //Right.
            }
            
            this.position += 2;
            
            //Wrap if looping, stop otherwise...
            if (this.position >= this.left.length) {
                if (this.loop) {
                    this.position = this.loopPosition;
                } else {
                    this.playing = false;
                }
            }
        }

        /**
         * Dispose of resources in use by this MemoryMusicRef.
         */
        @Override
        public synchronized void dispose() {
            this.playing = false;
            this.position = this.left.length + 1;
            this.left = null;
            this.right = null;
        }
    }
}
