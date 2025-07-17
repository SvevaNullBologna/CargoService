%====================================================================================
% cargoservice_overview description   
%====================================================================================
request( loadrequest, loadrequest(PID) ).
request( checkloadrequest, checkloadrequest(PID) ).
dispatch( command, command(C) ).
event( productDetected, productDetected(T) ).
event( anomalyDetected, anomalyDetected(T) ).
dispatch( cmd, cmd(C) ).
request( step, step(length) ).
dispatch( update, update(U) ).
%====================================================================================
context(ctx_cargoservice, "localhost",  "TCP", "8000").
context(ctx_productservice, "localhost",  "TCP", "8001").
context(ctx_basicrobot, "localhost",  "TCP", "8002").
context(ctx_companysim, "localhost",  "TCP", "8003").
context(ctx_sonarservice, "localhost",  "TCP", "8004").
 qactor( basicrobot, ctx_basicrobot, "external").
  qactor( productservice, ctx_productservice, "external").
  qactor( companysimulator, ctx_companysim, "it.unibo.companysimulator.Companysimulator").
 static(companysimulator).
  qactor( cargoservice, ctx_cargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( cargorobot, ctx_cargoservice, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
  qactor( sonarservice, ctx_sonarservice, "it.unibo.sonarservice.Sonarservice").
 static(sonarservice).
  qactor( webgui, ctx_cargoservice, "it.unibo.webgui.Webgui").
 static(webgui).
