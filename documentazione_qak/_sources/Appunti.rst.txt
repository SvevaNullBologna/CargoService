.. role:: red
.. role:: blue
.. role:: silde2
.. role:: red 
.. role:: blue 
.. role:: brown 
.. role:: remark
.. role:: worktodo
.. role:: slide
.. role:: slide1
.. role:: slide2
.. role:: slide3
.. role:: slidekp
.. role:: worktodo 

.. _appllevel: file:///C:/Didattica2025/mcrsv24/cargo2025/userDocs/cargoHistory.html#appllevel
.. _cargoenvBase.yml: file:///C:/Didattica2025/mcrsv24/cargo2025/yamls/cargoenvBase.yml
.. _DockerfileM2M: file:///C:/Didattica2025/mcrsv24/cargoserviceM2M/Dockerfile
.. _DockerfileM2MRasp: file:///C:/Didattica2025/mcrsv24/cargoserviceM2M/DockerfileM2mRasp

===================================
Appunti
===================================


-----------------------------------------
Aggregatori e API Gateway
-----------------------------------------

Sto costruendo un sistema software a microservizi che comprende prodotti, clienti, pagamenti e carico/scarico di mersi su un cargo. La logica di business si può riassumere come segue: un cliente regitsra un prodotto che vuole sia trasportato dal cargo a un certa destinazione, dopo opportuno pagamento. Ho già realizzato i singoli microservizi e vorrei sapere se, per costruire il sistema finale, sia utile, opportuno o necessario introdurre un aggregatore o una API gateway o entrambi.

Introducendo un aggregatore o una API Gateway, puoi migliorare significativamente la progettazione e il funzionamento del tuo sistema a microservizi. Analizziamo la loro utilità in base al tuo contesto:

+++++++++++++++++++++++++++++++++++++++
1. API Gateway
+++++++++++++++++++++++++++++++++++++++

Un'API Gateway è altamente consigliata in un'architettura a microservizi per i seguenti motivi:

- Punto di ingresso unico: Un'API Gateway funge da punto centrale attraverso cui i client (ad esempio, web app o mobile app) interagiscono con i microservizi, eliminando la necessità di conoscere la loro posizione e il loro contratto API.

- Autenticazione e autorizzazione: Può gestire centralmente la sicurezza, come autenticazione JWT, OAuth, o altro.

- Rate Limiting e Throttling: Protegge i tuoi microservizi da accessi eccessivi o attacchi DDoS.

- Orchestrazione semplice: Può instradare richieste multiple ai microservizi appropriati senza che il client debba preoccuparsi di orchestrare le operazioni (ad esempio, chiedere prima i dati di un cliente e poi quelli dei suoi pagamenti).

+++++++++++++++++++++++++++++++++++++++
2. Aggregatore
+++++++++++++++++++++++++++++++++++++++

Un aggregatore, rispetto a una semplice API Gateway, svolge un ruolo più complesso:

- Composizione di servizi: Se ci sono casi in cui i dati di più microservizi devono essere combinati in una risposta unica (es., dati del cliente, del prodotto e dei pagamenti per fornire una vista completa di un ordine), un aggregatore può orchestrare e combinare tali informazioni.
- Riduzione delle chiamate client-microservizi: Piuttosto che il client dover fare più chiamate, l'aggregatore compone i dati e li restituisce in un'unica risposta.

+++++++++++++++++++++++++++++++++++++++
Quando usarli insieme?
+++++++++++++++++++++++++++++++++++++++
In molti casi, è utile usare entrambi, ma con compiti distinti:

#. API Gateway:

  - Gestisce il routing delle richieste e gli aspetti di sicurezza e accesso.
  - Filtra e instrada le richieste ai microservizi o all'aggregatore.

#. Aggregatore:

  - Gestisce la logica di business complessa e le richieste che richiedono dati combinati da più microservizi.

+++++++++++++++++++++++++++++++++++++++
Caso specifico: il tuo sistema
+++++++++++++++++++++++++++++++++++++++

Nel tuo scenario, entrambi potrebbero essere utili per diverse ragioni:

- API Gateway:

  - Utile per centralizzare l'accesso ai microservizi come "Prodotti", "Clienti", "Pagamenti" e "Cargo".
  - Permetterà di gestire la sicurezza e limitare l'esposizione diretta dei microservizi ai client esterni.

- Aggregatore:

  - Può essere usato per costruire una funzionalità di "Gestione Ordine", che compone dati da "Clienti", "Prodotti" e "Pagamenti" per offrire una vista o un'operazione unificata (es. confermare un trasporto).

