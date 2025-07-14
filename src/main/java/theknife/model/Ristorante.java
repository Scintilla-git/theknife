package theknife.model;

/**
 * La classe Ristorante rappresenta un ristorante nel sistema.
 * Un ristorante ha un nome, una nazione, una città, un indirizzo, una latitudine e longitudine,
 * una fascia di prezzo, un tipo di cucina, e può offrire servizi come asporto e prenotazione online.
 *
 * @author Davide Suigi 759430 CO
 * @version 1.0
 */
public class Ristorante {
    private String nome;
    private String nazione;
    private String citta;
    private String indirizzo;
    private double latitudine;
    private double longitudine;
    private String fasciaPrezzo;
    private boolean asporto;
    private boolean prenotazioneOnline;
    private String tipoCucina;
    private String descrizione;
    private String titolare;

    /**
     * Costruttore vuoto della classe.
     */
    public Ristorante() {}

    /**
     * Costruttore della classe Ristorante con i seguenti parametri.
     *
     * @param nome                Il nome del ristorante.
     * @param nazione             La nazione in cui si trova il ristorante.
     * @param citta               La città in cui si trova il ristorante.
     * @param indirizzo           L'indirizzo del ristorante.
     * @param latitudine          La latitudine del ristorante.
     * @param longitudine         La longitudine del ristorante.
     * @param fasciaPrezzo        La fascia di prezzo del ristorante.
     * @param asporto             Indica se il ristorante offre servizio di asporto.
     * @param prenotazioneOnline  Indica se il ristorante accetta prenotazioni online.
     * @param tipoCucina          Il tipo di cucina offerta dal ristorante.
     * @param descrizione         Una breve descrizione del ristorante (opzionale).
     * @param titolare            Il nome del titolare del ristorante.
     */

    public Ristorante(String nome, String nazione, String citta, String indirizzo,
                      double latitudine, double longitudine, String fasciaPrezzo,
                      boolean asporto, boolean prenotazioneOnline, String tipoCucina, String descrizione, String titolare) {
        this.nome = nome;
        this.nazione = nazione;
        this.citta = citta;
        this.indirizzo = indirizzo;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.fasciaPrezzo = fasciaPrezzo;
        this.asporto = asporto;
        this.prenotazioneOnline = prenotazioneOnline;
        this.tipoCucina = tipoCucina;
        this.descrizione = descrizione;
        this.titolare = titolare;
    }

    // Getter e Setter dei parametri sopra definiti

    /**
     * Restituisce il nome del ristorante.
     *
     * @return Il nome del ristorante.
     */
    public String getNome() { return nome; }
    /**
     * Imposta il nome del ristorante.
     *
     * @param nome Il nome del ristorante.
     */
    public void setNome(String nome) { this.nome = nome; }

    /**
     * Restituisce la nazione in cui si trova il ristorante.
     *
     * @return La nazione del ristorante.
     */
    public String getNazione() { return nazione; }
    /**
     * Imposta la nazione in cui si trova il ristorante.
     *
     * @param nazione La nazione del ristorante.
     */
    public void setNazione(String nazione) { this.nazione = nazione; }

    /**
     * Restituisce la città in cui si trova il ristorante.
     *
     * @return La città del ristorante.
     */
    public String getCitta() { return citta; }
    /**
     * Imposta la città in cui si trova il ristorante.
     *
     * @param citta La città del ristorante.
     */
    public void setCitta(String citta) { this.citta = citta; }

    /**
     * Restituisce l'indirizzo del ristorante.
     *
     * @return L'indirizzo del ristorante.
     */
    public String getIndirizzo() { return indirizzo; }
    /**
     * Imposta l'indirizzo del ristorante.
     *
     * @param indirizzo L'indirizzo del ristorante.
     */
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }

    /**
     * Restituisce la latitudine del ristorante.
     *
     * @return La latitudine del ristorante.
     */
    public double getLatitudine() { return latitudine; }
    /**
     * Imposta la latitudine del ristorante.
     *
     * @param latitudine La latitudine del ristorante.
     */
    public void setLatitudine(double latitudine) { this.latitudine = latitudine; }

    /**
     * Restituisce la longitudine del ristorante.
     *
     * @return La longitudine del ristorante.
     */
    public double getLongitudine() { return longitudine; }
    /**
     * Restituisce la longitudine del ristorante.
     *
     * @return La longitudine del ristorante.
     */
    public void setLongitudine(double longitudine) { this.longitudine = longitudine; }

    /**
     * Restituisce la fascia di prezzo del ristorante.
     *
     * @return La fascia di prezzo del ristorante.
     */
    public String getFasciaPrezzo() { return fasciaPrezzo; }
    /**
     * Imposta la fascia di prezzo del ristorante.
     *
     * @param fasciaPrezzo La fascia di prezzo del ristorante.
     */
    public void setFasciaPrezzo(String fasciaPrezzo) { this.fasciaPrezzo = fasciaPrezzo; }

    /**
     * Restituisce true se il ristorante offre servizio di asporto, false altrimenti.
     *
     * @return true se il ristorante offre asporto, false altrimenti.
     */
    public boolean isAsporto() { return asporto; }
    /**
     * Imposta se il ristorante offre servizio di asporto.
     *
     * @param asporto true se il ristorante offre asporto, false altrimenti.
     */
    public void setAsporto(boolean asporto) { this.asporto = asporto; }

    /**
     * Restituisce true se il ristorante accetta prenotazioni online, false altrimenti.
     *
     * @return true se il ristorante accetta prenotazioni online, false altrimenti.
     */
    public boolean isPrenotazioneOnline() { return prenotazioneOnline; }
    /**
     * Imposta se il ristorante accetta prenotazioni online.
     *
     * @param prenotazioneOnline true se il ristorante accetta prenotazioni online, false altrimenti.
     */
    public void setPrenotazioneOnline(boolean prenotazioneOnline) { this.prenotazioneOnline = prenotazioneOnline; }

    /**
     * Restituisce il tipo di cucina offerta dal ristorante.
     *
     * @return Il tipo di cucina del ristorante.
     */
    public String getTipoCucina() { return tipoCucina; }
    /**
     * Imposta il tipo di cucina offerta dal ristorante.
     *
     * @param tipoCucina Il tipo di cucina del ristorante.
     */
    public void setTipoCucina(String tipoCucina) { this.tipoCucina = tipoCucina; }

    /**
     * Restituisce una breve descrizione del ristorante.
     *
     * @return La descrizione del ristorante.
     */
    public String getDescrizione() { return descrizione; }
    /**
     * Imposta una breve descrizione del ristorante.
     *
     * @param descrizione La descrizione del ristorante.
     */
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    /**
     * Restituisce il nome del titolare del ristorante.
     *
     * @return Il nome del titolare del ristorante.
     */
    public String getTitolare() { return titolare; }
    /**
     * Imposta il nome del titolare del ristorante.
     *
     * @param titolare Il nome del titolare del ristorante.
     */
    public void setTitolare(String titolare) { this.titolare = titolare; }
}
