%====================================================================================
% webguimodel description   
%====================================================================================
request( sendrequest, sendrequest(PID) ).
reply( sendrequestAnswer, sendrequestAnswer(Answ) ).  %%for sendrequest
request( loadrequest, loadrequest(PID) ).
reply( resultrequest, resultrequest(Rst) ).  %%for loadrequest
dispatch( sendendofrequesttogui, sendendofrequesttogui(Result) ).
%====================================================================================
context(ctx_webguimodel, "localhost",  "TCP", "8005").
context(ctx_cargoservice, "127.0.0.1",  "TCP", "8000").
context(ctx_webgui, "127.0.0.1",  "TCP", "8080").
 qactor( cargoservice, ctx_cargoservice, "external").
  qactor( webgui, ctx_webgui, "external").
  qactor( companyrequestreceiver, ctx_webguimodel, "it.unibo.companyrequestreceiver.Companyrequestreceiver").
 static(companyrequestreceiver).
