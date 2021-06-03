package seu.qz.qzserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RealTimeVideoController {

    @RequestMapping("/startVideo")
    public String startVideo(){
        return "index";
    }
}
