/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package garadragontrainer;
import javax.swing.*;
import java.awt.Image;
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
     * Carica un'icona/gif e la posiziona sulla progress bar
     * La icona seguirà il progresso della barra
     */
    public void caricaIconaDrago(String percorsoImmagine) {
        try {
            // Carica l'immagine, ImageIcon legge il file e lo converte in icona
            ImageIcon icon = new ImageIcon(percorsoImmagine);
            
            // Ridimensiona l'immagine
            Image img = icon.getImage(); //Estrae l'immagine da ImageIcon
            Image imgScalata = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);//Ridimensiona l'immagine a 40x40 pixel (Image.SCALE_SMOOTH = qualità migliore)
            ImageIcon iconScalata = new ImageIcon(imgScalata); //Converti l'immagine ridimensionata in ImageIcon
            
            // Assegna l'icona alla label
            lblDrago.setIcon(iconScalata);// Metti l'icona nella label
            lblDrago.setText(" "); // Rimuovi il testo (se c'era)
            
        } catch (Exception e) { // Se c'è un errore (file non trovato, ecc)
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