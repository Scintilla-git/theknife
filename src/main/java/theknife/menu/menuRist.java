package theknife.menu;

import theknife.model.Utente;
import theknife.model.Ristorante;
import theknife.model.Recensione;
import theknife.utils.UtilsCSV;

import java.util.*;
import java.io.*;

/**
 * La classe rappresenta il menu per i ristoratori.
 * I ristoratori possono creare, modificare e visualizzare i loro ristoranti,
 * oltre a gestire le recensioni ricevute.
 *
 * @author Davide Suigi 759430 CO
 * @version 1.0
 */
public class menuRist {
    /**
     * Mostra il menu per i ristoratori.<br>
     * Permette di gestire i ristoranti e le recensioni.<br>
     * 
     * @param scanner Lo scanner per leggere l'input dell'utente.
     * @param utente L'utente ristoratore che ha effettuato l'accesso.
     */
    public static void mostraMenu(Scanner scanner, Utente utente) {
        // Ciclo per il menu finchè l'utente non decide di uscire
        while (true) {
            System.out.println("\n--- Menu Ristoratore ---");
            System.out.println("1. Gestione ristoranti");
            System.out.println("2. Gestione recensioni");
            System.out.println("0. Logout");
            String scelta = scanner.nextLine();

            switch (scelta) {
                // CASO 1: Gestione ristoranti
                case "1":
                    boolean esciGestRist = false;
                    while (!esciGestRist) {
                        System.out.println("\n--- Gestione Ristoranti ---");
                        System.out.println("1. Crea nuovo ristorante");
                        System.out.println("2. Visualizza i tuoi ristoranti");
                        System.out.println("3. Cancella un tuo ristorante");
                        System.out.println("0. Torna indietro");
                        String sceltaGest = scanner.nextLine();
                        switch (sceltaGest) {
                            // CASO 1: Crea nuovo ristorante
                            case "1":
                                System.out.print("Nome: ");
                                String nome = scanner.nextLine();
                                System.out.print("Nazione: ");
                                String nazione = scanner.nextLine();
                                System.out.print("Città: ");
                                String citta = scanner.nextLine();
                                System.out.print("Indirizzo(senza n° civico): ");
                                String indirizzo = scanner.nextLine();
                                System.out.print("Latitudine: ");
                                double lat = Double.parseDouble(scanner.nextLine());
                                System.out.print("Longitudine: ");
                                double lon = Double.parseDouble(scanner.nextLine());
                                System.out.print("Fascia prezzo: ");
                                String fasciaPrezzo = scanner.nextLine();
                                System.out.println("Permette prenotazioni online? (Si/No)");
                                String prenotazioni = scanner.nextLine().equalsIgnoreCase("Si") ? "Si" : "No";
                                System.out.println("Permette asporto? (Si/No)");
                                String asporto = scanner.nextLine().equalsIgnoreCase("Si") ? "Si" : "No";
                                System.out.print("Tipo cucina: ");
                                String tipoCucina = scanner.nextLine();
                                System.out.print("Descrizione: ");
                                String descrizione = scanner.nextLine();
                                String descrizioneCSV = descrizione.replace(",", "&#44;");

                                // Salva nel CSV aggiungendo il ristoratore come titolare
                                try (FileWriter fw = new FileWriter("data/ristoranti.csv", true)) {
                                    fw.write(nome + "," + nazione + "," + citta + "," + indirizzo + "," + lat + "," + lon + "," + fasciaPrezzo + "," + prenotazioni + "," + asporto + "," + tipoCucina + "," + descrizioneCSV + "," + utente.getUsername() + "\n");
                                } catch (IOException e) {
                                    System.err.println("Errore scrittura ristorante: " + e.getMessage());
                                }
                                System.out.println("Ristorante creato!");
                                break;
                            // CASO 2: Visualizza i tuoi ristoranti
                            case "2":
                                // Carica i ristoranti dal CSV e filtra per il titolare
                                List<Ristorante> tutti = UtilsCSV.caricaRistoranti("data/ristoranti.csv");
                                List<Ristorante> miei = new ArrayList<>();
                                for (Ristorante r : tutti) {
                                    if (utente.getUsername().equalsIgnoreCase(r.getTitolare())) {
                                        miei.add(r);
                                    }
                                }
                                // Controlla se l'utente ha ristoranti registrati
                                if (miei.isEmpty()) {
                                    System.out.println("Non hai ristoranti registrati.");
                                }
                                // Altrimenti, mostra i ristoranti trovati
                                else {
                                    int idx = 1;
                                    for (Ristorante r : miei) {
                                        List<Recensione> recs = UtilsCSV.caricaRecensioniPerRistorante(r.getNome());
                                        double media = recs.stream().mapToInt(Recensione::getValutazione).average().orElse(0.0);
                                        System.out.println(idx++ + ") " + r.getNome() + " | Media valutazioni: " + (recs.isEmpty() ? "N/A" : String.format("%.2f", media)) + " | Numero recensioni: " + recs.size());
                                    }
                                }
                                break;
                            // CASO 3: Cancella un tuo ristorante
                            case "3":
                                // Carica i ristoranti dal CSV e filtra per il titolare
                                List<Ristorante> tuttiRist = UtilsCSV.caricaRistoranti("data/ristoranti.csv");
                                List<Ristorante> mieiRist = new ArrayList<>();
                                for (Ristorante r : tuttiRist) {
                                    if (utente.getUsername().equalsIgnoreCase(r.getTitolare())) {
                                        mieiRist.add(r);
                                    }
                                }
                                if (mieiRist.isEmpty()) {
                                    System.out.println("Non hai ristoranti da cancellare.");
                                    break;
                                }
                                for (int i = 0; i < mieiRist.size(); i++) {
                                    System.out.println((i + 1) + ") " + mieiRist.get(i).getNome());
                                }
                                System.out.print("Seleziona il numero del ristorante da cancellare (0 per annullare): ");
                                String inputCanc = scanner.nextLine();
                                int sceltaCanc;
                                try {
                                    sceltaCanc = Integer.parseInt(inputCanc);
                                } catch (NumberFormatException e) {
                                    System.out.println("Input non valido.");
                                    break;
                                }
                                if (sceltaCanc == 0) break;
                                if (sceltaCanc > 0 && sceltaCanc <= mieiRist.size()) {
                                    String nomeDaCancellare = mieiRist.get(sceltaCanc - 1).getNome();
                                    // Rimuovi dal CSV
                                    List<String> lines = new ArrayList<>();
                                    try (BufferedReader br = new BufferedReader(new FileReader("data/ristoranti.csv"))) {
                                        String line;
                                        while ((line = br.readLine()) != null) {
                                            if (!line.startsWith(nomeDaCancellare + ",")) {
                                                lines.add(line);
                                            }
                                        }
                                    } catch (IOException e) {
                                        System.err.println("Errore lettura ristoranti: " + e.getMessage());
                                    }
                                    try (PrintWriter pw = new PrintWriter(new FileWriter("data/ristoranti.csv"))) {
                                        for (String l : lines) pw.println(l);
                                    } catch (IOException e) {
                                        System.err.println("Errore scrittura ristoranti: " + e.getMessage());
                                    }
                                    System.out.println("Ristorante cancellato.");
                                } else {
                                    System.out.println("Scelta non valida.");
                                }
                                break;
                            // CASO 0: Torna indietro
                            case "0":
                                esciGestRist = true;
                                break;
                            // CASO DEFAULT: Scelta non valida
                            default:
                                System.out.println("Scelta non valida.");
                        }
                    }
                    break;
                // CASO 2: Gestione recensioni
                case "2":
                    // Visualizza recensioni dei propri ristoranti
                    List<Ristorante> tuttiRist2 = UtilsCSV.caricaRistoranti("data/ristoranti.csv");
                    List<Ristorante> mieiRist2 = new ArrayList<>();
                    // Filtra i ristoranti di proprietà dell'utente
                    for (Ristorante r : tuttiRist2) {
                        if (utente.getUsername().equalsIgnoreCase(r.getTitolare())) {
                            mieiRist2.add(r);
                        }
                    }
                    // Controlla se l'utente ha ristoranti registrati
                    List<Recensione> tutteRec = new ArrayList<>();
                    // Se non ha ristoranti, mostra un messaggio
                    for (Ristorante r : mieiRist2) {
                        tutteRec.addAll(UtilsCSV.caricaRecensioniPerRistorante(r.getNome()));
                    }
                    if (tutteRec.isEmpty()) {
                        System.out.println("Nessuna recensione ricevuta.");
                        break;
                    }
                    // Altrimenti, mostra le recensioni ricevute
                    for (int i = 0; i < tutteRec.size(); i++) {
                        Recensione rec = tutteRec.get(i);
                        System.out.println((i + 1) + ") " + rec.getRistoranteNome() + " | " + rec.getValutazione() + "/5: " + rec.getCommento() + " (da " + rec.getAutore().getUsername() + ")");
                    }
                    System.out.print("Seleziona una recensione per rispondere (0 per tornare indietro): ");
                    String inputRec = scanner.nextLine();
                    int sceltaRec;
                    try {
                        sceltaRec = Integer.parseInt(inputRec);
                    } catch (NumberFormatException e) {
                        System.out.println("Input non valido.");
                        break;
                    }
                    // Se l'utente sceglie di rispondere a una recensione
                    if (sceltaRec == 0) break;
                    if (sceltaRec > 0 && sceltaRec <= tutteRec.size()) {
                        Recensione recSelezionata = tutteRec.get(sceltaRec - 1);
                        // Controlla se esiste già una risposta
                        String rispostaEsistente = UtilsCSV.getRispostaRecensione(
                            recSelezionata.getRistoranteNome(),
                            utente.getUsername(),
                            recSelezionata.getAutore().getUsername()
                        );
                        // Se esiste, chiede se l'utente vuole sovrascriverla
                        if (rispostaEsistente != null) {
                            System.out.println("Risposta già presente: " + rispostaEsistente);
                            System.out.print("Vuoi sovrascrivere la risposta? (s/n): ");
                            String sovrascrivi = scanner.nextLine();
                            // Se l'utente non vuole sovrascrivere, torna al menu
                            if (!sovrascrivi.equalsIgnoreCase("s")) break;
                        }
                        System.out.print("Inserisci la tua risposta: ");
                        String risposta = scanner.nextLine();
                        UtilsCSV.salvaRispostaRecensione(
                            recSelezionata.getRistoranteNome(),
                            utente.getUsername(),
                            recSelezionata.getAutore().getUsername(),
                            risposta
                        );
                        System.out.println("Risposta salvata!");
                    } else {
                        System.out.println("Scelta non valida.");
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
