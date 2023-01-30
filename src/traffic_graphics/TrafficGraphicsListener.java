/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffic_graphics;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import traffic.TrafficListener;

/**
 *
 * @author fms
 */
public interface TrafficGraphicsListener extends TrafficListener {
    public Dimension getDimension();
    public Rectangle2D getMapArea();
}
