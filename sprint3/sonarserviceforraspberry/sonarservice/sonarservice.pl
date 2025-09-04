%====================================================================================
% sonarservice description   
%====================================================================================
mqttBroker("192.168.138.38", "1883", "unibo/qak/events").
event( productDetected, productDetected(T) ).
event( anomalyDetected, anomalyDetected(T) ).
event( anomalyFixed, anomalyFixed(T) ).
event( distance, distance(D) ).
%====================================================================================
context(ctx_sonarservice, "localhost",  "TCP", "8004").
 qactor( sonar_listener, ctx_sonarservice, "it.unibo.sonar_listener.Sonar_listener").
 static(sonar_listener).
  qactor( sonar_reactor, ctx_sonarservice, "it.unibo.sonar_reactor.Sonar_reactor").
 static(sonar_reactor).
  qactor( led_device, ctx_sonarservice, "it.unibo.led_device.Led_device").
 static(led_device).
