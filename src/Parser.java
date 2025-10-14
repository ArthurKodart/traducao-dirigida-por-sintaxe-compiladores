public class Parser {
    private byte[] input;
    private int current;

    public Parser(byte[] input) {
        this.input = input;
        this.current = 0;
    }

    public void parse() {
        expr();
        if (peek() != '\0') {
            throw new Error("Erro: caracteres restantes após o fim da expressão.");
        }
    }


    private char peek() {
        if (current < input.length)
            return (char) input[current];
        return '\0';
    }

    private void match(char c) {
        if (c == peek()) {
            current++;
        } else {
            throw new Error("Erro de sintaxe: esperado '" + c + "', encontrado '" + peek() + "'");
        }
    }
    private void expr() {
        digit();
        oper();
    }

    private void digit() {
        if (!Character.isDigit(peek())) {
            throw new Error("Erro de sintaxe: esperado dígito, encontrado '" + peek() + "'");
        }

        StringBuilder num = new StringBuilder();
        while (Character.isDigit(peek())) {
            num.append(peek());
            match(peek());
        }

        System.out.println("push " + num);
    }

    private void oper() {
        if (peek() == '+') {
            match('+');
            digit();
            System.out.println("add");
            oper();
        } else if (peek() == '-') {
            match('-');
            digit();
            System.out.println("sub");
            oper();
        } else if (peek() == '*') {
            match('*');
            digit();
            System.out.println("mul");
            oper();
        } else if (peek() == '/') {
            match('/');
            digit();
            System.out.println("div");
            oper();
        }
    }
}
