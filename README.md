#CURRENCY ANALAYER 
Currency Analayer è un'applicazione web con lo scopo di analizzare i dati relativi alle valute restituite dalla chiamata all'API currencylayer.
Il servizio è costituito dalla possibilità di visualizzare i dati storici delle valute di interesse nell'arco di 5 giorni, le relative statistiche e infine la creazione di un
investimento.

**INDICE**
- [Applicazione](#Applicazione)
  - [Statistiche](#Statistiche-bar_chart)
  - [Filtri](#Filtri-memo)
  - [Rotte](#Rotte-globe_with_meridians)
  - [Test](#Test)
  - [Eccezioni](#eccezioni)
- [Documentazione JavaDoc](#documentazione)
- [Software utilizzati](#software)
- [Autori](#autori)
  
  
#Applicazione
Tramite l'API CurrencyLayer il programma riceve salva e processa i dati relativi ad i cambi di valuta in base all USD, che vengono poi convertiti in base EUR. All'avvio viene creata una struttura dati contenente tutte le informazioni sulle valute degli ultimi 5 giorni fornite dall'API, che possono essere poi visualizzate dall'utente, inoltre si può creare un investimento su una valuta e valutarne poi l'andamento nei 5 giorni successivi mediante il salvataggio su file dello stesso.

##Statistiche :bar_chart:
TIPO DI STATISTICA | COSA RESTITUISCE 
------------ | -------------
VARIANZA: | Restitusce la variazione di una valuta in un periodo di tempo di 5 giorni.
MEDIA: | Restituisce il valore medio di una valuta in un periodo di tempo di 5 giorni.

##Filtri :memo:
TIPO DI FILTRO | COSA RESTITUISCE
------------ | -------------
GIORNO, MESE, ANNO: |  Vengono restituiti i valori delle valute di interesse relativi al giorno specificato e la valuta con andamento peggiore.
VALUTA: | Vengono restituite le statische della valuta di interesse.
