/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package garadragontrainer;
import javax.swing.*;
import java.awt.Image;
import java.util.Random;
import javax.sound.sampled.*;
import java.io.File;
/**
 * classe che controlla l'attacco del giocatore e la gif del drago che attaca 
 * @author lucia
 */
public class ControlloAttacco {
    
    private JLabel lblAttacco;          
    private JButton btnSchiva;           
    private Drago dragoGiocatore;        
    private Timer timerAttacchi;         
    private Timer timerReazione;          
    private Random random;              
    private boolean attaccoInCorso;     
    private Evento eventoCorrente;
    private javax.swing.JPanel panelGioco;  // Riferimento al pannello per cambiare colore
    private java.awt.Color coloreOriginale;  // Salva il colore originale del pannello
    private String percorsoAudio = "src/immagini/attacco.wav"; // File audio attacco
    
    //si usano static final per le costanti perchè non può essere modificato e static è condivisa nella classe (c'è n'è dolo una)
     // Costanti di gioco (si possono cambare se si vuole aumentare la difficoltà)
    private static final int tempoMinAttacco = 2000;  // 2 secondi minimo
    private static final int tempoMaxAttacco = 8000;  // 8 secondi massimo
    private static final long tempoReazione= 2000;    // 2 secondi per reagire
    private static final int attacco = 15;            // 15% di danno

    
    /**
     * 
     * @param lblAttacco  Label dove mostrare la GIF di attacco
     * @param btnSchiva Bottone per schivare
     * @param dragoGiocatore  Il drago del giocatore (drago1)
     * @param panelGioco Pannello di gioco per cambiare colore quando colpito
     */
    public ControlloAttacco(JLabel lblAttacco, JButton btnSchiva, Drago dragoGiocatore, javax.swing.JPanel panelGioco) {
        this.lblAttacco = lblAttacco;
        this.btnSchiva = btnSchiva;
        this.dragoGiocatore = dragoGiocatore;
        this.panelGioco = panelGioco;
        this.coloreOriginale = panelGioco.getBackground(); // Salva il colore originale
        this.random = new Random();
        this.attaccoInCorso = false;// inizilamnte non è in corso
        
        lblAttacco.setVisible(false);
        btnSchiva.setVisible(false);
        
        // Forza il Look&Feel di default (Basic) solo su questo bottone
        // Nimbus ignora setBackground(), BasicButtonUI invece lo rispetta sempre
        btnSchiva.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnSchiva.setOpaque(true);
        btnSchiva.setBorderPainted(false);
        btnSchiva.setFocusPainted(false);
    }
    
/**
     * Carica la GIF di attacco con DEBUG
     */
    public void caricaGifAttacco(String percorsoGif) {
        try {
            // Carica DIRETTAMENTE senza ridimensionare
            // SCALE_SMOOTH distrugge l'animazione GIF - usa file pre-ridimensionato
            ImageIcon icon = new ImageIcon(percorsoGif);
            
            if (icon.getImageLoadStatus() == java.awt.MediaTracker.ERRORED) {
                throw new Exception("File non trovato: " + percorsoGif);
            }
            
            lblAttacco.setIcon(icon);
            lblAttacco.setText("");
            System.out.println("✅ GIF attacco caricata OK: " + percorsoGif + 
                " (" + icon.getIconWidth() + "x" + icon.getIconHeight() + ")");
            
        } catch (Exception e) {
            System.out.println("❌ ERRORE caricamento GIF attacco: " + e.getMessage());
            lblAttacco.setText("⚠");
            lblAttacco.setForeground(java.awt.Color.RED);
            lblAttacco.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 48));
        }
    }
    
 /**
     * Avvia il sistema di attacchi casuali
     */
    public void avvia() {
        if (timerAttacchi != null && timerAttacchi.isRunning()) {
            return; // Già avviato
        }
        
        // Timer che genera attacchi casuali
        timerAttacchi = new Timer(getTempoRandomAttacco(), e -> {
            if (!attaccoInCorso && !dragoGiocatore.isFinito()) {
                eseguiAttacco();
            }
            // Resetta il timer con un nuovo tempo casuale
            timerAttacchi.setDelay(getTempoRandomAttacco());
        });
        
        timerAttacchi.start();
    }
    
    /**
     * Ferma il sistema di attacchi
     */
    public void ferma() {
        if (timerAttacchi != null) {
            timerAttacchi.stop();
        }
        if (timerReazione != null) {
            timerReazione.stop();
        }
        nascondiAttacco();
    }
    
    /**
     * Esegue un attacco
     */
    private void eseguiAttacco() {
        // IMPORTANTE: Non attaccare se il drago ha già finito!
        if (dragoGiocatore.isFinito()) {
            return;
        }
        
        attaccoInCorso = true; //l'attacco è in corso
        
        // Crea l'evento
        eventoCorrente = new Evento("ATTACCO", attacco, tempoReazione);
        
        // Mostra la GIF di attacco
        mostraAttacco();
        riproduciAudio(); // Suona l'audio di attacco
        
        // Mostra e abilita il bottone schiva
        btnSchiva.setVisible(true);
        btnSchiva.setEnabled(true);
        btnSchiva.setText("SCHIVA!");
        btnSchiva.setBackground(new java.awt.Color(255, 0, 0));
        btnSchiva.setForeground(java.awt.Color.WHITE);
        btnSchiva.repaint();
        
        // Timer per il tempo di reazione
        timerReazione = new Timer((int) tempoReazione, e -> {
            // se il Tempo è scaduto l'attacco termina e applica il danno
            if (attaccoInCorso) {
                applicaDanno();
                terminaAttacco();
                
            }
        });
        timerReazione.setRepeats(false); // Solo una volta
        timerReazione.start();
    }
    
    /**
     * Chiamato quando l'utente preme il bottone Schiva
     */
    public void schivaAttacco() {
        if (!attaccoInCorso) {
            return; // Nessun attacco in corso
        }
        
        // se invece l'attacco è in corso vuol dire che l'utente ha schivato l'attaco 
        eventoCorrente.setSchivato(true);
        System.out.println("✓ SCHIVATO! Bravo!");
        
        // Feedback visivo positivo
        btnSchiva.setText("SCHIVATO!");
        btnSchiva.setBackground(new java.awt.Color(0, 200, 0));
        btnSchiva.setForeground(java.awt.Color.WHITE);
        btnSchiva.setEnabled(false);
        btnSchiva.repaint();
        
        // Ferma il timer di reazione
        if (timerReazione != null) {
            timerReazione.stop();
        }
        
        // Aspetta un momento prima di nascondere tutto
        Timer delayTimer = new Timer(500, e -> terminaAttacco());
        delayTimer.setRepeats(false);
        delayTimer.start();
    }
    
    /**
     * Applica il danno al drago del giocatore
     */
    private void applicaDanno() {
        // IMPORTANTE: Non applicare danno se il drago ha già finito!
        if (dragoGiocatore.isFinito()) {
            return;
        }
        
        JProgressBar prgBar = dragoGiocatore.getPrgBar();//prende la barra del primo drago ch è il drago dell'utente
        int valoreAttuale = prgBar.getValue(); //salva il valore della bar nel momento prima dell'attacco.
        int nuovoValore = Math.max(0, valoreAttuale - attacco);//leva al valore della progressBar il danno (Math.max(0 si usa così che non vada in negativo)
        
        SwingUtilities.invokeLater(() -> prgBar.setValue(nuovoValore)); //aggoirna la progressBar
        
        System.out.println("❌ COLPITO! Danno: -" + attacco + "%");
        
        // FEEDBACK VISIVO: Cambia il bottone e il pannello in rosso
        mostraFeedbackColpito();
    }
    
    /**
     * Mostra feedback visivo quando viene colpito
     */
    private void mostraFeedbackColpito() {
        // Forza i colori del bottone (setOpaque è già nel costruttore)
        btnSchiva.setText("Colpito!");
        btnSchiva.setBackground(java.awt.Color.WHITE);
        btnSchiva.setForeground(new java.awt.Color(180, 0, 0)); // Testo rosso scuro
        btnSchiva.setEnabled(false);
        btnSchiva.repaint(); // Forza il ridisegno del bottone
        
        // Cambia il colore del pannello in rosso e forza il ridisegno
        panelGioco.setBackground(new java.awt.Color(220, 50, 50));
        panelGioco.repaint();
        
        // Timer per ripristinare tutto dopo 800ms
        Timer feedbackTimer = new Timer(800, e -> {
            panelGioco.setBackground(coloreOriginale);
            panelGioco.repaint();
            btnSchiva.setForeground(java.awt.Color.BLACK);
            btnSchiva.repaint();
        });
        feedbackTimer.setRepeats(false);
        feedbackTimer.start();
    }
    
    /**
     * Termina l'attacco corrente
     */
    private void terminaAttacco() {
        nascondiAttacco(); // nasconde la label dell'attacco
        btnSchiva.setVisible(false); // nasconde il bottone schiva
        btnSchiva.setEnabled(false); //disabilita il bottone schiva
        panelGioco.setBackground(coloreOriginale); // Ripristina il colore originale
        attaccoInCorso = false; 
        eventoCorrente = null;
    }
    
    /**
     * Mostra la GIF di attacco
     */
    private void mostraAttacco() {
        lblAttacco.setVisible(true);
        lblAttacco.repaint();
    }
    
    /**
     * Nasconde la GIF di attacco
     */
    private void nascondiAttacco() {
        lblAttacco.setVisible(false);
    }
    
    /**
     * Riproduce il file audio di attacco (non blocca il thread grafico)
     */
    private void riproduciAudio() {
        new Thread(() -> {
            try {
                File file = new File(percorsoAudio);
                if (!file.exists()) {
                    System.out.println("⚠ Audio non trovato: " + percorsoAudio);
                    return;
                }
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } catch (Exception e) {
                System.out.println("⚠ Errore audio: " + e.getMessage());
            }
        }).start();
    }
    
    /**
     * Imposta un percorso audio personalizzato
     */
    public void setPercorsoAudio(String percorso) {
        this.percorsoAudio = percorso;
    }
    
    /**
     * Genera un tempo casuale tra attacchi
     */
    private int getTempoRandomAttacco() {
        return tempoMinAttacco + 
               random.nextInt(tempoMaxAttacco - tempoMinAttacco);
    }
    
    /**
     * Verifica se c'è un attacco in corso
     */
    public boolean isAttaccoInCorso() {
        return attaccoInCorso;
    }
}