package theknife.menu;

import theknife.model.Utente;
import theknife.model.Utente.Ruolo;
import theknife.utils.LoginManager;

import java.util.List;
import java.util.Scanner;

/**
 * La classe menuLogin gestisce le operazioni di login e registrazione degli utenti.
 * La funzione <code>login()</code> controlla le credenziali con l'elenco nel file utenti.csv.
 * La funzione <code>registrazione()</code> crea un nuovo utente nel file utenti.csv, controllando che non ne esista già uno con lo stesso username.
 *
 * @author Davide Suigi 759430 CO
 * @version 1.0
 */
public class menuLogin {
    /**
     * Gestisce il login dell'utente.<br>
     * Chiede le credenziali e verifica se l'utente esiste nel sistema.<br>
     * 
     * @param utenti La lista degli utenti registrati.
     * @param scanner Lo scanner per leggere l'input dell'utente.
     * @return L'utente autenticato o null se le credenziali sono errate.
     */
    public static Utente login(List<Utente> utenti, Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Utente u = LoginManager.login(utenti, username, password);
        if (u != null) {
            System.out.println("Login effettuato come " + u.getRuolo());
            return u;
        } else {
            System.out.println("Username o password errati.");
            return null;
        }
    }

    /**
     * Gestisce la registrazione di un nuovo utente.<br>
     * Chiede i dati dell'utente e lo aggiunge alla lista degli utenti se lo username non esiste già.<br>
     * 
     * @param utenti La lista degli utenti registrati.
     * @param scanner Lo scanner per leggere l'input dell'utente.
     */
    public static void registrazione(List<Utente> utenti, Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Cognome: ");
        String cognome = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        if (LoginManager.usernameEsiste(utenti, username)) {
            System.out.println("Username già esistente.");
            return;
        }
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Luogo di domicilio: ");
        String luogo = scanner.nextLine();
        System.out.print("Ruolo (1=Cliente, 2=Ristoratore): ");
        int ruoloScelto = Integer.parseInt(scanner.nextLine());
        Ruolo ruolo = ruoloScelto == 2 ? Ruolo.RISTORATORE : Ruolo.CLIENTE;

        Utente nuovo = new Utente(nome, cognome, username, LoginManager.hashPassword(password), null, luogo, ruolo);
        utenti.add(nuovo);
        System.out.println("Registrazione completata!");
    }
}
