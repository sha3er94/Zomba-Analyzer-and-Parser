/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexicalanalyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 *
 * @author Akram
 */
public class Tokenizer {

    private Map<String, String> keywords;
    private ArrayList<String> code;
    public ArrayList<Token> List_of_tokens;
    private StringTokenizer st;
    private Token token;
    boolean flagbrace = false;

    public Tokenizer(Map<String, String> keywords, ArrayList<String> code) {
        this.keywords = keywords;
        this.code = code;
        List_of_tokens = new ArrayList<Token>();
    }

    public ArrayList tokenize() {
        int line_number = 1;
        for (String line_of_code : code) {
            st = new StringTokenizer(line_of_code, "[+-*=()&|\"'!,;<>.# ]", true);
            while (st.hasMoreTokens()) {
                String nextToken = st.nextToken();
                if (" ".equals(nextToken)) {
                    continue;
                }
                if (keywords.containsKey(nextToken)) {
                    token = new Token(nextToken, keywords.get(nextToken), line_number);
                    List_of_tokens.add(token);
                } else if (checkIdentifier(nextToken)) {
                    token = new Token(nextToken, "identifier", line_number);
                    List_of_tokens.add(token);
                } else if (checkNum(nextToken)) {
                    token = new Token(nextToken, "Number", line_number);
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
            int index=0;
            switch (temp) {
                case "{":
                    if (flagbrace == false) {
                        int it = i;
                        int countopen = 0;
                        int countclosed = 0;
                        while (it < List_of_tokens.size()) {
                            if (List_of_tokens.get(it).getValue().equals("{")) {
                                countopen++;
                            } else if (List_of_tokens.get(it).getValue().equals("}")) {
                                countclosed++;
                                index=it;
                            }
                            it++;
                        }
                        if (countopen == countclosed) {
                            break;
                        } else if (countopen > countclosed) {
                            int error = countopen - countclosed;
                          String q= List_of_tokens.get(i).getType();
                           List_of_tokens.get(i).setType(q+" ,Syntax error unclosed brace in line "+List_of_tokens.get(i).getLine_number());
                        }
                        else 
                        {
                            String q= List_of_tokens.get(index).getType();
                           List_of_tokens.get(index).setType(q+" ,Syntax error mislocated brace in line "+List_of_tokens.get(index).getLine_number());
                        }
                        flagbrace = true;
                    }
                    break;
                case "+":
                    if ("+".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue("++");
                        List_of_tokens.get(i).setType("PlusPlus");
                        List_of_tokens.remove(i + 1);
                    }
                    break;

                case "-":
                    if ("-".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue("--");
                        List_of_tokens.get(i).setType("MinusMinus");
                        List_of_tokens.remove(i + 1);
                    }
                    break;
                case "*":
                    if ("*".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue("**");
                        List_of_tokens.get(i).setType("ToThePower");
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

                case "=":
                    if ("=".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue("==");
                        List_of_tokens.get(i).setType("EqualEqual");
                        List_of_tokens.remove(i + 1);
                    }
                    break;

                case "&":
                    if ("&".equals(List_of_tokens.get(i + 1).getValue())) {
                        List_of_tokens.get(i).setValue("&&");
                        List_of_tokens.get(i).setType("LogicalAnd");
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

                case "\"":
                    String x = "";
                    int k = i + 1;
                    int line = List_of_tokens.get(i).getLine_number();
                    boolean flag = true;
                    while (flag && k < List_of_tokens.size()) {
                        if ("identifier".equals(List_of_tokens.get(k).getType()) && (List_of_tokens.get(k).getLine_number() == line)) {
                            x = x + " " + List_of_tokens.get(k).getValue();
                            List_of_tokens.remove(k);
                        } else if ("dquote".equals(List_of_tokens.get(k).getType())) {
                            x = x + "\"";
                            List_of_tokens.remove(k);
                            break;
                        } else {
                            flag = false;
                            break;
                        }
                    }
                    if (!x.endsWith("\"")) {
                        flag = false;
                    }
                    if (flag == true) {
                        List_of_tokens.get(i).setValue("\"" + x.substring(1));
                        List_of_tokens.get(i).setType("String");
                    } else {
                        List_of_tokens.get(i).setValue("\"" + x.substring(1));
                        List_of_tokens.get(i).setType("Syntax Error in Line " + line + " ,missing quotations");
                    }
                    break;

                case "'":
                    x = "";
                    k = i + 1;
                    flag = true;
                    int line1 = List_of_tokens.get(i).getLine_number();
                    while (flag && k < List_of_tokens.size()) {
                        if ("identifier".equals(List_of_tokens.get(k).getType()) && (List_of_tokens.get(k).getLine_number() == line1)) {
                            x = x + " " + List_of_tokens.get(k).getValue();
                            List_of_tokens.remove(k);
                        } else if ("squote".equals(List_of_tokens.get(k).getType())) {
                            x = x + "'";
                            List_of_tokens.remove(k);
                            break;
                        } else {
                            flag = false;
                            break;
                        }
                    }
                    if (!x.endsWith("'")) {
                        flag = false;
                    }
                    if (flag == true) {
                        List_of_tokens.get(i).setValue("'" + x.substring(1));
                        List_of_tokens.get(i).setType("String");
                    } else {
                        List_of_tokens.get(i).setValue("'" + x.substring(1));
                        List_of_tokens.get(i).setType("Syntax Error in Line " + line1 + " ,missing quotations");
                    }
                    break;
            }
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
