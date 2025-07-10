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
cargosystem25
===================================

---------------------------------------
Requisiti
---------------------------------------

Progettare e costruire un :blue:`sistema software` per il carico/scarico di prodotti  
in modo automatizzato mediante robot-DDR su di un cargo navale.
I prodotti da caricare/scaricare devono essere stati precedentemente registrati su database.
 
Versione precedente: :ref:`Cargo24`

-----------------------------------------
Impostazioni preliminari
-----------------------------------------

- Analisi dei requisiti e del problema: individuazione dei :blue:`BoundedContexts` applicativi
- Introduzione alla Clean Architecture e ai principi SOLID
- Comunicazione tra componenti software in termini di :blue:`Business Concepts`
- Introduzione allo sviluppo agile con ``SCRUM``
- Vesro componenti con :brown:`loose-coupling e high-coesion`
- Idea di :blue:`DevOps` e sua evoluzione
- Micro-servizi di supporto attivabili con il file 
  `cargoenvBase.yml`_  (**cargo2025/yamls/cargoenvBase.yml**)



-----------------------------------------
Progetto cargoproduct
-----------------------------------------

:slide3:`Goal`: Costruire un applicativo Java che realizza la logica **CRUD** di gestione di prodotti in uno storage 
di diverse forme: inizialmente una semplice lista in memoria volatile, poi un database MongoDB.

        .. image::  ./_static/img/Cargo/ProductServiceLogic.JPG
           :align: center 
           :width: 60%  

+++++++++++++++++++++++++++++++++++++
Key-points cargoproduct
+++++++++++++++++++++++++++++++++++++

- Impostazione del Workspace Eclipse e di un progetto Gradle con relativo build file.
- Adapter (``AdapterStorage``) per rendere la logica applicativa indipendente dai dispositivi 
  usati per la persistenza.
- Predisposizione di ``AdapterStorage`` per  selezionare la memoria volatile o il database MongoDB
  usando variabili di ambiente. In assenza, uso del singleton ``StorageVolatile``.
- Testing in modo automatizzato con JUnit.
- Logging locale su file.
- Logging su ElasticSearch e Kibana, attivati come micorservizi ELK su Docker.
- Deployment mediante file ``cargoproduct-1.0.jar`` di un componente software che:

   - non è autonomo
   - produce effetti perchè esegue procedure specificate nell'interfaccia ``ICrudOps``
   - nel caso di memoria piena, esegue il metodo ``createProduct`` restituendo una stringa generica di errore 

 

-----------------------------------------
Progetto cargoserviceM2M
-----------------------------------------

:slide3:`Goal`: rendere il sistema del :ref:`Progetto cargoproduct` disponibile in rete come (micro)servizio web 
per altri programmi  (interazione **M2M**).

        .. image::  ./_static/img/m2m/cargoserviceM2M.JPG
           :align: center 
           :width: 70%  

Appunti per lo sviluppo del prodotto: :ref:`cargoserviceM2M`

+++++++++++++++++++++++++++++++++++++
Key-points cargoserviceM2M
+++++++++++++++++++++++++++++++++++++

.. File cargoservice.properties per  selezionare la memoria volatile o il database MongoDB

- Uso di Spring e di un componente @RestController per rendere la logica applicativa accessibile via rete 
  ad altri programmi (interazione **M2M**-:brown:`RESTful`).
- @RestController come componente di Spring che riceve richieste HTTP e risponde con oggetti JSON 
  invocando le operazioni dell'interfaccia ``ICrudOps`` realizzate dal singleton ``ProductServiceLogic``.
- Registrazione su Eureka del servizio con nome ``cargoserviceM2M``.
- Interazioni via HTTP (sincrone) e via Web-sockets (asincrone).
- Supporto lato server per interazioni via WS: ``WSCargoM2M`` 
- Supporto lato server per interazioni via MQTT (emissione di eventi e ricezione di allarmi): ``CargoMqtt``
  che crea una 'conessione-MQTT'  mediante la coppia di topic :slidekp:`cargoin-cargoevents` .
