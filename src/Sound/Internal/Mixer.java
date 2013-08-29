package Sound.Internal;

import Util.Math.MyMath;
import java.util.ArrayList;
import java.util.List;

/**
 * The Mixer class is what does the audio data mixing for the Sound System.
 *
 * @author Carlos Martinez
 */
public class Mixer {

    private List<MusicRef> musics;
    private List<SoundRef> sounds;
    
    //Buffer for reading sound data.
    private int[] dataBuffer;
    
    private double globalVolume;

    public Mixer() {
        this.musics = new ArrayList<>();
        this.sounds = new ArrayList<>();
        
        this.dataBuffer = new int[2];
        
        this.globalVolume = 1.0;
    }

    /**
     * Set the global volume for this Mixer.
     *
     * @param volume the global volume to set.
     */
    public synchronized void setVolume(double volume) {
        if (volume >= 0.0) {
            this.globalVolume = volume;
        }
    }

    /**
     * Get the global volume for this Mixer.
     *
     * @return the global volume.
     */
    public synchronized double getVolume() {
        return this.globalVolume;
    }

    /**
     * Registers a MusicReference with this Mixer.
     *
     * @param music MusicReference to be registered.
     */
    public synchronized void registerMusicReference(MusicRef music) {
        this.musics.add(music);
    }

    /**
     * Registers a SoundReference with this Mixer.
     *
     * @param sound SoundReference to be registered.
     */
    public synchronized void registerSoundReference(SoundRef sound) {
        this.sounds.add(sound);
    }

    /**
     * Unregisters a MusicReference with this Mixer.
     *
     * @param music MusicReference to be unregistered.
     */
    public synchronized void unRegisterMusicReference(MusicRef music) {
        this.musics.remove(music);
    }

    /**
     * Unregisters all SoundReferences with a given soundID.
     *
     * @param soundID ID of SoundReferences to be unregistered.
     */
    public synchronized void unRegisterSoundReference(int soundID) {
        for (int i = this.sounds.size() - 1; i >= 0; i--) {
            if (this.sounds.get(i).getSoundID() == soundID) {
                this.sounds.remove(i).dispose();
            }
        }
    }

    /**
     * Unregister all Music registered with this Mixer.
     */
    public synchronized void clearMusic() {
        this.musics.clear();
    }

    /**
     * Unregister all Sounds registered with this Mixer.
     */
    public synchronized void clearSounds() {
        for (SoundRef sound : this.sounds) {
            sound.dispose();
        }
        
        this.sounds.clear();
    }

