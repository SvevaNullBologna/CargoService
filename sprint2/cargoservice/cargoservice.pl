%====================================================================================
% cargoservice description   
%====================================================================================
event( productDetected, productDetected(T) ).
event( anomalyDetected, anomalyDetected(T) ).
event( anomalyFixed, anomalyFixed(T) ).
dispatch( devicesStart, devicesStart(T) ).
dispatch( devicesStop, devicesStop(T) ).
event( reactorReady, reactorReady(T) ).
event( detectorReady, detectorReady(T) ).
dispatch( ready, ready(T) ).
dispatch( notReadyYet, notReadyYet(T) ).
%====================================================================================
context(ctx_sonarservice, "localhost",  "TCP", "8004").
 qactor( sonarservice, ctx_sonarservice, "it.unibo.sonarservice.Sonarservice").
 static(sonarservice).
  qactor( reactor, ctx_sonarservice, "it.unibo.reactor.Reactor").
 static(reactor).
  qactor( detector, ctx_sonarservice, "it.unibo.detector.Detector").
 static(detector).