- Problema degli accessi concorrenti e come evitare la possibile duplicazione di prodotti: il 
  componente :slidekp:`ActorForPsl` come primo esempio di un attore che sequenzializza le richieste
  di creazione di un prodotto, evitando di modificare il codice del :ref:`Progetto cargoproduct`
  per definire come :slidekp:`synchronized` il metodo ``createProduct``.   


        .. image::  ./_static/img/m2m/cargoM2MActorForPsl.jpg
           :align: center 
           :width: 80%  



+++++++++++++++++++++++++++++++++++++
Uso di cargoserviceM2M
+++++++++++++++++++++++++++++++++++++

.. list-table::
    :widths: 35,65
    :width: 100%
    
    * - **PSCallerHTTP.java**
      - Usa :blue:`HttpURLConnection` per effettuare *callPost* e *callGet* al servizio ``cargoserviceM2M`` di 
        cui conosce l'IP.    
    * - **PSDiscoverCallerHTTP.java**
      - Usa :blue:`HttpURLConnection` per effettuare *callPost* e *callGet* al servizio ``cargoserviceM2M``,
        il cui IP  viene scoperto mediante Eureka.
    * - **PSLDiscoverCallerHTTPInteraction.java**
      - Usa :blue:`Interaction` e :blue:`ConnectionFactory` (libreria ``unibo.basicomm23-1.0``)
        per effettuare *forward* e *request* 
        al servizio ``cargoserviceM2M``, il cui IP  viene scoperto mediante Eureka.
    * - **PSCallerWS.java**
      - Usa :blue:`javax.websocket` per effettuare interazioni asincrone con il servizio ``cargoserviceM2M`` di 
        cui conosce l'IP.    
    * - **PSLCallerWSInteraction.java**
      - Usa :blue:`Interaction` e :blue:`ConnectionFactory` (libreria ``unibo.basicomm23-1.0``) per effettuare 
        interazioni asincrone con il servizio ``cargoserviceM2M`` di 
        cui conosce l'IP.    
    * - **CargoM2MObserver.java**
      - Usa ::blue:`Interaction` e :blue:`ConnectionFactory` (libreria ``unibo.basicomm23-1.0``) 
        per percepire gli eventi emessi da ``cargoserviceM2M`` sulla topoc MQTT ``cargoevents``
        e per mettere un allarme sulla topoc MQTT ``cargoain``
 
 

   
+++++++++++++++++++++++++++++++++++++
Deployment di cargoserviceM2M
+++++++++++++++++++++++++++++++++++++

- Distribuzione del prodotto software in forma di micro-servizio su Docker.

File `DockerfileM2M`_ per la generazione della immagine *cargoservice:3.0*
e docker-compose file **cargo2025/yamls/cargoServiceNoGui.yml**,
con variabili di ambiente che permettono di usare ``MongoDB``:

  .. code::

    services:
      cargoservice:
        image: cargoservice:3.0 
        #image: natbodocker/cargoservice:3.0
        container_name: cargoservice
        environment:
          - EUREKA_INSTANCE_LEASE_RENEWAL_INTERVAL_IN_SECONDS=60
          - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka:8761/eureka/
          - MONGO_URL=mongodb://mongosrv:27017
        ports:
          - 8111:8111/tcp
          - 8111:8111/udp
        networks:
          - cargo-network

    networks:
      cargo-network:
        external: true


+++++++++++++++++++++++++++++++++++++
cargoserviceM2M su Raspberry
+++++++++++++++++++++++++++++++++++++

File `DockerfileM2MRasp`_ per la generazione della immagine *imgservicerasp:1.0*
e docker-compose file: **cargo2025/yamls/cargoServiceRasp.yml.yml**

