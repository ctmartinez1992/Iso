package Util;

import Util.Math.Float2;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author carlos
 */
public class AABB {
    
    private Float2 position;
    private Float2 dimensions;
    private Float2[] vertices;

    public AABB(Float2 position, Float2 dimensions) {
        this.position = position;
        this.dimensions = dimensions;
    }

    public AABB(AABB orig) {
        this.position = new Float2(orig.position);
        this.dimensions = new Float2(orig.dimensions);
    }
    
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int)position.getX(), (int)position.getY(), (int)dimensions.getX(), (int)dimensions.getY());
    }

    public boolean overlaps(AABB aabb2) {
        if (maxX() >= aabb2.minX() && minX() <= aabb2.maxX() && maxY() >= aabb2.minY() && minY() <= aabb2.maxY()) {
            return true;
        }
        return false;
    }

    public Float2 getOverlapPoint(AABB aabb2, boolean overlaps) {
        Float2 collision = new Float2(0, 0, true);
        if (overlaps) {
            if (maxX() >= aabb2.minX() && minX() <= aabb2.minX()) {
                collision.setX(maxX());
            } 
            if (minX() <= aabb2.maxX() && maxX() >= aabb2.maxX()) {
                collision.setX(minX());
            }
            if (maxX() >= aabb2.minX() && minX() <= aabb2.maxX()) {
                collision.setX(minX() + dimensions.getX() / 2);
            }
            if (maxY() >= aabb2.minY() && minY() <= aabb2.minY()) {
                collision.setY(maxY());
            }
            if (minY() <= aabb2.maxY() && maxY() >= aabb2.maxY()) {
                collision.setY(minY());
            }
            if (maxY() >= aabb2.minY() && minY() <= aabb2.maxY()) {
                collision.setY(minY() + dimensions.getY() / 2);
            }
            
            return collision;
        }
        
        return new Float2(0, 0, false);
    }

    public Float2 closestPointInsideAABBToPoint(Float2 p) {
        Float2 r = new Float2(p);
        if (p.getX() < minX()) {
            r.setX(minX());
        }
        if (p.getX() > maxX()) {
            r.setX(maxX());
        }
        if (p.getY() < minY()) {
            r.setY(minY());
        }
        if (p.getY() > maxY()) {
            r.setY(maxY());
        }

        return r;
    }

    public Float2 closestPointOnAABBToPoint(Float2 p, boolean useX, boolean useY) {
        if (!contains(p)) {
            return closestPointInsideAABBToPoint(p);
        } else {
            Float2 result = new Float2(p);
            float shortestExit = 1000000.0f;
            int side = -1;
            
            if (useX && minX() < p.getX() && p.getX() < maxX()) {
                float dist = Math.min(p.getX() - minX(), maxX() - p.getX());
                if (dist < shortestExit) {
                    side = 0;
                    shortestExit = dist;
                }
            }
            
            if (useY && minY() < p.getY() && p.getY() < maxY()) {
                float dist = Math.min(p.getY() - minY(), maxY() - p.getY());
                if (dist < shortestExit) {
                    side = 1;
                }
            }

            if (side == 0) {
                if (p.getX() > position.getX()) {
                    result.setX(maxX());
                } else {
                    result.setX(minX());
                }
            } else if (side == 1)  {
                if (p.getY() > position.getY()) {
                    result.setY(maxY());
                } else {
                    result.setY(minY());
                }
            }
            return result;
        }
    }

    public synchronized Float2[] getVertices() {
        if (vertices == null) {
            vertices = new Float2[8];
            for (int i = 0; i < 8; ++i) {
                vertices[i] = new Float2();
            }
            recalcVertices();
        }

        return vertices;
    }

    public void print() {
        System.out.println("Posição: ");
        position.print();
        System.out.println("Dimensões: ");
        dimensions.print();
    }

    public boolean contains(Float2 point) {
        return this.contains(point.getX(), point.getY());
    }

    public boolean contains(float x, float y) {
        if (maxX() < x || minX() > x) {
            return false;
        }
        if (maxY() < y || minY() > y) {
            return false;
        }

        return true;
    }

    public float minX() {
        return (getPosition().getX());
    }

    public float minY() {
        return (getPosition().getY());
    }

    public float maxX() {
        return (getPosition().getX() + dimensions.getX());
    }

    public float maxY() {
        return (getPosition().getY() + dimensions.getY());
    }

    public Float2 getDimensions() {
        return dimensions;
    }

    public Float2 getPosition() {
        return position;
    }

    public void setPosition(Float2 position) {
        position.set(position);
        recalcVertices();
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
        recalcVertices();
    }

    public void include(AABB aabb) {
        float minX = Math.min(aabb.minX(), minX());
        float maxX = Math.max(aabb.maxX(), maxX());

        float minY = Math.min(aabb.minY(), minY());
        float maxY = Math.max(aabb.maxY(), maxY());

        float centerX = (minX + maxX) / 2.0f;
        float centerY = (minY + maxY) / 2.0f;

        getPosition().set(centerX, centerY);

        float width = maxX - minX;
        float height = maxY - minY;

        dimensions.set(width / 2.0f, height / 2.0f);

        recalcVertices();
    }

    public float width() {
        return maxX() - minX();
    }

    public float height() {
        return maxY() - minY();
    }

    public synchronized void recalcVertices() {
        if (vertices != null) {
            // Front
            vertices[0].set(minX(), minY());
            vertices[1].set(maxX(), minY());
            vertices[2].set(maxX(), maxY());
            vertices[3].set(minX(), maxY());
            // Back
            vertices[4].set(minX(), minY());
            vertices[5].set(maxX(), minY());
            vertices[6].set(maxX(), maxY());
            vertices[7].set(minX(), maxY());
        }
    }

    public void set(AABB aabb) {
        getPosition().set(aabb.getPosition());
        getDimensions().set(aabb.getDimensions());
        recalcVertices();
    }
}
