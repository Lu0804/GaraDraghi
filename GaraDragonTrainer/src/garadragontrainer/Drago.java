/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package garadragontrainer;
import java.util.*;
import javax.swing.*;
/**
 *
 * @author lucia
 */



public class Drago implements Runnable{
    private JProgressBar prgBar;
    private boolean inPausa = false;
    private boolean reset = false;
    private boolean finito = false;
    private ControlloAnimazione animazione; // Aggiungi questo
    
    public Drago(JProgressBar prgBar) {
        this.prgBar = prgBar;
        this.animazione = null;
    }
    
    public Drago(JProgressBar prgBar, ControlloAnimazione animazione) {
        this.prgBar = prgBar;
        this.animazione = animazione;
    }
    
    public void run() {
        Random rnd = new Random();
        for (int i = 1; i <= 100; i++) {
            // Controlla reset
            if (reset) {
                SwingUtilities.invokeLater(() -> prgBar.setValue(0));
                return;
            }
            // Controlla pausa
            synchronized (this) {
                while (inPausa) {
                    try {
                        wait();
                    } catch (InterruptedException ignored) {}
                }
            }
            // Aggiorna la barra
            int value = i;
            SwingUtilities.invokeLater(() -> prgBar.setValue(value));
            
            // Aggiorna la posizione dell'animazione se presente
            if (animazione != null) {
                animazione.aggiornaPosizione();
            }
            
            // Sleep random tra 50 e 200ms per vedere il movimento
            try {
                Thread.sleep(50 + rnd.nextInt(150));
            } catch (InterruptedException e) {
                return;
            }
        }
        
        // Quando finisce, setta il flag
        finito = true;
    }
    
    public void pausa() {
        inPausa = true;
    }
    
    public void riprendi() {
        inPausa = false;
        synchronized (this) {
            notify();
        }
    }
    
    public void reset() {
        reset = true;
        inPausa = false;
        finito = false;
        synchronized (this) {
            notify();
        }
    }
    
    public JProgressBar getPrgBar() {
        return prgBar;
    }
    
    public boolean isInPausa() {
        return inPausa;
    }
    
    public boolean isReset() {
        return reset;
    }
    
    public boolean isFinito() {
        return finito;
    }
}