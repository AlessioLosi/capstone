# **EventNow - Backend**

### **Tecnologie Utilizzate**

- **Java 17**
- **Spring Boot**
- **Spring Security**
- **PostgreSQL**
- **Stripe**
- **Maven**

---

### **Funzionalità principali**

#### **Autenticazione**

- **Login Utente**:  
  **Endpoint**: `/auth/login` (POST)  
  **Descrizione**: Autenticazione utente con credenziali.  
  **Risposta**: Token JWT.

- **Registrazione Nuovo Utente**:  
  **Endpoint**: `/auth/register` (POST)  
  **Descrizione**: Registrazione di un nuovo utente.  
  **Risposta**: Dettagli utente registrato.

#### **Gestione Profilo Utente**

- **Visualizza Profilo**:  
  **Endpoint**: `/me` (GET)  
  **Descrizione**: Recupera i dettagli del profilo dell'utente autenticato.

- **Aggiorna Profilo**:  
  **Endpoint**: `/me` (PUT)  
  **Descrizione**: Aggiorna le informazioni del profilo dell'utente.

- **Carica Avatar**:  
  **Endpoint**: `/me/avatar` (PATCH)  
  **Descrizione**: Permette all'utente di caricare un nuovo avatar.

#### **Gestione Post**

- **Crea Post**:  
  **Endpoint**: `/me/post` (POST)  
  **Descrizione**: Permette a un utente autenticato di creare un post.

- **Visualizza Post Utente**:  
  **Endpoint**: `/me/post` (GET)  
  **Descrizione**: Recupera tutti i post creati dall'utente autenticato.

- **Elimina Post**:  
  **Endpoint**: `/me/post/{id}` (DELETE)  
  **Descrizione**: Elimina un post dato il suo ID.

#### **Gestione Eventi**

- **Crea Evento**:  
  **Endpoint**: `/me/eventi` (POST) - Solo per Admin  
  **Descrizione**: Crea un nuovo evento.

- **Visualizza Eventi Utente**:  
  **Endpoint**: `/me/eventi` (GET)  
  **Descrizione**: Recupera eventi creati dall'utente autenticato.

- **Visualizza Tutti gli Eventi**:  
  **Endpoint**: `/tuttieventi` (GET)  
  **Descrizione**: Recupera tutti gli eventi disponibili.

#### **Prenotazioni**

- **Visualizza Prenotazioni**:  
  **Endpoint**: `/me/prenotazioni` (GET)  
  **Descrizione**: Recupera tutte le prenotazioni effettuate dall'utente.

- **Elimina Prenotazione**:  
  **Endpoint**: `/me/prenotazioni/{id}` (DELETE)  
  **Descrizione**: Elimina una prenotazione effettuata dall'utente.

#### **Stripe**

- **Crea Sessione di Pagamento (Checkout)**:  
  **Endpoint**: `/api/payments/checkout/{eventId}` (POST)  
  **Descrizione**: Crea una sessione di pagamento per un evento selezionato.

- **Gestisci Webhook di Stripe**:  
  **Endpoint**: `/api/payments/webhook` (POST)  
  **Descrizione**: Gestisce la risposta di Stripe al pagamento completato.

#### **Commenti**

- **Aggiungi un Commento**:  
  **Endpoint**: `/me/commento` (POST)  
  **Descrizione**: Aggiunge un commento a un post.

- **Visualizza Commenti di un Post**:  
  **Endpoint**: `/me/commento/{id}` (GET)  
  **Descrizione**: Recupera tutti i commenti di un determinato post.

- **Elimina Commento**:  
  **Endpoint**: `/me/commento/{id}` (DELETE)  
  **Descrizione**: Elimina un commento creato dall'utente.

- **Modifica un Commento**:  
  **Endpoint**: `/me/commento/{id}` (PUT)  
  **Descrizione**: Modifica un commento esistente.

---

### **Contributi**

1. **Fork del repository**
2. **Crea un nuovo branch**:
   ```bash
   git checkout -b feature/nome-funzionalità

Aggiungi le tue modifiche
Fai il commit delle modifiche:
bash
Copia codice
git commit -am 'Aggiungi nuova funzionalità'
Push delle modifiche:
bash
Copia codice
git push origin feature/nome-funzionalità
Invia una pull request
Licenza
Questo progetto è concesso sotto la licenza MIT. Consulta il file LICENSE per maggiori dettagli.

Link utili

- **Stripe: https://stripe.com/docs;**
- **SpringBoot Doc:https://spring.io/projects/spring-boot;**