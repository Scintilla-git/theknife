package theknife.utils;

import theknife.model.Utente;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Gestisce le operazioni di login e registrazione degli utenti.
 * Fornisce metodi per cifrare le password e verificare le credenziali.
 *
 * @author Davide Suigi 759430 CO
 * @version 1.0
 */
public class LoginManager {

    /**
     * Cifra la password utilizzando l'algoritmo SHA-256.
     * @param password La password da cifrare.
     * @return La password cifrata in formato esadecimale.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo di hash non trovato", e);
        }
    }

    /**
     * Verifica le credenziali dell'utente e restituisce l'oggetto Utente se il login ha successo.
     * @param utenti La lista degli utenti registrati.
     * @param username Lo username dell'utente.
     * @param password La password dell'utente.
     * @return L'oggetto Utente se le credenziali sono corrette, altrimenti null.
     */
    public static Utente login(List<Utente> utenti, String username, String password) {
        String hashed = hashPassword(password);
        for (Utente u : utenti) {
            if (u.getUsername().equals(username) && u.getPasswordCifrata().equals(hashed)) {
                return u;
            }
        }
        return null;
    }

    /** 
     * Verifica se un username esiste già nella lista degli utenti.
     * @param utenti La lista degli utenti registrati.
     * @param username Lo username da verificare.
     * @return true se lo username esiste, false altrimenti.
     */
    public static boolean usernameEsiste(List<Utente> utenti, String username) {
        return utenti.stream().anyMatch(u -> u.getUsername().equals(username));
    }
}