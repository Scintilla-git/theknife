package theknife.utils;

import theknife.model.Ristorante;
import theknife.model.Recensione;

import java.util.List;
import java.util.stream.Collectors;

/**
 * La classe UtilsRicerca contiene i metodi relativi alla ricerca dei ristoranti.
 * Questi metodi permettono di filtrare i ristoranti in base ai seguenti criteri:
 * - Locazione Geografica (nazione, città, indirizzo)
 * - Tipologia di cucina
 * - Fascia di prezzo
 * - Servizi offerti (asporto, prenotazione online)
 * - Valutazione media delle recensioni
 * - Una combinazione di più criteri sopra elencati
 * 
 * @author Davide Suigi 759430 CO
 * @version 1.0
 */
public class UtilsRicerca {

    /**
     * Filtra i ristoranti in base alla locazione geografica.
     * @param ristoranti La lista di ristoranti da filtrare.
     * @param nazione La nazione in cui cercare i ristoranti (può essere null).
     * @param citta La città in cui cercare i ristoranti (può essere null).
     * @param indirizzo L'indirizzo specifico del ristorante (può essere null).
     * @return Una lista di ristoranti che corrispondono ai criteri di ricerca.
     */
    public static List<Ristorante> filtraPerLuogo(List<Ristorante> ristoranti, String nazione, String citta, String indirizzo) {
        return ristoranti.stream()
                .filter(r -> (nazione == null || r.getNazione().equalsIgnoreCase(nazione)) &&
                             (citta == null || r.getCitta().equalsIgnoreCase(citta)) &&
                             (indirizzo == null || r.getIndirizzo().equalsIgnoreCase(indirizzo)))
                .collect(Collectors.toList());
    }

    /**
     * Filtra i ristoranti in base al tipo di cucina.
     * @param ristoranti La lista di ristoranti da filtrare.
     * @param tipoCucina Il tipo di cucina desiderato (es. "Italiana", "Cinese", etc.).
     * @return Una lista di ristoranti che offrono il tipo di cucina specificato.
     */
    public static List<Ristorante> filtraPerTipoCucina(List<Ristorante> ristoranti, String tipoCucina) {
        return ristoranti.stream()
                .filter(r -> r.getTipoCucina().equalsIgnoreCase(tipoCucina))
                .collect(Collectors.toList());
    }

    /**
     * Filtra i ristoranti in base alla fascia di prezzo.
     * @param ristoranti La lista di ristoranti da filtrare.
     * @param fasciaPrezzo La fascia di prezzo desiderata (es. "Economica", "Media", "Alta").
     * @return Una lista di ristoranti che appartengono alla fascia di prezzo specificata.
     */
    public static List<Ristorante> filtraPerFasciaPrezzo(List<Ristorante> ristoranti, String fasciaPrezzo) {
        return ristoranti.stream()
                .filter(r -> r.getFasciaPrezzo().equalsIgnoreCase(fasciaPrezzo))
                .collect(Collectors.toList());
    }

    /**
     * Filtra i ristoranti in base ai servizi offerti.
     * @param ristoranti La lista di ristoranti da filtrare.
     * @param asporto Indica se si desidera solo ristoranti che offrono il servizio di asporto.
     * @return Una lista di ristoranti che corrispondono ai criteri di ricerca.
     */
    public static List<Ristorante> filtraPerAsporto(List<Ristorante> ristoranti, boolean asporto) {
        return ristoranti.stream()
                .filter(r -> r.isAsporto() == asporto)
                .collect(Collectors.toList());
    }

    /**
     * Filtra i ristoranti in base alla prenotazione online.
     * @param ristoranti La lista di ristoranti da filtrare.
     * @param prenotazioneOnline Indica se si desidera solo ristoranti che offrono la prenotazione online.
     * @return Una lista di ristoranti che corrispondono ai criteri di ricerca.
     */
    public static List<Ristorante> filtraPerPrenotazioneOnline(List<Ristorante> ristoranti, boolean prenotazioneOnline) {
        return ristoranti.stream()
                .filter(r -> r.isPrenotazioneOnline() == prenotazioneOnline)
                .collect(Collectors.toList());
    }

    /**
     * Filtra i ristoranti in base alla valutazione media delle recensioni.
     * @param ristoranti La lista di ristoranti da filtrare.
     * @param recensioni La lista di recensioni da considerare per il calcolo della valutazione media.
     * @param minMedia La valutazione media minima richiesta.
     * @return Una lista di ristoranti che hanno una valutazione media pari o superiore a minMedia.
     */
    public static List<Ristorante> filtraPerValutazioneMedia(List<Ristorante> ristoranti, List<Recensione> recensioni, double minMedia) {
        return ristoranti.stream()
                .filter(r -> {
                    double media = recensioni.stream()
                        .filter(rec -> rec.getRistoranteNome().equals(r))
                        .mapToInt(Recensione::getValutazione)
                        .average().orElse(0.0);
                    return media >= minMedia;
                })
                .collect(Collectors.toList());
    }

    /**
     * Filtra i ristoranti in base a una combinazione di criteri.
     * @param ristoranti La lista di ristoranti da filtrare.
     * @param nazione La nazione in cui cercare i ristoranti (può essere null).
     * @param citta La città in cui cercare i ristoranti (può essere null).
     * @param tipoCucina Il tipo di cucina desiderato (può essere null).
     * @param fasciaPrezzo La fascia di prezzo desiderata (può essere null).
     * @return Una lista di ristoranti che corrispondono a tutti i criteri specificati.
     */
    public static List<Ristorante> filtraCombinato(List<Ristorante> ristoranti, String nazione, String citta, String tipoCucina, String fasciaPrezzo) {
        return ristoranti.stream()
                .filter(r -> (nazione == null || r.getNazione().equalsIgnoreCase(nazione)) &&
                             (citta == null || r.getCitta().equalsIgnoreCase(citta)) &&
                             (tipoCucina == null || r.getTipoCucina().equalsIgnoreCase(tipoCucina)) &&
                             (fasciaPrezzo == null || r.getFasciaPrezzo().equalsIgnoreCase(fasciaPrezzo)))
                .collect(Collectors.toList());
    }
}
