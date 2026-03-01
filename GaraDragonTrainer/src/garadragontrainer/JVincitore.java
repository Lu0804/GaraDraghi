/*
 * Schermata finale che mostra il risultato della gara
 */
package garadragontrainer;

import javax.swing.*;
import java.awt.*;

/**
 * Form finale che mostra "Hai vinto!" o "Hai perso! Ha vinto Drago X"
 * @author lucia
 */
public class JVincitore extends javax.swing.JFrame {

    private int dragoVincitore; // 1=giocatore, 2/3/4=cpu

    /**
     * @param dragoVincitore Il numero del drago che ha vinto (1=giocatore)
     */
    public JVincitore(int dragoVincitore) {
        this.dragoVincitore = dragoVincitore;
        initComponents();
        setupContenuto();
    }

    /**
     * Imposta il testo e i colori in base a chi ha vinto
     */
    private void setupContenuto() {
        if (dragoVincitore == 1) {
            // IL GIOCATORE HA VINTO
            lblRisultato.setText("🏆 HAI VINTO! 🏆");
            lblRisultato.setForeground(new java.awt.Color(255, 210, 60)); // oro
            lblSottotitolo.setText("Il tuo drago ha dominato la gara!");
            lblSottotitolo.setForeground(new java.awt.Color(200, 220, 255));
        } else {
            // IL GIOCATORE HA PERSO
            lblRisultato.setText("HAI PERSO!");
            lblRisultato.setForeground(new java.awt.Color(220, 80, 80)); // rosso
            lblSottotitolo.setText("Ha vinto Drago " + dragoVincitore + "!");
            lblSottotitolo.setForeground(new java.awt.Color(190, 195, 215));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        // Pannello con immagine di sfondo
        pnlSfondo = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Carica e disegna l'immagine di sfondo
                ImageIcon bg = new ImageIcon("src/immagini/Vinci.jpg");
                if (bg.getIconWidth() > 0) {
                    g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Fallback: gradiente blu notte se immagine non trovata
                    Graphics2D g2 = (Graphics2D) g;
                    GradientPaint gp = new GradientPaint(
                        0, 0, new Color(10, 10, 30),
                        0, getHeight(), new Color(40, 20, 60)
                    );
                    g2.setPaint(gp);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        lblRisultato = new javax.swing.JLabel();
        lblSottotitolo = new javax.swing.JLabel();
        btnChiudi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Risultato Gara");
        setPreferredSize(new java.awt.Dimension(500, 400));
        setResizable(false);

        pnlSfondo.setLayout(null);

        // Label risultato principale
        lblRisultato.setFont(new java.awt.Font("Berlin Sans FB", Font.BOLD, 48));
        lblRisultato.setHorizontalAlignment(SwingConstants.CENTER);
        lblRisultato.setText("...");
        pnlSfondo.add(lblRisultato);
        lblRisultato.setBounds(30, 100, 440, 80);

        // Label sottotitolo
        lblSottotitolo.setFont(new java.awt.Font("Berlin Sans FB", Font.PLAIN, 22));
        lblSottotitolo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSottotitolo.setText("...");
        pnlSfondo.add(lblSottotitolo);
        lblSottotitolo.setBounds(30, 200, 440, 50);

        // Bottone chiudi
        btnChiudi.setFont(new java.awt.Font("Berlin Sans FB", Font.BOLD, 16));
        btnChiudi.setText("Chiudi");
        btnChiudi.setBackground(new java.awt.Color(70, 75, 90));
        btnChiudi.setForeground(java.awt.Color.WHITE);
        btnChiudi.setFocusPainted(false);
        btnChiudi.setBorderPainted(false);
        btnChiudi.setOpaque(true);
        btnChiudi.addActionListener(evt -> dispose());
        pnlSfondo.add(btnChiudi);
        btnChiudi.setBounds(185, 300, 130, 45);

        getContentPane().add(pnlSfondo);
        pnlSfondo.setBounds(0, 0, 500, 400);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSfondo, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSfondo, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null); // centra sullo schermo
    }

    // Variables declaration
    private javax.swing.JPanel pnlSfondo;
    private javax.swing.JLabel lblRisultato;
    private javax.swing.JLabel lblSottotitolo;
    private javax.swing.JButton btnChiudi;
}