package Sound;

/**
 * An abstraction for music.  
 * Music objects should only be loaded via the Sound System 
 * <code>loadMusic()</code> functions.
 * Music behaves as a normal music track (Pause; Play; Resume).
 * 
 * @author Carlos Martinez
 */
public interface Music {

	/**
	 * PLAY IT!!!
         * 
	 * @param loop Do you want the music to loop?
	 */
	public void play(boolean loop);
	
	/**
	 * PLAY IT LOUDER!!!
         * 
	 * @param loop Do you want the music to loop?
	 * @param volume The volume, between 0.0 and 1.0.
	 */
	public void play(boolean loop, double volume);
	
	/**
	 * PLAY IT! PAN IT! LOOP IT!
         * 
	 * @param loop Do you want the music to loop?
	 * @param volume The volume, between 0.0 and 1.0.
	 * @param pan The pan, between -1.0 and 1.0.
	 */
	public void play(boolean loop, double volume, double pan);
	
	/**
	 * Stop playing this Music and set its position to the beginning.
	 */
	public void stop();
	
	/**
	 * Stop playing this Music and keep its current position.
	 */
	public void pause();
	
	/**
	 * Play this Music from its current position.
	 */
	public void resume();
	
	/**
	 * Set this Music's position to the beginning, but don't stop it.
	 */
	public void rewind();
	
	/**
	 * Set this Music's position to the loop position, but don't stop it.
	 */
	public void rewindToLoopPosition();
	
	/**
	 * Is this Music playing?
         * 
	 * @return true if this Music is playing.
	 */
	public boolean playing();
	
	/**
	 * Is This Music Over?
         * 
	 * @return true if this Music ended.
	 */
	public boolean done();
	
	/**
	 * Will it loop?
         * 
	 * @return true if this Music will loop.
	 */
	public boolean getLoop();
	
	/**
	 * Set whether this Music will loop.
         * 
	 * @param loop whether this Music will loop.
	 */
	public void setLoop(boolean loop);
	
	/**
	 * Set the loop position of this Music by sample frame.
         * 
	 * @param frameIndex sample frame loop position to set.
	 */
	public void setLoopPositionByFrame(int frameIndex);
	
	/**
	 * Get the loop position of this Music by sample frame.
         * 
	 * @return loop position by sample frame.
	 */
	public int getLoopPositionByFrame();
	
	/**
	 * Set the loop position of this Music by seconds.
         * 
	 * @param seconds loop position to set by seconds.
	 */
	public void setLoopPositionBySeconds(double seconds);
	
	/**
	 * Get the loop position of this Music by seconds.
         * 
	 * @return loop position by seconds.
	 */
	public double getLoopPositionBySeconds();
	
	/**
	 * Set the volume of this Music.
	 * @param volume the volume of this Music.
	 */
	public void setVolume(double volume);
	
	/**
	 * Get the volume of this Music.
	 * @return volume the volume of this Music.
	 */
	public double getVolume();
	
	/**
	 * Set the pan of this Music.
         * Must be between -1.0 and 1.0.
         * Values outside the valid range will be ignored.
         * 
	 * @param pan the pan of this Music.
	 */
	public void setPan(double pan);
	
	/**
	 * Get the pan of this Music.
         * 
	 * @return pan the pan of this Music.
	 */
	public double getPan();
	
	/**
	 * Unload this Music from the system.  
         * Attempts to use this Music after unloading will result in error.
	 */
	public void unload();
	
}
