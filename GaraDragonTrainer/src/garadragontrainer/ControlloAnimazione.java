/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package garadragontrainer;
import javax.swing.*;
import java.awt.Container;

/**
 *
 * @author lucia
 */
public class ControlloAnimazione {

    
    private JLabel lblDrago; //  Label dove mettere l'icona del drago
    private JProgressBar prgBar; //Progress bar su cui si muove l'icona
    private Container panelGara;  // Cambia da JPanel a Container
    
    /**
     * 
     * @param lblDrago  Salva il riferimento alla label
     * @param prgBar Salva il riferimento alla progress bar
     * @param panelGara Salva il riferimento al pannello
     */
    public ControlloAnimazione(JLabel lblDrago, JProgressBar prgBar, Container panelGara) {  // Cambia qui
        this.lblDrago = lblDrago;
        this.prgBar = prgBar;
        this.panelGara = panelGara;
    }
    
    /**
     * Carica la GIF animata pre-ridimensionata (usa i file _small.gif)
     * NON usare getScaledInstance() - distrugge l'animazione GIF
     */
    public void caricaIconaDrago(String percorsoImmagine) {
        try {
            // Carica la GIF direttamente - ImageIcon gestisce l'animazione automaticamente
            ImageIcon icon = new ImageIcon(percorsoImmagine);
            
            lblDrago.setIcon(icon);
            lblDrago.setText("");
            // Dimensione della label uguale alla GIF pre-ridimensionata (60x40)
            lblDrago.setSize(80, 55);
            lblDrago.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            lblDrago.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
            
        } catch (Exception e) {
            System.out.println("Errore nel caricamento dell'immagine: " + e.getMessage());
        }
    }
    
    /**
     * Aggiorna la posizione del drago in base al valore della progress bar
     * Chiama questo metodo nel Drago.run() per ogni incremento
     */
    public void aggiornaPosizione() {
    // SwingUtilities.invokeLater() = esegui il codice nel Thread di Swing
    // (obbligatorio per modificare componenti GUI da altri thread)
        SwingUtilities.invokeLater(() -> {
        int progress = prgBar.getValue(); //Legge il valore attuale della progress bar
        int maxProgress = prgBar.getMaximum(); // Legge il valore massimo della progress bar (100)
        int barWidth = prgBar.getWidth(); //Legge la larghezza della progress bar in pixel
        int barX = prgBar.getX(); //Legge la posizione X della progress bar nel pannello
        int barY = prgBar.getY();//Legge la posizione Y della progress bar nel pannello
        
        // Calcola posizione
        int posX = barX + (int) ((progress * barWidth) / maxProgress) - 20;
        int posY = barY - 50;
        // setLocation(X, Y) = posiziona la label alle coordinate X, Y
        lblDrago.setLocation(posX, posY);
        lblDrago.repaint();// setLocation(X, Y) = posiziona la label alle coordinate X, Y
    });
}
}