%====================================================================================
% cargoservice description   
%====================================================================================
mqttBroker("mosquittoalone", "1883", "unibo/qak/events").
request( loadrequest, loadrequest(PID) ).
request( getProduct, product(ID) ).
reply( getProductAnswer, product(JSonString) ).  %%for getProduct
event( productDetected, productDetected(T) ).
event( anomalyDetected, anomalyDetected(T) ).
event( anomalyFixed, anomalyFixed(T) ).
dispatch( command, command(X,Y,DIR) ).
request( checkIfFits, checkIfFits(PID,Weight) ).
event( deliveredToSlot, deliveredToSlot(T) ).
event( finishedtransport, finishedtransport(T) ).
request( engage, engage(device,length) ).
request( moverobot, moverobot(x,y) ).
dispatch( setdirection, dir(D) ).
event( alarm, alarm(reason) ).
event( info, info(x) ).
reply( accepted, accepted(JsonString) ).  %%for checkIfFits
reply( refused, refused(Reason) ).  %%for checkIfFits
dispatch( update, update(HoldJsonString) ).
%====================================================================================
context(ctx_cargoservice, "localhost",  "TCP", "8000").
context(ctx_productservice, "127.0.0.1",  "TCP", "8111").
context(ctx_basicrobot, "127.0.0.1",  "TCP", "8020").
context(ctx_sonarservice, "127.0.0.1",  "TCP", "8004").
 qactor( basicrobot, ctx_basicrobot, "external").
  qactor( productservice, ctx_productservice, "external").
  qactor( companysimulator, ctx_cargoservice, "it.unibo.companysimulator.Companysimulator").
 static(companysimulator).
  qactor( cargoservice, ctx_cargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( cargorobot, ctx_cargoservice, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
  qactor( hold, ctx_cargoservice, "it.unibo.hold.Hold").
 static(hold).
