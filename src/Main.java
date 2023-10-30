import java.util.Scanner;

public class Main
{
    public static void main(String[] args) {
        Parser p = new Parser();
        Terminal terminal = new Terminal(p);
        System.out.println("Simple CLI - Type 'exit' to quit.");

        while (true) {
            System.out.print("> ");

            Scanner scanner = new Scanner(System.in);

            String input = scanner.nextLine();
//            scanner.close();
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
