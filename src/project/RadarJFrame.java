/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.plaf.FileChooserUI;
import org.opensky.libadsb.Position;
import traffic.Traffic;
import traffic_graphics.TrafficGraphics;
import traffic_graphics.TrafficGraphicsListener;
import traffic_graphics.TrafficGraphicsMap;

/**
 *
 * @author fms
 */
public class RadarJFrame extends javax.swing.JFrame implements TrafficGraphicsListener {

    private TrafficGraphicsMap tgm;
    private String antennaType;
    private String trackPath;

    public TrafficGraphicsMap getTgm() {
        return tgm;
    }

    public void setTgm(TrafficGraphicsMap tgm) {
        this.tgm = tgm;
    }

    private final String fileName = "src/resources/avion24_azul.png";
    private final Double lonWest = -1.78; // CCVV area
    private final Double lonEast = 1.22;
    private final Double latNorth = 40.79;
    private final Double latSud = 37.79;
    private ImageIcon iim;

    public RadarJFrame() throws IOException {
        initComponents();
        Position vlc = new Position(0.4727777777777778, 39.489444444444445, 4.0);
        this.setLocationRelativeTo(null);
         iim = new ImageIcon("src/resources/mapaBM.png");
         
        ChooserAntennaJDialog chooserAntennaJDialog = new ChooserAntennaJDialog(this,true);
        chooserAntennaJDialog.setVisible(true);
        
        this.antennaType = chooserAntennaJDialog.getAntennaType();
        System.out.println("Antenna type chosen is: "+this.antennaType);
       
        if(antennaType.equals("Beast")){
            tgm = new TrafficGraphicsMap("158.42.40.45", 10003,vlc,this);
        }else{
            tgm = new TrafficGraphicsMap(chooserAntennaJDialog.getFilePath(), vlc, this);
        }
        
        //   tgm = new TrafficGraphicsMap("src/resources/SSRtracks_2015_enero_29-05-44.txt", 1.0, this);
        radarPanel.setIcon(iim);
        radarPanel.setSize(iim.getIconWidth(), iim.getIconWidth());
        ((CPaint) radarPanel).setTrafficGenerator(tgm);
        
        ((CPaint) radarPanel).readFiles();
        
    }

    @Override
    public void putTraffic(Traffic t) {
        radarPanel.repaint();
    }

    @Override
    public void removeTraffic(Traffic t) {
        radarPanel.repaint();
    }

    @Override
    public void updateTraffic(Traffic t) {
        if(((TrafficGraphics) t).isSelected()){
            updateLabels((TrafficGraphics) t);
        }
        radarPanel.repaint();
    }

    @Override
    public void trafficStopped() {
        System.exit(0);
    }

    @Override
    public Dimension getDimension() {
        return radarPanel.getSize();
    }

    @Override
    public Rectangle2D getMapArea() {
        return new Rectangle2D.Double(lonWest, latNorth, lonEast - lonWest, latNorth - latSud);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        radarPanel = new CPaint(fileName);
        trafficDataPanel = new javax.swing.JPanel();
        recordButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        stopButton = new javax.swing.JButton();
        recordingPanel = new javax.swing.JPanel();
        trafficLabel = new javax.swing.JLabel();
        headingLabel = new javax.swing.JLabel();
        latitudeLabel = new javax.swing.JLabel();
        longitudeLabel = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        radarPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/mapaBM.PNG"))); // NOI18N
        radarPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radarPanelMouseClicked(evt);
            }
        });

        trafficDataPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        trafficDataPanel.setMaximumSize(new java.awt.Dimension(20, 429));

        recordButton.setText("Start");
        recordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordButtonActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Record flights data into txt");

        stopButton.setText("Stop");
        stopButton.setEnabled(false);
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout trafficDataPanelLayout = new javax.swing.GroupLayout(trafficDataPanel);
        trafficDataPanel.setLayout(trafficDataPanelLayout);
        trafficDataPanelLayout.setHorizontalGroup(
            trafficDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trafficDataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(trafficDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(trafficDataPanelLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(recordButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                        .addComponent(stopButton)
                        .addGap(34, 34, 34))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        trafficDataPanelLayout.setVerticalGroup(
            trafficDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trafficDataPanelLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(30, 30, 30)
                .addGroup(trafficDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(recordButton)
                    .addComponent(stopButton))
                .addGap(10, 10, 10))
        );

        recordingPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        trafficLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        trafficLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        trafficLabel.setMaximumSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout recordingPanelLayout = new javax.swing.GroupLayout(recordingPanel);
        recordingPanel.setLayout(recordingPanelLayout);
        recordingPanelLayout.setHorizontalGroup(
            recordingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, recordingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(recordingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(trafficLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(headingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(longitudeLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(latitudeLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        recordingPanelLayout.setVerticalGroup(
            recordingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recordingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(trafficLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(headingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(latitudeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(longitudeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(radarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(trafficDataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(recordingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(trafficDataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recordingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(radarPanel)))
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        tgm.stopit();
    }//GEN-LAST:event_formWindowClosing

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
    /*  if (radarPanel != null) {
            Image img = iim.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
            ImageIcon icon = new ImageIcon(img);
            radarPanel.setIcon(icon);
        }*/
    }//GEN-LAST:event_formComponentResized

    private void radarPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radarPanelMouseClicked
        
        TrafficGraphics[] tg = tgm.getTraffics();
        int distx,disty;
        
        for (TrafficGraphics trafficg : tg) {
            distx = Math.abs(evt.getX()-trafficg.getCoordX());
            disty = Math.abs(evt.getY()-trafficg.getCoordY());
            
            if(distx<10 && disty<10){
                if(trafficg.isSelected()){
                    trafficg.setSelected(false);
                    trafficLabel.setText("");
                    headingLabel.setText("");
                    latitudeLabel.setText("");
                    longitudeLabel.setText("");
                   
                }else{
                System.out.println("Plane clicked ICAO Code: "+trafficg.getICAO24());

                trafficg.setSelected(true);

                }
            }else{
                trafficg.setSelected(false);
            }
        }
    }//GEN-LAST:event_radarPanelMouseClicked

    private void recordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordButtonActionPerformed
        System.out.println("Recording Started");
        this.recordButton.setEnabled(false);
        this.stopButton.setEnabled(true);
        this.tgm.setSaveData(true);
    }//GEN-LAST:event_recordButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        System.out.println("Recording Stopped");
        this.recordButton.setEnabled(true);
        this.stopButton.setEnabled(false);
        this.tgm.setSaveData(false);
    }//GEN-LAST:event_stopButtonActionPerformed

    public void updateLabels(TrafficGraphics t){
        
        trafficLabel.setText("ICAO Code: "+t.getICAO24());
        headingLabel.setText(String.format("Heading: %.3f",t.getTrack())+"??");
        latitudeLabel.setText(String.format("Latitude: %.3f",t.getLatitude())+"??");
        longitudeLabel.setText(String.format("Longitude: %.3f",t.getLongitude())+"??");
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RadarJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RadarJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RadarJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RadarJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new RadarJFrame().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(RadarJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel headingLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel latitudeLabel;
    private javax.swing.JLabel longitudeLabel;
    private javax.swing.JLabel radarPanel;
    private javax.swing.JButton recordButton;
    private javax.swing.JPanel recordingPanel;
    private javax.swing.JButton stopButton;
    private javax.swing.JPanel trafficDataPanel;
    private javax.swing.JLabel trafficLabel;
    // End of variables declaration//GEN-END:variables

}
