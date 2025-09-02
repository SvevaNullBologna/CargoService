%====================================================================================
% webguimodel description   
%====================================================================================
dispatch( update, update(HoldJsonString) ).
event( filteredupdate, filteredupdate(Update) ).
dispatch( hitsend, hitsend(PID) ).
request( sendrequest, sendrequest(PID) ).
reply( sendrequestAnswer, sendrequestAnswer(Answ) ).  %%for sendrequest
%====================================================================================
context(ctx_webguimodel, "localhost",  "TCP", "8005").
context(ctx_cargoservice, "127.0.0.1",  "TCP", "8000").
 qactor( hold, ctx_cargoservice, "external").
  qactor( companyrequestreceiver, ctx_cargoservice, "external").
  qactor( webgui, ctx_webguimodel, "it.unibo.webgui.Webgui").
 static(webgui).
  qactor( loadrequestsendpage, ctx_webguimodel, "it.unibo.loadrequestsendpage.Loadrequestsendpage").
 static(loadrequestsendpage).
  qactor( holdshowpage, ctx_webguimodel, "it.unibo.holdshowpage.Holdshowpage").
 static(holdshowpage).
  qactor( usersim, ctx_webguimodel, "it.unibo.usersim.Usersim").
 static(usersim).
