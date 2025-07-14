package theknife.menu;

import theknife.model.Utente;
import theknife.utils.UtilsCSV;
import theknife.utils.UtilsRicerca;
import theknife.model.Ristorante;
import theknife.model.Recensione;

import java.util.List;
import java.util.Scanner;

/**
 * La classe gestisce i menu per gli utenti che hanno effettuato l'accesso come clienti.<br>
 * Gli utenti clienti possono cercare ristoranti, inserire recensioni e valutazioni, e gestire una lista di 
 * ristoranti preferiti.
 *
 * @author Davide Suigi 759430 CO
 * @version 1.0
 */
public class menuCliente {
    /**
     * Mostra il menu principale per gli utenti clienti.<br>
     * Permette di cercare ristoranti, visualizzare preferiti, gestire recensioni e altro.
     * @param scanner Scanner per l'input dell'utente.
     * @param utente L'utente che ha effettuato l'accesso.
     */
    public static void mostraMenu(Scanner scanner, Utente utente) {
        while (true) {
            System.out.println("\n--- Menu Cliente ---");
            System.out.println("1. Cerca ristorante");
            System.out.println("2. Lista preferiti");
            System.out.println("3. Le tue recensioni");
            System.out.println("4. Ristoranti nella tua zona");
            System.out.println("0. Logout");
            String scelta = scanner.nextLine();

            switch (scelta) {
                // CASO 1: Cerca ristorante
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

                    // PATH del file CSV dei ristoranti
                    List<Ristorante> ristoranti = UtilsCSV.caricaRistoranti("data/ristoranti.csv");
                    // Filtra i ristoranti in base ai criteri inseriti
                    List<Ristorante> risFiltro = UtilsRicerca.filtraCombinato(ristoranti, nazione, citta, tipoCucina, fasciaPrezzo);

                    // Se non sono stati trovati ristoranti, informa l'utente
                    if (risFiltro.isEmpty()) {
                        System.out.println("Nessun ristorante trovato con i criteri specificati.");
                    }
                    // Altrimenti, mostra i ristoranti trovati
                    else {
                        int j = 0;
                        System.out.println("Ristoranti trovati:");
                        for (Ristorante r : risFiltro) {
                            j++;
                            System.out.println(j + ") " + r.getNome() + " - " + r.getCitta() + ", " + r.getNazione() + " - " + r.getTipoCucina() + " - Prezzo: " + r.getFasciaPrezzo());
                        }
                        System.out.print("Seleziona il numero di un ristorante per vedere i dettagli (0 per tornare indietro): ");
                        String input = scanner.nextLine();
                        // Scelto il ristorante, visualizza i dettagli
                        try {
                            int sceltaRist = Integer.parseInt(input);
                            if (sceltaRist > 0 && sceltaRist <= risFiltro.size()) {
                                Ristorante selezionato = risFiltro.get(sceltaRist - 1);
                                System.out.println("\n--- Dettagli Ristorante ---");
                                System.out.println("Nome: " + selezionato.getNome());
                                System.out.println("Descrizione: " + (selezionato.getDescrizione() != null ? selezionato.getDescrizione() : "Nessuna descrizione"));
                                System.out.println("Prezzo: " + selezionato.getFasciaPrezzo());
                                System.out.println("Titolare: " + selezionato.getTitolare());

                                boolean esciMenuRistorante = false;
                                while (!esciMenuRistorante) {
                                    System.out.println("\n--- Menu Ristorante ---");
                                    System.out.println("1. Visualizza recensioni");
                                    System.out.println("2. Crea recensione");
                                    System.out.println("3. Modifica recensione");
                                    System.out.println("4. Cancella recensione");
                                    System.out.println("5. Aggiungi ai preferiti");
                                    System.out.println("0. Torna indietro");
                                    String sceltaRistMenu = scanner.nextLine();

                                    // Carica recensioni e preferiti
                                    List<Recensione> recensioni = UtilsCSV.caricaRecensioniPerRistorante(selezionato.getNome());
                                    Recensione miaRecensione = recensioni.stream()
                                        .filter(r -> r.getAutore().getUsername().equals(utente.getUsername()))
                                        .findFirst().orElse(null);

                                    switch (sceltaRistMenu) {
                                        // CASO 1: Visualizza recensioni
                                        case "1":
                                            if (recensioni.isEmpty()) {
                                                System.out.println("Nessuna recensione presente.");
                                            } else {
                                                System.out.println("--- Recensioni ---");
                                                for (Recensione rec : recensioni) {
                                                    System.out.println(rec.getAutore().getUsername() + " (" + rec.getValutazione() + "/5): " + rec.getCommento());
                                                    String risposta = UtilsCSV.getRispostaRecensione(
                                                        selezionato.getNome(),
                                                        selezionato.getTitolare(),
                                                        rec.getAutore().getUsername()
                                                    );
                                                    if (risposta != null) {
                                                        System.out.println("  Risposta del ristoratore: " + risposta);
                                                    }
                                                }
                                            }
                                            break;
                                        // CASO 2: Crea recensione
                                        case "2":
                                            if (miaRecensione != null) {
                                                System.out.println("Hai già recensito questo ristorante. Modifica o cancella la tua recensione.");
                                            } else {
                                                System.out.print("Inserisci valutazione (1-5): ");
                                                int voto = Integer.parseInt(scanner.nextLine());
                                                System.out.print("Inserisci commento: ");
                                                String commento = scanner.nextLine();
                                                Recensione nuova = new Recensione(voto, commento, utente, selezionato);
                                                UtilsCSV.salvaRecensione(nuova); // Implementa questo metodo
                                                System.out.println("Recensione aggiunta!");
                                            }
                                            break;
                                        // CASO 3: Modifica recensione
                                        case "3":
                                            if (miaRecensione == null) {
                                                System.out.println("Non hai ancora recensito questo ristorante.");
                                            } else {
                                                System.out.print("Nuova valutazione (1-5): ");
                                                int nuovoVoto = Integer.parseInt(scanner.nextLine());
                                                System.out.print("Nuovo commento: ");
                                                String nuovoCommento = scanner.nextLine();
                                                miaRecensione.setValutazione(nuovoVoto);
                                                miaRecensione.setCommento(nuovoCommento);
                                                UtilsCSV.aggiornaRecensione(miaRecensione); // Implementa questo metodo
                                                System.out.println("Recensione aggiornata!");
                                            }
                                            break;
                                        // CASO 4: Cancella recensione
                                        case "4":
                                            if (miaRecensione == null) {
                                                System.out.println("Non hai ancora recensito questo ristorante.");
                                            } else {
                                                UtilsCSV.cancellaRecensione(miaRecensione); // Implementa questo metodo
                                                System.out.println("Recensione cancellata!");
                                            }
                                            break;
                                        // CASO 5: Aggiungi ai preferiti
                                        case "5":
                                            UtilsCSV.aggiungiPreferito(utente, selezionato); // Implementa questo metodo
                                            System.out.println("Ristorante aggiunto ai preferiti!");
                                            break;
                                        // CASO 0: Torna indietro
                                        case "0":
                                            esciMenuRistorante = true; // Esce solo dal menu ristorante
                                            break;
                                        // CASO DEFAULT: Scelta non valida
                                        default:
                                            System.out.println("Scelta non valida.");
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

                // CASO 2: Lista preferiti
                case "2":
                    // Carica i ristoranti preferiti dell'utente
                    System.out.println("=== Lista dei preferiti ===");
                    List<String> preferiti = UtilsCSV.caricaPreferiti(utente.getUsername());
                    // Se non ci sono preferiti, informa l'utente
                    if (preferiti.isEmpty()) {
                        System.out.println("Nessun ristorante nei preferiti.");
                    }
                    // Altrimenti, mostra i preferiti
                    else {
                        boolean esciPreferiti = false;
                        while (!esciPreferiti) {
                            System.out.println("I tuoi ristoranti preferiti:");
                            for (int k = 0; k < preferiti.size(); k++) {
                                System.out.println((k + 1) + ") " + preferiti.get(k));
                            }
                            System.out.println("0. Torna al menu utente");
                            // Chiede all'utente di selezionare un ristorante preferito
                            System.out.print("Seleziona un ristorante per gestirlo: ");
                            String input = scanner.nextLine();
                            int sceltaPref;
                            try {
                                sceltaPref = Integer.parseInt(input);
                            } catch (NumberFormatException e) {
                                System.out.println("Input non valido.");
                                continue;
                            }
                            if (sceltaPref == 0) {
                                esciPreferiti = true;
                            } else if (sceltaPref > 0 && sceltaPref <= preferiti.size()) {
                                String nomeRist = preferiti.get(sceltaPref - 1);
                                // Carica il ristorante selezionato
                                Ristorante selezionato = UtilsCSV.caricaRistoranti("data/ristoranti.csv")
                                        .stream().filter(r -> r.getNome().equals(nomeRist)).findFirst().orElse(null);
                                if (selezionato == null) {
                                    System.out.println("Ristorante non trovato.");
                                    continue;
                                }
                                boolean esciMenuPrefRist = false;
                                while (!esciMenuPrefRist) {
                                    // Carica recensioni per questo ristorante
                                    List<Recensione> recensioni = UtilsCSV.caricaRecensioniPerRistorante(selezionato.getNome());
                                    Recensione miaRecensione = recensioni.stream()
                                        .filter(r -> r.getAutore().getUsername().equals(utente.getUsername()))
                                        .findFirst().orElse(null);

                                    System.out.println("\n--- Gestione preferito: " + selezionato.getNome() + " ---");
                                    System.out.println("1. Crea recensione");
                                    System.out.println("2. Modifica recensione");
                                    System.out.println("3. Cancella recensione");
                                    System.out.println("4. Rimuovi dai preferiti");
                                    System.out.println("0. Torna alla lista preferiti");
                                    String sceltaGestPref = scanner.nextLine();

                                    // Gestione delle scelte dell'utente
                                    switch (sceltaGestPref) {
                                        // CASO 1: Crea recensione
                                        case "1":
                                            if (miaRecensione != null) {
                                                System.out.println("Hai già recensito questo ristorante. Modifica o cancella la tua recensione.");
                                            } else {
                                                System.out.print("Inserisci valutazione (1-5): ");
                                                int voto = Integer.parseInt(scanner.nextLine());
                                                System.out.print("Inserisci commento: ");
                                                String commento = scanner.nextLine();
                                                Recensione nuova = new Recensione(voto, commento, utente, selezionato);
                                                UtilsCSV.salvaRecensione(nuova);
                                                System.out.println("Recensione aggiunta!");
                                            }
                                            break;
                                        // CASO 2: Modifica recensione
                                        case "2":
                                            if (miaRecensione == null) {
                                                System.out.println("Non hai ancora recensito questo ristorante.");
                                            } else {
                                                System.out.print("Nuova valutazione (1-5): ");
                                                int nuovoVoto = Integer.parseInt(scanner.nextLine());
                                                System.out.print("Nuovo commento: ");
                                                String nuovoCommento = scanner.nextLine();
                                                miaRecensione.setValutazione(nuovoVoto);
                                                miaRecensione.setCommento(nuovoCommento);
                                                UtilsCSV.aggiornaRecensione(miaRecensione);
                                                System.out.println("Recensione aggiornata!");
                                            }
                                            break;
                                        // CASO 3: Cancella recensione
                                        case "3":
                                            if (miaRecensione == null) {
                                                System.out.println("Non hai ancora recensito questo ristorante.");
                                            } else {
                                                UtilsCSV.cancellaRecensione(miaRecensione);
                                                System.out.println("Recensione cancellata!");
                                            }
                                            break;
                                        // CASO 4: Rimuovi dai preferiti
                                        case "4":
                                            UtilsCSV.rimuoviPreferito(utente, selezionato);
                                            System.out.println("Ristorante rimosso dai preferiti!");
                                            // Aggiorna la lista preferiti dopo la rimozione
                                            preferiti = UtilsCSV.caricaPreferiti(utente.getUsername());
                                            esciMenuPrefRist = true;
                                            break;
                                        // CASO 0: Torna alla lista preferiti
                                        case "0":
                                            esciMenuPrefRist = true;
                                            break;
                                        // CASO DEFAULT: Scelta non valida
                                        default:
                                            System.out.println("Scelta non valida.");
                                    }
                                }
                            } else {
                                System.out.println("Scelta non valida.");
                            }
                        }
                    }
                    break;

                // CASO 3: Le tue recensioni
                case "3":
                    // Carica tutte le recensioni e filtra per l'utente corrente
                    List<Recensione> tutteRecensioni = UtilsCSV.caricaTutteRecensioni();
                    boolean trovato = false;
                    System.out.println("=== Le tue recensioni: ===");
                    for (Recensione rec : tutteRecensioni) {
                        if (rec.getAutore().getUsername().equals(utente.getUsername())) {
                            trovato = true;
                            System.out.println("- " + rec.getRistoranteNome() + " (" + rec.getValutazione() + "/5): " + rec.getCommento());
                        }
                    }
                    if (!trovato) {
                        System.out.println("Non hai ancora scritto recensioni.");
                    }
                    break;
                
                // CASO 4: Ristoranti nella tua zona
                case "4":
                    // Controlla se l'utente ha impostato un domicilio e lo carica come attrbiuto
                    String domicilio = utente.getLuogoDomicilio();
                    if (domicilio == null || domicilio.isBlank()) {
                        System.out.println("Non hai un domicilio impostato.");
                        break;
                    }
                    // Carica i ristoranti nella zona del domicilio dell'utente
                    List<Ristorante> tuttiRistorantiZona = UtilsCSV.caricaRistoranti("data/ristoranti.csv");
                    List<Ristorante> filtratiZona = UtilsRicerca.filtraCombinato(tuttiRistorantiZona, null, domicilio, null, null);

                    // Se non sono stati trovati ristoranti, informa l'utente
                    if (filtratiZona.isEmpty()) {
                        System.out.println("Nessun ristorante trovato nella tua zona (" + domicilio + ").");
                    }
                    // Altrimenti, mostra i ristoranti trovati
                    else {
                        int i = 1;
                        System.out.println("Ristoranti nella tua zona (" + domicilio + "):");
                        for (Ristorante r : filtratiZona) {
                            System.out.println(i++ + ") " + r.getNome() + " - " + r.getTipoCucina() + " - Prezzo: " + r.getFasciaPrezzo());
                        }
                        System.out.print("Seleziona il numero di un ristorante per vedere i dettagli (0 per tornare indietro): ");
                        String inputZona = scanner.nextLine();
                        // Scelto il ristorante, visualizza i dettagli
                        try {
                            int sceltaZona = Integer.parseInt(inputZona);
                            if (sceltaZona > 0 && sceltaZona <= filtratiZona.size()) {
                                Ristorante selezionato = filtratiZona.get(sceltaZona - 1);
                                System.out.println("\n--- Dettagli Ristorante ---");
                                System.out.println("Nome: " + selezionato.getNome());
                                System.out.println("Descrizione: " + (selezionato.getDescrizione() != null ? selezionato.getDescrizione() : "Nessuna descrizione"));
                                System.out.println("Prezzo: " + selezionato.getFasciaPrezzo());

                                // Variabile di controllo
                                boolean esciMenuRistorante = false;
                                // Chiedi all'utente le seguenti opzioni
                                while (!esciMenuRistorante) {
                                    System.out.println("\n--- Menu Ristorante ---");
                                    System.out.println("1. Visualizza recensioni");
                                    System.out.println("2. Crea recensione");
                                    System.out.println("3. Modifica recensione");
                                    System.out.println("4. Cancella recensione");
                                    System.out.println("5. Aggiungi ai preferiti");
                                    System.out.println("0. Torna indietro");
                                    String sceltaRistMenu = scanner.nextLine();

                                    // Carica recensioni
                                    List<Recensione> recensioni = UtilsCSV.caricaRecensioniPerRistorante(selezionato.getNome());
                                    Recensione miaRecensione = recensioni.stream()
                                        .filter(r -> r.getAutore().getUsername().equals(utente.getUsername()))
                                        .findFirst().orElse(null);

                                    switch (sceltaRistMenu) {
                                        // Gestione delle scelte dell'utente
                                        // CASO 1: Visualizza recensioni
                                        case "1":
                                            if (recensioni.isEmpty()) {
                                                System.out.println("Nessuna recensione presente.");
                                            } else {
                                                System.out.println("--- Recensioni ---");
                                                for (Recensione rec : recensioni) {
                                                    System.out.println(rec.getAutore().getUsername() + " (" + rec.getValutazione() + "/5): " + rec.getCommento());
                                                    String risposta = UtilsCSV.getRispostaRecensione(
                                                        selezionato.getNome(),
                                                        selezionato.getTitolare(),
                                                        rec.getAutore().getUsername()
                                                    );
                                                    if (risposta != null) {
                                                        System.out.println("  Risposta del ristoratore: " + risposta);
                                                    }
                                                }
                                            }
                                            break;
                                        // CASO 2: Crea recensione
                                        case "2":
                                            if (miaRecensione != null) {
                                                System.out.println("Hai già recensito questo ristorante. Modifica o cancella la tua recensione.");
                                            } else {
                                                System.out.print("Inserisci valutazione (1-5): ");
                                                int voto = Integer.parseInt(scanner.nextLine());
                                                System.out.print("Inserisci commento: ");
                                                String commento = scanner.nextLine();
                                                Recensione nuova = new Recensione(voto, commento, utente, selezionato);
                                                UtilsCSV.salvaRecensione(nuova);
                                                System.out.println("Recensione aggiunta!");
                                            }
                                            break;
                                        // CASO 3: Modifica recensione
                                        case "3":
                                            if (miaRecensione == null) {
                                                System.out.println("Non hai ancora recensito questo ristorante.");
                                            } else {
                                                System.out.print("Nuova valutazione (1-5): ");
                                                int nuovoVoto = Integer.parseInt(scanner.nextLine());
                                                System.out.print("Nuovo commento: ");
                                                String nuovoCommento = scanner.nextLine();
                                                miaRecensione.setValutazione(nuovoVoto);
                                                miaRecensione.setCommento(nuovoCommento);
                                                UtilsCSV.aggiornaRecensione(miaRecensione); 
                                                System.out.println("Recensione aggiornata!");
                                            }
                                            break;
                                        // CASO 4: Cancella recensione
                                        case "4":
                                            if (miaRecensione == null) {
                                                System.out.println("Non hai ancora recensito questo ristorante.");
                                            } else {
                                                UtilsCSV.cancellaRecensione(miaRecensione); 
                                                System.out.println("Recensione cancellata!");
                                            }
                                            break;
                                        // CASO 5: Aggiungi ai preferiti
                                        case "5":
                                            UtilsCSV.aggiungiPreferito(utente, selezionato); 
                                            System.out.println("Ristorante aggiunto ai preferiti!");
                                            break;
                                        // CASO 0: Torna indietro
                                        case "0":
                                            esciMenuRistorante = true; // Esce solo dal menu ristorante
                                            break;
                                        // CASO DEFAULT: Scelta non valida
                                        default:
                                            System.out.println("Scelta non valida.");
                                    }
                                }
                            } else if (sceltaZona == 0) {
                                // Torna al menu precedente
                            } else {
                                System.out.println("Scelta non valida.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Input non valido.");
                        }
                    }
                    break;

                // CASO 0: Logout
                case "0":
                    System.out.println("Logout effettuato.");
                    return;

                // CASO DEFAULT: Scelta non valida
                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }
}
