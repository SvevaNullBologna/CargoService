%====================================================================================
% webguimodel description   
%====================================================================================
dispatch( update, update(HoldJsonString) ).
request( sendrequest, sendrequest(PID) ).
reply( sendAnswerToRequest, sendAnswerToRequest(Answ) ).  %%for sendrequest
request( loadrequest, loadrequest(PID) ).
reply( resultrequest, resultrequest(Rst) ).  %%for loadrequest
%====================================================================================
context(ctx_webguimodel, "local",  "TCP", "8006").
context(ctx_cargoservice, "127.0.0.1",  "TCP", "8000").
 qactor( cargoservice, ctx_cargoservice, "external").
