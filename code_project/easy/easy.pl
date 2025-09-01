%====================================================================================
% easy description   
%====================================================================================
request( getProduct, product(ID) ).
reply( getProductAnswer, product(JSonString) ).  %%for getProduct
%====================================================================================
context(ctx_easy, "localhost",  "TCP", "8000").
context(ctx_productservice, "127.0.0.1",  "TCP", "8111").
 qactor( productservice, ctx_productservice, "external").
  qactor( tryproductservice, ctx_easy, "it.unibo.tryproductservice.Tryproductservice").
 static(tryproductservice).
