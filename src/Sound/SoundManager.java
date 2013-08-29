package Sound;

public class SoundManager {
    
    public static Music ruins;
    
    public static Sound clav;
    public static Sound guiro;
    public static Sound hat;
    public static Sound kick;
    public static Sound shake;
    public static Sound toc;
    public static Sound tom1;
    public static Sound tom2;
    public static Sound tom3;
    public static Sound tom4;
    public static Sound triangle;

    public static void init() {
        MainSound.init();
        
        ruins = MainSound.loadMusic("sound/music/ambient/ruins.wav");
        ruins.setVolume(0.2f);
            
        clav = MainSound.loadSound("sound/sound/test/clav.wav");
        guiro = MainSound.loadSound("sound/sound/test/guiro.wav");
        hat = MainSound.loadSound("sound/sound/test/hat.wav");
        kick = MainSound.loadSound("sound/sound/test/kick.wav");
        shake = MainSound.loadSound("sound/sound/test/shake.wav");
        toc = MainSound.loadSound("sound/sound/test/toc.wav");
        tom1 = MainSound.loadSound("sound/sound/test/tom1.wav");
        tom2 = MainSound.loadSound("sound/sound/test/tom2.wav");
        tom3 = MainSound.loadSound("sound/sound/test/tom3.wav");
        tom4 = MainSound.loadSound("sound/sound/test/tom4.wav");
        triangle = MainSound.loadSound("sound/sound/test/triangle.wav");
        
        playMusic(ruins);
    }
    
    public static void shutdown() {
        MainSound.shutdown();
    }
    
    public static void playMusic(Music music) {
        music.play(true);
    }
    
    public static void stopMusic(Music music) {
        music.stop();
    }
    
    public static void pauseMusic(Music music) {
        music.pause();
    }
    
    public static void resumeMusic(Music music) {
        music.resume();
    }
    
    public static void increaseMusicVolume(Music music) {
        music.setVolume(music.getVolume() + 0.1);
    }
    
    public static void decreaseMusicVolume(Music music) {
        music.setVolume(music.getVolume() - 0.1);
    }
    
    public static void setMusicVolume(Music music, double volume) {
        music.setVolume(volume);
    }
    
    public static void playSound(Sound sound) {
        sound.play();
    }
    
    public static void playSound(Sound sound, double volume) {
        sound.play(volume);
    }
    
    public static void playSound(Sound sound, double volume, double pan) {
        sound.play(volume, pan);
    }
    
    public static void stopSound(Sound sound) {
        sound.stop();
    }
}
