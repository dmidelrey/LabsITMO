package ru.lab.commands;

import ru.lab.controller.FlatController;

public class Help extends Command {
    @Override
    public void execute(FlatController flatController) {
        setMessage("help : вывести справку по доступным командам\n" +
        "info : вывести в стандартный поток вывода информацию о коллекции\n" +
        "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
        "add {element} : добавить новый элемент в коллекцию\n" +
        "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
        "remove_by_id id : удалить элемент из коллекции по его id\n" +
        "clear : очистить коллекцию\n" +
        "execute_script file_name : считать и исполнить скрипт из указанного файла.\n" +
        "exit : завершить программу\n" +
        "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
        "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
        "history : вывести последние 14 команд (без их аргументов)\n" +
        "remove_any_by_house house : удалить из коллекции один элемент, значение поля house которого эквивалентно заданному\n" +
        "filter_less_than_transport transport : вывести элементы, значение поля transport которых меньше заданного\n" +
        "print_field_descending_number_of_rooms : вывести значения поля numberOfRooms всех элементов в порядке убывания\n");
    }

    @Override
    public boolean commandValid() {
        CommandsHistory.addCommand("help");
        return true;
    }
}
