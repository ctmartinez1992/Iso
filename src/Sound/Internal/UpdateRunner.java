package Sound.Internal;

import java.util.concurrent.atomic.AtomicBoolean;
import javax.sound.sampled.SourceDataLine;

import Sound.MainSound;

/**
 * Handles the automatic updates of the Sound system.
 *
 * @author Carlos Martinez
 */
public class UpdateRunner implements Runnable {

    private AtomicBoolean running;
    
    private SourceDataLine outLine;
    
    private Mixer mixer;

    /**
     * @param mixer the mixer to read audio data from.
     * @param outLine the line to write audio data to.
     */
    public UpdateRunner(Mixer mixer, SourceDataLine outLine) {
        this.running = new AtomicBoolean();
        this.outLine = outLine;
        this.mixer = mixer;
    }

    /**
     * Stop this UpdateRunner.
     */
    public void stop() {
        this.running.set(false);
    }

    @Override
    public void run() {
        this.running.set(true);
        
        //1-sec buffer.
        int bufSize = (int) MainSound.FORMAT.getFrameRate() * MainSound.FORMAT.getFrameSize();
        byte[] audioBuffer = new byte[bufSize];
        
        //Only buffer some number of frames...
        int maxFramesPerUpdate = (int) ((MainSound.FORMAT.getFrameRate() / 1000) * 25);
        int nBytesRead = 0;
        long lastUpdate = System.nanoTime();
        double framesAccumulated = 0;
        
        while (this.running.get()) {
            long currTime = System.nanoTime();
            
            //Accumulate frames...
            double delta = currTime - lastUpdate;
            double secDelta = (delta / 1000000000L);
            framesAccumulated += secDelta * MainSound.FORMAT.getFrameRate();
            
            
            int framesToRead = (int) framesAccumulated;
            int framesToSkip = 0;
            
            //Should we skip some frames to catch up?
            if (framesToRead > maxFramesPerUpdate) {
                framesToSkip = framesToRead - maxFramesPerUpdate;
                framesToRead = maxFramesPerUpdate;
            }
            
            //Skip it...
            if (framesToSkip > 0) {
                int bytesToSkip = framesToSkip
                        * MainSound.FORMAT.getFrameSize();
                this.mixer.skip(bytesToSkip);
            }
            
            //Read it...
            if (framesToRead > 0) {
                int bytesToRead = framesToRead * MainSound.FORMAT.getFrameSize();
                int tmpBytesRead = this.mixer.read(audioBuffer, nBytesRead, bytesToRead);
                
                //How many to read...
                nBytesRead += tmpBytesRead;
                
                //Clear the rest with 0s.
                int remaining = bytesToRead - tmpBytesRead;
                for (int i = 0; i < remaining; i++) {
                    audioBuffer[nBytesRead + i] = 0;
                }
                
                //Mark the remaining 0s.
                nBytesRead += remaining;
            }
            
            //Mark frames read and skipped.
            framesAccumulated -= (framesToRead + framesToSkip);
            
            //Write to speakers.
            if (nBytesRead > 0) {
                this.outLine.write(audioBuffer, 0, nBytesRead);
                nBytesRead = 0;
            }
            
            lastUpdate = currTime;
            
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
    }
}