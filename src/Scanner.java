public class Scanner {
    private byte[] input;
    private int current;

    public enum TokenType {
        NUMBER, PLUS, MINUS, MUL, DIV, EOF
    }

    public static class Token {
        public TokenType type;
        public String value;

        public Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Token(" + type + ", " + value + ")";
        }
    }

    public Scanner(byte[] input) {
        this.input = input;
        this.current = 0;
    }

    private char peek() {
        if (current < input.length)
            return (char) input[current];
        return '\0';
    }

    private void advance() {
        current++;
    }

    public Token nextToken() {
        // Ignorar espaços em branco
        while (Character.isWhitespace(peek())) {
            advance();
        }

        char c = peek();

        if (c == '\0') {
            return new Token(TokenType.EOF, "");
        } else if (Character.isDigit(c)) {
            StringBuilder num = new StringBuilder();
            while (Character.isDigit(peek())) {
                num.append(peek());
                advance();
            }
            return new Token(TokenType.NUMBER, num.toString());
        } else if (c == '+') {
            advance();
            return new Token(TokenType.PLUS, "+");
        } else if (c == '-') {
            advance();
            return new Token(TokenType.MINUS, "-");
        } else if (c == '*') {
            advance();
            return new Token(TokenType.MUL, "*");
        } else if (c == '/') {
            advance();
            return new Token(TokenType.DIV, "/");
        } else {
            throw new Error("Erro léxico: caractere inesperado '" + c + "'");
        }
    }
}
