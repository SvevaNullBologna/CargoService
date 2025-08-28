%====================================================================================
% sonarservice description   
%====================================================================================
mqttBroker("localhost", "1883", "unibo/qak/events").
event( productDetected, productDetected(T) ).
event( anomalyDetected, anomalyDetected(T) ).
event( anomalyFixed, anomalyFixed(T) ).
event( distance, distance(D) ).
%====================================================================================
context(ctx_sonarservice, "localhost",  "TCP", "8004").
 qactor( sonarsimul, ctx_sonarservice, "it.unibo.sonarsimul.Sonarsimul").
 static(sonarsimul).
  qactor( sonar_reactor, ctx_sonarservice, "it.unibo.sonar_reactor.Sonar_reactor").
 static(sonar_reactor).
  qactor( led_device, ctx_sonarservice, "it.unibo.led_device.Led_device").
 static(led_device).
