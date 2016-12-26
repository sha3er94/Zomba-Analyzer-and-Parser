package lexicalanalyzer;
public class Token {
    private String value;
    private String type;
    private int line_number;
        public Token(String value,String type,int line_number) {
           this.value = value;
           this.type=type;
           this.line_number=line_number;
        }
        public void showPair(){
            if(!" ".equals(this.value))
                System.out.println(this.value + " -> " + this.type);
        }
        
    public String getValue() {
        return value;
    }

    public int getLine_number() {
        return line_number;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
         
    
}
