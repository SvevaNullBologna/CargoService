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


.. _Cargo Overview: file:///C:/Didattica2025/mcrsv24/cargo2025/userDocs/cargoOverview.html
.. _Cargo History: file:///C:/Didattica2025/mcrsv24/cargo2025/userDocs/cargoHistory.html
.. _CorePOJO: file:///C:/Didattica2025/mcrsv24/cargo2025/userDocs/cargoOverview.html#pojo
.. _actor0: file:///C:/Didattica2025/mcrsv24/cargo2025/userDocs/cargoHistory.html#actor0
.. _PojoSpring: file:///C:/Didattica2025/mcrsv24/cargo2025/userDocs/cargoOverview.html#cargowareserviceSpring

.. _REST: https://en.wikipedia.org/wiki/Representational_state_transfer
.. _SonarAndLed: ../../../../it.unibo.raspIntro2024/code/python/SonarAndLed.py   


================================
Cargo24
================================


----------------------------------
Servizi e GUI
----------------------------------

Si veda  `Cargo Overview`_,  `Cargo History`_.   


.. :guilabel:`Cargo Overview`

.. .. sidebar:: Un primo sistema 
  
..  .. figure:: ./_static/img/m2m/cargoserviceM2MAndGui.jpg
..      :alt: Un sistema
..      :width: 200px



.. .. sidebar:: Sample Sidebar

.. .. sidebar:: Una sidebar per la sezione 1

..   Questa sidebar contiene informazioni aggiuntive sulla sezione 1.

.. .. raw:: html

..   <a href="https://www.google.com">Vai su Google</a> 





.. list-table::
    :widths: 30,70
    :width: 100%
    
    * - cargoproduct  `CorePOJO`_

        .. image::  ./_static/img/Cargo/ProductServiceLogic.JPG
           :align: center 
           :width: 100%  
      - 
         Using ``StorageVolatile`` 

        
        - Clean architecture: la ripartizione domain-adapter
        - Test automatizzato con JUnit
        - Logging locale

        **caller**: ``ProductServiceCaller`` in Cargo25


    * - :ref:`cargoserviceM2M`  `PojoSpring`_

        .. image::  ./_static/img/m2m/cargoserviceM2M.jpg
           :align: center 
           :width: 100%  

      - 
        Spring RestController on ``9111`` - Register as ``M2MPRODUCTSERVICE`` - CorsConfig

        - Uso di Spring e RestController con CleanArchitecture
        - Interazioni via HTTP e via Web-sockets
        - Introduzione all'uso di ELK e al logging distribuito
        - Deployment in Docker

        **callers**: ``PSLCallerHTTP, PSLDiscoverCallerInteraction, PSLDiscoverCallerHttp, WebSocketClient`` 
    
    * - cargoserviceM2MGui

        .. image::  ./_static/img/m2m/cargoserviceM2MGui.jpg
           :align: center 
           :width: 100%  
     
      - 
        Spring Controller on ``9110`` - Interacts WS with ``cargoserviceM2M`` (discovered)

        - Pagina HTML che invia su WS comandi a :ref:`cargoserviceM2M`

        **callers**:  ``WebSocketClient``

    * - cargoserviceM2M and cargoserviceM2MGui

        **callers**: ``PSLCallerHTTP`` e ``WebSocketClient``
      - 
        .. image::  ./_static/img/m2m/cargoserviceM2MAndGui.jpg
           :align: center 
           :width: 100%  


    * - cargoservice 

        Si veda `actor0`_

        .. image::  ./_static/img/Cargo/ProductService.JPG
           :align: center 
           :width: 100%  

      - 
        qak on 8111 - Register as PRODUCTSERVICE 
        
        Use StorageVolatile in local / Mongo in Docker

        **callers**: ProductServiceCallerJsonTcp, ProductServiceCallerCoap, 
        ProductServiceCallerMqtt, ProductServiceDiscoverCallerTcp
    * - cargoserviceQakGUI
      - 
        Spring RestController on 8075  - Interacts TCP with cargoservice remote

        **callers**: PSLCallerHTTP,
    * - cargoserviceM2MQakLocal
      - 
        Spring RestController - Activates qak. Interacts local with guiworker 


 

----------------------------------
C:\Didattica2024\qak24\cargo:
----------------------------------

Nuova versione che prova coi colori Latex e con il ruolo 4

#.  Microservizio di memorizzazione prodotti:  
#.  Come actor attorno a un CorePOJO ``ProductServiceLogic``
#.  ``ProductServiceLogic`` usa ``AdapterStorage`` per realizzare persistenza in memoria 
    (``AdapterStorageList``) oppure su MongoDB (``AdapterStorageMongo``)
#.  Che usa logback.xml, elasticseacrh e logstash attivati usando ``docker-compose-EFKOnly.yml`` 
#.  Che si avvale di ``ProductServiceCallerCoap`` come client
#.  Deployed mediante Dockerfile e ``docker-compose.yml``
#.  Che fa parte di un insieme più ampio di microservizi

