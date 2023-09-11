package commands;

import application.CollectionManager;
import application.Data;
import data.Flat;

import java.util.ArrayDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterStartsWithNameCommand extends Command{
    final String ANSI_YELLOW = "\u001B[33m";
    final String ANSI_RESET = "\u001B[0m";
    public FilterStartsWithNameCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public String execute (Data data) {
        ArrayDeque<Flat> collection = collectionManager.getCollection();
        StringBuilder filterStartsWithName = new StringBuilder();
        Pattern pattern = Pattern.compile("^" + data.getParam()[0]);
        for (Flat flat : collection) {
            Matcher matcher = pattern.matcher(flat.getName());
            if (matcher.find()) {
                filterStartsWithName.append(flat).append("\n");
            }
        }
        if (filterStartsWithName.isEmpty()) {
            return ANSI_YELLOW + "Нет элементов, значение поля name, которых начинается с заданной подстроки: '" + data.getParam()[0]
            + "'!\n" + ANSI_RESET;
        } else {
            return filterStartsWithName.toString();
        }
    }
}
