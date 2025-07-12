%====================================================================================
% cargoservice_overview description   
%====================================================================================
request( loadrequest, loadrequest(PID) ).
request( checkloadrequest, checkloadrequest(PID) ).
%====================================================================================
context(ctxddrrobot, "localhost",  "TCP", "11800").
context(ctxsonarservice, "localhost",  "TCP", "11801").
context(ctxcargoservice, "localhost",  "TCP", "11802").
context(ctxproductservice, "localhost",  "TCP", "11803").
context(ctxcompanysim, "localhost",  "TCP", "11804").
 qactor( basicrobot, ctxddrrobot, "external").
  qactor( productservice, ctxproductservice, "external").
  qactor( cargoservice, ctxcargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( request_simulator, ctxcompanysim, "it.unibo.request_simulator.Request_simulator").
 static(request_simulator).