-----------------------------------------
PSLCallerHTTP
-----------------------------------------

- Ho una applicazione Java APP e un servizio S che accetta messaggi via HTTP. 
  APP attiva un thread T che chiama una funzione makeCall, la quale attiva una java.net.HttpURLConnection  
  con S per invia un messaggio M1 e attende (quindi all'interno di T) la risposta sulla connessione 
  (che richiede 4sec). Subito dopo avere attivato T, 
  la applicazione APP invoca di nuovo makeCall per inviare a S un messaggio M2, 
  cui il server risponde in 2sec. 
  E' possibile che la prima invocazione di makeCall riceva la risposta relativa a M2?



E' possibile che la prima invocazione di makeCall riceva la risposta relativa a M2. 
Questo scenario è un classico esempio di race condition in programmazione concorrente.

Perché può accadere?
- Ordine non garantito delle risposte: Quando si effettuano chiamate HTTP, non c'è alcuna garanzia che le risposte arrivino nello stesso ordine in cui sono state inviate le richieste. Il tempo di risposta può variare a causa di diversi fattori come la latenza di rete, il carico del server, ecc.
- Condivisione della connessione: Se le due invocazioni di makeCall condividono la stessa connessione HTTP (o la stessa pool di connessioni), è possibile che le risposte vengano confuse.
- Problemi di sincronizzazione: Se non viene utilizzata una corretta sincronizzazione tra il thread T e il codice che gestisce le risposte, è possibile che una risposta venga associata alla richiesta sbagliata.

Come prevenire questo problema?

- Identificatori univoci per le richieste: Assegna un ID univoco a ciascuna richiesta e
  includi questo ID nel messaggio inviato al server. Il server può quindi includere l'ID nella risposta, 
  permettendo al client di correlare correttamente le richieste e le risposte.
- Gestione asincrona delle richieste: Utilizza un meccanismo asincrono per gestire le richieste HTTP, 
  come le future o le promesse. In questo modo, puoi associare una callback a ciascuna richiesta e ricevere 
  la risposta corretta quando arriva.
- Utilizzo di una libreria HTTP client apposita: Librerie come Apache HttpClient o OkHttp offrono 
  funzionalità avanzate per la gestione delle richieste HTTP, come la gestione delle connessioni, 
  la gestione degli errori e la gestione delle risposte asincronamente.
- Sincronizzazione corretta: Se si utilizzano meccanismi di sincronizzazione, 
  assicurarsi che siano corretti e non introducano deadlock o altre problematiche.

----------------------------------
Il mcrsrv ProductService 
----------------------------------


- **ProductService** è una applicazione software che viene distribuita mediante una immagine 
  docker memorizzata su DockerHub.
- All'esterno, *ProductService* appare come un servizio RESTful che espone un endpoint 
  (alla porta ``6001``) per 
  memorizzare prodotti e ottenere informazioni su di essi.
- :blue:`ProductService` può essere cosiderato come un :brown:`microservizio` che fa potrebbe fare parte di 
  un sistema più grande di microservizi.
- Per interagire con :blue:`ProductService` è possibile usare un client RESTful che usa il protocollo CoAP.
  Al momento l'uso di HTTP via curl o Postman è escluso, ma vi è però la possibilità di usare un client 
  TCP sempre verso la porta ``6001``.
- l'URL di aceesso alla risorsa a :blue:`ProductService` è  ``http://<HOST>:6001/ctxcargo/productservice``

    .. code::
      
      protocollo://[username[:password]@]host[:porta][</percorso>][?querystring][#fragment]
    
    Si veda: https://it.wikipedia.org/wiki/Uniform_Resource_Locator



- i contenuti dei messaggi da trasmettere via Coap sono 'standardizzati' nella forma 
  :slide2:`msg(MSGID,MSGTYPE,SENDER,RECEIVER,CONTENT,N)`.

Usando le utility di :slide2:`unibo.basicomm23`, possiamo costruire e inviare un messaggio di richiesta in questo modo:

.. code::

  val CoapConn = 
    ConnectionFactory.createClientSupport(ProtocolType.coap, "localhost:6001","ctxcargo/productservice")

  val msgCreate = MsgUtil.buildRequest(name, "createProduct", "product(5,p5,50)", "productservice")
  //msg(createProduct,request,SENDER,productservice,product(5,p5,50),N)
	
  var Answer    = CoapConn.request( msgCreate )  

.. _TestProduct: ../../../../../qak24/cargo/src/main/java/test/TestProduct.java
.. _TestCargo: ../../../../../qak24/cargo/src/main/java/test/TestCargo.javaxx
.. _Cargo logback.xml: ../../../../../qak24/cargo/src/main/resources/logback.xml








 



 


    