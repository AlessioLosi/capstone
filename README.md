EventNow
EventNow è un'applicazione per la gestione di eventi, pensata per la gestione e la prenotazione di eventi da utenti che
possono registrarsi o autenticarsi, visualizzare/acquistare gli eventi disponibili e gestire il proprio profilo.

Tecnologie Utilizzate
Maven: per la gestione delle dipendenze e il build del progetto.
Java 17: versione di Java utilizzata.
Spring Boot: framework per la creazione dell'applicazione.
Spring Security: per la gestione della sicurezza e dell'autenticazione.
PostgreSQL: database relazionale per l'archiviazione dei dati.
Stripe: per l'integrazione dei pagamenti online.
Funzionalità
AUTENTICAZIONE
Login Utente

Endpoint: POST /auth/login
Descrizione: Consente agli utenti di effettuare il login fornendo le credenziali.
Parametri: Un oggetto NewUserLoginDTO con le credenziali dell'utente.
Risposta: Un oggetto UserLoginResponseDTO con il token JWT.
Registrazione Nuovo Utente

Endpoint: POST /auth/register
Descrizione: Permette di registrare un nuovo utente.
Parametri: Un oggetto UtentiDTO con i dettagli dell'utente.
Risposta: Ritorna l'utente registrato (status 201).
USER
Visualizzare il Profilo dell'Utente

Endpoint: GET /me
Descrizione: Recupera i dettagli del profilo dell'utente autenticato.
Risposta: Ritorna le informazioni dell'utente.
Aggiornare il Profilo dell'Utente

