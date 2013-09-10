package Map;

import Build.Build;
import Entity.Entity;
import Util.Math.Int2;
import Tile.GroundTiles.*;
import Tile.Tile;
import Util.GUI.GUI;
import Util.Math.MyMath;
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
    
    private ArrayList<Entity> entities;
    
    private ArrayList<Object> renderList;
    
    private Vector2f right;
    private Vector2f down;
    
    private boolean doBuild;
    private boolean doTile;
    private boolean doPeasant;
    private boolean sortRenderList;

    public Map(int gridWidth, int gridHeight, int tileWidth) {
        UV.gridWidth = gridWidth;
        UV.gridHeight = gridHeight;

        UV.tileWidth = tileWidth;
        UV.tileHeight = tileWidth / 2;

        tiles = new Tile[gridHeight * gridWidth];
        
        builds = new ArrayList();
        UV.choosingBuild = null;
        
        entities = new ArrayList();
        
        renderList = new ArrayList();
        
        doBuild = false;
        doTile = false;
        doPeasant = false;
        sortRenderList = false;
    }
    
    public void init(GameContainer container) {
        int halfTileWidth = UV.tileWidth / 2;
        int halfTileHeight = UV.tileHeight / 2;
        
        square = new Polygon();
        square.addPoint(0, 0);
        square.addPoint(halfTileWidth, halfTileHeight);
        square.addPoint(UV.tileWidth, 0);
        square.addPoint(halfTileWidth, -halfTileHeight);

        UV.origin = new Vector2f(0, container.getHeight() / 2);
        right = new Vector2f(halfTileWidth, halfTileHeight);
        down = new Vector2f(halfTileWidth, -halfTileHeight);

        for (int y=0; y<UV.gridHeight; y++) {
            for (int x=0; x<UV.gridWidth; x++) {
                Vector2f position = UV.origin.copy().add(right.copy().scale(x)).add(down.copy().scale(y));
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
            if (MyMath.interval(UV.mapX * UV.gridWidth + UV.mapY, 0, UV.gridWidth * UV.gridHeight)) {
                UV.initChoosedBuild = false;
                UV.choosingBuild.init(tiles[UV.mapX * UV.gridWidth + UV.mapY].getPosition(), new Int2(UV.mapX, UV.mapY));
                UV.choosingBuild.setGridPositionProperly(UV.mapX, UV.mapY);
            }
        }
        
        if (UV.initChoosedTile) {
            if (MyMath.interval(UV.mapX * UV.gridWidth + UV.mapY, 0, UV.gridWidth * UV.gridHeight)) {
                UV.initChoosedTile = false;
                UV.choosingTile.init(tiles[UV.mapX * UV.gridWidth + UV.mapY].getPosition());
            }
        }
        
        if (UV.initChoosedPeasant) {
            if (MyMath.interval(UV.mapX * UV.gridWidth + UV.mapY, 0, UV.gridWidth * UV.gridHeight)) {
                UV.initChoosedPeasant = false;
                UV.choosingPeasant.init((int) tiles[UV.mapX * UV.gridWidth + UV.mapY].getPosition().x, (int) tiles[UV.mapX * UV.gridWidth + UV.mapY].getPosition().y, UV.mapX, UV.mapY);
            }
        }
        
        for (Tile tile : sortedTiles) {
            tile.setHighlight(false);
        }

        int position = UV.mapX * UV.gridHeight + UV.mapY;
        if (position >= 0 && position < (UV.gridHeight * UV.gridWidth) && isInBoundaries(UV.mapX, UV.mapY)) {
            tiles[position].setHighlight(true);
        }
        
        for (Entity entity : entities) {
            entity.update(container, builds);
        }
        
        for (Build build : builds) {
            build.update(container, delta);
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
        
        for (Object object : renderList) {
            if (object instanceof Build) {
                ((Build)object).render(container, graphics);
            } else {
                ((Entity)object).render(container, graphics);
            }
        }
                
        if (UV.choosingBuild != null && !UV.initChoosedBuild) {
            UV.choosingBuild.renderTranslucent(container, graphics, doBuild);
        }
        
        if (UV.choosingTile != null && !UV.initChoosedTile) {
            UV.choosingTile.renderTranslucent(container, graphics, doTile);
            for (Tile tile : UV.choosingSquareTiles) {
                tile.renderTranslucent(container, graphics, doTile);
            }
        }        
            
        if (UV.choosingPeasant != null && !UV.initChoosedPeasant) {
            UV.choosingPeasant.renderTranslucent(container, graphics, doPeasant);
        }
    }
    
    public void renderAfterGUI(GameContainer container, Graphics graphics) {
        for (Build build : builds) {
            build.renderAfterGUI(container, graphics);
        }
    }
    

    /*
     * 
     * INPUT HANDLERS
     * 
     */

    
    public void mouseLeftPressed(int button, int x, int y) {
        doBuild = false;
        doTile = false;
        doPeasant = false;
        
        if (GUI.showConstructionLeftMenu) {
            if (isInBoundaries(UV.mapX, UV.mapY) && UV.choosingBuild != null) {            
                doBuild = true;
                for (Build build : builds) {                
                    if (UV.choosingBuild.getAABB().overlaps(build.getAABB())) {
                        doBuild = false;
                        break;
                    }
                }
            }
        }
        
        if (GUI.showTileLeftMenu) {
            if (isInBoundaries(UV.mapX, UV.mapY) && UV.choosingTile != null) {            
                doTile = true;
                UV.tileDrawStarted.set(UV.mapX, UV.mapY);
                UV.choosingSquareTiles.add(UV.choosingTile.cloneTile(UV.choosingTile.getPosition()));
            }
        }
        
        if (GUI.showPeasantLeftMenu) {
            if (isInBoundaries(UV.mapX, UV.mapY) && UV.choosingPeasant != null) {            
                doPeasant = true;
            }
        }
        
        for (Build build : builds) {
            build.mousePressed(button, x, y);
        }
    }
    
    public void mouseRightPressed(int button, int x, int y) {
    }
    
    public void mouseMoved(int oldX, int oldY, int newX, int newY) {
        doBuild = false;
        doTile = false;
        doPeasant = false;
        
        Int2 coords = getGridCoords(newX, newY);
        int index = coords.getX() * UV.gridWidth + coords.getY();
        
        if (UV.choosingBuild != null) {
            if (isInBoundaries(coords.getX(), coords.getY())) {
                UV.choosingBuild.setPositionProperly(tiles[index].getPosition());
                UV.choosingBuild.setGridPositionProperly(coords.getX(), coords.getY());
                
                doBuild = true;
                for(Build build : builds) {
                    if (UV.choosingBuild.getAABB() != null && UV.choosingBuild.getAABBEntrance()!= null && build.getAABB() != null && build.getAABBEntrance() != null) {
                        if (UV.choosingBuild.getAABB().overlaps(build.getAABB()) || UV.choosingBuild.getAABBEntrance().overlaps(build.getAABB()) || UV.choosingBuild.getAABB().overlaps(build.getAABBEntrance()) || UV.choosingBuild.getAABBEntrance().overlaps(build.getAABBEntrance())) {
                            doBuild = false;
                            break;
                        }
                    }
                }
            }
        }
        
        if (UV.choosingTile != null) {
            if (isInBoundaries(coords.getX(), coords.getY())) {            
                doTile = true;
                UV.choosingTile.setPosition(tiles[index].getPosition());    
            }
        }
        
        if (UV.choosingPeasant != null) {
            if (isInBoundaries(coords.getX(), coords.getY())) {            
                doPeasant = true;
                UV.choosingPeasant.setPosition(tiles[index].getPosition().x, tiles[index].getPosition().y);    
                UV.choosingPeasant.setGridPosition(UV.mapX, UV.mapY);
            }
        }
    }
    
    public void mouseDragged(int oldX, int oldY, int newX, int newY) {
        doTile = false;
        
        Int2 coords = getGridCoords(newX, newY);
        
        if (UV.choosingTile != null) {
            if (isInBoundaries(coords.getX(), coords.getY())) {                
                doTile = true;
                UV.tileDrawStopped.set(coords.getX(), coords.getY());
                UV.choosingSquareTiles.clear();
                
                int xMin = (UV.tileDrawStarted.getX() - UV.tileDrawStopped.getX() > 0) ? UV.tileDrawStopped.getX() : UV.tileDrawStarted.getX();
                int xMax = (UV.tileDrawStarted.getX() - UV.tileDrawStopped.getX() > 0) ? UV.tileDrawStarted.getX() + 1 : UV.tileDrawStopped.getX() + 1;
                int yMin = (UV.tileDrawStarted.getY() - UV.tileDrawStopped.getY() > 0) ? UV.tileDrawStopped.getY() : UV.tileDrawStarted.getY();
                int yMax = (UV.tileDrawStarted.getY() - UV.tileDrawStopped.getY() > 0) ? UV.tileDrawStarted.getY() + 1 : UV.tileDrawStopped.getY() + 1;                
                for (int x=xMin; x<xMax; x++) {
                    for (int y=yMin; y<yMax; y++) {
                        UV.choosingSquareTiles.add(UV.choosingTile.cloneTile(tiles[x * UV.gridWidth + y].getPosition()));
                    }
                }
            }
        }
    }
    
    public void mouseLeftReleased(int button, int x, int y) {  
        doBuild = false;
        doTile = false;
        doPeasant = false;
        
        UV.selectedBuild = null;
        for (Build build : builds) {
            build.mouseReleased(button, x, y);
        }
        
        if (UV.choosingBuild != null) {
            if (isInBoundaries(UV.mapX, UV.mapY)) {
                UV.choosingBuild.setGridPositionProperly(UV.mapX, UV.mapY);
                
                doBuild = true;
                for(Build build : builds) {
                    if (UV.choosingBuild.getAABB().overlaps(build.getAABB()) || UV.choosingBuild.getAABBEntrance().overlaps(build.getAABB()) || UV.choosingBuild.getAABB().overlaps(build.getAABBEntrance()) || UV.choosingBuild.getAABBEntrance().overlaps(build.getAABBEntrance())) {
                        doBuild = false;
                        break;
                    }
                }
            } else {
                UV.choosingBuild = null;
            }
        }
        
        if (UV.choosingTile != null) {
            if (isInBoundaries(UV.mapX, UV.mapY)) {
                UV.choosingTile.setPosition(tiles[UV.mapX * UV.gridWidth + UV.mapY].getPosition());
                doTile = true;
            } else {
                UV.choosingSquareTiles.clear();
                UV.choosingTile = null;
            }
        }
        
        if (UV.choosingPeasant != null) {
            if (isInBoundaries(UV.mapX, UV.mapY)) {
                UV.choosingPeasant.setPosition(tiles[UV.mapX * UV.gridWidth + UV.mapY].getPosition().x, tiles[UV.mapX * UV.gridWidth + UV.mapY].getPosition().y);
                UV.choosingPeasant.setGridPosition(UV.mapX, UV.mapY);
                doPeasant = true;
            } else {
                UV.choosingPeasant = null;
            }
        }
        
        if (doBuild) {
            builds.add(UV.choosingBuild);
            renderList.add(UV.choosingBuild);
            
            sortRenderList = true;
            
            Collections.sort(builds, new Comparator<Build>() {
                @Override
                public int compare(Build b1, Build b2) {
                    return (int) (b1.getPosition().y - b2.getPosition().y);
                }
            });
            
            GUI.buttonShowMenuBakery = false;
        }
        
        if (doTile) {
            for (Tile tile : UV.choosingSquareTiles) {
                Tile tmpTile = tile.cloneTile(tile.getPosition());
                Int2 coords = getGridCoords((int) tmpTile.getPosition().x, (int) tmpTile.getPosition().y);
                tiles[coords.getX() * UV.gridWidth + coords.getY()] = tmpTile;
            }
            
            sortedTiles = Arrays.copyOf(tiles, tiles.length);
            Arrays.sort(sortedTiles, new Comparator<Tile>() {
                @Override
                public int compare(Tile t1, Tile t2) {
                    return (int) (t1.getPosition().y - t2.getPosition().y);
                }
            });
        
            UV.choosingSquareTiles.clear();
        }
        
        if (doPeasant) {
            entities.add(UV.choosingPeasant);
            renderList.add(UV.choosingPeasant);
            
            sortRenderList = true;
            
            Collections.sort(entities, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    return (int) (e1.getPosition().y - e2.getPosition().y);
                }
            });
        }
            
        Collections.sort(renderList, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof Build && o2 instanceof Build) {
                    return (int) (((Build)o1).getPosition().y - ((Build)o2).getPosition().y);
                } else if (o1 instanceof Build && o2 instanceof Entity) {
                    return (int) (((Build)o1).getPosition().y - ((Entity)o2).getPosition().y);
                } else if (o1 instanceof Entity && o2 instanceof Build) {
                    return (int) (((Entity)o1).getPosition().y - ((Build)o2).getPosition().y);
                } else {
                    return (int) (((Entity)o1).getPosition().y - ((Entity)o2).getPosition().y);
                }
            }
        });
            
        UV.choosingBuild = null;
        UV.choosingTile = null;
        UV.choosingPeasant = null;
        
        doBuild = false;
        doTile = false;
        doPeasant = false;
        
        GUI.falseContructionVariables();
        GUI.falseTileVariables();
        GUI.falsePeasantVariables();
        GUI.leftMenuNeedsUpdate = true;
    }
    
    public void mouseRightReleased(int button, int x, int y) {
        if (UV.choosingBuild != null) {
            if (UV.choosingBuild.getImageToDrawCounter() == 3) {
                UV.choosingBuild.setImageToDrawCounter(0);
            } else {
                UV.choosingBuild.setImageToDrawCounter(UV.choosingBuild.getImageToDrawCounter() + 1);
            }
        }
    }
    
    
    /*
     * 
     * GETS
     * 
     */
    
    
    public Int2 getGridCoords(int x, int y) {
        Int2 out = new Int2();
        
        out.setX((int) (((x - UV.origin.x) - 2 * (y - UV.origin.y)) / UV.tileWidth));
        out.setY((int) (((x - UV.origin.x) + 2 * (y - UV.origin.y)) / UV.tileWidth));

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