+++++++++++++++++++++++++++++++++++++++
Esempio di flusso con entrambi:
+++++++++++++++++++++++++++++++++++++++

#. Un cliente effettua una richiesta per registrare un prodotto per il trasporto.
#. La richiesta passa attraverso l'API Gateway, che la instrada al microservizio corretto o all'aggregatore.
#. L'aggregatore raccoglie i dati dai microservizi necessari (ad esempio, verifica il cliente, conferma il pagamento, registra il prodotto e aggiorna il carico del cargo).
#. Il risultato finale è restituito al client tramite l'API Gateway.

+++++++++++++++++++++++++++++++++++++++
AA - Conclusione
+++++++++++++++++++++++++++++++++++++++

- API Gateway: Quasi essenziale in un sistema a microservizi, per gestione di accesso, sicurezza e orchestrazione base.
- Aggregatore: Utile per semplificare la logica complessa e ridurre le chiamate client-microservizi.

Se il sistema è già suddiviso in microservizi, l'introduzione di entrambi i componenti è un passo naturale per migliorarne la scalabilità, la gestione e l'efficienza.

-----------------------------------------
Uso di eventi nel sistema cargo
-----------------------------------------

Con riferimento al problema precedente, vorrei qualche suggerimento e/o considerazione sulle situazioni in cui sia utile che uno dei servizi indicati interagisca con gli altri o gli utenti esterni al sistema, tramire emissione di 'eventi' usando un broker publish-subscribe

L'uso di un broker di messaggistica basato su un modello publish-subscribe può essere estremamente utile in un sistema a microservizi come il tuo. Questo approccio consente ai servizi di comunicare in modo asincrono e decouplato, migliorando la scalabilità e l'estensibilità. Di seguito, alcune considerazioni e suggerimenti relativi al tuo caso:

++++++++++++++++++++++++++++++++++++++++++++++
Quando usare il modello Publish-Subscribe
++++++++++++++++++++++++++++++++++++++++++++++

#. Eventi di dominio importanti:

  - Quando accadono eventi significativi nel sistema (ad esempio, un cliente registra un prodotto per il trasporto, un pagamento è stato completato, o un cargo è stato caricato), è utile notificare altri servizi interessati senza doverli contattare direttamente.
  - Esempio: Quando un pagamento viene completato, il servizio "Pagamenti" potrebbe emettere un evento PaymentConfirmed per notificare altri servizi, come "Cargo" e "Clienti".

#. Riduzione del coupling:

  - Invece di chiamare direttamente un altro servizio (tight coupling), l'emissione di eventi permette ai microservizi di essere indipendenti. I servizi che consumano gli eventi possono cambiare o essere sostituiti senza che il servizio emettitore debba essere modificato.

#. Workflow asincroni:

  - Per processi che non richiedono risposte immediate, l'uso di eventi consente di gestire il flusso senza rallentare il servizio emittente.
  - Esempio: Dopo che un cliente registra un prodotto, un evento ProductRegistered potrebbe avviare l'allocazione dello spazio sul cargo e la generazione di un documento di trasporto.

#. Notifiche agli utenti esterni:

  - Eventi pubblicati dal sistema possono essere usati per notificare gli utenti, ad esempio tramite e-mail, SMS o notifiche push.
  - Esempio: Un evento CargoShipped potrebbe attivare un servizio di notifica che informa il cliente che il prodotto è stato spedito.

++++++++++++++++++++++++++++++++++++++++++++++
Esempi specifici di utilizzo nel tuo sistema
++++++++++++++++++++++++++++++++++++++++++++++

#. 1. Microservizio "Clienti"

  - Evento: CustomerCreated
    - Usato per sincronizzare un nuovo cliente con altri sistemi (es., un CRM esterno o un modulo di reporting).

  - Evento: ProductRequested
    - Emettere un evento quando un cliente registra un prodotto per il trasporto, per avviare la catena di elaborazione.

#. 2. Microservizio "Prodotti"

  - Evento: ProductRegistered
    - Informare i servizi "Pagamenti" e "Cargo" che un nuovo prodotto è stato registrato e può essere elaborato.

  - Evento: ProductDelivered
    - Emettere un evento quando un prodotto viene consegnato, per aggiornare i dati del cliente o inviare notifiche.

#. 3. Microservizio "Pagamenti"

  - Evento: PaymentConfirmed
    - Notificare il servizio "Cargo" che può procedere con l'inserimento del prodotto nella lista di carico.

  - Evento: PaymentFailed
    - Notificare il cliente e fermare l'elaborazione del trasporto.

