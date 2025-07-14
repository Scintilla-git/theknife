package theknife.model;

import java.time.LocalDate;
/**
 * La classe Utente rappresenta un utente del sistema.
 * Un utente può essere un ristoratore o un cliente.
 * Gli utenti "clienti" possono cercare ristoranti, inserire recensioni e valutazione, e gestire una lista di 
 * ristoranti preferiti.
 * Gli utenti "ristoratori" possono creare ristoranti, modificare le informazioni, 
 * commentare le recensioni ricevute e visualizzare una media delle valutazioni.
 *
 * @author Davide Suigi 759430 CO
 * @version 1.0
 */
public class Utente {
    private String nome;
    private String cognome;
    private String username;
    private String passwordCifrata;
    private LocalDate dataNascita;
    private String luogoDomicilio;
    private Ruolo ruolo;

    /**
     * enum Ruolo rappresenta i possibili ruoli di un utente nel sistema.
     * Un utente può essere un CLIENTE o un RISTORATORE.
     * 
     * CLIENTE: Un utente che può cercare ristoranti, creare,modificare e cancellare proprie recensioni e gestire preferiti.
     * RISTORATORE: Un utente che può creare ristoranti, visualizzarne un riepilogo e rispondere alle recensioni.
     */
    public enum Ruolo {
        CLIENTE,
        RISTORATORE
    }

    /**
     * Costruttore vuoto della classe Utente.
     */
    public Utente() {}

    /**
     * Costruttore della classe Utente con i seguenti parametri.
     *
     * @param nome          Il nome dell'utente.
     * @param cognome       Il cognome dell'utente.
     * @param username      Lo username dell'utente.
     * @param passwordCifrata      La password dell'utente.
     * @param dataNascita   La data di nascita dell'utente.(può essere null)
     * @param luogoDomicilio Il luogo di domicilio dell'utente.
     * @param ruolo         Il ruolo dell'utente (CLIENTE o RISTORATORE).
     */

     public Utente(String nome, String cognome, String username, String passwordCifrata,
                   LocalDate dataNascita, String luogoDomicilio, Ruolo ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.passwordCifrata = passwordCifrata;
        this.dataNascita = dataNascita;
        this.luogoDomicilio = luogoDomicilio;
        this.ruolo = ruolo;
    }

    // Getter e Setter dei parametri sopra definiti
    /**
     * Restituisce il nome dell'utente.
     *
     * @return Il nome dell'utente.
     */
    public String getNome() { return nome; }
    /**
     * Imposta il nome dell'utente.
     *
     * @param nome Il nome da impostare.
     */
    public void setNome(String nome) { this.nome = nome; }

    /**
     * Restituisce il cognome dell'utente.
     *
     * @return Il cognome dell'utente.
     */
    public String getCognome() { return cognome; }
    /**
     * Imposta il cognome dell'utente.
     *
     * @param cognome Il cognome da impostare.
     */
    public void setCognome(String cognome) { this.cognome = cognome; }

    /**
     * Restituisce lo username dell'utente.
     *
     * @return Lo username dell'utente.
     */
    public String getUsername() { return username; }
    /**
     * Imposta lo username dell'utente.
     *
     * @param username Lo username dell'utente.
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Restituisce la password cifrata dell'utente.
     *
     * @return La password cifrata dell'utente.
     */
    public String getPasswordCifrata() { return passwordCifrata; }
    /**
     * Imposta la password cifrata dell'utente.
     *
     * @param passwordCifrata La password cifrata da impostare.
     */
    public void setPasswordCifrata(String passwordCifrata) { this.passwordCifrata = passwordCifrata; }

    /**
     * Restituisce la data di nascita dell'utente.
     *
     * @return La data di nascita dell'utente.
     */
    public LocalDate getDataNascita() { return dataNascita; }
    /**
     * Imposta la data di nascita dell'utente.
     *
     * @param dataNascita La data di nascita da impostare.
     */
    public void setDataNascita(LocalDate dataNascita) { this.dataNascita = dataNascita; }

    /**
     * Restituisce il luogo di domicilio dell'utente.
     *
     * @return Il luogo di domicilio dell'utente.
     */
    public String getLuogoDomicilio() { return luogoDomicilio; }
    /**
     * Imposta il luogo di domicilio dell'utente.
     *
     * @param luogoDomicilio Il luogo di domicilio da impostare.
     */
    public void setLuogoDomicilio(String luogoDomicilio) { this.luogoDomicilio = luogoDomicilio; }
    
    /**
     * Restituisce il ruolo dell'utente.
     *
     * @return Il ruolo dell'utente (CLIENTE o RISTORATORE).
     */
    public Ruolo getRuolo() { return ruolo; }
    /**
     * Imposta il ruolo dell'utente.
     *
     * @param ruolo Il ruolo da impostare (CLIENTE o RISTORATORE).
     */
    public void setRuolo(Ruolo ruolo) { this.ruolo = ruolo; }
}
