// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import java.io.File;

public class Terminal {
    Parser parser;
    File currentPath =  new File(System.getProperty("user.dir"));

    private void copyDirectory(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdirs();
            }

            String[] files = source.list();
            if (files != null) {
                for (String file : files) {
                    File srcFile = new File(source, file);
                    File destFile = new File(destination, file);

                    copyDirectory(srcFile, destFile);
                }
            }
        } else {
            try (InputStream in = new FileInputStream(source);
                 OutputStream out = new FileOutputStream(destination)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            }
        }
    }
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
    public void rm(String[] args) {
        if (args.length != 1) {
             System.out.print ("Usage: rm <file_name>");
        }

        String fileName = args[0];
        File fileToDelete = new File(currentDirectory, fileName);

        if (fileToDelete.exists() && fileToDelete.isFile()) {
            if (fileToDelete.delete()) {
                 System.out.print ("Removed file: " + fileName);
            } else {
                 System.out.print ("Failed to remove file: " + fileName);
            }
        } else {
             System.out.print ("File not found: " + fileName);
        }
    }
    public void cpR(String[] args) {
        if (args.length != 2) {
            System.out.print ("Usage: cp -r source_directory destination_directory");
        }

        String sourceDirectoryPath = args[0];
        String destinationDirectoryPath = args[1];

        File sourceDirectory = new File(sourceDirectoryPath);
        File destinationDirectory = new File(destinationDirectoryPath);

        if (!sourceDirectory.exists() || !sourceDirectory.isDirectory()) {
            System.out.print ("Source directory does not exist.");
        }

        if (destinationDirectory.exists() && !destinationDirectory.isDirectory()) {
            System.out.print ("Destination is not a directory.");
        }

        try {
            copyDirectory(sourceDirectory, destinationDirectory);
            System.out.print ("Directory copied successfully.");
        } catch (IOException e) {
            System.out.print ("Error copying directory: " + e.getMessage());
        }
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
            cd(args);
        }else if("rm".equals(command))
        {
            rm(args);
        } else if ("cp".equals(command) && option!=null ) {
            cpR(args);
            
        }
        
        
        else {
            System.out.println("Error: '"+ command +"' not found or invalid parameters are entered!");
        }

    }
   
}