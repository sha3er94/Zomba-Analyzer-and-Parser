package lexicalanalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class LexicalAnalyzer {

    public static Map<String, String> keywords = new HashMap<String, String>();
    public static int currentIndex = 0;
    public static String currentTokentype;
    public static ArrayList<Token> tokensList;
    public static ArrayList<String> output = new ArrayList<>();
    public static Stack st = new Stack();
    public static int line_number;
    public static boolean error=false;

    public static void main(String[] args) {
        ArrayList<String> code = new ArrayList<>();
        IO file = new IO();
        tokensList = new ArrayList<>();
        keywords = file.LoadKeywords(keywords); //Load Keywords
        code = file.ReadFile("code.rb");//Load Code
        Tokenizer tokenizer = new Tokenizer(keywords, code);
        tokensList = tokenizer.tokenize();
        for (int i = 0; i < tokensList.size(); i++) {
            if (tokensList.get(i).getType().equals("Space")) {
                tokensList.remove(i);
            }
        }
//        if(!tokenizer.isErrorflag())
//        {
//            for (Token tokensList1:tokensList)
//            {
//                tokensList1.showPair();
//            }
//        }
        parser parse_object = new parser(keywords,tokensList);
        
        parse_object.parse();
    }

}
