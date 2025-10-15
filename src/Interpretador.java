import java.util.*;
import java.util.stream.Collectors;

class Command {

    public enum Type {
        ADD,
        SUB,
        PUSH,
        POP,
        PRINT
    }

    public Command.Type type;
    public String arg = "";

    public Command(String[] command) {
        type = Command.Type.valueOf(command[0].toUpperCase());
        if (command.length > 1) {
            arg = command[1];
        }
    }

    public String toString() {
        return type.name() + " " + arg;
    }
}

public class Interpretador {

    List<String[]> commands;
    Stack<Integer> stack = new Stack<>();
    Map<String, Integer> variables = new HashMap<>();

    Interpretador(String input) {
        final String eol = System.getProperty("line.separator");
        var output = input.split(eol);
        commands = Arrays.stream(output)
                .map(String::strip)
                .filter(s -> !s.isEmpty() && !s.startsWith("//"))
                .map(s -> s.split(" "))
                .collect(Collectors.toList());
    }

    public boolean hasMoreCommands() {
        return !commands.isEmpty();
    }

    public Command nextCommand() {
        return new Command(commands.remove(0));
    }

    public void run() {
        while (hasMoreCommands()) {
            var command = nextCommand();
            switch (command.type) {
                case ADD -> {
                    var arg2 = stack.pop();
                    var arg1 = stack.pop();
                    stack.push(arg1 + arg2);
                }
                case SUB -> {
                    var arg2 = stack.pop();
                    var arg1 = stack.pop();
                    stack.push(arg1 - arg2);
                }
                case PUSH -> {
                    var value = variables.get(command.arg);
                    if (value != null) {
                        stack.push(value);
                    } else {
                        stack.push(Integer.parseInt(command.arg));
                    }
                }
                case POP -> {
                    var value = stack.pop();
                    variables.put(command.arg, value);
                }
                case PRINT -> {
                    var arg = stack.pop();
                    System.out.println(arg);
                }
            }
        }
    }
}
