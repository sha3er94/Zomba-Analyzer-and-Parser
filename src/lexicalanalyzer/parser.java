/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexicalanalyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author Akram
 */
public class parser {
    private  Map<String, String> keywords ;
    private  int currentIndex = 0;
    private  String currentTokentype;
    private  ArrayList<Token> tokensList;
    private  Stack st = new Stack();
    private  int line_number;
    private  boolean error=false;

    public parser(Map keywords , ArrayList tokensList) {
        this.keywords=keywords;
        this.tokensList = tokensList;
    }
    public  void parse() {
        this.st.push("S");
        while (!st.isEmpty()) {
            if (this.currentIndex < this.tokensList.size()) {
                String currentStack = this.st.peek().toString();
                switch (currentStack) {
                    case "S":
                        S();
                        break;
                    case "statement-list":
                        statement_list();
                        break;
                    case "statment":
                        statment();
                        break;
//                    case "operator":
//                        operator();
//                        break;
                    case "condition":
                        condition();
                        break;
                    case "cases":
                        cases();
                        break;
//                    case "equal":
//                        equal();
//                        break;
                    case "math":
                        math();
                        break;
                    default:
                                line_number = this.tokensList.get(this.currentIndex).getLine_number();
                                //System.out.println("HOBBA HNA");
                        if (this.keywords.containsKey(currentStack) || "id".equals(currentStack) || "number".equals(currentStack) || "operator".equals(currentStack) || "equal".equals(currentStack)|| "iterate".equals(currentStack)||"math".equals(currentStack)) {
                            if (currentStack.equals(this.tokensList.get(this.currentIndex).getType())) {
                                System.out.println(this.tokensList.get(this.currentIndex).getValue());
                                this.currentIndex++;
                                this.st.pop();
                            }
                            else
                             error=true;
                        }
                        else
                            error=true;
                        break;

                }
              //  System.out.println(Arrays.toString(st.toArray()));
                if(error)
                        {
                            System.out.println("Syntax Error line "+line_number);
                            break;
                        }
            } else {
                break;
            }
        }
    }

    public  void S() {
        this.currentTokentype = this.tokensList.get(this.currentIndex).getType();
        line_number = this.tokensList.get(this.currentIndex).getLine_number();
        switch (this.currentTokentype) {
            case "while":
                System.out.println(this.st.pop());
                this.st.push("statement-list");
                break;
            case "if":
                System.out.println(this.st.pop());
                this.st.push("statement-list");
                break;
            case "id":
                System.out.println(this.st.pop());
                this.st.push("statement-list");
                break;
            case "for":
                System.out.println(this.st.pop());
                this.st.push("statement-list");
                break;
            case "op":
                System.out.println(this.st.pop());
                this.st.push("statement-list");
                break;
            case "print":
                System.out.println(this.st.pop());
                this.st.push("statement-list");
                break;
            case "scan":
                System.out.println(this.st.pop());
                this.st.push("statement-list");
                break;
            case "block":
                System.out.println(this.st.pop());
                this.st.push("statement-list");
                break;
            case "call":
                System.out.println(this.st.pop());
                this.st.push("statement-list");
                break;
            default:
                error=true;
                break;

        }

    }

    public  void statement_list() {
        this.currentTokentype = this.tokensList.get(this.currentIndex).getType();
        line_number = this.tokensList.get(this.currentIndex).getLine_number();
        switch (this.currentTokentype) {
            
            case "while":
                System.out.println(this.st.pop());
                this.st.push("statement-list");
                this.st.push("statment");
                break;
            case "end":
                System.out.println(this.st.pop());
                break;
            case "if":
                System.out.println(this.st.pop());
                this.st.push("statement-list");
                this.st.push("statment");
                break;
              case "for":
                System.out.println(this.st.pop());
                this.st.push("statement-list");
                this.st.push("statment");
                break;
            case "id":
                System.out.println(this.st.pop());
                this.st.push("statement-list"); 
                this.st.push("statment");
                break;
            case "print":
                System.out.println(this.st.pop());
                this.st.push("statement-list");
                this.st.push("statment");
                break;
            case "op":
                System.out.println(this.st.pop());
                this.st.push("statement-list");
                this.st.push("statment");
                break;
               case "scan":
                System.out.println(this.st.pop());
                this.st.push("statement-list");
                this.st.push("statment");
                break;  
                 case "block":
                System.out.println(this.st.pop());
                this.st.push("statement-list");
                this.st.push("statment");
                break;   
                      case "call":
                System.out.println(this.st.pop());
                this.st.push("statement-list");
                this.st.push("statment");
                break;
            default:
                                error=true;

                break;

        }

    }