.. code::

    services:
          
      cargoservice:
        image: imgservicerasp:1.0 
        #image: natbodocker/imgservicerasp:1.0
        container_name: cargoraspservice
        environment:
          - EUREKA_INSTANCE_LEASE_RENEWAL_INTERVAL_IN_SECONDS=60
          - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka:8761/eureka/
          - MONGO_URL=mongodb://192.168.1.132:27017
        ports:
          - 8111:8111/tcp
          - 8111:8111/udp


 

-----------------------------------------
Progetto cargoserviceM2MGui
-----------------------------------------

:slide3:`Goal`: dotare il sistema del :ref:`Progetto cargoserviceM2M` di una GUI per la interazione uomo-macchina
(interazione **H2M**).

        .. image::  ./_static/img/m2m/cargoserviceM2MGui.jpg
           :align: center 
           :width: 60%  

Appunti per lo sviluppo del prodotto: :ref:`cargoserviceM2MGui`

+++++++++++++++++++++++++++++++++++++
Key-points cargoserviceM2MGui
+++++++++++++++++++++++++++++++++++++

- Uso di Spring e di un componente @Controller per rendere la logica applicativa accessibile via rete ad 
  esseri umani (interazione **H2M**).
- Realizzare una GUI in HTML e Javascript che invia comandi e riceve sia risposte sia aggiornamenti.
- Aggiornamento della pagina mediante Theamleaf
- Uso di form e dell'operatore ``fetch``  per l'invio di comandi come messaggi HTTP.
- Discovery del servizio ``cargoserviceM2M`` mediante Eureka
- Definizione di un caller (``GuiCallerHTTP``) che usa il servizio con Interaction su HTTP, 
  sperimentando diversi tipi  di risposta da part del @Controller


TODO

- Multiple UI, Aggregatori, API Gateway
- User experience
- BFF (Beckends Bor Frontends)

-----------------------------------------
Sistema cargoserviceM2M 
-----------------------------------------

:slide3:`Goal`: costruire il sistema facendo interagire due micro-servizi deployed su Docker

        .. image::  ./_static/img/m2m/cargoserviceM2MAndGui.jpg
           :align: center 
           :width: 60%  

- Deployment mediante file docker-compose: *cargo2025/yamls/cargowareservice.yml* 

  .. code::

    services:
          
      cargoservice:
        #image: cargoservice:3.0  
        image: natbodocker/cargoservice:3.0 
        container_name: cargoservice
        environment:
          - EUREKA_INSTANCE_LEASE_RENEWAL_INTERVAL_IN_SECONDS=60
          - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka:8761/eureka/
          - MONGO_URL=mongodb://mongosrv:27017
    #      - RASP_ADDR=192.168.1.248   
        ports:
          - 8111:8111/tcp
          - 8111:8111/udp
        networks:
          - cargo-network


      cargoservicegui:
        #image: cargoservicespringnat:3.0 
        image: natbodocker/cargoservicespringnat:3.0 
        container_name: cargoservicegui
        environment:
          - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka:8761/eureka/
          - SERVICE_URL=http://localhost:8075   ##set to http://CommUtils.getMyPublicip() by PSLControllerWithQak 
          - PRODUCT_SERVICEADDR=cargoservice    
        ports:
          - 8075:8075/tcp
        depends_on:
          - cargoservice
        networks:
          - cargo-network

    networks:
      cargo-network:
        external: true


-----------------------------------------
cargoserviceM2M con eventi
-----------------------------------------

+++++++++++++++++++++++++++++++++++++++
Ossevazioni preliminari
+++++++++++++++++++++++++++++++++++++++

- I (micro)servizi non sono solo anemici CRUD-Wrappers
- Il logging distribuito è un meccanismo di monitoraggio e registrazione delle attività e non un meccanismo 
  di comunicazione utile a realizzare in modo generale ed efficiente la comunicazione tra componenti software.
- I (micro)servizi sono concettualmente enti autonomi che possono emettere/percepire eventi e interagire tra loro
  con meccanismi asincroni (es. Web-sockets, publish-subscribe) di comunicazione.

+++++++++++++++++++++++++++++++++++++++
Pub-sub o WebSocket?
+++++++++++++++++++++++++++++++++++++++