Endpoint: PUT /me
Descrizione: Aggiorna le informazioni del profilo dell'utente.
Corpo della richiesta: UtentiDTO (dati aggiornati dell'utente).
Risposta: Ritorna le informazioni aggiornate dell'utente.
Aggiornare l'Avatar

Endpoint: PATCH /me/avatar
Descrizione: Permette all'utente di caricare un nuovo avatar.
Parametri di query: avatar (file immagine da caricare).
Risposta: Ritorna il percorso dell'avatar caricato.
POST
Creare un Post

Endpoint: POST /me/post
Descrizione: Permette a un utente autenticato di creare un post.
Corpo della richiesta: PostDTO (dettagli del post).
Risposta: Ritorna il post creato.
Visualizzare i Post dell'Utente

Endpoint: GET /me/post
Descrizione: Recupera tutti i post creati dall'utente autenticato.
Parametri di query: page, size (per la paginazione).
Risposta: Lista paginata dei post.
Eliminare un Post

Endpoint: DELETE /me/post/{id}
Descrizione: Elimina un post dato il suo ID.
Variabile nel percorso: id (ID del post).
Risposta: Stato 204 (No Content).
Visualizzare Tutti i Post

Endpoint: GET /tuttipost
Descrizione: Recupera tutti i post con supporto alla paginazione e ordinamento.
Parametri di query: page, size, sortBy (opzionale per l'ordinamento).
Risposta: Lista paginata di tutti i post.
Aggiornare un Post

Endpoint: PUT /me/post/{id}
Descrizione: Aggiorna un post esistente dato il suo ID.
Variabile nel percorso: id (ID del post).
Corpo della richiesta: PostDTO (dettagli aggiornati del post).
Risposta: Ritorna il post aggiornato.
EVENTI
Creare un Evento

Endpoint: POST /me/eventi
Descrizione: Crea un nuovo evento (solo per admin).
Corpo della richiesta: NewEventDTO (dati dell'evento).
Risposta: Ritorna l'evento creato.
Visualizzare Eventi per Utente

Endpoint: GET /me/eventi
Descrizione: Recupera eventi creati dall'utente autenticato, filtrabili per artista.
Parametri di query: artista, page, size.
Risposta: Ritorna una pagina di eventi.
Visualizzare Tutti gli Eventi

Endpoint: GET /tuttieventi
Descrizione: Recupera tutti gli eventi, ordinati.
Parametri di query: page, size, sortBy.
Risposta: Ritorna una pagina di eventi.
Visualizzare un Evento per ID

Endpoint: GET /eventi/{eventId}
Descrizione: Recupera i dettagli di un evento specifico.
Risposta: Ritorna l'evento richiesto.
Eliminare un Evento

Endpoint: DELETE /me/eventi/{id_evento}
Descrizione: Elimina un evento (solo per admin).
Risposta: Nessuna risposta (status 204).
Aggiornare un Evento

Endpoint: PUT /me/eventi/{id}
Descrizione: Aggiorna un evento esistente (solo per admin).
Corpo della richiesta: NewEventDTO (dati aggiornati dell'evento).
Risposta: Ritorna l'evento aggiornato.
Caricare una Foto per un Evento

Endpoint: PATCH /me/foto/{id_evento}
Descrizione: Carica una foto per un evento (solo per admin).
Parametri di query: foto (file immagine da caricare).
Risposta: Ritorna il percorso della foto caricata.
PRENOTAZIONE
Visualizzare Prenotazioni dell'Utente

Endpoint: GET /me/prenotazioni
Descrizione: Recupera tutte le prenotazioni effettuate dall'utente autenticato.
Parametri di query: page, size.
Risposta: Ritorna una pagina di prenotazioni.
Eliminare una Prenotazione

Endpoint: DELETE /me/prenotazioni/{id}
Descrizione: Elimina una prenotazione specifica per l'utente autenticato.
Risposta: Nessuna risposta (status 204).
STRIPE
Crea una Sessione di Pagamento (Checkout)

Endpoint: POST /api/payments/checkout/{eventId}
Descrizione: Crea una sessione di pagamento per un evento selezionato, associando la prenotazione al pagamento.
Parametri: eventId (ID dell'evento per il quale si sta effettuando la prenotazione).
Risposta: URL per completare il pagamento tramite Stripe.
Gestisci Webhook di Stripe

Endpoint: POST /api/payments/webhook
Descrizione: Gestisce la risposta di Stripe al pagamento completato tramite webhook.
Parametri: payload (dati inviati da Stripe).
Risposta: Nessuna risposta visibile (aggiorna lo stato della prenotazione a "Completato").
COMMENTI
Aggiungi un Commento

Endpoint: POST /me/commento
Descrizione: Consente agli utenti di aggiungere un commento a un post.
Parametri: Un oggetto CommentiDTO con il contenuto del commento e un ID del post.
Risposta: Ritorna il commento creato.
Visualizza Commenti di un Post

Endpoint: GET /me/commento/{id}
Descrizione: Recupera tutti i commenti di un determinato post.
Parametri: id del post e opzioni di paginazione (page, size).
Risposta: Una lista di commenti relativi al post.
Visualizza Commenti dell'Utente

Endpoint: GET /me/commenti/utente
Descrizione: Recupera tutti i commenti creati dall'utente autenticato.
Parametri: Paginazione tramite page e size.
Risposta: Una lista di commenti dell'utente autenticato.
Elimina un Commento
Ecco la continuazione del documento, che completa la sezione Commenti:

Elimina un Commento

Endpoint: DELETE /me/commento/{id}
Descrizione: Elimina un commento creato dall'utente autenticato.
Parametri: id del commento da eliminare.
Risposta: Nessuna risposta (status 204).
Modifica un Commento

Endpoint: PUT /me/commento/{id}
Descrizione: Permette all'utente di aggiornare un commento esistente.
Parametri: id del commento e un oggetto CommentiDTO con i nuovi dati.
Risposta: Il commento aggiornato.
Contributi
Se desideri contribuire al progetto EventNow, segui questi passaggi:

Fork del repository:

Fai un fork del progetto dal repository principale.
Crea un nuovo branch:

Esegui il comando:
bash
Copia codice
git checkout -b feature/nome-funzionalità
Aggiungi le tue modifiche:

Implementa la nuova funzionalità o correggi i bug.
Fai il commit delle modifiche:

Esegui il comando:
bash
Copia codice
git commit -am 'Aggiungi nuova funzionalità'
Push delle modifiche:

Esegui il comando:
bash
Copia codice
git push origin feature/nome-funzionalità
Invia una pull request:

Crea una pull request per unire le modifiche al branch principale.
Licenza
Questo progetto è concesso sotto la licenza MIT. Consulta il file LICENSE per maggiori dettagli.
Link utili
Stripe: https://stripe.com/docs;
SpringBoot Doc:https://spring.io/projects/spring-boot;