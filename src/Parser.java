public class Parser {
    private Scanner scanner;
    private Scanner.Token lookahead;

    public Parser(byte[] input) {
        this.scanner = new Scanner(input);
        this.lookahead = scanner.nextToken();
    }

    private void match(Scanner.TokenType type) {
        if (lookahead.type == type) {
            lookahead = scanner.nextToken();
        } else {
            throw new Error("Erro de sintaxe: esperado " + type + " mas encontrado " + lookahead.type);
        }
    }

    public void parse() {
        expr();

        if (lookahead.type != Scanner.TokenType.EOF) {
            throw new Error("Erro: tokens restantes após o fim da expressão.");
        }
    }

    // expr -> number oper
    private void expr() {
        number();
        oper();
    }

    // number -> [0-9]+
    private void number() {
        if (lookahead.type == Scanner.TokenType.NUMBER) {
            System.out.println("push " + lookahead.value);
            match(Scanner.TokenType.NUMBER);
        } else {
            throw new Error("Erro de sintaxe: esperado número, encontrado " + lookahead.type);
        }
    }

    // oper -> (+|-|*|/) number oper | ε
    private void oper() {
        if (lookahead.type == Scanner.TokenType.PLUS) {
            match(Scanner.TokenType.PLUS);
            number();
            System.out.println("add");
            oper();
        } else if (lookahead.type == Scanner.TokenType.MINUS) {
            match(Scanner.TokenType.MINUS);
            number();
            System.out.println("sub");
            oper();
        } else if (lookahead.type == Scanner.TokenType.MUL) {
            match(Scanner.TokenType.MUL);
            number();
            System.out.println("mul");
            oper();
        } else if (lookahead.type == Scanner.TokenType.DIV) {
            match(Scanner.TokenType.DIV);
            number();
            System.out.println("div");
            oper();
        }
        // ε: vazio, termina
    }
}
