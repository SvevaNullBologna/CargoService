%====================================================================================
% easy description   
%====================================================================================
request( engage, engage(device,length) ).
request( moverobot, moverobot(x,y) ).
dispatch( setdirection, dir(D) ).
%====================================================================================
context(ctx_easy, "localhost",  "TCP", "8000").
context(ctx_basicrobot, "127.0.0.1",  "TCP", "8020").
context(ctx_productservice, "127.0.0.1",  "TCP", "8111").
 qactor( basicrobot, ctx_basicrobot, "external").
  qactor( example, ctx_easy, "it.unibo.example.Example").
 static(example).
