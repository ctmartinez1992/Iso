package Sound;

/**
 * An abstraction for sound effects.
 * Sound objects should only be loaded via the Sound System
 * <code>loadSound()</code> functions.
 * Sounds can be played repeatedly in an overlapping fashion.
 * 
 * @author Carlos Martinez
 */
public interface Sound {

	/**
	 * Plays this Sound.
	 */
	public void play();
	
	/**
	 * Plays this Sound with the given volume.
         * 
	 * @param volume the volume at which this Sound will play.
	 */
	public void play(double volume);
	
	/**
	 * Plays this Sound with the given volume and pan.
         * 
	 * @param volume the volume at which this Sound will play 
	 * @param pan the pan at which this Sound will play,
         * between -1.0 and 1.0.
	 */
	public void play(double volume, double pan);
	
	/**
	 * All instances of this Sound will stop.
	 */
	public void stop();
	
	/**
	 * Unloads this Sound from the system.
         * Attempts to use this Sound after unloading will result in error.
	 */
	public void unload();
	
}
