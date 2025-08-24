package unibo.disi.webgui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;


//S@Controller //ANNOTAZIONE IMPORTANTE
public class HIControllerDemo {
@Value("${spring.application.name}")
String appName;

@GetMapping("/")
public String homePage(Model model) {
    model.addAttribute("arg", appName);
    return "webgui";
}

}