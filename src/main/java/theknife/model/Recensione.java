package theknife.model;

/**
 * La classe recensione rappresenta una recensione di un ristorante.
 * Una recensione contiene una valutazione da 1 a 5, un commento, l'utente che l'ha scritta e il ristorante valutato.
 * Le recensioni possono essere visualizzate solo dagli utenti che hanno effettuato l'accesso
 *
 * @author Davide Suigi 759430 CO
 * @version 1.0
 */

public class Recensione {
    private int valutazione;
    private String commento;
    private Utente autore;
    private Ristorante ristorante;
    private String ristoranteNome;

    /**
     * Costruttore vuoto della classe Recensione.
     */
    public Recensione() {}

    /**
     * Costruttore della classe Recensione con i seguenti parametri.
     *
     * @param valutazione La valutazione da 1 a 5.
     * @param commento Il commento della recensione.
     * @param autore L'utente che ha scritto la recensione.
     * @param ristorante Il ristorante valutato nella recensione.
     */
    public Recensione(int valutazione, String commento, Utente autore, Ristorante ristorante) {
        if (valutazione < 1 || valutazione > 5) {
            throw new IllegalArgumentException("La valutazione deve essere compresa tra 1 e 5.");
        }
        this.valutazione = valutazione;
        this.commento = commento;
        this.autore = autore;
        this.ristorante = ristorante;
    }
    
    /**
     * Restituisce la valutazione della recensione.
     * 
     * @return La valutazione della recensione.
     * @throws IllegalArgumentException Se la valutazione non è compresa tra 1 e 5.
     * @see #setValutazione(int)
     */
    public int getValutazione() { return valutazione; }
    /**
     * Imposta la valutazione della recensione.
     * 
     * @param v La valutazione da impostare.
     * @throws IllegalArgumentException Se la valutazione non è compresa tra 1 e 5.
     */
    public void setValutazione(int v) { this.valutazione = v; }

    /**
     * Restituisce il commento della recensione.
     * 
     * @return Il commento della recensione.
     */
    public String getCommento() { return commento; }
    /**
     * Imposta il commento della recensione.
     * 
     * @param c Il commento da impostare.
     */
    public void setCommento(String c) { this.commento = c; }

    /**
     * Restituisce il ristorante valutato nella recensione.
     * 
     * @return Il ristorante della recensione.
     */
    public Utente getAutore() { return autore; }
    /**
     * Imposta l'utente che ha scritto la recensione.
     * 
     * @param a L'utente da impostare come autore della recensione.
     */
    public void setAutore(Utente a) { this.autore = a; }

    /**
     * Restituisce il ristorante valutato nella recensione.
     * 
     * @return Il ristorante della recensione.
     */
    public String getRistoranteNome() { return ristoranteNome != null ? ristoranteNome : (ristorante != null ? ristorante.getNome() : null); }
    /**
     * Imposta il nome del ristorante valutato nella recensione.
     * 
     * @param nome Il nome del ristorante da impostare.
     */
    public void setRistoranteNome(String nome) { this.ristoranteNome = nome; }
}
