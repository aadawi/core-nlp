package stanford.core.nlp.service;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import org.springframework.stereotype.Service;
import stanford.core.nlp.model.Data;

import java.util.Properties;

@Service
public  class CoreNlpService {

    public  void ner(Data input) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,parse");
        props.setProperty("pos.model", "edu/stanford/nlp/models/pos-tagger/arabic.tagger");
        props.setProperty("parse.model", "edu/stanford/nlp/models/lexparser/arabicFactored.ser.gz");
        props.setProperty("segment.model", "edu/stanford/nlp/models/segmenter/arabic/arabic-segmenter-atb+bn+arztrain.ser.gz");
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
