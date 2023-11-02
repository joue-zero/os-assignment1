// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Terminal {
    Parser parser;
    File currentPath =  new File(System.getProperty("user.dir"));

    List<String> history;


    public Terminal(Parser parser) {
        this.parser = parser;
        this.history = new ArrayList<>();
    }

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
    private  void catSingleFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
 }
  private  void catTwoFiles(String filePath1, String filePath2) {
    try (BufferedReader reader1 = new BufferedReader(new FileReader(filePath1));
         BufferedReader reader2 = new BufferedReader(new FileReader(filePath2))) {
        String line;
        while ((line = reader1.readLine()) != null) {
            System.out.println(line);
        }
        while ((line = reader2.readLine()) != null) {
            System.out.println(line);
        }
    } catch (IOException e) {
        System.err.println("Error reading the files: " + e.getMessage());
    }
    
}



    // Add Commands Here
    public void echo(String[] args)
    {
        for(int i=0;i< args.length;i++)
        {
            System.out.print(args[i]);
        }

        history.add(parser.getFullCommand());

    }

    public void pwd()
    {
        System.out.println("Current dir:" + this.currentPath.getAbsolutePath());
        history.add(parser.getFullCommand());
    }

    public void cd(String[] args) {
        if (args.length == 0) {
            // Case 1
            File userHomeDirectory = new File(System.getProperty("user.home"));
            System.out.println("Changed directory to home directory: " + userHomeDirectory.getAbsolutePath());
            this.currentPath = userHomeDirectory;
            history.add(parser.getFullCommand());
            
        } else if (args.length == 1) {
            String newDirectory = args[0];
            if (newDirectory.equals("..")) {
                // Case 2
                File parentDirectory = this.currentPath.getParentFile();
                if (parentDirectory != null) {
                    System.out.println("Changed directory to the previous directory: " + parentDirectory.getAbsolutePath());
                    this.currentPath = parentDirectory;
                } else{
                    System.out.println("Already at the root directory, cannot go up.");
                }
            } else {                
                	  File newDir;
                      String newPath = args[0];
                      for (int i = 1; i < args.length; i++) {
                          newPath +=  args[i];
                      }
                      if(newPath.startsWith(File.separator) || new File(newPath).isAbsolute() || newPath.contains(":"))
                      {
                          newDir = new File(newPath);
                      }
                      else{
                            newDir = new File(this.currentPath, newPath);
                      } 
                      if (newDir.exists() && newDir.isDirectory()) {
                          System.out.println("Changed directory to: " + newDir.getAbsolutePath());
                          this.currentPath = newDir;
                      } else {
                          System.out.println("Directory not found: " + newPath);
                      }
                  
            } 
            history.add(parser.getFullCommand());
        } else {
            System.out.println("Usage: cd [directory]");
        }
    }

    
    public void ls(String options){
        String folderPath = currentPath.getPath();
        File folder = new File(folderPath);
        List<String> cur = new ArrayList<>();
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            for (File file : files) {
                cur.add(file.getName());
            }
            if(options != null){
                Collections.reverse(cur);
            }else{
                Collections.sort(cur);
            }
            if(!cur.isEmpty()){
                System.out.println("Contents of " + folderPath + ":");
                for(String ele: cur)
                    System.out.println(ele);
            }else{
                System.out.println("The folder is empty.");
            }
            history.add(parser.getFullCommand());
        } else {
            System.out.println("The specified folder does not exist or is not a directory.");
        }
    }


   public void mkdir(String[] args)
    {
        for (int i=0;i<args.length;i++)
        {
            File directory = new File(args[i]);

            //Handling Case of File Path
            if (directory.isAbsolute() || args[i].contains(File.separator))
            {
                directory.mkdirs();

            }
            //Handling Case of DirectoryName
            else
            {
                File newDir = new File(currentPath, args[i]);
                newDir.mkdirs();
            }
        }
        history.add(parser.getFullCommand());
    }

    public void rmdir(String[] args)
    {
        if(args.length > 1)
        {
            System.out.print("rmdir Takes Only One Argument");
            return;
        }

        else if(args[0].equals("*"))
        {
            File[] directories = new File(this.currentPath.toString()).listFiles(File::isDirectory);


            for (int i=0;i<directories.length;i++)
            {

                if (directories[i].list() != null && directories[i].list().length == 0)
                {
                    boolean isDeleted = directories[i].delete();
                    if (isDeleted) {
                        System.out.println(directories[i].getAbsolutePath() + " has been successfully deleted.");
                    } else {
                        System.out.println("Error occurred  " + directories[i].getAbsolutePath());
                    }
                }
            }
            history.add(parser.getFullCommand());
        }

        else
        {
            File directory = new File(args[0]);

            if (!directory.exists() || !directory.isDirectory()) {
                System.out.println(args[0] + " is not a valid directory.");
                return;
            }

            if (directory.list().length > 0) {
                System.out.println(args[0] + " is not empty.");
                return;
            }

            boolean isDeleted = directory.delete();

            if (isDeleted) {
                System.out.println("Directory  Has been successfully deleted.");
                history.add(parser.getFullCommand());

            } else {
                System.out.println("Error occurred Try Again :) " + args[0]);
            }

        }
    }

    public void touch(String[] args){

    }
    public  void Cat(String[] args) {
        if (args.length == 1) {
            catSingleFile(args[0]);
            
        } else if (args.length == 2) {
            catTwoFiles(args[0], args[1]);
            
        } else {
            System.err.println("Usage: CatFiles <file1> [file2]");
            return;
        }
        history.add(parser.getFullCommand());
    }

    public void rm(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: rm <file_name>");
        } else {
            String fileName = args[0];
            File fileToDelete = new File(this.currentPath, fileName);

            if (fileToDelete.exists() && fileToDelete.isFile()) {
                if (fileToDelete.delete()) {
                    System.out.println("Removed file: " + fileName);
                    history.add(parser.getFullCommand());
                } else {
                    System.out.println("Failed to remove file: " + fileName);
                }
            } else {
                System.out.println("File not found: " + fileName);
            }
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
            history.add(parser.getFullCommand());
        } catch (IOException e) {
            System.out.print ("Error copying directory: " + e.getMessage());
        }
    }
    public void cp(String[] args) {
        if (args.length != 2) {
            System.out.print ("Usage: cp  source_file destination_file");
            return;
        }
        String sourceFilePath = currentPath + "\\" + args[0];
        String destinationFilePath = currentPath + "\\" + args[1];
        if(args[0].length() >= 3 && args[0].startsWith(":\\", 1)){
            sourceFilePath = args[0];
        }
        if(args[1].length() >= 3 && args[1].startsWith(":\\", 1)){
            destinationFilePath = args[1];
        }
        File sourceFile = new File(sourceFilePath);
        File destinationFile = new File(destinationFilePath);

        if (!sourceFile.exists() || !destinationFile.isFile()) {
            System.out.print ("Source file does not exist.");
            return;
        }
        if (destinationFile.exists() && !destinationFile.isFile()) {
            System.out.print ("Destination is not a directory.");
            return;
        }
        try {
            FileInputStream inputStream = new FileInputStream(sourceFile);
            FileOutputStream outputStream = new FileOutputStream(destinationFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outputStream.close();
            System.out.println("File copied successfully.");
            history.add(parser.getFullCommand());
        } catch (IOException e) {
            e.printStackTrace();
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
        else if("cd".equals(command)) {
            cd(args);
        }else if("rm".equals(command))
        {
            rm(args);
        } else if("cp".equals(command)) {
            if(option != null){
                cpR(args);
            }else{
                cp(args);
            }

        }else if("ls".equals(command)){
            ls(option);
        }else if("history".equals(command)){
            getHistory();
        } else if ("mkdir".equals(command))
        {
            mkdir(args);

        } else if ("echo".equals(command))
        {
            echo(args);

        } else if ("rmdir".equals(command))
        {
            rmdir(args);

        } else if("touch".equals(command))
        {
            touch(args);
        } else if("cat".equals(command))
        {
            Cat(args);
           
        }
         else {
            System.out.println("Error: '"+ command +"' not found or invalid parameters are entered!");
        }

    }

    void getHistory(){
        System.out.println("Previous History:");
        for(String ele: history){
            System.out.println(ele);
        }
    }
}