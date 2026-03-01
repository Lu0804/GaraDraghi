/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package garadragontrainer;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author lucia
 */
public class ControlloVincitore {
    
    private Drago d1, d2, d3, d4;
    private JLabel lblVincitore1, lblVincitore2, lblVincitore3, lblVincitore4;
    private Timer controlTimer;
    private int posizione = 1;
    
    /**
     * Costruttore che riceve i draghi e le label
     */
    public ControlloVincitore (Drago d1, Drago d2, Drago d3, Drago d4, JLabel lblVincitore1, JLabel lblVincitore2, JLabel lblVincitore3, JLabel lblVincitore4) {
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
        this.d4 = d4;
        this.lblVincitore1 = lblVincitore1;
        this.lblVincitore2 = lblVincitore2;
        this.lblVincitore3 = lblVincitore3;
        this.lblVincitore4 = lblVincitore4;
    }

public void avvia() {
    posizione = 1;  // Resetta la posizione a 1
    
    // Crea un Timer che si attiva ogni 100ms 
    controlTimer = new Timer(100, e -> {
        
        // Controlla DRAGO 1:
        // Se d1 ha finito (isFinito() torna true)
        // E la label ancora dice "." (non è stata assegnata)
        if (d1.isFinito() && lblVincitore1.getText().equals(" ")) {
            aggiornaVincitore(lblVincitore1);  // Assegna la posizione
        }
        
        // Stessa logica per gli altri 3 draghi
        if (d2.isFinito() && lblVincitore2.getText().equals(" ")) {
            aggiornaVincitore(lblVincitore2);
        }
        
        if (d3.isFinito() && lblVincitore3.getText().equals(" ")) {
            aggiornaVincitore(lblVincitore3);
        }
        
        if (d4.isFinito() && lblVincitore4.getText().equals(" ")) {
            aggiornaVincitore(lblVincitore4);
        }
   
        // Se posizione > 4, significa che tutti hanno finito quindi il timer si ferma
        if (posizione > 4) {
            controlTimer.stop();
        }
    });
    
    controlTimer.start();  // Avvia il timer
}

public void ferma() {
    // Controlla se il timer esiste e se sta ancora andando
    if (controlTimer != null && controlTimer.isRunning()) {
        controlTimer.stop();  // Lo ferma
    }
}

private void aggiornaVincitore(JLabel label) {
    // Array che contiene le 4 posizioni possibili
    String[] posizioni = {"1°", "2°", "3°", "4°"};
    
    label.setText(posizioni[posizione - 1]);
    
    // Se è il primo a finire (posizione == 1) apri la schermata risultato
    if (posizione == 1) {
        // Capisce quale drago ha vinto confrontando le label
        int dragoVincitore;
        if (label == lblVincitore1) {
            dragoVincitore = 1;
        } else if (label == lblVincitore2) {
            dragoVincitore = 2;
        } else if (label == lblVincitore3) {
            dragoVincitore = 3;
        } else {
            dragoVincitore = 4;
        }
        
        // Apri la schermata del vincitore dopo 1 secondo (per vedere il 1° sulla label)
        javax.swing.Timer timerApertura = new javax.swing.Timer(1000, e -> {
            new JVincitore(dragoVincitore).setVisible(true);
        });
        timerApertura.setRepeats(false);
        timerApertura.start();
    }
    
    posizione++;
}

public void reset() {
    posizione = 1;  // Torna a 1
    ferma();        // Ferma il timer
}

}
