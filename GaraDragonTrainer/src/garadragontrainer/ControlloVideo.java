/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package garadragontrainer;
import javax.swing.*;
import java.awt.Image;

/**
 *
 * @author lucia
 */
public class ControlloVideo {
 
    private JLabel lblVideo; // Label dove mostrare la GIF
/**
     * Costruttore
     * @param lblVideo Riferimento alla label dove mostrare il video
     */
    public ControlloVideo(JLabel lblVideo) {
        this.lblVideo = lblVideo;
    }
    
    /**
     * Carica e visualizza la GIF Video_Pov
     * @param percorsoGif Percorso del file GIF
     */
    public void caricaVideo(String percorsoGif) {
        try {
            // Carica la GIF
            ImageIcon gifIcon = new ImageIcon(percorsoGif);
            
            // Imposta la GIF nella label
            lblVideo.setIcon(gifIcon);
            lblVideo.setText(""); // Rimuovi eventuale testo
            
            // Centra la GIF
            lblVideo.setHorizontalAlignment(JLabel.CENTER);
            lblVideo.setVerticalAlignment(JLabel.CENTER);
            
        } catch (Exception e) {
            System.out.println("Errore nel caricamento del video: " + e.getMessage());
            lblVideo.setText("Errore caricamento video");
        }
    }
    
    /**
     * Carica la GIF con ridimensionamento
     * @param percorsoGif Percorso del file GIF
     * @param larghezza Larghezza desiderata in pixel
     * @param altezza Altezza desiderata in pixel
     */
    public void caricaVideoRidimensionato(String percorsoGif, int larghezza, int altezza) {
        try {
            // Carica la GIF
            ImageIcon gifIcon = new ImageIcon(percorsoGif);
            
            // Ridimensiona
            Image img = gifIcon.getImage();
            Image imgScalata = img.getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
            ImageIcon iconScalata = new ImageIcon(imgScalata);
            
            // Imposta la GIF ridimensionata
            lblVideo.setIcon(iconScalata);
            lblVideo.setText("");
            
            // Centra la GIF
            lblVideo.setHorizontalAlignment(JLabel.CENTER);
            lblVideo.setVerticalAlignment(JLabel.CENTER);
            
        } catch (Exception e) {
            System.out.println("Errore nel caricamento del video: " + e.getMessage());
            lblVideo.setText("Errore caricamento video");
        }
    }
    
    /**
     * Rimuove il video dalla label
     */
    public void rimuoviVideo() {
        lblVideo.setIcon(null);
        lblVideo.setText("");
    }
}

