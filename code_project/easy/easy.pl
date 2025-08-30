%====================================================================================
% easy description   
%====================================================================================
dispatch( message, message(Msg) ).
request( sendrequest, sendrequest(PID) ).
reply( sendAnswerToRequest, sendAnswerToRequest(Answ) ).  %%for sendrequest
%====================================================================================
context(ctx_easy, "localhost",  "TCP", "8000").
 qactor( example, ctx_easy, "it.unibo.example.Example").
 static(example).
