%====================================================================================
% cargoservice description   
%====================================================================================
mqttBroker("192.168.137.1", "1883", "sonar/events").
event( productDetected, productDetected(T) ).
event( anomalyDetected, anomalyDetected(T) ).
event( anomalyFixed, anomalyFixed(T) ).
event( sonardata, distance(D) ).
%====================================================================================
context(ctx_sonarservice, "localhost",  "TCP", "8004").
 qactor( listener, ctx_sonarservice, "it.unibo.listener.Listener").
 static(listener).
  qactor( reactor, ctx_sonarservice, "it.unibo.reactor.Reactor").
 static(reactor).
