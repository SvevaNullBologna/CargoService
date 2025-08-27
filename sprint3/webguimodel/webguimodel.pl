%====================================================================================
% webguimodel description   
%====================================================================================
dispatch( update, update(HoldJsonString) ).
%====================================================================================
context(ctx_webguimodel, "local",  "TCP", "8006").
context(ctx_cargoservice, "127.0.0.1",  "TCP", "8000").
context(ctx_springboot_webgui, "127.0.0.1",  "TCP", "8085").
 qactor( hold, ctx_cargoservice, "external").
  qactor( webgui, ctx_springboot_webgui, "external").
  qactor( webguimodel, ctx_webguimodel, "it.unibo.webguimodel.Webguimodel").
 static(webguimodel).
  qactor( gui_request_handler, ctx_webguimodel, "it.unibo.gui_request_handler.Gui_request_handler").
 static(gui_request_handler).
  qactor( gui_api_gateway, ctx_webguimodel, "it.unibo.gui_api_gateway.Gui_api_gateway").
 static(gui_api_gateway).
