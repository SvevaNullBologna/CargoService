%====================================================================================
% easy description   
%====================================================================================
dispatch( message, message(Msg) ).
request( sendrequest, sendrequest(PID) ).
reply( sendAnswerToRequest, sendAnswerToRequest(Answ) ).  %%for sendrequest
%====================================================================================
context(ctx_easy, "localhost",  "TCP", "8000").
 qactor( companyrequestreceiver, ctx_easy, "it.unibo.companyrequestreceiver.Companyrequestreceiver").
 static(companyrequestreceiver).
