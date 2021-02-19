package ru.lab.commands;

import ru.lab.client.Client;
import ru.lab.client.CommandData;
import ru.lab.controller.FlatController;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ExecuteScript extends Command{
    private CommandData commandData;
    private ArrayList<String> workingScripts;

    public ExecuteScript(ArrayList<String> workingScripts) {
        commandData = new CommandData(true);
        this.workingScripts = workingScripts;
    }

    public ExecuteScript() {
        commandData = new CommandData(true);
        this.workingScripts = new ArrayList<>();
    }

    @Override
    public void execute(FlatController flatController) {
    }

    @Override
    public boolean commandValid() {
        String fileName = getValue();
        try (Scanner scanner = new Scanner(new FileReader(fileName))) {
            workingScripts.add("execute_script " + fileName);
            String input = "";
            
            while (scanner.hasNext()) {
                input = scanner.nextLine();
                if (input.contains("execute_script")) {
                    if (workingScripts.contains(input)) {
                        System.out.println(input.split(" ")[1] + " is already working. Command skipped.\n");
                    } else {
                        Command command = new ExecuteScript(workingScripts);
                        System.out.println(input.split(" ")[1]);
                        command.setValue(input.split(" ")[1]);
                        command.setUser(getUser());
                        command.commandValid();
                    }
                } else {
                    commandData.setCommand(input);
                    Command command = commandData.getCommand();
                    command.setUser(getUser());
                    command.commandValid();
                    command = Client.exchangeCommands(command);
                    System.out.println(command.getMessage());
                    if (command.getMessage().contains("Logging out")) {
                        System.exit(0);
                    }
                }
                
            }
        } catch (IOException e) {
            System.out.println("File not found or access denied");
        } catch (Exception e) {
            System.out.println("Invalid input in execute_script " + fileName);
            e.printStackTrace();
        }
        System.out.println("script " + fileName + " finished working");
        setMessage("script");
        workingScripts.remove("execute_script " + fileName);
        CommandsHistory.addCommand("execute_script");
        return true;
    }
}
