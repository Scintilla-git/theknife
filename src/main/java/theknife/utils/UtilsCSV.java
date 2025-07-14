package theknife.utils;

import theknife.model.Recensione;
import theknife.model.Utente;
import theknife.model.Utente.Ruolo;
import theknife.model.Ristorante;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Utils per la gestione dei file CSV dove necessario.<p>
 * 
 * Le funzioni principali includono:<br>
 * - Caricamento e salvataggio di utenti<br>
 * - Caricamento di ristoranti<br>
 * - Gestione delle recensioni (caricamento, salvataggio, aggiornamento, cancellazione)<br>
 * - Gestione dei preferiti degli utenti<br>
 * - Risposte alle recensioni da parte dei ristoratori
 *
 * @author Davide Suigi 759430 CO
 * @version 1.0
 */
public class UtilsCSV {
    /**
     * Carica gli utenti da un file CSV.
     * @param path Il percorso del file CSV.
     * @return Una lista di utenti caricati dal file.
     */
    public static List<Utente> caricaUtenti(String path){
        List<Utente> utenti = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine(); // Salta la prima riga
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(",");
                if (campi.length < 7) continue; // Salta righe non valide
                String nome = campi[0];
                String cognome = campi[1];
                String username = campi[2];
                String passwordCifrata = campi[3];
                LocalDate dataNascita = campi[4].isEmpty() ? null : LocalDate.parse(campi[4]);
                String luogoDomicilio = campi[5];
                Ruolo ruolo = campi[6].equalsIgnoreCase("RISTORATORE") ? Ruolo.RISTORATORE : Ruolo.CLIENTE;
                utenti.add(new Utente(nome, cognome, username, passwordCifrata, dataNascita, luogoDomicilio, ruolo));
            }
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file: " + e.getMessage());
        } return utenti;
    }

    /**
     * Salva la lista di utenti in un file CSV.
     * @param path Il percorso del file CSV.
     * @param utenti La lista di utenti da salvare.
     */
    public static void salvaUtente(String path, List<Utente> utenti) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            pw.println("Name,Surname,Username,Password,Birthdate,Address,Type");
            for (Utente u : utenti) {
                pw.printf("%s,%s,%s,%s,%s,%s,%s%n",
                        u.getNome(),
                        u.getCognome(),
                        u.getUsername(),
                        u.getPasswordCifrata(),
                        u.getDataNascita() != null ? u.getDataNascita().toString() : "",
                        u.getLuogoDomicilio(),
                        u.getRuolo().toString());
            }
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura del file: " + e.getMessage());
        }
    }

    /**
     * Carica i ristoranti da un file CSV.
     * @param path Il percorso del file CSV.
     * @return Una lista di ristoranti caricati dal file.
     */
    public static List<Ristorante> caricaRistoranti(String path) {
        List<Ristorante> ristoranti = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine(); // Salta intestazione
            while ((line = br.readLine()) != null) {
                // Split che gestisce le virgole tra virgolette
                String[] campi = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                String nome = campi[0].replace("\"", "");
                String nazione = campi[1].replace("\"", "");
                String citta = campi[2].replace("\"", "");
                String indirizzo = campi[3].replace("\"", "");
                double latitudine = campi[4].isBlank() ? 0.0 : Double.parseDouble(campi[4]);
                double longitudine = campi[5].isBlank() ? 0.0 : Double.parseDouble(campi[5]);
                String fasciaPrezzo = campi[6].replace("\"", "");
                String tipoCucina = campi[9].replace("\"", "");
                String descrizione = campi[10]
                    .replace("&#44;", ",")
                    .replace("\"", "");
                String titolare = campi.length > 11 ? campi[11].replace("\"", "") : "";

                boolean asporto = false;
                boolean prenotazioneOnline = false;

                ristoranti.add(new Ristorante(
                    nome, nazione, citta, indirizzo,
                    latitudine, longitudine, fasciaPrezzo,
                    asporto, prenotazioneOnline, tipoCucina, descrizione, titolare
                ));
            }
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file: " + e.getMessage());
        }

        return ristoranti;
    }

    // --- RECENSIONI ---
    /**
     * Carica le recensioni per un ristorante specifico.
     * @param nomeRistorante Il nome del ristorante per cui caricare le recensioni.
     * @return Una lista di recensioni per il ristorante specificato.
     */
    public static List<Recensione> caricaRecensioniPerRistorante(String nomeRistorante) {
        List<Recensione> recensioni = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/recensioni.csv"))) {
            String line = br.readLine(); // salta intestazione
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(",", 4);
                if (campi.length < 4) continue;
                if (campi[0].equalsIgnoreCase(nomeRistorante)) {
                    String username = campi[1];
                    int valutazione = Integer.parseInt(campi[2]);
                    String commento = campi[3].replace("&#44;", ",");
                    Utente autore = new Utente();
                    autore.setUsername(username);
                    Recensione rec = new Recensione();
                    rec.setRistoranteNome(nomeRistorante);
                    rec.setAutore(autore);
                    rec.setValutazione(valutazione);
                    rec.setCommento(commento);
                    recensioni.add(rec);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore lettura recensioni: " + e.getMessage());
        }
        return recensioni;
    }

    /**
     * Salva una nuova recensione.
     * @param rec La recensione da salvare.
     */
    public static void salvaRecensione(Recensione rec) {
        // Aggiunge una nuova recensione
        try (FileWriter fw = new FileWriter("data/recensioni.csv", true)) {
            // Sostituisci solo le virgole con uno spazio o altro carattere, ma lascia la punteggiatura
            String commentoCSV = rec.getCommento().replace(",", "&#44;");
            fw.write(rec.getRistoranteNome() + "," + rec.getAutore().getUsername() + "," + rec.getValutazione() + "," + commentoCSV + "\n");
        } catch (IOException e) {
            System.err.println("Errore scrittura recensione: " + e.getMessage());
        }
    }

    /**
     * Aggiorna una recensione esistente.
     * @param rec La recensione da aggiornare.
     */
    public static void aggiornaRecensione(Recensione rec) {
        // Sovrascrive la recensione dell'utente per il ristorante
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/recensioni.csv"))) {
            String line = br.readLine();
            if (line != null) lines.add(line); // intestazione
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(",", 4);
                if (campi.length < 4) continue;
                if (campi[0].equals(rec.getRistoranteNome()) && campi[1].equals(rec.getAutore().getUsername())) {
                    lines.add(rec.getRistoranteNome() + "," + rec.getAutore().getUsername() + "," + rec.getValutazione() + "," + rec.getCommento().replace(",", " "));
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore lettura recensioni: " + e.getMessage());
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter("data/recensioni.csv"))) {
            for (String l : lines) pw.println(l);
        } catch (IOException e) {
            System.err.println("Errore scrittura recensioni: " + e.getMessage());
        }
    }

    /**
     * Cancella una recensione esistente.
     * @param rec La recensione da cancellare.
     */
    public static void cancellaRecensione(Recensione rec) {
        // Rimuove la recensione dell'utente per il ristorante
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/recensioni.csv"))) {
            String line = br.readLine();
            if (line != null) lines.add(line); // intestazione
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(",", 4);
                if (campi.length < 4) continue;
                if (!(campi[0].equals(rec.getRistoranteNome()) && campi[1].equals(rec.getAutore().getUsername()))) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore lettura recensioni: " + e.getMessage());
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter("data/recensioni.csv"))) {
            for (String l : lines) pw.println(l);
        } catch (IOException e) {
            System.err.println("Errore scrittura recensioni: " + e.getMessage());
        }
    }

    /**
     * Carica tutte le recensioni da un file CSV.
     * @return Una lista di tutte le recensioni.
     */
    public static List<Recensione> caricaTutteRecensioni() {
        List<Recensione> recensioni = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/recensioni.csv"))) {
            String line = br.readLine(); // salta intestazione
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(",", 4);
                if (campi.length < 4) continue;
                String nomeRistorante = campi[0];
                String username = campi[1];
                int valutazione = Integer.parseInt(campi[2]);
                String commento = campi[3].replace("&#44;", ",");
                Utente autore = new Utente();
                autore.setUsername(username);
                Recensione rec = new Recensione();
                rec.setRistoranteNome(nomeRistorante);
                rec.setAutore(autore);
                rec.setValutazione(valutazione);
                rec.setCommento(commento);
                recensioni.add(rec);
            }
        } catch (IOException e) {
            System.err.println("Errore lettura recensioni: " + e.getMessage());
        }
        return recensioni;
    }

    // --- PREFERITI ---
    /**
     * Carica i preferiti di un utente da un file CSV.
     * @param username Il nome utente per cui caricare i preferiti.
     * @return Una lista di ristoranti preferiti per l'utente specificato.
     */
    public static List<String> caricaPreferiti(String username) {
        List<String> preferiti = new ArrayList<>();
        String path = "data/preferiti.csv";
        File file = new File(path);
        if (!file.exists()) return preferiti;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(",", 2);
                if (campi.length == 2 && campi[0].equals(username)) {
                    preferiti.add(campi[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Errore lettura preferiti: " + e.getMessage());
        }
        return preferiti;
    }

    /**
     * Aggiunge un ristorante ai preferiti di un utente.
     * Se il ristorante è già presente, non viene aggiunto nuovamente.
     * @param utente L'utente per cui aggiungere il preferito.
     * @param ristorante Il ristorante da aggiungere ai preferiti.
     */
    public static void aggiungiPreferito(Utente utente, Ristorante ristorante) {
        String path = "data/preferiti.csv";
        //List<String> preferiti = caricaPreferiti(utente.getUsername());
        // Controlla se già presente per questo utente
        boolean giàPresente = false;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(",", 2);
                if (campi.length == 2 && campi[0].equals(utente.getUsername()) && campi[1].equals(ristorante.getNome())) {
                    giàPresente = true;
                    break;
                }
            }
        } catch (IOException e) {
            // Se il file non esiste, va bene, lo creeremo
        }
        if (!giàPresente) {
            try (FileWriter fw = new FileWriter(path, true)) {
                fw.write(utente.getUsername() + "," + ristorante.getNome() + System.lineSeparator());
            } catch (IOException e) {
                System.err.println("Errore scrittura preferiti: " + e.getMessage());
            }
        }
    }

    /**
     * Rimuove un ristorante dai preferiti di un utente.
     * Se il ristorante non è presente, non viene fatto nulla.
     * @param utente L'utente per cui rimuovere il preferito.
     * @param ristorante Il ristorante da rimuovere dai preferiti.
     */
    public static void rimuoviPreferito(Utente utente, Ristorante ristorante) {
        String path = "data/preferiti.csv";
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(",", 2);
                if (campi.length == 2 && !(campi[0].equals(utente.getUsername()) && campi[1].equals(ristorante.getNome()))) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore lettura preferiti: " + e.getMessage());
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            for (String l : lines) pw.println(l);
        } catch (IOException e) {
            System.err.println("Errore scrittura preferiti: " + e.getMessage());
        }
    }

    /**
     * Recupera la risposta a una recensione specifica.
     * @param nomeRistorante Il nome del ristorante.
     * @param ristoratore Il nome del ristoratore che ha risposto.
     * @param cliente Il nome del cliente che ha scritto la recensione.
     * @return La risposta alla recensione, o null se non esiste.
     */
    public static String getRispostaRecensione(String nomeRistorante, String ristoratore, String cliente) {
        String path = "data/risposte.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine(); // salta intestazione
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(",", 4);
                if (campi.length < 4) continue;
                if (campi[0].equals(nomeRistorante)
                    && campi[1].equalsIgnoreCase(ristoratore)
                    && campi[2].equalsIgnoreCase(cliente)) {
                    return campi[3].replace("&#44;", ",");
                }
            }
        } catch (IOException e) {
            // file non trovato o vuoto
        }
        return null;
    }

    /**
     * Salva una risposta a una recensione.
     * Se la risposta esiste già, viene aggiornata.
     * @param nomeRistorante Il nome del ristorante.
     * @param ristoratore Il nome del ristoratore che risponde.
     * @param cliente Il nome del cliente che ha scritto la recensione.
     * @param risposta La risposta da salvare.
     */
    public static void salvaRispostaRecensione(String nomeRistorante, String ristoratore, String cliente, String risposta) {
        String path = "data/risposte.csv";
        List<String> lines = new ArrayList<>();
        boolean aggiornata = false;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            if (line != null) lines.add(line); // intestazione
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(",", 4);
                if (campi.length < 4) continue;
                if (campi[0].equals(nomeRistorante)
                    && campi[1].equalsIgnoreCase(ristoratore)
                    && campi[2].equalsIgnoreCase(cliente)) {
                    lines.add(nomeRistorante + "," + ristoratore + "," + cliente + "," + risposta.replace(",", "&#44;"));
                    aggiornata = true;
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            // file non trovato, lo creeremo
        }
        if (!aggiornata) {
            if (lines.isEmpty()) lines.add("nomeRistorante,ristoratore,cliente,commento");
            lines.add(nomeRistorante + "," + ristoratore + "," + cliente + "," + risposta.replace(",", "&#44;"));
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            for (String l : lines) pw.println(l);
        } catch (IOException e) {
            System.err.println("Errore scrittura risposte: " + e.getMessage());
        }
    }
}
