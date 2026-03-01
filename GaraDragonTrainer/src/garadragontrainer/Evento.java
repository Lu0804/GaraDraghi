/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package garadragontrainer;

/**
 * quetsa classe  serve per capire il tipo di danno e tempo di reazione dell'utente, capisce se l'attacco viene schivato e calcola il danno  
 * @author abenante.lucia
 */
public class Evento {
    private String tipo;           // Tipo di evento ("ATTACCO", "DANNO_CASUALE")
    private int danno;            // Quantità di danno (in percentuale)
    private boolean schivato;     // True se l'attacco è stato schivato
    private long tempoReazione;   // Tempo in millisecondi per reagire (long è un tipo numererico intero ma molto più grande di int )

    public Evento(String tipo, int danno, long tempoReazione) {
        this.tipo = tipo;
        this.danno = danno;
        this.tempoReazione = tempoReazione;
    }
    
    /**
     * costrtuttore per evento casule senza poter schivare
     * @param tipo tipo di danno
     * @param danno danno in percentuale
     */
    public Evento(String tipo, int danno) {
        this.tipo = tipo;
        this.danno = danno;
    }

    
    // set e get
    public String getTipo() {
        return tipo;
    }

    public int getDanno() {
        return danno;
    }

    public boolean isSchivato() {
        return schivato;
    }

    public long getTempoReazione() {
        return tempoReazione;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDanno(int danno) {
        this.danno = danno;
    }

    public void setSchivato(boolean schivato) {
        this.schivato = schivato;
    }

    public void setTempoReazione(long tempoReazione) {
        this.tempoReazione = tempoReazione;
    }
    
     /**
     * Calcola il danno effettivo considerando se è stato schivato
     * @return Il danno da applicare (0 se schivato, altrimenti il danno pieno)
     */
    public int calcolaDannoEffettivo() {
        return schivato ? 0 : danno;
    }
    
    @Override
    public String toString() {
        return "Evento{" +
                "tipo='" + tipo + '\'' +
                ", danno=" + danno +
                ", schivato=" + schivato +
                ", tempoReazione=" + tempoReazione +
                '}';
    }
    
    
}
