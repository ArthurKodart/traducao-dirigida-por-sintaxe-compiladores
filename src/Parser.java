public class Parser {

    private Scanner scan;
    private Token currentToken;

    public Parser(byte[] input) {
        scan = new Scanner(input);
        currentToken = scan.nextToken();
    }

    private void nextToken() {
        currentToken = scan.nextToken();
    }

    private void match(TokenType type) {
        if (currentToken.type == type) {
            nextToken();
        } else {
            throw new Error("syntax error: expected " + type + " but found " + currentToken.type);
        }
    }

    void digit() {
        if (currentToken.type == TokenType.NUMBER) {
            System.out.println("push " + currentToken.lexeme);
            match(TokenType.NUMBER);
        } else {
            throw new Error("syntax error at " + currentToken.lexeme);
        }
    }

    void oper() {
        if (currentToken.type == TokenType.PLUS) {
            match(TokenType.PLUS);
            digit();
            System.out.println("add");
            oper();
        } else if (currentToken.type == TokenType.MINUS) {
            match(TokenType.MINUS);
            digit();
            System.out.println("sub");
            oper();
        } else if (currentToken.type == TokenType.EOF) {
            // fim da expressão
        }
    }

    void expr() {
        digit();
        oper();
    }

    public void parse() {
        expr();
    }
}