.. list-table::
    :widths: 15,35,50
    :width: 100%
    
    * - **Caratteristica**
      - **Broker**    
      - **WebSocket**
    * - Modello
      - Pub-Sub    
      - Bidirezionale, full-duplex
    * - Decoupling
      - Alto    
      - Basso
    * - Scalabilità
      - Alta    
      - Dipende dall'implementazione
    * - Flessibilità
      - Alta    
      - Più limitata
    * - Persistenza
      - Dipende dal broker   
      - Dipende dall'implementazione del server WebSocket
    * - Utilizzi tipici
      - Sistemi distribuiti, streaming, IoT    
      - Applicazioni in tempo reale, chat, notifiche push

:slide3:`Goal`: estendere il servizio del :ref:`Progetto cargoserviceM2M` in modo che possa emettere informazioni 
in forma di :blue:`eventi` percepibili e visualizzabili nella GUI del :ref:`Progetto cargoserviceM2MGui`

+++++++++++++++++++++++++++++++++++++++
Key-points cargoserviceM2M con eventi
+++++++++++++++++++++++++++++++++++++++

- Supporti per interazioni pub-sub in MQTT (**Message Queuing Telemetry Transport**)
- Introduzione di un broker MQTT (es. **Mosquitto**) per la comunicazione asincrona tra i servizi
- Introduzione di un supporto MQTT lato service per la pubblicazione degli eventi
- Introduzione di un supporto MQTT lato GUI per la ricezione degli eventi pubblicati dal servizio

.. - Introduzione ai tipi logici di messggio `appllevel`_ (*I Protocolli e i dati*)
.. - Reference: Enterprise Integration Patterns (**EIP**)  

.. image::  ./_static/img/m2m/cargoserviceM2MEvents.jpg
  :align: center 
  :width: 60%  

Gli eventi di allarme emessi dal servizio sono percepiti dalla GUI per essere visualizzati in modo appropriato.

.. image::  ./_static/img/m2m/cargoserviceM2MGuiEvents.jpg
  :align: center 
  :width: 60%  


**CargoM2MObserver** viene introdotto come esempio 
di un componente che percepisce l'evento emesoo alla creazione di un prodotto 
e come esempio di un agente esterno che possa emettere un evento di allarme
sulla topic ``cargoain``
che possa a sua volta essere percepito dal servizio, modificandone il suo comportamento.
 

.. image::  ./_static/img/m2m/cargoserviceM2MEventsAndObs.jpg
  :align: center 
  :width: 80%  
 
La percezione di un allarme da parte del servizio deve incidere  sia sul ``Controller`` sia su ``WSCargoM2M``
Per questo è utile un oggetto  usato da entrambi che dovrebbe operare come un FSM
sensibile sia ai comandi sia agli eventi.


===================================
cargosystem25 con attori qak
===================================

-----------------------------------------
Progetto cargoservice
-----------------------------------------

:slide3:`Goal`: costruire un micorservizio basato sugli attori.

+++++++++++++++++++++++++++++++++++++
Key-points cargoservice
+++++++++++++++++++++++++++++++++++++

-----------------------------------------
Progetto cargoserviceQakGui
-----------------------------------------

:slide3:`Goal`: dotare il sistema del :ref:`Progetto cargoservice` di una GUI per la interazione uomo-macchina.

+++++++++++++++++++++++++++++++++++++
Key-points cargoserviceQakGui
+++++++++++++++++++++++++++++++++++++

-----------------------------------------
Progetto cargoserviceM2MQakLocal
-----------------------------------------

+++++++++++++++++++++++++++++++++++++++
Key-points cargoserviceM2MQakLocal
+++++++++++++++++++++++++++++++++++++++

:slide3:`Goal`: dotare il sistema del :ref:`Progetto cargoservice` di una GUI per la interazione uomo-macchina
evitando la comunicazione via rete tra il RestController della GUI e il serviceqak.
