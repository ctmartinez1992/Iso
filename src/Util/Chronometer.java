package Util;

public final class Chronometer {
    
    private long begin;
    private long end;
    
    public Chronometer() {
        begin = 0;
        end = 0;
    }
 
    public void start() {
        begin = System.currentTimeMillis();
    }
 
    public void stop() {
        end = System.currentTimeMillis();
    }
    
    public void reset() {
        begin = 0;
        end = 0;
    }
 
    public long getTimePassed() {
        return System.currentTimeMillis() - begin;
    }
 
    public long getTimeEnd() {
        return end - begin;
    }
 
    public double getTimePassedSeconds() {
        return getTimePassed() / 1000.0;
    }
 
    public double getTimeEndSeconds() {
        return getTimeEnd() / 1000.0;
    }
 
    public double getMinutes() {
        return (end - begin) / 60000.0;
    }
 
    public double getHours() {
        return (end - begin) / 3600000.0;
    }
}