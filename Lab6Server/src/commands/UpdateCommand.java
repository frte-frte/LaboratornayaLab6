package commands;

import application.CollectionManager;
import application.Data;
import data.*;

import java.time.LocalDate;
import java.util.ArrayDeque;

public class UpdateCommand extends Command {
    final String ANSI_YELLOW = "\u001B[33m";
    final String ANSI_RESET = "\u001B[0m";
    public UpdateCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public String execute(Data data) {
        ArrayDeque<Flat> collection = collectionManager.getCollection();
        for (Flat flat : collection) {
            if (flat.getId() == Long.parseLong(data.getParam()[0])) {
                collection.remove(flat);
                collection.add(new Flat(Long.parseLong(data.getParam()[0]), data.getParam()[1],
                        new Coordinates(Long.parseLong(data.getParam()[2]), Integer.parseInt(data.getParam()[3])),
                        LocalDate.now(), Double.parseDouble(data.getParam()[4]),
                        Long.parseLong(data.getParam()[5]), Furnish.valueOf(data.getParam()[6]),
                        View.valueOf(data.getParam()[7]), Transport.valueOf(data.getParam()[8]), new House(data.getParam()[9],
                        Integer.parseInt(data.getParam()[10]), Long.parseLong(data.getParam()[11]))));
                return ANSI_YELLOW + "Элемент был обновлён!\n" + ANSI_RESET;
            }
        }
        return ANSI_YELLOW + "Нет значения с id = " + ANSI_RESET + data.getParam()[0] + "\n";
    }
}
