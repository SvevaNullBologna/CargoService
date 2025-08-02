%====================================================================================
% cargoservice description   
%====================================================================================
request( loadrequest, loadrequest(PID) ).
request( getProduct, product(ID) ).
reply( getProductAnswer, product(JSonString) ).  %%for getProduct
dispatch( accepted, accepted(PID,Weight,Slot) ).
dispatch( refused, refused(PID,Weight) ).
event( productDetected, productDetected(T) ).
event( anomalyDetected, anomalyDetected(T) ).
event( anomalyFixed, anomalyFixed(T) ).
dispatch( command, command(X,Y,DIR) ).
request( engage, engage(device,length) ).
request( moverobot, moverobot(x,y) ).
dispatch( setdirection, dir(D) ).
dispatch( cmd, cmd(C) ).
event( alarm, alarm(reason) ).
dispatch( update, update(U) ).
event( finishedtransport, finishedtransport(T) ).
%====================================================================================
context(ctx_cargoservice, "localhost",  "TCP", "8000").
context(ctx_productservice, "127.0.0.1",  "TCP", "8111").
context(ctx_basicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( basicrobot, ctx_basicrobot, "external").
  qactor( productservice, ctx_productservice, "external").
  qactor( companysimulator, ctx_cargoservice, "it.unibo.companysimulator.Companysimulator").
 static(companysimulator).
  qactor( cargoservice, ctx_cargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( cargorobot, ctx_cargoservice, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
  qactor( sonarmock, ctx_cargoservice, "it.unibo.sonarmock.Sonarmock").
 static(sonarmock).
  qactor( webguimock, ctx_cargoservice, "it.unibo.webguimock.Webguimock").
 static(webguimock).
