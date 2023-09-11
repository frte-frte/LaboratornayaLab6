package commands;

import application.CollectionManager;
import application.Data;
import data.Flat;
import data.View;

import java.util.ArrayDeque;

public class FilterByViewCommand extends Command{
    final String ANSI_YELLOW = "\u001B[33m";
    final String ANSI_RESET = "\u001B[0m";

    public FilterByViewCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public String execute(Data data) {
        ArrayDeque<Flat> collection = collectionManager.getCollection();
        StringBuilder filterByView = new StringBuilder();
        for (Flat flat : collection) {
            if (flat.getView() == View.valueOf(data.getParam()[0])) {
                filterByView.append(flat).append("\n");
            }
        }
        if (filterByView.isEmpty()) {
            return ANSI_YELLOW + "Нет элементов с таким полем!\n" + ANSI_RESET;
        }
        return filterByView.toString();
    }
}
