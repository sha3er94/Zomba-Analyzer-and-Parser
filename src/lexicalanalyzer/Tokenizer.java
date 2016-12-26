package lexicalanalyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Tokenizer {

    private Map<String, String> keywords;
    private ArrayList<String> code;
    public ArrayList<Token> List_of_tokens;
    private StringTokenizer st;
    private Token token;
    boolean flagbrace = false;
    private boolean errorflag=false;

    public boolean isErrorflag() {
        return errorflag;
    }
    

    public Tokenizer(Map<String, String> keywords, ArrayList<String> code) {
        this.keywords = keywords;
        this.code = code;
        List_of_tokens = new ArrayList<Token>();
    }

    public ArrayList tokenize() {
        int line_number = 1;
        for (String line_of_code : code) {
            st = new StringTokenizer(line_of_code, "[+-*=()&|\\\"'!,;<>.#{}[]~^%?:\\/ ]", true);
            while (st.hasMoreTokens()) {
String nextToken = st.nextToken().trim();
                if(nextToken.equals(""))
                    continue;
                if (keywords.containsKey(nextToken)) {
                    token = new Token(nextToken, keywords.get(nextToken), line_number);
                    List_of_tokens.add(token);
                } else if (checkIdentifier(nextToken)) {
                    token = new Token(nextToken, "id", line_number);
                    List_of_tokens.add(token);
                } else if (checkNum(nextToken)) {
                    token = new Token(nextToken, "number", line_number);
                    List_of_tokens.add(token);
                } else {                    
                    token = new Token(nextToken, "UNKNOWN", line_number);
                    List_of_tokens.add(token);
                }
            }
            line_number++;
        }

        List_of_tokens = UpdateTokens();
        return List_of_tokens;

    }

    private ArrayList UpdateTokens() {
        for (int i = 0; i < List_of_tokens.size(); i++) {
            String temp = List_of_tokens.get(i).getValue();
            int index = 0;
            switch (temp) {
//                case "{":
//                    if (flagbrace == false) {
//                        int it = i;
//                        int countopen = 0;
//                        int countclosed = 0;
//                        while (it < List_of_tokens.size()) {
//                            if (List_of_tokens.get(it).getValue().equals("{")) {
//                                countopen++;
//                            } else if (List_of_tokens.get(it).getValue().equals("}")) {
//                                countclosed++;
//                                index = it;
//                            }
//                            it++;
//                        }
//                        if (countopen == countclosed) {
//                            break;
//                        } else if (countopen > countclosed) {
//                            System.out.println("Syntax Error , unclosed brace in line "+List_of_tokens.get(i).getLine_number());
//                            System.exit(0);
////                            int error = countopen - countclosed;
////                            String q = List_of_tokens.get(i).getType();
////                            List_of_tokens.get(i).setType(q + " ,Syntax error unclosed brace in line " + List_of_tokens.get(i).getLine_number());
//                        } else {
//                              System.out.println("Syntax Error , unclosed brace in line "+List_of_tokens.get(i).getLine_number());
//                              System.exit(0);
////                            String q = List_of_tokens.get(index).getType();
////                            List_of_tokens.get(index).setType(q + " ,Syntax error mislocated brace in line " + List_of_tokens.get(index).getLine_number());
//                        }
//                        flagbrace = true;
//                    }
//                    break;
                case "?":
                    if (":".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue("?:");
                        List_of_tokens.get(i).setType("ConditionalExpression");
                        List_of_tokens.remove(i + 1);
                    }
                    break;
                case "defined":
                     if ("?".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue("defined?");
                        List_of_tokens.get(i).setType("defined?");
                        List_of_tokens.remove(i + 1);
                    }
                    break;
                    
                  case "%":
                     if ("=".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue("%=");
                        List_of_tokens.get(i).setType("ModulusAssignment");
                        List_of_tokens.remove(i + 1);
                    }
                    break;
                case "+":
                    if ("+".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue("++");
                        List_of_tokens.get(i).setType("iterate");
                        List_of_tokens.remove(i + 1);
                    }
                    else if ("=".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue("+=");
                        List_of_tokens.get(i).setType("PlusEqual");
                        List_of_tokens.remove(i + 1);
                    }
                    break;
                case "<":
                    if ("=".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue("<=");
                        List_of_tokens.get(i).setType("LessThanOrEqual");
                        List_of_tokens.remove(i + 1);
                    }
                    else if ("<".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue("<<");
                        List_of_tokens.get(i).setType("BinaryLeftShift");
                        List_of_tokens.remove(i + 1);
                    }
                    break;
                case ">":
                    if ("=".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue(">=");
                        List_of_tokens.get(i).setType("GreaterThanOrEqual");
                        List_of_tokens.remove(i + 1);
                    }
                    else if (">".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue(">>");
                        List_of_tokens.get(i).setType("BinaryRightShift");
                        List_of_tokens.remove(i + 1);
                    }
                    break;

                case "-":
                    if ("-".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue("--");
                        List_of_tokens.get(i).setType("iterate");
                        List_of_tokens.remove(i + 1);
                    }
                     else if ("=".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue("-=");
                        List_of_tokens.get(i).setType("MinusEqual");
                        List_of_tokens.remove(i + 1);
                    }
                    break;
                     case ".":
                    if (".".equals(List_of_tokens.get(i + 1).getValue())) {
                        if (".".equals(List_of_tokens.get(i + 2).getValue())) {
                            List_of_tokens.get(i).setValue("...");
                            List_of_tokens.get(i).setType("RangeExclusive");
                            List_of_tokens.remove(i + 1);
                            List_of_tokens.remove(i + 1);

                        }
                        else{
                        List_of_tokens.get(i).setValue("..");
                        List_of_tokens.get(i).setType("RangeInclusive");
                        List_of_tokens.remove(i + 1);
                        }
                    }
                case "*":
                    if ("*".equals(List_of_tokens.get(i + 1).getValue())) {
                        if ("*".equals(List_of_tokens.get(i + 1).getValue())) {
                            List_of_tokens.get(i).setValue("**=");
                            List_of_tokens.get(i).setType("ExponentAssignment");
                            List_of_tokens.remove(i + 1);
                            List_of_tokens.remove(i + 1);

                        }
                        else{
                        List_of_tokens.get(i).setValue("**");
                        List_of_tokens.get(i).setType("Exponent");
                        List_of_tokens.remove(i + 1);
                        }
                    }
                    
                     else if ("=".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue("*=");
                        List_of_tokens.get(i).setType("MultiplyEqual");
                        List_of_tokens.remove(i + 1);
                    }
                    break;

                case "!":
                    if ("=".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue("!=");
                        List_of_tokens.get(i).setType("notequalsoperator");
                        List_of_tokens.remove(i + 1);
                    }
                    break;
                    
                case "#":
                    int myLine = List_of_tokens.get(i).getLine_number();
                    int next = i + 1;
                    String comment = "";
                    while (next < List_of_tokens.size() && List_of_tokens.get(next).getLine_number() == myLine) {
                        String tempComment = List_of_tokens.get(next).getValue();
                        List_of_tokens.remove(next);
                        comment = comment+ " " + tempComment;
                    }
                    comment=comment.trim();
                    //comment = "#" + comment;
                    List_of_tokens.get(i).setValue(comment);
                    List_of_tokens.get(i).setType("comment");
                    break;

                case "=":
                   if ("=".equals(List_of_tokens.get(i + 1).getValue())) {
                        if ("=".equals(List_of_tokens.get(i + 1).getValue())) {
                            List_of_tokens.get(i).setValue("===");
                            List_of_tokens.get(i).setType("EqualEqualEqual");
                            List_of_tokens.remove(i + 1);
                            List_of_tokens.remove(i + 1);
                        }
                        else{
                        List_of_tokens.get(i).setValue("==");
                        List_of_tokens.get(i).setType("EqualEqual");
                        List_of_tokens.remove(i + 1);
                        }
                    
                    } else if (List_of_tokens.get(i + 1).getValue().equalsIgnoreCase("begin")) {
                        List_of_tokens.remove(i+1);
                        int nextIndex = i + 1;
                        String blockComment = "";
                        while (nextIndex<List_of_tokens.size() && !List_of_tokens.get(nextIndex).getValue().equals("=") ) {
                            blockComment = blockComment + List_of_tokens.get(nextIndex).getValue();
                            List_of_tokens.remove(nextIndex);
                        }
                        if(nextIndex>=List_of_tokens.size() || !List_of_tokens.get(nextIndex+1).getValue().equalsIgnoreCase("end")  )
                        {
                            System.out.println("Missing the end of commnt block in line "+List_of_tokens.get(i).getLine_number());
                            errorflag=true;
                            break;
//                            System.exit(0);
                        }
                        List_of_tokens.remove(nextIndex);
			List_of_tokens.remove(nextIndex);
                        blockComment = blockComment.trim();
                        List_of_tokens.get(i).setValue(blockComment);
                        List_of_tokens.get(i).setType("blockComment");
                    }
                    else if(List_of_tokens.get(i + 1).getValue().equalsIgnoreCase("end"))
                    {
                        System.out.println("Ending a comment block without =begin in line  "+List_of_tokens.get(i).getLine_number());
                        errorflag=true;
                            break;
                            //System.exit(0);
                    }
                    break;

                case "&":
                    if ("&".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue("&&");
                        List_of_tokens.get(i).setType("LogicalAnd");
                        List_of_tokens.remove(i + 1);

                    }
                    break;
                    
                    case "/":
                    if ("=".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue("/=");
                        List_of_tokens.get(i).setType("DivideEqual");
                        List_of_tokens.remove(i + 1);

                    }
                    break;

                case "|":
                    if ("|".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue("||");
                        List_of_tokens.get(i).setType("LogicalOr");
                        List_of_tokens.remove(i + 1);
                    }
                    break;

               case ("\""):
                    boolean flag_Found = false;
                    String elstring = "";
                    int it = i+1;
                    while (it < List_of_tokens.size()) {
                        if (List_of_tokens.get(it).getValue().equals("\"")) {
                            elstring = elstring + "\"";
                            flag_Found = true;
                            break;
                        } else {
                            elstring = elstring + List_of_tokens.get(it).getValue();
                        }
                        it++;
                    }
                    if (flag_Found == false) {
                        int line_nu=List_of_tokens.get(i).getLine_number();
                        //int j=List_of_tokens.get(i).getLine_number();
                        String the_string="";
                        int j=i+1;
                         while (j<List_of_tokens.size()){
                             if ((List_of_tokens.get(j).getLine_number() != line_nu) || j==List_of_tokens.size())
                             {
                                 break;
                             }
                             else
                             {
                                 the_string=the_string+List_of_tokens.get(j).getValue();
                                 List_of_tokens.remove(j);
                             }
                        }
                        List_of_tokens.get(i).setValue("\""+the_string);
                         System.out.println("Syntax Error , missing quotations in line "+List_of_tokens.get(i).getLine_number());
                         errorflag=true;
                            break;
//                         System.exit(0);

//                        List_of_tokens.get(i).setType("Syntax Error in Line " + List_of_tokens.get(i).getLine_number() + " ,missing quotations");
                    } else {

                        for (int j = i; j < it; j++) {
                            List_of_tokens.remove(i);
                        }
                        List_of_tokens.get(i).setValue("\"" + elstring);
                        List_of_tokens.get(i).setType("String");
                    }
                    break;

                case ("'"):
                    flag_Found = false;
                    elstring = "";
                    it = i+1;
                    while (it < List_of_tokens.size()) {
                        if (List_of_tokens.get(it).getValue().equals("'")) {
                            elstring = elstring + "'";
                            flag_Found = true;
                            break;
                        } else {
                            elstring = elstring  + List_of_tokens.get(it).getValue();
                        }
                        it++;
                    }
                    if (flag_Found == false) {
                        int line_nu=List_of_tokens.get(i).getLine_number();
                        //int j=List_of_tokens.get(i).getLine_number();
                        String the_string="";
                        int j=i+1;
                         while (j<List_of_tokens.size()){
                             if ((List_of_tokens.get(j).getLine_number() != line_nu) || j==List_of_tokens.size())
                             {
                                 break;
                             }
                             else
                             {
                                 the_string=the_string+List_of_tokens.get(j).getValue();
                                List_of_tokens.remove(j);
                             }
                        }
                        List_of_tokens.get(i).setValue("'"+the_string);
                         System.out.println("Syntax Error , missing quotations in line "+List_of_tokens.get(i).getLine_number());
                         errorflag=true;
                            break;
//                         System.exit(0);
                       // List_of_tokens.get(i).setType("Syntax Error in Line " + List_of_tokens.get(i).getLine_number() + " ,missing quotations");
                    } else {

                        for (int j = i; j < it; j++) {
                            List_of_tokens.remove(i);
                        }
                        List_of_tokens.get(i).setValue("'" + elstring);
                        List_of_tokens.get(i).setType("String");
                    }
                    break;
            }
            if(List_of_tokens.get(i).getType().equals("UNKNOWN"))
            {
                 System.out.println("Unkown identifier in line "+List_of_tokens.get(i).getLine_number());
                 errorflag=true;
                            break;
//                         System.exit(0);
            }
            if (errorflag)
                break;
        }

        return List_of_tokens;
    }

    public static boolean checkIdentifier(String arg) {
        return Pattern.compile("^[a-zA-Z_][a-zA-Z_0-9]*$").matcher(arg).find();
    }

    public static boolean checkNum(String arg) {
        return Pattern.compile("^[-+]?\\d+(\\.\\d+)?$").matcher(arg).find();
    }

    public Map<String, String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Map<String, String> keywords) {
        this.keywords = keywords;
    }

    public ArrayList<String> getCode() {
        return code;
    }

    public void setCode(ArrayList<String> code) {
        this.code = code;
    }

}
