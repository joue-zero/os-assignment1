// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import java.io.File;

public class Terminal {
    Parser parser;
    File currentPath =  new File(System.getProperty("user.dir"));

    // Add Commands Here
    public void echo(String input){

    }
    public void pwd(){

    }
    public void cd(String[] args){
        if (args.length == 0) {
            // Case 1
            File userHomeDirectory = new File(System.getProperty("user.home"));
            System.out.println("Changed directory to home directory: " + userHomeDirectory.getAbsolutePath());
            this.currentPath = userHomeDirectory;
        } else if (args.length == 1) {
            String newDirectory = args[0];
            if (newDirectory.equals("..")) {
                // Case 2
                File parentDirectory = this.currentPath.getParentFile();
                if (parentDirectory != null) {
                    System.out.println("Changed directory to the previous directory: " + parentDirectory.getAbsolutePath());
                    this.currentPath = parentDirectory;
                } else {
                    System.out.println("Already at the root directory, cannot go up.");
                }
            } else {
                // Case 3
                File newDir = new File(this.currentPath, newDirectory);
                if (newDir.exists() && newDir.isDirectory()) {
                    System.out.println("Changed directory to: " + newDir.getAbsolutePath());
                    this.currentPath = newDir;
                } else {
                    System.out.println("Directory not found: " + newDirectory);
                }
            }
        } else {
            System.out.println("Usage: cd [directory]");
        }

    }
    public void ls(String options){

    }

    public void mkdir(String[] args){

    }
    public void rmdir(String[] args){

    }

    public void touch(String[] args){

    }
    // ...
    //This method will choose the suitable command method to be called
    public void chooseCommandAction(){
        String command = parser.getCommandName();
        String[] args = parser.getArgs();
        String option = parser.getOption();
        if ("pwd".equals(command)) {
             pwd();
            
        }
        else if ("cd".equals(command)) {
            cd(args);}
        // write commmends conditions .....
        
        else {
            System.out.println("Error: '"+ command +"' not found or invalid parameters are entered!");
        }

    }
   
}