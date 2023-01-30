/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import traffic.Traffic;
import traffic_graphics.TrafficGraphics;
import traffic_graphics.TrafficGraphicsMap;

/**
 *
 * @author fms
 */
public class CPaint extends JLabel {

    private TrafficGraphicsMap tgm;

    private BufferedImage aircraft = null;
    
    private ConcurrentHashMap<String,int[]> airpMap;
    private ConcurrentHashMap<String,int[]> vorMap;

    public CPaint(String file) {
        
        try {
            aircraft = ImageIO.read(new File(file));
        } catch (IOException ex) {
            Logger.getLogger(CPaint.class.getName()).log(Level.SEVERE, null, ex);
        }         
    }
    
    public void readFiles(){
                // Leer el archivo de AIRP y almacenar la información en un mapa String, double[] (Nombre,[Lat,long])
        try {             
        File myObj = new File("src/resources/AIRP.txt");
        Scanner myReader = new Scanner(myObj);
        
        this.airpMap = new ConcurrentHashMap<String,int[]>();  
        double[] airpCoor = new double[2];
        int[] airpCoorxy;
        
        while (myReader.hasNextLine()) {
          airpCoorxy = new int[2];
          String data = myReader.nextLine();
          String[] splited = data.split("\\s+");
 
          airpCoor[0] = Double.parseDouble(splited[3]);
          airpCoor[1] = Double.parseDouble(splited[5]);
          
          airpCoorxy = normalize(airpCoor[0], airpCoor[1]);
                     
          airpMap.put(splited[1],airpCoorxy);
        }
        myReader.close();

        File myObj2 = new File("src/resources/VOR.txt");
        Scanner myReader2 = new Scanner(myObj2);
        this.vorMap = new ConcurrentHashMap<String,int[]>();
        double[] vorCoor = new double[2];
        int[] vorCoorxy;
        
        while (myReader2.hasNextLine()) {
          vorCoorxy = new int[2];
          String data = myReader2.nextLine();
          String[] splited = data.split(",");
 
          vorCoor[0] = Double.parseDouble(splited[2]);
          vorCoor[1] = Double.parseDouble(splited[3]);
          
          vorCoorxy = normalize(vorCoor[0], vorCoor[1]);
                     
          vorMap.put(splited[0],vorCoorxy);
        }
        myReader.close();
        
        System.out.println("File reading ended");
        
        } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
    }
    
    private int[] normalize(double lat,double lon){
        int coordNormalized[] = new int[2];
        coordNormalized[0] = (int) (tgm.getPanelSize().getWidth() * (lon - tgm.getMapArea().getX()) / tgm.getMapArea().getWidth());
        coordNormalized[1] = (int) (tgm.getPanelSize().getHeight() * (tgm.getMapArea().getY() - lat) / tgm.getMapArea().getHeight());
        return coordNormalized;
    }
    


    public void setTrafficGenerator(TrafficGraphicsMap tgm) {
        this.tgm = tgm;
    }

    public void paintFlightData(Graphics2D g) {

        TrafficGraphics[] fo = tgm.getTraffics();

        // Pinta un círculo verde en la posición de cada aeropuerto
        g.setColor(Color.GREEN);
        for (int[] airp : airpMap.values()){
            g.fillArc(airp[0], airp[1], 20, 20, 0, 360);
        }

        // Pinta un hexágono rojo en la posición de cada VOR
        g.setColor(Color.RED);
        for (int[] vorCoord : vorMap.values()){
            int xyHex[][] = makeHexagon(vorCoord);
            int[] xHex = xyHex[0];
            int[] yHex = xyHex[1];
            g.drawPolygon(xHex,yHex,xHex.length);
            g.drawLine(vorCoord[0], vorCoord[1], vorCoord[0], vorCoord[1]);
        } 
            
        for (int i = 0; i < fo.length; i++) {
            if ((fo[i].getCoordX() >= 0) && (fo[i].getCoordY() >= 0) && (fo[i].getCoordX() < this.getWidth()) && (fo[i].getCoordY() < this.getHeight())) {
                AffineTransform at = new AffineTransform();
                // inverse order:
                // 3. translate it to the center of the component
                at.translate(fo[i].getCoordX(), fo[i].getCoordY());
                // 2. do the actual rotation
                at.rotate(Math.PI * fo[i].getTrack() / 180.0);
                // 1. translate the object so that you rotate it around the center
                at.translate(-aircraft.getWidth() / 2, -aircraft.getHeight() / 2);
                g.drawImage(aircraft, at, null);
                g.setColor(Color.GREEN);
                if(fo[i].isSelected()){
                    g.drawArc(fo[i].getCoordX()-2*aircraft.getWidth()/3, fo[i].getCoordY()-2*aircraft.getHeight()/3, 30, 30, 0, 360);
                }
                g.setColor(Color.YELLOW);
                g.drawString(fo[i].getICAO24(), fo[i].getCoordX() + 5, fo[i].getCoordY() - 5);
            }
        }
        //   System.out.println(fo[i].HexIdent + " " + lonNor + " " + latNor); 
    }
    
    public int[][] makeHexagon(int[] center){
        int[][] hexCoord = new int[2][6];
        int[] varx = new int[]{5,10,5,-5,-10,-5};
        int[] vary = new int[]{7,0,-7,-7,0,7};
        for(int i=0; i<6;i++){
            hexCoord[0][i]=center[0]+varx[i];
            hexCoord[1][i]=center[1]+vary[i];
        }  
        return hexCoord;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        paintFlightData(g2);
    }
    
}
