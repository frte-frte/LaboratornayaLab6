package commands;

import application.CollectionManager;
import application.Data;
import data.Flat;

import java.util.ArrayDeque;

public class RemoveByIdCommand extends Command {
    final String ANSI_YELLOW = "\u001B[33m";
    final String ANSI_RESET = "\u001B[0m";
    public RemoveByIdCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public String execute(Data data) {
        ArrayDeque<Flat> collection = collectionManager.getCollection();
        for (Flat flat : collection) {
            if (flat.getId() == Long.parseLong(data.getParam()[0])) {
                collection.remove();
                return ANSI_YELLOW + "Элемент с id = " + ANSI_RESET + data.getParam()[0] + ANSI_YELLOW + " был удалён\n" + ANSI_RESET;
            } else {
                return ANSI_YELLOW + "Нет элемента с таким id\n" +ANSI_RESET;
            }
        }
        return null;
    }
}

