package Map;

import Build.Build;
import Util.Math.Int2;
import Util.Math.MyMath;
import Tile.GroundTiles.*;
import Tile.Tile;
import Util.GUI.GUI;
import Util.UV;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

public class Map {
    
    //Each square on the map is the same polygon drawn several times in diferent positions.
    private Polygon square;
    
    private Tile[] tiles;
    private Tile[] sortedTiles;
    
    private ArrayList<Build> builds;

    private Vector2f origin;
    private Vector2f right;
    private Vector2f down;
    
    private boolean doBuild;

    public Map(int gridWidth, int gridHeight, int tileWidth) {
        UV.gridWidth = gridWidth;
        UV.gridHeight = gridHeight;

        UV.tileWidth = tileWidth;
        UV.tileHeight = tileWidth / 2;

        tiles = new Tile[gridHeight * gridWidth];
        
        builds = new ArrayList();
        UV.choosingBuild = null;
        
        doBuild = false;
    }
    
    public void init(GameContainer container) {
        int halfTileWidth = UV.tileWidth / 2;
        int halfTileHeight = UV.tileHeight / 2;
        
        square = new Polygon();
        square.addPoint(0, 0);
        square.addPoint(halfTileWidth, halfTileHeight);
        square.addPoint(UV.tileWidth, 0);
        square.addPoint(halfTileWidth, -halfTileHeight);

        origin = new Vector2f(0, container.getHeight() / 2);
        right = new Vector2f(halfTileWidth, halfTileHeight);
        down = new Vector2f(halfTileWidth, -halfTileHeight);

        for (int y=0; y<UV.gridHeight; y++) {
            for (int x=0; x<UV.gridWidth; x++) {
                Vector2f position = origin.copy().add(right.copy().scale(x)).add(down.copy().scale(y));
                tiles[y * UV.gridHeight + x] = new NormalGrassTile();
                tiles[y * UV.gridHeight + x].init(position);
            }
        }

        sortedTiles = Arrays.copyOf(tiles, tiles.length);
        Arrays.sort(sortedTiles, new Comparator<Tile>() {
            @Override
            public int compare(Tile t1, Tile t2) {
                return (int) (t1.getPosition().y - t2.getPosition().y);
            }
        });
    }

    public void update(GameContainer container, int delta) {
        if (UV.initChoosedBuild) {
            UV.initChoosedBuild = false;
            UV.choosingBuild.init(tiles[UV.mapX * UV.gridWidth + UV.mapY].getPosition(), getGridCoords(UV.mouseX, UV.mouseY));
        }
        
        for (Tile tile : sortedTiles) {
            tile.setHighlight(false);
        }

        int position = UV.mapX * UV.gridHeight + UV.mapY;
        if (position >= 0 && position < (UV.gridHeight * UV.gridWidth) && isInBoundaries(UV.mapX, UV.mapY)) {
            tiles[position].setHighlight(true);
        }
    }

    public void render(GameContainer container, Graphics graphics) {
        for (Tile tile : sortedTiles) {
            square.setLocation(tile.getPosition());
            
            if (tile.isHighlight()) {
                graphics.setLineWidth(2);
                graphics.setColor(new Color(UV.highlightColor.getX(), UV.highlightColor.getY(), UV.highlightColor.getZ()));
            } else {
                graphics.setLineWidth(1);
                graphics.setColor(new Color(UV.normalColor.getX(), UV.normalColor.getY(), UV.normalColor.getZ()));
            }
            
            tile.render(container, graphics);
            graphics.draw(square);
        }
        
        for (Build build : builds) {
            build.render(container, graphics);
        }
        
        if (UV.choosingBuild != null) {
            UV.choosingBuild.renderTranslucent(container, graphics, doBuild);
        }
    }
    

    /*
     * 
     * INPUT HANDLERS
     * 
     */
    
