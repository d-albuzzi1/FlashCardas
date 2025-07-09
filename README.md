# Flashcardas - App Android per lo studio con flashcard

## Introduzione

**Flashcardas** è un'app Android progettata per aiutare gli utenti a creare, organizzare e studiare set di flashcard in modo semplice ed efficace.  
Gli utenti si autenticano (i dati sono salvati localmente) e accedono all’app dove possono creare mazzi di flashcard associando immagini a parole.

Durante l’allenamento, l’utente scorre le immagini:
- verso destra se conosce la traduzione,
- verso sinistra se non la conosce.

L’allenamento prosegue fino al completamento dell’intero mazzo, favorendo la memorizzazione attiva e il ripasso efficace.

---

## Architettura – MVVM

L'app è strutturata seguendo il pattern architetturale **MVVM (Model-View-ViewModel)**, che consente una netta separazione tra interfaccia utente, logica di presentazione e logica di business, facilitando testabilità, manutenibilità e scalabilità del codice.

Il modello MVVM suddivide il codice in tre componenti principali:

- **Model**: contiene la logica di accesso ai dati e le strutture dati principali (es. `Deck`, `Flashcard`), eventualmente collegato a una fonte dati (ad esempio file locale o database remoto).

- **ViewModel**: contiene la logica di presentazione. Comunica con il Model e fornisce alla View i dati tramite **LiveData**, osservabili in tempo reale. Il ViewModel funge da “single source of truth” per la UI.

- **View**: costituita da **Fragment** e **layout XML**. Si occupa esclusivamente della presentazione e dell’interazione con l’utente, senza logica di business.

### Vantaggi pratici di MVVM

- **Separazione chiara** tra logica di business e UI
- Facilità nel **testing** delle singole componenti senza dipendenze complesse
- Migliore gestione del ciclo di vita grazie a `ViewModel` e `LiveData`
- Facilità di estendere e mantenere il codice, aggiungendo nuove funzionalità senza impattare le parti esistenti

---

## Struttura del progetto

- **Model**  
  Contiene le classi `Deck` e `Flashcard`, che rappresentano rispettivamente un mazzo di carte e una singola flashcard. Queste classi sono serializzabili per permettere il salvataggio e il recupero dei dati.

- **Views**  
  I fragment (`CreateDeckFragment`, `DeckListFragment`, ecc.) gestiscono l’interfaccia utente tramite file XML, con layout basati su `ConstraintLayout` e `LinearLayout`.  
  I fragment interagiscono esclusivamente con il ViewModel per aggiornare o ricevere dati.

- **ViewModel**  
  Il `DeckViewModel` funge da ponte tra View e Model, gestendo la logica per creare, modificare, eliminare e aggiornare i mazzi. Utilizza `LiveData` per notificare automaticamente le Views sui cambiamenti dei dati.

- **Adapter**  
  Contiene classi come `DeckEditAdapter`, che adattano liste di flashcard o mazzi per la visualizzazione con `RecyclerView`.

- **Utils**  
  Contiene classi di utilità, ad esempio `IdUtils` per generare ID unici.

---

## Identificazione utente con Firebase Authentication

L'app utilizza **Firebase Authentication** per identificare in modo sicuro ogni utente.  
Il sistema supporta:

- Accesso anonimo
- Accesso con email e password

Ogni utente autenticato ottiene un **UID univoco**, che permette di distinguere logicamente i dati di ciascun utente.

Attualmente si utilizza principalmente l’**accesso anonimo** per evitare la registrazione manuale, mantenendo però un’identità persistente.

---

## Persistenza dei dati in memoria locale (Local Storage)

I dati dell’utente (mazzi e flashcard) sono salvati **interamente in locale** sul dispositivo, senza sincronizzazione su Firestore o altri servizi cloud.

La classe `LocalStorage`, nel package `repository`, gestisce:

- Il **salvataggio** dell’intera lista dei mazzi (`List<Deck>`) in un file JSON chiamato `decks.json`
- Il **caricamento** dei dati all’avvio dell’app o dei fragment
- La conversione degli oggetti da e verso JSON tramite la libreria **Gson**

### Cosa viene salvato

- Mazzi creati dall’utente (ID, nome, lista di flashcard)
- Flashcard (parola, traduzione, eventuali immagini)

Questi dati sono persistenti e restano disponibili anche dopo la chiusura dell’app.

---

## Gestione in memoria e reattività

Per migliorare la reattività dell’interfaccia, i dati vengono mantenuti in memoria tramite `ViewModel` e osservati con `LiveData`, permettendo alla UI di aggiornarsi automaticamente quando i dati cambiano.

---

## Obiettivi della struttura

La struttura del progetto mira a:

- Separare chiaramente la **logica di UI** dalla **logica di business**
- Permettere scalabilità e manutenibilità, ad esempio aggiungendo facilmente funzionalità come statistiche o gamification
- Integrare in futuro funzionalità cloud (es. Firestore) senza stravolgere l’architettura esistente

---

## In sintesi

L’app è progettata per essere **reattiva, scalabile e manutenibile**, con:

- Architettura **MVVM**
- Gestione utente tramite **Firebase Authentication**
- Persistenza dati tramite **memoria locale (file JSON)**
- UI moderna basata su `RecyclerView`, `LiveData` e `ViewModel`
- Solida base per future estensioni e integrazioni cloud
---

