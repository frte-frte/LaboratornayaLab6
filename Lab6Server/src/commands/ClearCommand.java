package commands;

import application.CollectionManager;
import data.Flat;

import java.util.ArrayDeque;

public class ClearCommand extends Command{
    final String ANSI_YELLOW = "\u001B[33m";
    final String ANSI_RESET = "\u001B[0m";

    public ClearCommand (CollectionManager collectionManager) {
        super(collectionManager);
    }

    public String execute() {
        ArrayDeque<Flat> collection = collectionManager.getCollection();
        if (collectionManager.getCollection().isEmpty()) {
            return ANSI_YELLOW + "Коллекция пуста\n" + ANSI_RESET;
        } else {
            collection.clear();
            return ANSI_YELLOW + "Коллекция была отчищена!\n" + ANSI_RESET;
        }
    }
}
