%====================================================================================
% cargoservice_overview description   
%====================================================================================
request( loadrequest, loadrequest(PID) ).
request( getweight, getweight(PID) ).
dispatch( accepted, accepted(PID,Weight,Slot) ).
dispatch( refused, refused(PID,Weight) ).
event( productDetected, productDetected(T) ).
event( anomalyDetected, anomalyDetected(T) ).
event( anomalyFixed, anomalyFixed(T) ).
dispatch( command, command(C) ).
request( engage, engage(device,length) ).
request( moverobot, moverobot(x,y) ).
dispatch( cmd, cmd(C) ).
event( alarm, alarm(reason) ).
dispatch( update, update(U) ).
event( finishedtransport, finishedtransport(T) ).
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
  qactor( sonarmock, ctx_sonarservice, "it.unibo.sonarmock.Sonarmock").
 static(sonarmock).
  qactor( webguimock, ctx_cargoservice, "it.unibo.webguimock.Webguimock").
 static(webguimock).