#. 4. Microservizio "Cargo"

  - Evento: CargoLoaded
    - Informare i sistemi di monitoraggio o generare un avviso ai clienti.

  - Evento: CargoShipped
    - Notificare il servizio di gestione dei clienti e il sistema di reportistica.

++++++++++++++++++++++++++++++++++++++++++++++
Vantaggi dell'approccio Publish-Subscribe
++++++++++++++++++++++++++++++++++++++++++++++

#. Scalabilità:

  - I consumatori possono essere scalati indipendentemente per elaborare grandi volumi di eventi.
  - Ad esempio, se i pagamenti generano molti eventi, il consumatore del servizio "Pagamenti" può scalare senza impattare gli altri.

#. Estensibilità:

  - Nuovi servizi possono essere aggiunti facilmente semplicemente iscrivendosi agli eventi esistenti.
  - Ad esempio, un nuovo servizio di analisi può consumare eventi ProductRegistered o PaymentConfirmed per produrre statistiche.

#. Resilienza:

  - I servizi che pubblicano eventi non si bloccano se un consumatore è temporaneamente offline: il broker (es. RabbitMQ, Kafka, o altri) può bufferizzare i messaggi fino a che i consumatori siano di nuovo disponibili.

++++++++++++++++++++++++++++++++++++++++++++++
Considerazioni sull'implementazione
++++++++++++++++++++++++++++++++++++++++++++++

#. Scelta del broker:

Usa un broker robusto come RabbitMQ, Apache Kafka, o AWS SNS/SQS.
Kafka è ideale per sistemi con eventi a elevato throughput e necessità di persistenza a lungo termine.
RabbitMQ è più semplice da configurare per comunicazioni tradizionali e con flussi di eventi a bassa latenza.

#. Progettazione degli eventi:

Usa un formato standard come JSON o Avro per gli eventi.
Assegna un ID univoco e un timestamp a ogni evento per garantire tracciabilità.
Esempio di payload evento PaymentConfirmed:

.. code::
 
    {
      "eventId": "12345",
      "eventType": "PaymentConfirmed",
      "timestamp": "2023-12-21T12:00:00Z",
      "data": {
        "paymentId": "67890",
        "customerId": "54321",
        "amount": 100.00
      }
    }

#. Gestione degli errori:

- Implementa una dead letter queue (DLQ) per gestire messaggi che non possono essere elaborati.
- Consenti ai consumatori di ripetere l'elaborazione degli eventi in caso di fallimenti temporanei.

#. Monitoraggio:

- Usa strumenti come Prometheus, Grafana, o Elasticsearch per monitorare il traffico degli eventi e rilevare eventuali colli di bottiglia.

++++++++++++++++++++++++++++++++++++++++++++++
Conclusione1
++++++++++++++++++++++++++++++++++++++++++++++
Integrare un sistema di eventi tramite un broker publish-subscribe è estremamente utile per un'architettura a microservizi come la tua. Migliorerà la scalabilità, la resilienza e la flessibilità, consentendo ai servizi di evolversi e interagire senza dipendenze rigide. Progettare eventi chiari e semantici è fondamentale per il successo dell'approccio.


-----------------------------------------
Ma forse ci sono anche svantaggi
-----------------------------------------

Grazie. Credo però che l'uso di eventi con meccanismi publish-subscribe abbia anche qualche svantaggio e ponga diverse problematiche non banali


Hai assolutamente ragione: l'uso di eventi e meccanismi publish-subscribe porta grandi vantaggi, ma introduce anche una serie di sfide e svantaggi che è importante considerare attentamente. Ecco un elenco di problematiche non banali associate a questo approccio, con possibili strategie per mitigarle:

#. 1. Complessità architetturale

  - Problema:

    - L'introduzione di un broker e del paradigma publish-subscribe aumenta la complessità del sistema. Devi gestire la configurazione del broker, garantire la consegna dei messaggi e monitorare l'infrastruttura degli eventi.
  
  - Mitigazione:

    - Usa soluzioni consolidate e ben documentate come Apache Kafka, RabbitMQ o AWS SNS/SQS.
    - Introduci gradualmente gli eventi in base alle necessità, piuttosto che usarli ovunque.
    - Automatizza il deployment e il monitoraggio del broker.

#. 2. Difficoltà di tracciamento (Tracing)
  
    - Problema:
  
      - In un sistema asincrono basato su eventi, è difficile tracciare il flusso di una richiesta end-to-end, specialmente quando molti eventi sono correlati a una singola azione utente.
    
    - Mitigazione:
  
      - Usa tecnologie di distributed tracing come OpenTelemetry o strumenti specifici come Jaeger e Zipkin.
      - Includi correlation ID negli eventi per poterli raggruppare e ricostruire i flussi di elaborazione.