    /**
     * Read bytes from this Mixer.
     * Assume little-endian; Stereo; 16-bit; signed PCM.
     *
     * @param data the buffer to read the bytes into.
     * @param offset the start index to read bytes into.
     * @param length the maximum number of bytes that should be read.
     * @return number of bytes read into buffer.
     */
    public synchronized int read(byte[] data, int offset, int length) {
        int numRead = 0;
        boolean bytesRead = true;
        
        for (int i = offset; i < (length + offset) && bytesRead; i += 4) {
            bytesRead = false;
            
            //Track value across audio sources...
            double leftValue = 0.0;
            double rightValue = 0.0;
            
            //Run through the musics...
            for (int m = 0; m < this.musics.size(); m++) {
                MusicRef music = this.musics.get(m);
                
                if (music.getPlaying() && music.bytesAvailable() > 0) {
                    //Volume it...
                    music.nextTwoBytes(this.dataBuffer, false);
                    double volume = music.getVolume() * this.globalVolume;
                    double leftCurr = (this.dataBuffer[0] * volume);
                    double rightCurr = (this.dataBuffer[1] * volume);
                    
                    //Pan it...
                    double pan = music.getPan();
                    if (pan != 0.0) {
                        double ll = (pan <= 0.0) ? 1.0 : (1.0 - pan);
                        double lr = (pan <= 0.0) ? Math.abs(pan) : 0.0;
                        double rl = (pan >= 0.0) ? pan : 0.0;
                        double rr = (pan >= 0.0) ? 1.0 : (1.0 - Math.abs(pan));
                        
                        double tmpL = (ll * leftCurr) + (lr * rightCurr);
                        double tmpR = (rl * leftCurr) + (rr * rightCurr);
                        
                        leftCurr = tmpL;
                        rightCurr = tmpR;
                    }
                    
                    leftValue += leftCurr;
                    rightValue += rightCurr;
                    
                    bytesRead = true;
                }
            }
            
            //Run through the sounds...
            for (int s = this.sounds.size() - 1; s >= 0; s--) {
                SoundRef sound = this.sounds.get(s);
                
                if (sound.bytesAvailable() > 0) {
                    //Volume it...
                    sound.nextTwoBytes(this.dataBuffer, false);
                    double volume = sound.getVolume() * this.globalVolume;
                    double leftCurr = (this.dataBuffer[0] * volume);
                    double rightCurr = (this.dataBuffer[1] * volume);
                    
                    //Pan it...
                    double pan = sound.getPan();
                    if (pan != 0.0) {
                        double ll = (pan <= 0.0) ? 1.0 : (1.0 - pan);
                        double lr = (pan <= 0.0) ? Math.abs(pan) : 0.0;
                        double rl = (pan >= 0.0) ? pan : 0.0;
                        double rr = (pan >= 0.0) ? 1.0 : (1.0 - Math.abs(pan));
                        
                        double tmpL = (ll * leftCurr) + (lr * rightCurr);
                        double tmpR = (rl * leftCurr) + (rr * rightCurr);
                        
                        leftCurr = tmpL;
                        rightCurr = tmpR;
                    }
                    
                    leftValue += leftCurr;
                    rightValue += rightCurr;
                    
                    bytesRead = true;
                    
                    //Dispose of the ref if we are done.
                    if (sound.bytesAvailable() <= 0) {
                        this.sounds.remove(s).dispose();
                    }
                } else { //Otherwise remove this ref.
                    this.sounds.remove(s).dispose();
                }
            }
            
            //if we read bytes, store in the buffer...
            if (bytesRead) {
                int finalLeftValue = (int) leftValue;
                int finalRightValue = (int) rightValue;
                
                finalLeftValue = MyMath.interval(finalLeftValue, Short.MIN_VALUE, Short.MAX_VALUE);
                finalRightValue = MyMath.interval(finalRightValue, Short.MIN_VALUE, Short.MAX_VALUE);
                
                //Left.
                data[i + 1] = (byte) ((finalLeftValue >> 8) & 0xFF);            //MSB
                data[i] = (byte) (finalLeftValue & 0xFF);                       //LSB
                
                //Right.
                data[i + 3] = (byte) ((finalRightValue >> 8) & 0xFF);           //MSB
                data[i + 2] = (byte) (finalRightValue & 0xFF);                  //LSB
                
                numRead += 4;
            }
        }
        
        return numRead;
    }

    /**
     * Skip specified number of bytes of all audio in this Mixer.
     *
     * @param nBytes the number of bytes to skip.
     */
    public synchronized void skip(int nBytes) {
        //Run through the musics...
        for (int m = 0; m < this.musics.size(); m++) {
            MusicRef music = this.musics.get(m);
            
            if (music.getPlaying() && music.bytesAvailable() > 0) {
                music.skipBytes(nBytes);
            }
        }
        
        //Run through the sounds...
        for (int s = this.sounds.size() - 1; s >= 0; s--) {
            SoundRef sound = this.sounds.get(s);
            
            if (sound.bytesAvailable() > 0) {
                sound.skipBytes(nBytes);
                
                //Dispose of the ref if we are done.
                if (sound.bytesAvailable() <= 0) {
                    this.sounds.remove(s).dispose();
                }
            } else { //Otherwise remove this ref.
                this.sounds.remove(s).dispose();
            }
        }
    }
}
