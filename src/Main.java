public class Main {
    public static void main(String[] args) {

        // Teste com código-fonte da linguagem
        String input = """
            let a = 42 + 2;
            let b = 15 + 3;
            print a + b;
        """;

        Parser p = new Parser(input.getBytes());
        p.parse();

        System.out.println("\n=== Código Gerado ===");
        System.out.println(p.output());

        System.out.println("\n=== Execução ===");
        Interpretador i = new Interpretador(p.output());
        i.run();
    }
}
