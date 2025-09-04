%====================================================================================
% easy description   
%====================================================================================
mqttBroker("localhost", "1883", "unibo/qak/events").
event( productDetected, productDetected(T) ).
event( anomalyDetected, anomalyDetected(T) ).
event( anomalyFixed, anomalyFixed(T) ).
%====================================================================================
context(ctx_easy, "localhost",  "TCP", "8000").
 qactor( trymqtt, ctx_easy, "it.unibo.trymqtt.Trymqtt").
 static(trymqtt).
