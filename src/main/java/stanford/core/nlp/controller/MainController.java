package stanford.core.nlp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import stanford.core.nlp.model.Data;
import stanford.core.nlp.service.CoreNlpService;

@Controller
public class MainController {

    @Autowired
    private CoreNlpService coreNlpService;

    @GetMapping("/")
    public String greetingForm(Model model) {
        model.addAttribute("data", new Data());
        return "welcome";
    }

    @PostMapping("/")
    public String greetingSubmit(@ModelAttribute Data data, Model model) {
        coreNlpService.ner(data);
        model.addAttribute("data", data);
        return "welcome";
    }


}
