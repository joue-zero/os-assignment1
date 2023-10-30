public class Main
{
    public static void main(String[] args) {
        Terminal terminal = new Terminal();
        System.out.println("Simple CLI - Type 'exit' to quit.");

        while (true) {
            System.out.print("> ");
            
            String input = System.console().readLine();
            input.toLowerCase();
            
            if ("exit".equals(input)) {
                break;
            }
            else if (terminal.parser.parse(input)) {
                
                terminal.chooseCommandAction();
            } else {
                System.out.println("Invalid command format.");
            }
        }

    }
}
