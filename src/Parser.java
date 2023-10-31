import java.util.Arrays;

public class Parser {
    private String commandName;
    private String option;
    private String[] args;

    private String fullCommand;


    public boolean parse(String input) {
        fullCommand = input;
        String[] parts = input.split(" ");
        if (parts.length > 0) {
            commandName = parts[0];
            if (parts.length > 1 && parts[1].startsWith("-")) {
                option = parts[1].substring(1);
                args = Arrays.copyOfRange(parts, 2, parts.length);
            } else {
                args = Arrays.copyOfRange(parts, 1, parts.length);
            }
            return true;
        }
        return false;
    }

    public String getCommandName() {
        return commandName;
    }
    public String getFullCommand() {
        return fullCommand;
    }

    public String getOption() {
        return option;
    }

    public String[] getArgs() {
        return args;
    }

}
