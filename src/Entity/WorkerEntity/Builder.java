package Entity.WorkerEntity;

import Build.Build;
import Util.Loader.FontLoader;
import Util.Loader.ImageLoader;
import Util.Math.Int2;
import Util.UV;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author carlos
 */
public class Builder extends Worker {

    public Builder() {
    }

    @Override
    public Builder init(int x, int y, int gridX, int gridY) {
        this.position = new Vector2f(x, y);
        this.gridPosition = new Int2(gridX, gridY);
        
        this.image = ImageLoader.loadImage("res/people/builder/builder.png");
        
        this.name = "Builder";
        this.showName = true;
        
        this.buildAssigned = null;
        this.working = false;
        
        this.moveX = 0;
        this.moveY = 0;
        this.moving = false;
        
        return this;
    }

    @Override
    public void update(GameContainer container, ArrayList<Build> builds) {
        if (buildAssigned == null) {
            for (Build build : builds) {
                if (build.getSpecifications() != null) {
                    if (!build.getSpecifications().isConstructionDone() && build.getSpecifications().isReadyToConstruct()) {
                        this.buildAssigned = build;
                        this.moveX = (int) buildAssigned.getAABBEntrance().getPosition().getX();
                        this.moveY = (int) buildAssigned.getAABBEntrance().getPosition().getY();
                        break;
                    }
                }
            }
        } else {
            if (working) {
                if (buildAssigned.getSpecifications().isConstructionDone()) {
                    this.buildAssigned = null;
                    this.working = false;
                }
            }
        }
        
        if (this.buildAssigned != null) {
            if ((int) buildAssigned.getAABBEntrance().getPosition().getX() != gridPosition.getX() || (int) buildAssigned.getAABBEntrance().getPosition().getY() != gridPosition.getY()) {
                this.moving = true;
            }
                    
            if ((int) buildAssigned.getAABBEntrance().getPosition().getX() == gridPosition.getX() && (int) buildAssigned.getAABBEntrance().getPosition().getY() == gridPosition.getY()) {
                if (!working) {
                    buildAssigned.getSpecifications().addNWorkerConstruction();
                    this.working = true;
                    this.moving = false;
                }
            }
        
            if (buildAssigned.getSpecifications().isReadyToConstruct()) {
                move(container);
            }
        }
    }

    @Override
    public void render(GameContainer container, Graphics graphics) {
        graphics.drawImage(this.image, this.position.getX() - (this.image.getWidth() / 2), this.position.getY() - ((this.image.getHeight() - 4)));
        graphics.drawRect(this.position.getX() - 2, this.position.getY() - 2, 4, 4);
        if (this.showName) {
            graphics.setColor(new Color(255, 255, 255));
            graphics.setFont(FontLoader.arialFont10);
            graphics.drawString(this.name, this.getPosition().getX() - 16, this.getPosition().getY() - 60);
        }
    }

    @Override
    public void renderTranslucent(GameContainer container, Graphics graphics, boolean doPeasant) {
        Color transparent = new Color(1, 1, 1, 0.8f);
        graphics.drawImage(this.image, this.position.getX() - (this.image.getWidth() / 2), this.position.getY() - ((this.image.getHeight() - 4)), transparent);
    }
    
    @Override
    public void setPosition(float x, float y) {
        //this.position.set(x + ((UV.tileWidth / 2) - (this.image.getWidth() / 2)), y - ((this.image.getHeight() - 4)));
        if (this.position != null) {
            this.position.set(x + (UV.tileWidth / 2), y);
        }
    }
    
    //Fix this shit
    private void move(GameContainer container) {
        //Needs some inprovement
        if (buildAssigned != null && moving) {
            if (moveX > this.gridPosition.getX()) {
                this.position.x += 1;
                this.position.y += -0.5;
            } else if (moveX < this.gridPosition.getX()) {
                this.position.x += -1;
                this.position.y += 0.5;
            } else if (moveY > this.gridPosition.getY()) {
                this.position.x += 1;
                this.position.y += 0.5;
            } else if (moveY < this.gridPosition.getY()) {
                this.position.x += -1;
                this.position.y += -0.5;
            }
            
            this.gridPosition.setX((int) (((this.position.x - UV.origin.x) - 2 * (this.position.y - UV.origin.y)) / UV.tileWidth));
            this.gridPosition.setY((int) (((this.position.x - UV.origin.x) + 2 * (this.position.y - UV.origin.y)) / UV.tileWidth));
        }
    }
}
