/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffic_graphics;

import java.awt.Color;
import org.opensky.libadsb.Position;
import traffic.Traffic;

/**
 *
 * @author fms
 */
public class TrafficGraphics extends Traffic {

    private int coordX;
    private int coordY;
    private Color color;
    private boolean selected;
 
    public TrafficGraphics(String icao24, Position ref) {
        //super(icao24, ref);
        super(icao24, "", "",0);
        this.color = Color.WHITE;
        this.selected = false;
    }

    public TrafficGraphics(String icao24, String callsign, String squawk, long timestamp) {
        super(icao24, callsign, squawk, timestamp);
        this.color = Color.WHITE;
        this.selected = false;
    }

    public TrafficGraphics(String icao24, String callsign, String squawk, Double lon, Double lat, Double alt, Double gs, Double track, Double vr, Boolean alert, Boolean SPI, Boolean iog, long timestamp) {
        super(icao24, callsign, squawk, lon, lat, alt, gs, track, vr, alert, SPI, iog, timestamp);
        this.color = Color.WHITE;
        this.selected = false;
    }

    public void setGraphicsCoord(int x, int y) {
        this.coordX = x;
        this.coordY = y;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