    public void mouseMoved(int oldX, int oldY, int newX, int newY) {
        Int2 coords = getGridCoords(newX, newY);
        doBuild = false;
        
        if (UV.choosingBuild != null) {
            if (isInBoundaries(coords.getX(), coords.getY())) {
                UV.choosingBuild.setPosition(tiles[coords.getX() * UV.gridWidth + coords.getY()].getPosition());
                UV.choosingBuild.setGridPosition(coords);
                
                doBuild = true;
                for(Build build : builds) {
                    if (MyMath.boxIntersect(UV.choosingBuild.getGridPosition(), UV.choosingBuild.getTileSpace(), build.getGridPosition(), build.getTileSpace())) {
                        doBuild = false;
                        break;
                    }
                }
            }
        }
    }

    public void mousePressed(int button, int x, int y) {
        doBuild = false;
        
        if (isInBoundaries(UV.mapX, UV.mapY) && UV.choosingBuild != null) {            
            doBuild = true;
            for (Build build : builds) {
                
                UV.choosingBuild.getGridPosition();
                UV.choosingBuild.getTileSpace();
                build.getGridPosition();
                build.getTileSpace();
                
                if (MyMath.boxIntersect(UV.choosingBuild.getGridPosition(), UV.choosingBuild.getTileSpace(), build.getGridPosition(), build.getTileSpace())) {
                    doBuild = false;
                    break;
                }
            }
        }
    }
    
    public void mouseDragged(int oldX, int oldY, int newX, int newY) {
        Int2 coords = getGridCoords(newX, newY);
        doBuild = false;
        
        if (UV.choosingBuild != null) {
            if (isInBoundaries(coords.getX(), coords.getY())) {
                UV.choosingBuild.setPosition(tiles[coords.getX() * UV.gridWidth + coords.getY()].getPosition());
                UV.choosingBuild.setGridPosition(coords);
                
                doBuild = true;
                for(Build build : builds) {
                    if (MyMath.boxIntersect(UV.choosingBuild.getGridPosition(), UV.choosingBuild.getTileSpace(), build.getGridPosition(), build.getTileSpace())) {
                        doBuild = false;
                        break;
                    }
                }
            }
        }
    }
    
    public void mouseReleased(int button, int x, int y) {
        doBuild = false;
        
        if (UV.choosingBuild != null) {
            if (isInBoundaries(UV.mapX, UV.mapY)) {
                doBuild = true;
                for(Build build : builds) {
                    if (MyMath.boxIntersect(UV.choosingBuild.getGridPosition(), UV.choosingBuild.getTileSpace(), build.getGridPosition(), build.getTileSpace())) {
                        doBuild = false;
                        break;
                    }
                }
            }
        }
        
        if (doBuild) {
            builds.add(UV.choosingBuild);
        }

        Collections.sort(builds, new Comparator<Build>() {
            @Override
            public int compare(Build b1, Build b2) {
                return (int) (b1.getPosition().y - b2.getPosition().y);
            }
        });
        
        GUI.falseAllVariables();
        GUI.leftMenuNeedsUpdate = true;
        
        UV.choosingBuild = null;
    }
    
    
    /*
     * 
     * GETS
     * 
     */
    
    
    public Int2 getGridCoords(int x, int y) {
        Int2 out = new Int2();

        out.setX((int) (((x - origin.x) - 2 * (y - origin.y)) / UV.tileWidth));
        out.setY((int) (((x - origin.x) + 2 * (y - origin.y)) / UV.tileWidth));

        return out;
    }
    
    public Build getBuildGivenCoords(Vector2f position) {
        for (Build build : builds) {
            if (build.getPosition() == position) {
                return build;
            }
        }
        
        return null;
    }
    
    public boolean isInBoundaries(int x, int y) {
        return (x >= 0 && x < UV.gridWidth && y >= 0 && y < UV.gridHeight);
    }

    public int getWidth() {
        return UV.gridWidth;
    }

    public int getHeight() {
        return UV.gridHeight;
    }
}
