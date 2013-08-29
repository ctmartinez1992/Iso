package Sound.Internal;

import Sound.Sound;

/**
 * The MemSound class is an implementation of the Sound interface that stores
 * all audio data in memory for low latency.
 *
 * @author Carlos Martinez
 */
public class MemorySound implements Sound {

    private final int ID;
    
    private Mixer mixer;
    
    private byte[] left;
    private byte[] right;

    /**
     * @param left left channel of sound data.
     * @param right right channel of sound data.
     * @param mixer Mixer that will handle this Sound.
     * @param id unique ID of this Sound.
     */
    public MemorySound(byte[] left, byte[] right, Mixer mixer, int id) {
        this.left = left;
        this.right = right;
        this.mixer = mixer;
        this.ID = id;
    }

    /**
     * Plays this Sound.
     */
    @Override
    public void play() {
        this.play(1.0);
    }

    /**
     * Plays this Sound with a specified volume.
     *
     * @param volume the volume this Sound will play.
     */
    @Override
    public void play(double volume) {
        this.play(volume, 0.0);
    }

    /**
     * Plays this MemSound with a specified volume and pan.
     *
     * @param volume the volume at which this Sound will play.
     * @param pan the pan at which the Sound will play, between -1.0 and 1.0.
     */
    @Override
    public void play(double volume, double pan) {
        SoundRef ref = new MemorySoundRef(this.left, this.right, volume, pan, this.ID);
        this.mixer.registerSoundReference(ref);
    }

    /**
     * Stops all instances of this Sound from playing.
     */
    @Override
    public void stop() {
        this.mixer.unRegisterSoundReference(this.ID);
    }

    /**
     * Unloads this Sound from the system. Attempts to use this Sound after
     * unloading will result in error.
     */
    @Override
    public void unload() {
        this.mixer.unRegisterSoundReference(this.ID);
        this.mixer = null;
        this.left = null;
        this.right = null;
    }

    /**
     * The MemorySoundRef implements the SoundReference interface.
     *
     * @author Carlos Martinez
     */
    private static class MemorySoundRef implements SoundRef {

        public final int SOUND_ID;
        
        private byte[] left;
        private byte[] right;
        
        private double volume;
        private double pan;
        
        private int position;

        /**
         * @param left left channel of sound data.
         * @param right right channel of sound data.
         * @param volume volume at which to play the sound.
         * @param pan pan at which to play the sound.
         * @param soundID ID of the Sound for which this is a reference.
         */
        public MemorySoundRef(byte[] left, byte[] right, double volume, double pan, int soundID) {
            this.SOUND_ID = soundID;
            
            this.left = left;
            this.right = right;
            
            this.volume = (volume >= 0.0) ? volume : 1.0;
            this.pan = (pan >= -1.0 && pan <= 1.0) ? pan : 0.0;
            
            this.position = 0;
        }

        /**
         * Get the ID of the MemorySound that produced this MemorySoundRef.
         *
         * @return the ID of this MemorySoundRef's parent MemorySound.
         */
        @Override
        public int getSoundID() {
            return this.SOUND_ID;
        }

        /**
         * Gets the volume of this MemSoundRef.
         *
         * @return volume of this MemSoundRef.
         */
        @Override
        public double getVolume() {
            return this.volume;
        }

        /**
         * Gets the pan of this MemSoundRef.
         *
         * @return pan of this MemSoundRef.
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
            return this.left.length - this.position;
        }

        /**
         * Skip a specified number of bytes of the audio data.
         *
         * @param amount number of bytes to skip.
         */
        @Override
        public synchronized void skipBytes(long amount) {
            this.position += amount;
        }

        /**
         * Get the next two bytes from the sound data in the specified endian.
         *
         * @param data length-2 array to write in next 2 bytes from each channel.
         * @param bigEndian true if the bytes should be read big endian.
         */
        @Override
        public void nextTwoBytes(int[] data, boolean bigEndian) {
            if (bigEndian) {
                data[0] = ((this.left[this.position] << 8) | (this.left[this.position + 1] & 0xFF));            //Left.
                data[1] = ((this.right[this.position] << 8) | (this.right[this.position + 1] & 0xFF));          //Right.
            } else {
                data[0] = ((this.left[this.position + 1] << 8) | (this.left[this.position] & 0xFF));            //Left.
                data[1] = ((this.right[this.position + 1] << 8) | (this.right[this.position] & 0xFF));          //Right.
            }
            this.position += 2;
        }

        /**
         * Dispose of resources in use by this MemorySoundRef.
         */
        @Override
        public void dispose() {
            this.position = this.left.length + 1;
            this.left = null;
            this.right = null;
        }
    }
}
