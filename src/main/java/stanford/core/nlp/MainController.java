package stanford.core.nlp;


import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.trees.Tree;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import stanford.core.nlp.model.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Controller
public class MainController {


    private List<String> tasks = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

//    @GetMapping("/")
//    public String main(Model model) {
//        model.addAttribute("message", "hello world");
//        model.addAttribute("tasks", tasks);
//
//        return "welcome"; //view
//    }

    // /hello?name=kotlin
    @GetMapping("/hello")
    public String mainWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "")
                    String name, Model model) {

        model.addAttribute("message", name);

        return "welcome";
    }

    @GetMapping("/")
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Data());
        return "welcome";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute Data data, Model model) {
        ner(data);
        model.addAttribute("data", data);
        return "welcome";
    }


    public void ner(Data input) {

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,parse");
        props.setProperty("pos.model", "edu/stanford/nlp/models/pos-tagger/arabic.tagger");
        props.setProperty("parse.model", "edu/stanford/nlp/models/lexparser/arabicFactored.ser.gz");
        props.setProperty("segment.model", "edu/stanford/nlp/models/segmenter/arabic/arabic-segmenter-atb+bn+arztrain.ser.gz");
//        props.put("ner.model", "");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        CoreDocument doc = new CoreDocument(input.getContent());
        pipeline.annotate(doc);
        doc.sentences().forEach(sentence -> {
            Tree constituencyParse = sentence.constituencyParse();
            System.out.println("Example: constituency parse");
            System.out.println(constituencyParse);
            input.setResult(constituencyParse.pennString());
//            printTree(constituencyParse, 0);
            System.out.println(constituencyParse.constituents());
            System.out.println(constituencyParse.pennString());
        });
    }

}
