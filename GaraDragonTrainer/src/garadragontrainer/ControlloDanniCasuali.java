/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package garadragontrainer;
import javax.swing.*;
import java.util.Random;
/**
 *
 * @author lucia
 */
public class ControlloDanniCasuali {
    private Drago drago; 
    private Timer timerDanni; 
    private Random random;
    
    // Costanti
    private static final int tempoEventoMin = 4000;   // 4 secondi
    private static final int tempoEventoMax = 10000;  // 10 secondi
    private static final int dannoMin = 5;                 // 5% minimo
    private static final int dannoMax = 20;                // 20% massimo
    private static final double probabiltaDanno = 0.4;    // 40% di probabilità
    
    /**
     * Costruttore
     */
    public ControlloDanniCasuali(Drago drago) {
        this.drago = drago;
        this.random = new Random();
    }
    
    /**
     * Avvia il sistema di danni casuali
     */
    public void avvia() {
        if (timerDanni != null && timerDanni.isRunning()) {
            return; // Già avviato
        }
        
        timerDanni = new Timer(getTempoRandomEvento(), e -> {
            if (!drago.isFinito()) {
                generaEventoCasuale();
            }
            // Resetta con nuovo tempo random
            timerDanni.setDelay(getTempoRandomEvento());
        });
        
        timerDanni.start();
    }
    
    /**
     * Ferma il sistema di danni casuali
     */
    public void ferma() {
        if (timerDanni != null) {
            timerDanni.stop();
        }
    }
    
    /**
     * Genera un evento casuale (può essere danno o nulla)
     */
    private void generaEventoCasuale() {
        // Probabilità di subire danno
        if (random.nextDouble() < probabiltaDanno) {
            int danno = dannoMin + random.nextInt(dannoMax - dannoMin + 1);
            applicaDanno(danno);
        }
        // Altrimenti non succede nulla (fortuna!)
    }
    
    /**
     * Applica danno al drago
     */
    private void applicaDanno(int danno) {
        // IMPORTANTE: Non applicare danno se il drago ha già finito!
        if (drago.isFinito()) {
            return;
        }
        
        JProgressBar prgBar = drago.getPrgBar();
        int valoreAttuale = prgBar.getValue();
        int nuovoValore = Math.max(0, valoreAttuale - danno);
        
        SwingUtilities.invokeLater(() -> prgBar.setValue(nuovoValore));
        
        System.out.println("⚡ Drago subisce danno casuale: -" + danno + "% ");
    }
    
    /**
     * Genera un tempo casuale tra eventi
     */
    private int getTempoRandomEvento() {
        return tempoEventoMin + 
               random.nextInt(tempoEventoMax - tempoEventoMin);
    }
    
    
    
}