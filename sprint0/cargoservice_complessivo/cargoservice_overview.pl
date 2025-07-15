%====================================================================================
% cargoservice_overview description   
%====================================================================================
request( loadrequest, loadrequest(PID) ).
request( checkloadrequest, checkloadrequest(PID) ).
dispatch( updategui, updategui(robotstate,robotposition,slots,led) ).
dispatch( transport, transport(PID,Slot) ).
dispatch( stop, stop(C) ).
dispatch( resume, resume(C) ).
dispatch( distance, distance(D) ).
dispatch( ledOn, ledOn(T) ).
dispatch( command, command(C) ).
request( step, step(length) ).
%====================================================================================
context(ctx_cargoservice, "localhost",  "TCP", "8000").
context(ctx_productservice, "localhost",  "TCP", "8001").
context(ctx_basicrobot, "localhost",  "TCP", "8002").
context(ctx_companysim, "localhost",  "TCP", "8003").
context(ctx_sonarservice, "localhost",  "TCP", "8004").
context(ctx_webgui, "localhost",  "TCP", "8005").
 qactor( basicrobot, ctx_basicrobot, "external").
  qactor( productservice, ctx_productservice, "external").
  qactor( companysimulator, ctx_companysim, "it.unibo.companysimulator.Companysimulator").
 static(companysimulator).
  qactor( cargoservice, ctx_cargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( cargorobot, ctx_cargoservice, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
  qactor( basicrobot, ctx_basicrobot, "it.unibo.basicrobot.Basicrobot").
 static(basicrobot).
  qactor( productservice, ctx_cargoservice, "it.unibo.productservice.Productservice").
 static(productservice).
  qactor( hold, ctx_cargoservice, "it.unibo.hold.Hold").
 static(hold).
  qactor( webgui, ctx_webgui, "it.unibo.webgui.Webgui").
 static(webgui).
  qactor( sonarservice, ctx_sonarservice, "it.unibo.sonarservice.Sonarservice").
 static(sonarservice).
