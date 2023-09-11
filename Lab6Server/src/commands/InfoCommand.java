package commands;

import application.CollectionManager;

public class InfoCommand extends Command {

    public InfoCommand (CollectionManager collectionManager) {
        super(collectionManager);
    }

    public String execute() {
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RESET = "\u001B[0m";

        return ANSI_GREEN + "Тип коллекции" + ANSI_RESET + ": " +
                collectionManager.getCollection().getClass() +
                "\n" +
                ANSI_GREEN + "Время инициализации" + ANSI_RESET + ": " +
                collectionManager.getInitializationTime() +
                "\n" +
                ANSI_GREEN + "Количество элементов" + ANSI_RESET + ": " + collectionManager.getCollection().size() + "\n";
    }
}
