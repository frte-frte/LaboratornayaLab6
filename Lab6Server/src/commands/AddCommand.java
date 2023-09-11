package commands;

import application.CollectionManager;
import application.Data;
import data.*;

import java.time.LocalDate;
import java.util.ArrayDeque;

public class AddCommand extends Command{
    final String ANSI_YELLOW = "\u001B[33m";
    final String ANSI_RESET = "\u001B[0m";
    public AddCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public String execute(Data data) {
        ArrayDeque<Flat> collection = collectionManager.getCollection();
        collection.add(new Flat(collectionManager.generateId(), data.getParam()[0],
                new Coordinates(Long.parseLong(data.getParam()[1]), Integer.parseInt(data.getParam()[2])),
                LocalDate.now(), Double.parseDouble(data.getParam()[3]),
                Long.parseLong(data.getParam()[4]), Furnish.valueOf(data.getParam()[5]),
                View.valueOf(data.getParam()[6]), Transport.valueOf(data.getParam()[7]), new House(data.getParam()[8],
                Integer.parseInt(data.getParam()[9]), Long.parseLong(data.getParam()[10]))));
    return ANSI_YELLOW + "Новый элемент был добавлен!\n" + ANSI_RESET;
    }


}
