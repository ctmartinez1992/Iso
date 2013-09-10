package Entity.WorkerEntity;

import Build.Build;
import Util.Loader.FontLoader;
import Util.Loader.ImageLoader;
import Util.Math.Int2;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author carlos
 */
public class Serf extends Worker {

    public Serf() {
    }

    @Override
    public Serf init(int x, int y, int gridX, int gridY) {
        this.position = new Vector2f(x, y);
        this.image = ImageLoader.loadImage("res/people/serf/serf.png");
        
        this.name = "Serf";
        this.showName = true;
        
        return this;
    }

    @Override
    public void update(GameContainer container, ArrayList<Build> builds) {
        //System.out.println("I exist at " + position.getX() + ", " + position.getY());
    }

    @Override
    public void render(GameContainer container, Graphics graphics) {
        graphics.drawImage(this.image, this.position.getX() + 24, this.position.getY() - 48);
        if (this.showName) {
            graphics.setColor(new Color(255, 255, 255));
            graphics.setFont(FontLoader.arialFont10);
            //graphics.drawString(this.name, this.getNamePosition().getX() + 22, this.getNamePosition().getY() - 60);
        }
    }

    @Override
    public void renderTranslucent(GameContainer container, Graphics graphics, boolean doPeasant) {
        Color transparent = new Color(1, 1, 1, 0.8f);
        graphics.drawImage(this.image, this.position.getX() + 24, this.position.getY() - 48, transparent);
    }
}
