%====================================================================================
% easyguimodel description   
%====================================================================================
dispatch( message, message(Msg) ).
request( updatedMsg, updatedMsg(Msg) ).
%====================================================================================
context(ctx_webgui, "localhost",  "TCP", "8005").
context(ctx_easy, "127.0.0.1",  "TCP", "8000").
 qactor( example, ctx_easy, "external").
  qactor( webgui, ctx_webgui, "it.unibo.webgui.Webgui").
 static(webgui).
