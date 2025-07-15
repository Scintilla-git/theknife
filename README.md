# README: The Knife
## Getting started
### Prerequisiti
Il progetto è stato creato interamente in java, dunque è necessario avere installata una versione di java compatibile.  
La versione utilizzata dal progetto è la seguente:  
```shell
> java -version
openjdk version "21.0.6" 2025-01-21 LTS
OpneJDK Runtime Environment Temurin-21.0.6+7 (build 21.0.6+7-LTS)
OpneJDK 64-Bit Server VM Temurin-21.0.6+7 (build 21.0.6+7-LTS, mixed mode sharing)
```
Tutte le versioni diverse potrebbero dare errori inattesi dovuti a possibili cambiamenti a livello di metodi o classi.  

### Installazione
Il progetto è caricato su github.  
Per il download del codice sorgente, schiacciare sull'icona apposita, oppure scrivere il seguente comando su terminale, se si ha git:
```shell
> git repo clone Scintilla-git/TheKnife
```

Per quanto riguarda l'installazione della JDK 21,  
- Windows: [link](https://download.java.net/java/GA/jdk21.0.2/f2283984656d49d69e91c558476027ac/13/GPL/openjdk-21.0.2_windows-x64_bin.zip)
- Linux:  
  - tramite apt: ```> sudo apt install openjdk21```
  - tramite pacman: ```> sudo pacman -S jdk-openjdk```
- MaxOS: [link](https://download.java.net/java/GA/jdk21.0.2/f2283984656d49d69e91c558476027ac/13/GPL/openjdk-21.0.2_macos-x64_bin.tar.gz)

### Compilazione e uso dell'applicazione
#### Tramite IDE
Qualunque tipo di IDE che supporta java come linguaggio di programmazione rileverà TheKnife.java come main class.  
Una volta premuto il tasto di run, compilerà e avvierà automaticamente l'applicazione, che sarà interagibile nell'area del terminale relativa.

#### Tramite CMD/Shell
Prima di poter avviare la main class contenuta in TheKnife.java, l'intero progetto deve essere compilato e avviato dall'interno della cartella del progetto(preferibilmente in una cartella "test" o "out").  
```shell
#Il comando cerca nella cartella specificata tutti i file che contengono *.java e li compila nella cartella "out", e dopodichè esegue il main contenuto in theknife.TheKnife
> javac -d out $(find src/main/java -name "*.java") && java -cp out theknife.TheKnife
```

#### Tramite file JAR
Il file JAR è un eseguibile che richiede di aver installato come minimo una versione della JRE.  
Per essere eseguito, basta scaricare il file e farci doppio click, oppure tramite linea di comando:
```shell
> java -jar theknife.jar
```

## Uso dell'applicazione
Per una guida pronta all'uso, riferirsi al [Manuale Utente](/doc/TheKnife%20-%20Manuale%20Utente.pdf).  
Per invece una guida più dettagliata sulle scelte di codice, algoritmi e metodi, riferirsi al [Manuale Tecnico](/doc/The%20Knife%20-%20Manuale%20Tecnico.pdf).  

## Bug report
Se vengono incontrati dei bug, reportarli nella tab [Issues](github.com/Scintilla-git/TheKnife/Issues) di GitHub.  
