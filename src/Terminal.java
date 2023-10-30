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
        // write commmends conditions .....
        
        else {
            System.out.println("Error: '"+ command +"' not found or invalid parameters are entered!");
        }

    }
   
}