#. 3. Mancanza di controllo diretto

  - Problema:

    - Il servizio che pubblica un evento non ha controllo su come e quando i consumatori elaborano l'evento.
    - I consumatori potrebbero elaborare gli eventi in ritardo o non rispettare le tue aspettative.
  - Mitigazione:

    - Definisci contratti chiari per gli eventi (es., specifiche Avro o JSON Schema).
    - Usa meccanismi di timeout e retry per gestire ritardi e problemi nei consumatori.
    - Implementa una dead letter queue (DLQ) per eventi non elaborabili.

#. 4. Ordine e duplicazione degli eventi
  
    - Problema:
  
      - Garantire l'ordine degli eventi o evitare duplicazioni è complesso.
      - In certi scenari, l'elaborazione fuori ordine può portare a inconsistenze nei dati.
    - Mitigazione:
  
      - Usa un broker che supporti partizionamento e ordinamento nativo (es., Kafka).
      - Disegna i consumatori per essere idempotenti (ossia, capaci di gestire lo stesso evento più volte senza effetti collaterali).
      - Usa sequenze o timestamp negli eventi per rilevare ed eventualmente gestire eventi fuori ordine.

#. 5. Gestione degli errori
  
    - Problema:
  
      - Se un consumatore fallisce durante l'elaborazione, l'evento potrebbe andare perso o bloccarsi in un ciclo di tentativi.
      - Gli errori possono propagarsi silenziosamente senza essere visibili.
    - Mitigazione:
  
      - Implementa una strategia di retry con backoff esponenziale per i consumatori.
      - Configura una DLQ per catturare eventi problematici e analizzarli.
      - Usa metriche e log per identificare rapidamente consumatori che falliscono.

#. 6. Overhead infrastrutturale
    
      - Problema:
    
        - L'introduzione di un broker richiede risorse aggiuntive, sia in termini di hardware che di competenze tecniche.
        - Un broker mal configurato può diventare un single point of failure.
      - Mitigazione:
    
        - Usa servizi gestiti (es., AWS MSK per Kafka o Google Pub/Sub) p
          per ridurre il carico di gestione.
        - Configura il broker per avere ridondanza (es., partizioni replicate in Kafka).
        - Implementa strategie di failover e disaster recovery.

#. 7. Dipendenze implicite
      
        - Problema:
      
          - Sebbene il paradigma publish-subscribe riduca il coupling diretto, introduce dipendenze implicite: i consumatori dipendono dagli eventi pubblicati, ma tali dipendenze non sono sempre visibili o formalmente documentate.
        
        - Mitigazione:
      
          - Usa un Event Schema Registry (ad esempio, Confluent Schema Registry per Kafka) per mantenere una documentazione aggiornata degli eventi.
          - Definisci i contratti per gli eventi in modo esplicito e versiona correttamente gli eventi per gestire modifiche nel tempo.


#. 8. Debugging più complesso
  
    - Problema:
  
      - Individuare la causa di un problema in un sistema distribuito basato su eventi richiede strumenti e competenze specializzate.
    - Mitigazione:
  
      - Implementa logging strutturato per gli eventi.
      - Includi nei log informazioni utili per identificare l'origine e il percorso dell'evento (es., timestamp, producer, consumer).

