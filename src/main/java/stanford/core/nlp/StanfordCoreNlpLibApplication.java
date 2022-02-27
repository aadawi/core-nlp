package stanford.core.nlp;

import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.CoreQuote;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.trees.Tree;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@SpringBootApplication
public class StanfordCoreNlpLibApplication {

    public static void main(String[] args) {
        SpringApplication.run(StanfordCoreNlpLibApplication.class, args);
    }

//    @PostConstruct
}
