import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Parser {

    private Scanner scan;
    private Token currentToken;
    private ByteArrayOutputStream buffer;
    private PrintStream out;

    public Parser(byte[] input) {
        scan = new Scanner(input);
        currentToken = scan.nextToken();
        buffer = new ByteArrayOutputStream();
        out = new PrintStream(buffer);
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

    // === EXPRESSÕES ===
    void term() {
        if (currentToken.type == TokenType.NUMBER) {
            out.println("push " + currentToken.lexeme);
            match(TokenType.NUMBER);
        } else if (currentToken.type == TokenType.IDENT) {
            out.println("push " + currentToken.lexeme);
            match(TokenType.IDENT);
        } else {
            throw new Error("syntax error in term");
        }
    }

    void oper() {
        if (currentToken.type == TokenType.PLUS) {
            match(TokenType.PLUS);
            term();
            out.println("add");
            oper();
        } else if (currentToken.type == TokenType.MINUS) {
            match(TokenType.MINUS);
            term();
            out.println("sub");
            oper();
        }
    }

    void expr() {
        term();
        oper();
    }

    // === COMANDOS ===
    void letStatement() {
        match(TokenType.LET);
        String id = currentToken.lexeme;
        match(TokenType.IDENT);
        match(TokenType.EQ);
        expr();
        out.println("pop " + id);
        match(TokenType.SEMICOLON);
    }

    void printStatement() {
        match(TokenType.PRINT);
        expr();
        out.println("print");
        match(TokenType.SEMICOLON);
    }

    void statement() {
        if (currentToken.type == TokenType.PRINT) {
            printStatement();
        } else if (currentToken.type == TokenType.LET) {
            letStatement();
        } else {
            throw new Error("syntax error in statement");
        }
    }

    void statements() {
        while (currentToken.type != TokenType.EOF) {
            statement();
        }
    }

    public void parse() {
        statements();
    }

    public String output() {
        return buffer.toString();
    }
}
