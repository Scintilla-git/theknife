# Progetto Laboratorio Interdisciplinare A: The Knife
## Obiettivo
Una piattaforma che consente di trovare ristoranti in tutto il mondo e selezionarli in base al luogo, alla tipologia del ristorante stesso, alla fascia di prezzo, alla possibilità di prenotare un tavolo o di ordinare da asporto.  
(Simili alle funzionalità di TheFork).  

Il progetto deve:  
- permettere, a partire da una repo di dati:  
    - Ai ristoratori, di creare, definire e visualizzare le recensioni dei propri ristoranti  
    - Agli utenti, di cercare i ristoranti in base alle caratteristiche fesiferate, inserire e modificare recensioni  
- Contenere due file, di cui  
    - Risotoranti: un file che contiene una lista di informazioni dei ristoranti disponibili  
        - https://www.kaggle.com/datasets/ngshiheng/michelin-guide-restaurants-2021  
    - Utenti: file che contiente le informazioni base di ogni utente iscritto

### Funzionalità
L'applicazione deve avere le seguenti funzionalità principali:  
- Utenti non registrati
    - Visualizzare i ristoranti e dettagli
    - Visualizzare le recensioni dei ristoranti in forma anonima
    - Registrarsi all'applicazione
- Utenti non ristoratori
    - Definire una lista di ristoranti preferiti, e visualizzarla
    - Inserire le proprie recensioni per un ristorante
    - modificare e cancellarle
    - Logout
- Utenti ristoratori
    - Inserire un nuovo ristorante
    - Visualizzare le recensioni relative ai propri ristoranti e rispondere
    - Visualizzare la valutazione media del proprio ristorante e il numero di recensioni ricevute
    - Logout

#### Nel dettaglio...
Per la ricerca dei ristoranti(no login)  
- ```cercaRistorante()```  
    - Locazione geografica(obb)
    - Per tipo di cucina
    - Per fascia di prezzo
        - min di 30€ o tra 20 e 50€
    - in base al servizio di asporto
    - in base al servizio di prenotazione online
    - per media del n° di stelle
    - Una combinazione di tutto ciò
- ```visualizzaRistorante()``` e ```visualizzaRecensione()```
    - Visualizzare un ristorante(le sue caratteristiche) o le recensioni(commento e stelle)
- ```registrazione()```
    - la possibilià di registrarsi come utente
- Interazioni una volta loggato, quali
    - ```aggiungi/rimuovi/visualizzaPref()```
    - ```aggiungi/modifica/eliminaRecensione()```
    - ```visualizzaRiepilogo()```(solo ristoratore)
    - ```visualizza/rispondiRecensione()```(solo ristoratore)

## Il progetto
All'avvio, l'applicazione mostra un menù iniziale, in cui è possibile loggarsi o registrarsi.  
Viene anche visualizzata l'opzione "guest", per chi vuole usarla senza autenticarsi.  
L'applicazione consente, di base:
- Visualizzazione dei ristoranti vicini alla città dell'utente registrato, o della città specificata dal guest
- Ricerca di ristoranti
- Visualizzazione dei preferiti
- Visualizzazione dei ristoranti a cui è stata scritta una recensione
- Visualizzazione dei ristoranti inseriti e valutazione media(ristoratore)
- Visualizzazione e risposta alle recensioni(ristoratore)

Inoltre, l'applicazione prevederà:
- File e strutture dati, quali
    - Ristoranti
    - Utenti
- Cosa e come vengono memorizzati i file
- Interfaccia utente
    - Da scegliere se Terminale o GUI(uso di JavaFX)

### Codice Sorgente
Il codice sarà interamente scritto in java, e dovrà essere opportunamente commentato tramite l'utilizzo di Javadoc.  
Il main dell'applicazione è contenuto all'interno della classe TheKnife.java.  
Sarà presente un file .jar per l'esecuzione del programma.  
Ogni intestazione di ogni file sarà la seguente:  
```java
// Davide Suigi N°759430 CO
```  

### Richieste finali
La consegna finale dovrà includere:
- Lo sviluppo della soluzione software
- Documentazione del progetto, quali
    - Manuale utente
    - Manuale Tecnico
