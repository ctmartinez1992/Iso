package Sound.Internal;

/**
 * This is the Mixer's interface to the audio data of a Sound object.
 * 
 * @author Carlos Martinez
 */
public interface SoundRef {

	/**
	 * Get the ID of the Sound that produced this SoundRef.
         * 
	 * @return the ID of this SoundRef's parent Sound.
	 */
	public int getSoundID();
	
	/**
	 * Gets the volume of this SoundRef.
         * 
	 * @return volume of this SoundRef.
	 */
	public double getVolume();
	
	/**
	 * Gets the pan of this SoundRef.
         * 
	 * @return pan of this SoundRef.
	 */
	public double getPan();
	
	/**
	 * Get the number of bytes remaining for each channel.
         * 
	 * @return number of bytes remaining for each channel.
	 */
	public long bytesAvailable();
	
	/**
	 * Skip a specified number of bytes of the audio data.
         * 
	 * @param amount number of bytes to skip.
	 */
	public void skipBytes(long amount);
	
	/**
	 * Get the next two bytes from the sound data in the specified endian.
         * 
	 * @param data length-2 array to write in next two bytes from each channel.
	 * @param bigEndian true if the bytes should be read big endian.
	 */
	public void nextTwoBytes(int[] data, boolean bigEndian);
	
	/**
	 * Dispose of resources in use by this SoundRef.
	 */
	public void dispose();
	
}
