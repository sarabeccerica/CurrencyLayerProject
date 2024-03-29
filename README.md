![currencyAnaLayer](https://user-images.githubusercontent.com/91334936/148727928-0c3d3569-cc83-4fc8-8817-2169607a4250.jpeg)
# CURRENCY ANALAYER 
Currency Analayer è un'applicazione web con lo scopo di analizzare i dati relativi alle valute restituite dalla chiamata all'API currencylayer.
Il servizio è costituito dalla possibilità di visualizzare i dati storici delle valute di interesse nell'arco di 5 giorni, le relative statistiche e infine la creazione di un
investimento.

**INDICE**
- [Applicazione](#APPLICAZIONE)
  - [Statistiche](#STATISTICHE-bar_chart)
  - [Filtri](#FILTRI-memo)
  - [Valute](#VALUTE-moneybag)
  - [Rotte](#Rotte-globe_with_meridians)
  - [Test](#Test)
  - [Eccezioni](#eccezioni)
- [Documentazione JavaDoc](#documentazione)
- [Software utilizzati](#software)
- [Autori](#autori)
  
  
# Applicazione :desktop_computer:
Tramite l'API CurrencyLayer il programma riceve salva e processa i dati relativi ad i cambi di valuta in base all USD, che vengono poi convertiti in base EUR. All'avvio viene creata una struttura dati contenente tutte le informazioni sulle valute degli ultimi 5 giorni fornite dall'API, che possono essere poi visualizzate dall'utente, inoltre si può creare un investimento su una valuta e valutarne poi l'andamento nei 5 giorni successivi mediante il salvataggio su file dello stesso.
Una volta lanciato il programma tramite un IDE o tramite il terminale si può visionare il progetto passando i vari parametri,descritti in seguito, all'indirizzo http://localhost:8080.

## Statistiche :bar_chart:
TIPO DI STATISTICA | COSA RESTITUISCE 
------------ | -------------
VARIANZA | Restitusce la variazione di una valuta in un periodo di tempo di 5 giorni.
MEDIA | Restituisce il valore medio di una valuta in un periodo di tempo di 5 giorni.

## Filtri :memo:
TIPO DI FILTRO | COSA RESTITUISCE
------------ | -------------
GIORNO, MESE, ANNO |  Vengono restituiti i valori delle valute di interesse relativi al giorno specificato e la valuta con andamento peggiore.
VALUTA | Vengono restituite le statische della valuta di interesse.


## Valute :moneybag:
Le valute disponibili di interesse selezionate sono:
VALUTA | PROVENIENZA
------------ | -------------
CHF | Franco svizzero.
GBP | Sterlina inglese.
AUD | Dollaro australiano.
USD | Dollaro statunitense.
KYD | Dollaro delle isole Cayman.
JPY | Yen giapponese.
CNY | Yuan cinese.


## Rotte :round_pushpin:
Sono disponibili 6 rotte:
ROTTA | PARAMETRI | RITORNO
------------ | ------------- | -------------
/convert | Non prende nessun parametro. | Le valute ci arrivano in cambio USD, questa rotta le converte in EUR. Restituisce una stringa. 
/quotes | Non prende nessun parametro. | Restituisce un JSONArray con tutti i dati sull'andamento delle valute negli ultimi 5 giorni.
/quotes/daily | Prende in input tre interi che corrispondono a una data: YEAR, MONTH, DAY. | Restituisce un JSONObject con i dati sulle valute relativi ad uno specifico giorno e la valuta con andamento peggiore.
/quotes/analysis | Prende in input una stringa che corrisponde alla VALUTA di cui si desidera avere le analisi. | Restituisce un JSONObject con la media e la varianza di una valuta.
/quotes/currency | Prende in input una stringa che corrisponde alla VALUTA di cui si desidera avere l'andamento. | Restituisce un JSONArray con i dati sull'andamento di una specifica valuta negli ultimi cinque giorni.
/investment | Prende in input una stringa che corrisponde al nome dell'investitore, una stringa che correisponde alla VALUTA su cui si vuole investire e un intero che corrisponde a quanto si vuole investire. |  Restituisce una stringa che riferisce se l'investimento è stato eseguito o meno.
/investment/earning | Prende in input una stringa che corrisponde al nome dell'investitore. | Restituisce un vettore con i guadagni di ogni giornata.

Prima di iniziare a utilizzare le altre rotte bisogna utilizzare una volta la rotta /convert per convertire tutte le valute in cambio EUR.
ATTENZIONE: si può lanciare solo una volta a inizio programma la rotta /convert.

ESECUZIONE SU POSTMAN: 

1- CONVERSIONE VALUTE 

  ![convert2](https://user-images.githubusercontent.com/91334936/148727887-9dac53f1-3168-4511-a7ad-24b34b6b577e.png)

2- DATI STORICI DI TUTTE LE VALUTE

  ![quotes7](https://user-images.githubusercontent.com/91334936/148727883-0e0e2b11-cea2-457e-9a26-7807077a68c9.png)
   
3- VALUTE IN UN DETERMINATO GIORNO

  ![daily4](https://user-images.githubusercontent.com/91334936/148727891-49896926-cf37-4139-82f0-f3a6a68bbd82.png)

   
4- MEDIA E VARIANZA DI UNA VALUTA

  ![analysis1](https://user-images.githubusercontent.com/91334936/148727885-75b12a48-4f6d-47b2-8827-d9dc3a73ccef.png)
  
5- DATI STORICI DI UNA VALUTA

  ![currency3](https://user-images.githubusercontent.com/91334936/148727890-6f875178-ecf5-4d6e-a1bc-df9c3104ed25.png)

6- INVESTIMENTO

  ![investment6](https://user-images.githubusercontent.com/91334936/148727877-f29988c4-5e72-4ce5-a89f-cfab5d3c3c9d.png)
  
7- GUADAGNI DI UN INVESTIMENTO

  ![earnings5](https://user-images.githubusercontent.com/91334936/148727894-887ef726-d193-424d-868f-85a401b8585c.png)

## Eccezioni :warning:
Abbiamo creato delle eccezioni specifiche per le nostre esigenze che potete trovare [qui](https://github.com/sarabeccerica/CurrencyLayerProject/tree/master/src/main/java/Exceptions).
* **CurrencyNotFoundException:** viene lanciata se la VALUTA  presa in input non è tra le valute disponibili e quando parte viene visualizzato il messaggio:
  ### "ERRORE: Currency Not Found."
* **DateNotFoundException:** viene lanciata se la DATA inserita non è disponibile, quindi se sono passati più di 5 giorni dalla data o se è una data futura e quando parte viene visualizzato il seguente messaggio: 
  ### "ERRORE: Date Not Found."
  
## Test :white_check_mark:
Con il frameword JUnit abbiamo implementato due test:
* 1° TEST: abbiamo testato il metodo DailyLower della classe HistoricalData.
* 2° TEST: abbiamo testato il metodo DaysNumber della classe Investment.

Potete trovare le classi con i test [qui](https://github.com/sarabeccerica/CurrencyLayerProject/tree/master/src/test/java/Tests).


---

# Documentazione JavaDoc :books:
Potete trovare la documentazione javaDoc [qui](https://github.com/sarabeccerica/CurrencyLayerProject/tree/master/doc).


---

# Software utilizzati :globe_with_meridians:
* [Eclipse](https://www.eclipse.org/downloads/) - Ambiente di sviluppo
* [Maven](https://maven.apache.org/) - software di gestione di progetti e librerie
* [Spring Boot](https://spring.io/projects/spring-boot) - framework per sviluppo di applicazioni in Java
* [Postman](https://www.postman.com/) - ambiente di sviluppo API per effettuare richieste


---

# Autori :busts_in_silhouette:
Questo progetto è stato realizzato da:
* Beccerica Sara: APICall, ReadFile, Contoller, exceptions, ReadMe.
* Attili Loris: BaseClasses, interfacce, HistoricalData, Test, JavaDoc.
