package unibo.disi.webgui;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WebSocketHandlerDemo extends AbstractWebSocketHandler {//che implements WebSocketHandler interface

  private final List<WebSocketSession> sessions=new CopyOnWriteArrayList<>();
    
  @Override  //AbstractWebSocketHandler
  public void afterConnectionEstablished(WebSocketSession session) throws Exception{
      sessions.add(session);
      super.afterConnectionEstablished(session);
  }
  @Override //AbstractWebSocketHandler
  public void afterConnectionClosed( WebSocketSession session,
                          CloseStatus status) throws Exception{
      sessions.remove(session);
      super.afterConnectionClosed(session, status);
  }
  
  @Override //AbstractWebSocketHandler
  protected void handleTextMessage(WebSocketSession session,
                      TextMessage message) throws IOException{
      String cmd = message.getPayload();
      sendToAll(new TextMessage("echo: "+cmd));
  }

  protected void sendToAll(TextMessage message) throws IOException{
      Iterator<WebSocketSession> iter = sessions.iterator();
      while( iter.hasNext() ){ iter.next().sendMessage(message);}
  }
}