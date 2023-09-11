package application;

import data.*;

import javax.naming.StringRefAddr;

public class CommandManager {
    final static String ANSI_YELLOW = "\u001B[33m";
    final static String ANSI_RESET = "\u001B[0m";
    private String[] command;
    public CommandManager(String[] command) {
        this.command = command;
    }

    public Data commandToObj() {
        try {
            switch (command[0]) {
                case ("help") :
                case ("info") :
                case ("show") :
                case ("clear") :
                case ("remove_first") :
                case ("remove_head") :
                case ("print_field_descending_transport") :
                    if (command.length == 1) {
                        return (new Data(command[0],null));
                    } else {
                        System.out.println(ANSI_YELLOW + "У этой команды нет аргументов\n" + ANSI_RESET);
                        break;
                    }
                case ("remove_by_id") :
                    try {
                        if (command.length == 2){
                            Long id = Long.parseLong(command[1]);
                            return (new Data(command[0], new String[]{command[1]}));
                        } else {
                            System.out.println(ANSI_YELLOW + "Неверное количество аргументов для команды!\n" + ANSI_RESET);
                            break;
                        }
                    } catch (NumberFormatException exception) {
                        System.out.println(ANSI_YELLOW + "Неверные данные!\n" + ANSI_RESET);
                        break;
                    }
                case ("add") :
                    try {
                        if (command.length == 12) {
                            Long x = Long.parseLong(command[2]);
                            Integer y = Integer.parseInt(command[3]);
                            Double area = Double.parseDouble(command[4]);
                            Long numberOfRooms = Long.parseLong(command[5]);
                            Furnish furnish = Furnish.valueOf(command[6]);
                            View view = View.valueOf(command[7]);
                            Transport transport = Transport.valueOf(command[8]);
                            Integer year = Integer.parseInt(command[10]);
                            Long numberOfFlatsOnFloor = Long.parseLong(command[11]);
                            return (new Data(command[0], new String[]{command[1], command[2], command[3], command[4],
                                    command[5], command[6], command[7], command[8], command[9],command[10], command[11]}));
                        } else {
                            System.out.println(ANSI_YELLOW + "Неверное количество аргументов для команды!\n" + ANSI_RESET);
                            break;
                        }
                    } catch (NumberFormatException exception) {
                        System.out.println(ANSI_YELLOW + "Неверные данные!\n" + ANSI_RESET);
                        break;
                    }  catch (IllegalArgumentException exception) {
                        System.out.println(ANSI_YELLOW + "Неверные данные!\n" + ANSI_RESET);
                        break;
                    }
                case ("update") :
                case ("add_if_min") :
                    try {
                        if (command.length == 13) {
                            Long id = Long.parseLong(command[1]);
                            Long x = Long.parseLong(command[3]);
                            Integer y = Integer.parseInt(command[4]);
                            Double area = Double.parseDouble(command[5]);
                            Long numberOfRooms = Long.parseLong(command[6]);
                            Furnish furnish = Furnish.valueOf(command[7]);
                            View view = View.valueOf(command[8]);
                            Transport transport = Transport.valueOf(command[9]);
                            Integer year = Integer.parseInt(command[11]);
                            Long numberOfFlatsOnFloor = Long.parseLong(command[12]);
                            return (new Data(command[0], new String[]{command[1], command[2], command[3], command[4],
                                    command[5], command[6], command[7], command[8], command[9], command[10], command[11],
                                    command[12]}));
                        } else {
                            System.out.println(ANSI_YELLOW + "Неверное количество аргументов для команды!\n" + ANSI_RESET);
                            break;
                        }
                    } catch (NumberFormatException exception) {
                        System.out.println(ANSI_YELLOW + "Неверные данные!\n" + ANSI_RESET);
                        break;
                    } catch (IllegalArgumentException exception) {
                        System.out.println(ANSI_YELLOW + "Неверные данные!\n" + ANSI_RESET);
                        break;
                    }
                case ("filter_by_view") :
                    try {
                        View view = View.valueOf(command[1]);
                        return (new Data(command[0], new String[]{command[1]}));
                    } catch (IllegalArgumentException exception) {
                        System.out.println(ANSI_YELLOW + "Неверные данные!\n" + ANSI_RESET);
                        break;
                    }
                case ("filter_starts_with_name") :
                case ("execute_script") :
                    if (command.length == 2) {
                        return (new Data(command[0], new String[]{command[1]}));
                    } else {
                        System.out.println(ANSI_YELLOW + "Неверное количество аргументов для команды!\n" + ANSI_RESET);
                        break;
                    }
                case ("exit") :
                    if (command.length == 1) {
                        System.out.println(ANSI_YELLOW + "Клиент завершил работу!" + ANSI_RESET);
                        System.exit(0);
                        break;
                    } else {
                        System.out.println(ANSI_YELLOW + "У этой команды нет аргументов\n" + ANSI_RESET);
                        break;
                    }
                default :
                    System.out.println(ANSI_YELLOW + "Неизвестная команда! Напишите [help] для вывода списка команд.\n" + ANSI_RESET);
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println(ANSI_YELLOW + "Неверное количество аргументов для команды!\n" + ANSI_RESET);
        }
        return null;
    }

    public String[] getCommand() {
        return command;
    }

    public void setCommand(String[] command) {
        this.command = command;
    }
}