#. 9. Overhead sui dati
    
      - Problema:
    
        - Gli eventi possono contenere molti dati per supportare casi d'uso generici, aumentando il consumo di memoria e banda.
      - Mitigazione:
    
        - Riduci i dati negli eventi, includendo solo ciò che è strettamente necessario.
        - Usa un approccio event-carried state transfer (l'evento trasporta solo dati rilevanti) o un approccio event sourcing se il sistema richiede un livello maggiore di persistenza.

#. 10. Problemi di consistenza

    - Problema:
      
      - Un modello basato su eventi può introdurre consistenza eventuale, creando situazioni in cui diversi servizi hanno uno stato divergente per un certo periodo di tempo.
    
    - Mitigazione:
    
      - Progetta il sistema per tollerare inconsistenze temporanee.
      - Usa una CQRS (Command Query Responsibility Segregation) se necessario per separare i comandi dagli eventi di lettura.

++++++++++++++++++++++++++++++++++++++++++++++
Conclusione2
++++++++++++++++++++++++++++++++++++++++++++++

Sebbene il modello publish-subscribe porti benefici significativi, la sua implementazione non è priva di sfide. Per mitigare questi problemi, è cruciale:

- Progettare il sistema tenendo conto delle problematiche di tracciabilità, ordine e gestione degli errori.
- Avere una chiara strategia di logging, monitoraggio e testing.
- Introdurre gli eventi in modo incrementale, partendo dai casi d'uso in cui offrono il massimo valore.

Adottando le giuste tecniche e strumenti, i benefici possono superare ampiamente i costi e i rischi associati.





Prelievo Ematico: PER ESEGUIRE L' ESAME E' NECESSARIO ESSERE A DIGIUNO DALLA MEZZANOTTE, E' AMMESSO BERE ACQUA. 
L' ASSUNZIONE O LA SOSPENSIONE DI TERAPIE FARMACOLOGICHE IN CORSO VA PREVENTIVAMENTE CONCORDATA CON IL MEDICO CURANTE. 
PARTICOLARITA': NEI BAMBINI FINO AL COMPIMENTO DEI 2 ANNI DI ETA', 
NON E' NECESSARIO IL DIGIUNO PRIMA DEL PRELIEVO EMATICO, SALVO DIVERSA INDICAZIONE DEL PRESCRITTORE NELLA RICHIESTA. 
Prelievo Ematico: PER ESEGUIRE L' ESAME E' NECESSARIO ESSERE A DIGIUNO DALLA MEZZANOTTE, E' AMMESSO BERE ACQUA. 
L' ASSUNZIONE O LA SOSPENSIONE DI TERAPIE FARMACOLOGICHE IN CORSO VA PREVENTIVAMENTE CONCORDATA CON IL MEDICO CURANTE. 
PARTICOLARITA': NEI BAMBINI FINO AL COMPIMENTO DEI 2 ANNI DI ETA', NON E' NECESSARIO IL DIGIUNO PRIMA DEL PRELIEVO EMATICO, 
SALVO DIVERSA INDICAZIONE DEL PRESCRITTORE NELLA RICHIESTA. ; 
PER LA CORRETTA ESECUZIONE DELL'ANALISI CREATINCHINASI (CPK O CK), SI RACCOMANDA DI NON FARE ATTIVITA' SPORTIVA 
PRIMA DI EFFETTUARE IL PRELIEVO Urine: RACCOGLIERE IL CAMPIONE DELLE URINE DOPO IL RIPOSO NOTTURNO EVITANDO 
ATTIVITÀ FISICA INTENSA E RAPPORTI SESSUALI NELLA GIORNATA PRECEDENTE. 
NON RACCOGLIERE LE URINE DURANTE IL CICLO MESTRUALE. 
LAVARE LE MANI E TOGLIERE IL TAPPO SVITANDO IN SENSO ANTIORARIO. 
APPOGGIARE SU SUPERFICIE PULITA IL COPERCHIO CAPOVOLTO. 
NON TOCCARE LE SUPERFICI INTERNE DEL BARATTOLO E DEL COPERCHIO. 
LAVARE I GENITALI CON ACQUA E SAPONE, SCARTARE IL PRIMO GETTO DI URINA, RACCOGLIERE IL SUCCESSIVO 
E RIEMPIRE IL CONTENITORE NON PIÙ DEI ¾ DELLA SUA CAPACITÀ. 
RICHIUDERE ACCURATAMENTE IL CONTENITORE, AVVITANDO IL COPERCHIO IN SENSO ORARIO. 
PER I BAMBINI UTILIZZARE IL SACCHETTO ADESIVO REPERIBILE IN FARMACIA


GPT (ALT) (TRANSAMINASI) - sangue
GOT (AST) (TRANSAMINASI) - sangue
BILIRUBINA REFLEX - sangue
CALCIO TOTALE - sangue
COBALAMINA (VIT.B12) - sangue
COLESTEROLO HDL - sangue
COLESTEROLO LDL DETERMINAZIONE DIRETTA - sangue
COLESTEROLO TOTALE - sangue
CREATINCHINASI (CPK o CK) - sangue
CREATININA - sangue
FERRITINA - sangue
FERRO SIERICO
FOLATO - sangue
GAMMA GLUTAMIL TRANSPEPTIDASI (GAMMA GT) - sangue
GLUCOSIO - sangue
EMOGLOBINA (Hb) GLICATA - sangue
POTASSIO - sangue
ELETTROFORESI PROTEINE SIERICHE - sangue
SODIO - sangue
TRIGLICERIDI - sangue
URATO - sangue
UREA - sangue
URINE ESAME COMPLETO
VITAMINA D 25-OH - sangue
EMOCROMO COMPL.CON FORMULA E PIASTRINE - sangue
TSH REFLEX (CON EVENTUALI FT3/FT4) - sangue
PSA REFLEX (CON EVENTUALE PSA FREE) - sangue