    public  void statment() {
        this.currentTokentype = this.tokensList.get(this.currentIndex).getType();
        line_number = this.tokensList.get(this.currentIndex).getLine_number();
        switch (this.currentTokentype) {
            case "while":
                System.out.println(this.st.pop());
                this.st.push("end");
                this.st.push("statement-list");
                this.st.push("condition");
                this.st.push("while");
                break;
            case "if":
                System.out.println(this.st.pop());
                this.st.push("end");
                this.st.push("statement-list");
                this.st.push("condition");
                this.st.push("if");
                break;
                case "for":
                System.out.println(this.st.pop());
                this.st.push("end");
                this.st.push("statement-list");
                this.st.push(")");
                this.st.push("iterate");
                this.st.push("id");
                this.st.push(",");
                this.st.push("condition");
                this.st.push(",");
                this.st.push("number");
                this.st.push("equal");
                this.st.push("id");
                this.st.push("(");
                this.st.push("for");
                break;
            case "id":
                System.out.println(this.st.pop());
                this.st.push("cases");
                this.st.push("equal");
                this.st.push("id");
                break;
            
            case "op":
                System.out.println(this.st.pop());
                this.st.push("cases");
                this.st.push("operator");
                this.st.push("cases");
                this.st.push("equal");
                this.st.push("id");
                this.st.push("op");
                break;
            case "print":
                System.out.println(this.st.pop());
                this.st.push("id");
                this.st.push("print");
                break;
                 case "scan":
                System.out.println(this.st.pop());
                this.st.push("id");
                this.st.push("scan");
                break;
                  case "block":
                System.out.println(this.st.pop());
                this.st.push("end");
                this.st.push("statement-list");
                this.st.push("id");
                st.add("block");
                break;
                    case "call":
                System.out.println(this.st.pop());
                this.st.push("id");
                this.st.push("call");
                break;    
            default:
                                error=true;

                break;

        }
    }


    public  void condition() {
        this.currentTokentype = this.tokensList.get(this.currentIndex).getType();
        line_number = this.tokensList.get(this.currentIndex).getLine_number();
        switch (this.currentTokentype) {
            case "id":
                System.out.println(this.st.pop());
                this.st.push("number");
                this.st.push("operator");
                this.st.push("id");
                break;
            default:
                                error=true;

                break;

        }

    }

    public  void operator() {
        this.currentTokentype = this.tokensList.get(this.currentIndex).getType();
        line_number = this.tokensList.get(this.currentIndex).getLine_number();
        switch (this.currentTokentype) {
            case "<":
                System.out.println(this.st.pop());
                this.st.push("<");
                break;
            case "==":
                System.out.println(this.st.pop());
                this.st.push("==");
                break;
            case "<=":
                System.out.println(this.st.pop());
                this.st.push("<=");
                break;
            case ">=":
                System.out.println(this.st.pop());
                this.st.push(">=");
                break;
            case "!=":
                System.out.println(this.st.pop());
                this.st.push("!=");
                break;
            case ">":
                System.out.println(this.st.pop());
                this.st.push(">");
                break;
            case "=":
                System.out.println(this.st.pop());
                this.st.push("=");
                break;
            default:
                                error=true;

                break;

        }
    }

    public  void cases() {
        this.currentTokentype = this.tokensList.get(this.currentIndex).getType();
        line_number = this.tokensList.get(this.currentIndex).getLine_number();
        switch (this.currentTokentype) {
            case "id":
                System.out.println(this.st.pop());
                this.st.push("id");
                break;
            case "number":
                System.out.println(this.st.pop());
                this.st.push("number");
                break;
            default:
                                error=true;

                break;

        }
    }

    public  void equal() {
        this.currentTokentype = this.tokensList.get(this.currentIndex).getType();
        line_number = this.tokensList.get(this.currentIndex).getLine_number();
        switch (this.currentTokentype) {
            case "equal":
                System.out.println(this.st.pop());
                this.st.push("=");
                break;
            default:
                                error=true;

                break;

        }
    }

    public  void math() {
        this.currentTokentype = this.tokensList.get(this.currentIndex).getType();
        line_number = this.tokensList.get(this.currentIndex).getLine_number();
        switch (this.currentTokentype) {
            case "+":
                System.out.println(this.st.pop());
                this.st.push("+");
                break;
            case "-":
                System.out.println(this.st.pop());
                this.st.push("-");
                break;
            case "*":
                System.out.println(this.st.pop());
                this.st.push("*");
                break;
            case "/":
                System.out.println(this.st.pop());
                this.st.push("/");
                break;
            default:
                                error=true;

                break;

        }
    }
    
    
}
