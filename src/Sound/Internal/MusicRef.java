package Sound.Internal;

/**
 * This is the Mixer's interface to the audio data of a Music object.
 * 
 * @author Carlos Martinez
 */
public interface MusicRef {
	
	/**
	 * Set whether this MusicRef is playing.
         * 
	 * @param playing whether this MusicRef is playing.
	 */
	public void setPlaying(boolean playing);

	/**
	 * Get the playing setting of this MusicRef.
         * 
	 * @return true if this MusicRef is set to play.
	 */
	public boolean getPlaying();
	
	/**
	 * Set whether this MusicRef will loop.
         * 
	 * @param loop whether this MusicReference will loop.
	 */
	public void setLoop(boolean loop);
	
	/**
	 * Get the loop setting of this MusicRef.
         * 
	 * @return true if this MusicRef is set to loop.
	 */
	public boolean getLoop();
	
	/**
	 * Set the byte index of this MusicRef.
         * 
	 * @param position the byte index to set.
	 */
	public void setPosition(long position);
	
	/**
	 * Get the byte index of this MusicRef.
         * 
	 * @return byte index of this MusicRef.
	 */
	public long getPosition();
	
	/**
	 * Set the loop position byte index of this MusicRef.
         * 
	 * @param loopPosition the loop position byte index to set.
	 */
	public void setLoopPosition(long loopPosition);
	
	/**
	 * Get the loop position byte index of this MusicRef.
         * 
	 * @return loop position byte index of this MusicRef.
	 */
	public long getLoopPosition();
	
	/**
	 * Set the volume of this MusicReference.
         * 
	 * @param volume the desired volume of this MusicRef.
	 */
	public void setVolume(double volume);
	
	/**
	 * Get the volume of this MusicRef.
         * 
	 * @return volume of this MusicRef.
	 */
	public double getVolume();
	
	/**
	 * Set the pan of this MusicRef, between -1.0 and 1.0.
         * 
	 * @param pan the desired pan of this MusicRef.
	 */
	public void setPan(double pan);
	
	/**
	 * Get the pan of this MusicRef.
         * 
	 * @return pan of this MusicRef.
	 */
	public double getPan();
	
	/**
	 * Get the number of bytes remaining for each channel until the end 
         * of this Music.
	 * @return number of bytes remaining for each channel.
	 */
	public long bytesAvailable();
	
	/**
	 * Determine if there are no bytes remaining and play has stopped.
         * 
	 * @return true if there are no bytes remaining and is no longer playing.
	 */
	public boolean done();
	
	/**
	 * Skip a specified number of bytes of the audio data.
         * 
	 * @param amount number of bytes to skip.
	 */
	public void skipBytes(long amount);
	
	/**
	 * Get the next two bytes from the music data in the specified endian.
         * 
	 * @param data length-2 array to write in next two bytes from each channel.
	 * @param bigEndian true if the bytes should be read big endian.
	 */
	public void nextTwoBytes(int[] data, boolean bigEndian);
	
	/**
	 * Dispose of resources in use by this MusicReference.
	 */
	public void dispose();
	
}
