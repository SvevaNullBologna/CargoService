package unibo.disi.webgui;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
public static final WebSocketHandlerDemo wshandler = new WebSocketHandlerDemo();
public static final String wspath                  = "socket";
  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
    registry.addHandler(wshandler, wspath).setAllowedOrigins("*");
  }
}