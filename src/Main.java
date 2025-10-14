public class Main {
    public static void main(String[] args) throws Exception {
        // Você pode testar qualquer expressão abaixo:
        String input = "45+89-876*2/4";
        Parser p = new Parser(input.getBytes());
        p.parse();
    }
}
