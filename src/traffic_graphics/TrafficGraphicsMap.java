/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffic_graphics;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensky.libadsb.ModeSDecoder;
import org.opensky.libadsb.Position;
import org.opensky.libadsb.msgs.ModeSReply;
import org.opensky.libadsb.tools;

import traffic.Traffic;
import traffic.TrafficMap;

/**
 *
 * @author fms
 */
public class TrafficGraphicsMap extends TrafficMap {

    Dimension panelSize;
    Rectangle2D mapArea;
    private static ModeSDecoder ADSBdecoder = new ModeSDecoder();

    private boolean saveData;

    public TrafficGraphicsMap(String host, int port, Position pos, TrafficGraphicsListener listener) throws IOException {
        super(host, port, pos, listener);
        panelSize = listener.getDimension();
        mapArea = listener.getMapArea();
    }

    public TrafficGraphicsMap(String file, Position pos, TrafficGraphicsListener listener) {
        super(file, pos, listener);
        panelSize = listener.getDimension();
        mapArea = listener.getMapArea();
    }

    @Override
    public synchronized boolean processMsg(long timestamp, ModeSReply msg) {
        TrafficGraphics trg;
        boolean ok = false;
        String icao24 = "";
        byte ftc = 0;
        int subtype = 0;
        boolean newTraffic;

        if(saveData){
             String filepath = "src/resources/tracks/recordedTrack.txt";
            try {
                FileWriter fw = new FileWriter(filepath,true); // Append mode. True means the filewriter appends the current data. False means the fliwriter overwrites the data.
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
                pw.println(timestamp+","+msg.getHexMessage());
                pw.flush();
                pw.close();
                //System.out.println(".csv was created successfully");
            } catch (Exception e) {
                System.out.println("Error while writing csv file");
            }
        }
        
        processTimestamp(timestamp);

        icao24 = tools.toHexString(msg.getIcao24());

        if (this.containsKey(icao24)) {
            trg = (TrafficGraphics) this.get(icao24);
            newTraffic = false;
        } else {
            trg = new TrafficGraphics(icao24, ref);
            newTraffic = true;
        }
        // Decode message and update Traffic
        ok = trg.updateTraffic(msg, timestamp);
        this.panelSize = ((TrafficGraphicsListener)listener).getDimension();
        int x = (int) (panelSize.getWidth() * (trg.getLongitude() - mapArea.getX()) / mapArea.getWidth()); // normalization
        int y = (int) (panelSize.getHeight() * (mapArea.getY() - trg.getLatitude()) / mapArea.getHeight());
        trg.setGraphicsCoord(x, y);
        if (!ok) {
            return ok; // Corrupted messagges are not proccessed
        }
        if (newTraffic) {
            if (trg.isPositioned()) {
                this.put(icao24, trg);
                if (listener != null) {
                    listener.putTraffic(trg); // notify
                }
            }
        } else {
            if (listener != null) {
                listener.updateTraffic(trg); // notify
            }
        }
        return ok;
        
    }

  
    
    
    @Override
    public void removeTraffic(Traffic tr
    ) {
        TrafficGraphics trg = (TrafficGraphics) tr;
        this.remove(tr.getICAO24());
        if (this.getListener() != null) {
            this.getListener().removeTraffic(trg); // notify
        }
    }

    @Override
    public TrafficGraphics[] getTraffics() {
        return (super.getTrafficsMap().values().toArray(new TrafficGraphics[0]));
    }
    
    public JSONObject getJSONTraffic(Traffic t){
        JSONObject trafficJSON = new JSONObject();
        try {
            trafficJSON.append("ICAO Code", t.getICAO24());
            trafficJSON.append("Latitude",t.getLatitude());
            trafficJSON.append("Longitude",t.getLongitude());
            trafficJSON.append("Altitude", t.getAltitude());
            trafficJSON.append("Heading", t.getHeading());
            
        } catch (JSONException ex) {
            Logger.getLogger(TrafficGraphicsMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return trafficJSON;
    }
         

    @Override
    public byte[] getTrafficsByteArray() {
        byte[] return_value = null;
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintWriter pwaos = new java.io.PrintWriter(baos);
        java.util.Enumeration<String> enumeration = this.keys();

        //Bucle per imprimir la hashtable
        TrafficGraphics i = null;

        while (enumeration.hasMoreElements()) {
            i = (TrafficGraphics) this.get(enumeration.nextElement());
            pwaos.println(i.getICAO24() + "," + i.getCallsign() + "," + i.getLongitude() + "," + i.getLatitude() + "," + i.getAltitude() + "," + i.getGs() + "," + i.getTrack() + "," + i.getVr() + "," + i.getSquawk());
        }

        pwaos.flush();
        return_value = baos.toByteArray();
        return return_value; //To change body of generated methods, choose Tools | Templates.
    }
    
    public static ModeSDecoder getADSBdecoder() {
        return ADSBdecoder;
    }
    
    public boolean isSaveData() {
        return saveData;
    }

    public void setSaveData(boolean saveData) {
        this.saveData = saveData;
    }
    
        public Dimension getPanelSize() {
        return panelSize;
    }

    public void setPanelSize(Dimension panelSize) {
        this.panelSize = panelSize;
    }

    public Rectangle2D getMapArea() {
        return mapArea;
    }

    public void setMapArea(Rectangle2D mapArea) {
        this.mapArea = mapArea;
    }

}
