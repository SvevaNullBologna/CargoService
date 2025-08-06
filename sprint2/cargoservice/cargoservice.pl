%====================================================================================
% cargoservice description   
%====================================================================================
mqttBroker("192.168.137.1", "1883", "sonar/events").
event( productDetected, productDetected(T) ).
event( anomalyDetected, anomalyDetected(T) ).
event( anomalyFixed, anomalyFixed(T) ).
dispatch( devicesStart, devicesStart(T) ).
dispatch( devicesStop, devicesStop(T) ).
event( reactorReady, reactorReady(T) ).
event( detectorReady, detectorReady(T) ).
dispatch( ready, ready(T) ).
dispatch( notReadyYet, notReadyYet(T) ).
dispatch( correctDistanceDetected, correctDistanceDetected(T) ).
dispatch( keepDetecting, keepDetecting(T) ).
%====================================================================================
context(ctx_sonarservice, "localhost",  "TCP", "8004").
 qactor( sonarservice, ctx_sonarservice, "it.unibo.sonarservice.Sonarservice").
 static(sonarservice).
  qactor( reactor, ctx_sonarservice, "it.unibo.reactor.Reactor").
 static(reactor).
