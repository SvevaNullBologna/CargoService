%====================================================================================
% webguimodel description   
%====================================================================================
dispatch( update, update(HoldJsonString) ).
request( sendrequest, sendrequest(PID) ).
reply( sendrequestAnswer, sendrequestAnswer(Answ) ).  %%for sendrequest
%====================================================================================
context(ctx_webguimodel, "localhost",  "TCP", "8005").
context(ctx_cargoservice, "127.0.0.1",  "TCP", "8000").
 qactor( hold, ctx_cargoservice, "external").
  qactor( companyrequestreceiver, ctx_cargoservice, "external").
  qactor( loadrequestsendpage, ctx_webguimodel, "it.unibo.loadrequestsendpage.Loadrequestsendpage").
 static(loadrequestsendpage).
  qactor( holdshowpage, ctx_cargoservice, "it.unibo.holdshowpage.Holdshowpage").
 static(holdshowpage).
