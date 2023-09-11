package commands;

import application.CollectionManager;
import data.Flat;

import java.util.*;

public class ShowCommand extends Command {
    public ShowCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public String execute() {
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_RESET = "\u001B[0m";
        StringBuilder showCommand = new StringBuilder();
        ArrayDeque<Flat> collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            return ANSI_YELLOW + "Коллекция пуста!\n" + ANSI_RESET;
        } else {
            List<Flat> sortCollection = new ArrayList<>(collection);
            Collections.sort(sortCollection);
            collection.removeIf(element -> true);
            collection.addAll(sortCollection);
            for (Flat flat : collection) {
                showCommand.append(flat.toString()).append("\n");
            }
            return showCommand.toString();
        }
    }
}