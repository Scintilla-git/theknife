package theknife.menu;

import theknife.model.Ristorante;
import theknife.model.Recensione;
import theknife.utils.UtilsCSV;
import theknife.utils.UtilsRicerca;

import java.util.List;
import java.util.Scanner;

/**
 * La classe rappresenta il menu per gli utenti guest, ovvero chi non ha effettuato l'accesso.<br>
 * L'utente guest ha può eseguire solo la ricerca di ristoranti.<p>
 * 
 * - Gli utenti guest possono cercare ristoranti in base a vari criteri come nazione, città, tipo di cucina e fascia di prezzo.<br>
 * - Possono visualizzare i dettagli dei ristoranti trovati, comprese le recensioni.<br>
 * - Non hanno accesso a funzionalità come l'inserimento di recensioni o la gestione dei preferiti.
 *
 * @author Davide Suigi 759430 CO
 * @version 1.0
 */
public class menuGuest {
    /**
     * Mostra il menu per gli utenti guest.<br>
     * Permette di cercare ristoranti e visualizzare i dettagli dei ristoranti trovati.<br>
     * 
     * @param scanner Lo scanner per leggere l'input dell'utente.
     */
    public static void mostraMenu(Scanner scanner) {
        // Ciclo per il menu finchè l'utente non decide di uscire
        while (true) {
            System.out.println("\n--- Benvenuto Guest! ---");
            System.out.println("1. Cerca ristoranti");
            System.out.println("2. Visualizza ristoranti in una zona");
            System.out.println("0. Torna al menu principale");
            String scelta = scanner.nextLine();

            switch (scelta) {
                // CASO 1: Cerca ristoranti
                case "1":
                    System.out.println("=== Ricerca ristorante ===");
                    System.out.print("Nazione (invio per ignorare): ");
                    String nazione = scanner.nextLine();
                    if (nazione.isBlank()) nazione = null;
                    System.out.print("Città (invio per ignorare): ");
                    String citta = scanner.nextLine();
                    if (citta.isBlank()) citta = null;
                    System.out.print("Tipo cucina (invio per ignorare): ");
                    String tipoCucina = scanner.nextLine();
                    if (tipoCucina.isBlank()) tipoCucina = null;
                    System.out.print("Fascia prezzo (invio per ignorare): ");
                    String fasciaPrezzo = scanner.nextLine();
                    if (fasciaPrezzo.isBlank()) fasciaPrezzo = null;

                    // Carica i ristoranti dal CSV e filtro in base ai criteri
                    List<Ristorante> ristoranti = UtilsCSV.caricaRistoranti("data/ristoranti.csv");
                    List<Ristorante> risFiltro = UtilsRicerca.filtraCombinato(ristoranti, nazione, citta, tipoCucina, fasciaPrezzo);

                    if (risFiltro.isEmpty()) {
                        System.out.println("Nessun ristorante trovato con i criteri specificati.");
                    } else {
                        int j = 0;
                        System.out.println("Ristoranti trovati:");
                        for (Ristorante r : risFiltro) {
                            j++;
                            System.out.println(j + ") " + r.getNome() + " - " + r.getCitta() + ", " + r.getNazione() + " - " + r.getTipoCucina() + " - Prezzo: " + r.getFasciaPrezzo());
                        }
                        System.out.print("Seleziona il numero di un ristorante per vedere i dettagli (0 per tornare indietro): ");
                        String input = scanner.nextLine();
                        try {
                            int sceltaRist = Integer.parseInt(input);
                            if (sceltaRist > 0 && sceltaRist <= risFiltro.size()) {
                                Ristorante selezionato = risFiltro.get(sceltaRist - 1);
                                System.out.println("\n--- Dettagli Ristorante ---");
                                System.out.println("Nome: " + selezionato.getNome());
                                System.out.println("Descrizione: " + (selezionato.getDescrizione() != null ? selezionato.getDescrizione() : "Nessuna descrizione"));
                                System.out.println("Prezzo: " + selezionato.getFasciaPrezzo());

                                // Visualizza recensioni senza autore
                                List<Recensione> recensioni = UtilsCSV.caricaRecensioniPerRistorante(selezionato.getNome());
                                if (recensioni.isEmpty()) {
                                    System.out.println("Nessuna recensione presente.");
                                } else {
                                    System.out.println("--- Recensioni ---");
                                    for (Recensione rec : recensioni) {
                                        System.out.println("- (" + rec.getValutazione() + "/5): " + rec.getCommento());
                                    }
                                }
                            } else if (sceltaRist == 0) {
                                // Torna al menu precedente
                            } else {
                                System.out.println("Scelta non valida.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Input non valido.");
                        }
                    }
                    break;

                // CASO 2: Visualizza ristoranti in una zona
                case "2":
                    System.out.print("Inserisci la città/zona di interesse: ");
                    String zona = scanner.nextLine();
                    if (zona.isBlank()) {
                        System.out.println("Zona non valida.");
                        break;
                    }
                    // Carica i ristoranti dal CSV e filtro in base alla zona
                    List<Ristorante> tuttiRistoranti = UtilsCSV.caricaRistoranti("data/ristoranti.csv");
                    List<Ristorante> filtratiZona = UtilsRicerca.filtraCombinato(tuttiRistoranti, null, zona, null, null);

                    // Controlla se sono stati trovati ristoranti nella zona
                    if (filtratiZona.isEmpty()) {
                        System.out.println("Nessun ristorante trovato in questa zona.");
                    }
                    // Altrimenti, mostra i ristoranti trovati
                    else {
                        System.out.println("Ristoranti nella zona '" + zona + "':");
                        int i = 1;
                        for (Ristorante r : filtratiZona) {
                            System.out.println(i++ + ") " + r.getNome() + " - " + r.getTipoCucina() + " - Prezzo: " + r.getFasciaPrezzo());
                        }
                    }
                    break;

                // CASO 0: Torna al menu principale
                case "0":
                    System.out.println("Tornando al menu principale...");
                    return;
                // CASO DEFAULT: Scelta non valida
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
    }
}
