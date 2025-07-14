package theknife;

import theknife.model.Utente;
import theknife.model.Utente.Ruolo;
import theknife.utils.LoginManager;
import theknife.utils.UtilsCSV;
import theknife.menu.menuCliente;
import theknife.menu.menuGuest;
import theknife.menu.menuRist;

import java.util.List;
import java.util.Scanner;

/**
 * Il programma "The Knife" è un'applicazione Java che simula la gestione di un ristorante.<br>
 * Permette ai ristoratori di creare i ristoranti, modifcarne le informazioni e visualizzare e rispondere alle recensioni di essi.<br>
 * Gli utenti possono cercare i ristoranti in base alle caratteristiche desiderate e inserire, modificare e cancellare le recensioni.<br>
 * Inoltre, possono aggiungere o rimuovere i ristoranti dai preferiti.
 * 
 * @author Davide Suigi 759430 CO
 * @version 1.0
 */
public class TheKnife {
    public static void main(String[] args) {
        // Percorso del file CSV degli utenti
        String pathUtenti = "data/utenti.csv";
        // Carica nella lista gli utenti del file CSV
        List<Utente> utenti = UtilsCSV.caricaUtenti(pathUtenti);
        Scanner scanner = new Scanner(System.in);

        while (true)
        {
            System.out.println("\n=== Benvenuto in The Knife! ===");
            System.out.println("Scegli un'opzione:");
            System.out.println("1. Login");
            System.out.println("2. Registrazione");
            System.out.println("3. Accesso come guest");
            System.out.println("0. Chiudi il programma");
            System.out.print("Scelta: ");
            // La scelta dell'utente
            String scelta = scanner.nextLine();

            switch (scelta) {
                // CASO 1: Login
                case "1":
                    //Username e password dell'utente
                    System.out.print("\nUsername: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    // Verifica delle credenziali
                    Utente u = LoginManager.login(utenti, username, password);
                    if (u != null) {
                        System.out.println("Login effettuato come " + u.getRuolo());
                        System.out.println("Benvenuto " + u.getNome() + " " + u.getCognome() + "!\n");
                        System.out.println("Accesso effettuato come "+ u.getRuolo() + ".");
                        if (u.getRuolo() == Ruolo.CLIENTE){
                            menuCliente.mostraMenu(scanner, u);
                        } else if (u.getRuolo() == Ruolo.RISTORATORE){
                            menuRist.mostraMenu(scanner, u);
                        }
                    } else {
                        System.out.println("Username o password errati.");
                    }
                    break;

                // CASO 2: Registrazione
                case "2":
                    //Inserimento dei dati dell'utente
                    System.out.print("\nNome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Cognome: ");
                    String cognome = scanner.nextLine();
                    System.out.print("Username: ");
                    String newUsername = scanner.nextLine();
                    // Controllo se lo username esiste già
                    if (LoginManager.usernameEsiste(utenti, newUsername)) {
                        System.out.println("Username già esistente.");
                        break;
                    }
                    System.out.print("Password: ");
                    String newPassword = scanner.nextLine();
                    System.out.print("Luogo di domicilio: ");
                    String luogo = scanner.nextLine();
                    System.out.print("Ruolo (1=Cliente, 2=Ristoratore): ");
                    int ruoloScelto = Integer.parseInt(scanner.nextLine());
                    Ruolo ruolo = ruoloScelto == 2 ? Ruolo.RISTORATORE : Ruolo.CLIENTE;

                    // Salcataggio nel file CSV indicato a inizio file
                    Utente nuovo = new Utente(nome, cognome, newUsername, LoginManager.hashPassword(newPassword), null, luogo, ruolo);
                    utenti.add(nuovo);
                    UtilsCSV.salvaUtente(pathUtenti, utenti);
                    System.out.println("Registrazione completata!");
                    break;

                // CASO 3: Accesso come guest
                case "3":
                    System.out.println("Accesso come guest. Funzionalità limitate.");
                    menuGuest.mostraMenu(scanner);
                    break;
                
                // CASO 0: Chiudi il programma
                case "0":
                    System.out.println("Chiudi il programma.");
                    scanner.close();
                    return;
                
                // CASO DEFAULT: Scelta non valida
                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }
}
