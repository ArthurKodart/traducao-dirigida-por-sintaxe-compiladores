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

    private void match(TokenType t) {
        if (currentToken.type == t) {
            nextToken();
        } else {
            throw new Error("syntax error: expected " + t + " but found " + currentToken.type);
        }
    }

    // === Regras de Expressão ===

    // term -> number | identifier
    void term() {
        if (currentToken.type == TokenType.NUMBER) {
            System.out.println("push " + currentToken.lexeme);
            match(TokenType.NUMBER);
        } else if (currentToken.type == TokenType.IDENT) {
            System.out.println("push " + currentToken.lexeme);
            match(TokenType.IDENT);
        } else {
            throw new Error("syntax error");
        }
    }

    // oper -> + term oper | - term oper | ε
    void oper() {
        if (currentToken.type == TokenType.PLUS) {
            match(TokenType.PLUS);
            term();
            System.out.println("add");
            oper();
        } else if (currentToken.type == TokenType.MINUS) {
            match(TokenType.MINUS);
            term();
            System.out.println("sub");
            oper();
        }
    }

    // expr -> term oper
    void expr() {
        term();
        oper();
    }

    // === Regras de Comandos ===

    // letStatement -> 'let' identifier '=' expression ';'
    void letStatement() {
        match(TokenType.LET);
        String id = currentToken.lexeme;
        match(TokenType.IDENT);
        match(TokenType.EQ);
        expr();
        System.out.println("pop " + id);
        match(TokenType.SEMICOLON);
    }

    // printStatement -> 'print' expression ';'
    void printStatement() {
        match(TokenType.PRINT);
        expr();
        System.out.println("print");
        match(TokenType.SEMICOLON);
    }

    // statement -> printStatement | letStatement
    void statement() {
        if (currentToken.type == TokenType.PRINT) {
            printStatement();
        } else if (currentToken.type == TokenType.LET) {
            letStatement();
        } else {
            throw new Error("syntax error");
        }
    }

    // statements -> statement*
    void statements() {
        while (currentToken.type != TokenType.EOF) {
            statement();
        }
    }

    public void parse() {
        statements();
    }
}
