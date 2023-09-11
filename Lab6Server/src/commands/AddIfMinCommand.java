package commands;

import application.CollectionManager;
import application.Data;
import data.*;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddIfMinCommand extends Command {
    final String ANSI_YELLOW = "\u001B[33m";
    final String ANSI_RESET = "\u001B[0m";

    public AddIfMinCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public String execute(Data data) {
        ArrayDeque<Flat> collection = collectionManager.getCollection();
        List<Long> idList = new ArrayList<>();
        for (Flat flat : collection) {
            idList.add(flat.getId());
        }
        Collections.sort(idList);
        if (Long.parseLong(data.getParam()[0]) < idList.get(0)){
            collection.add(new Flat(collectionManager.generateId(), data.getParam()[1],
                    new Coordinates(Long.parseLong(data.getParam()[2]), Integer.parseInt(data.getParam()[3])),
                    LocalDate.now(), Double.parseDouble(data.getParam()[4]), Long.parseLong(data.getParam()[5]),
                    Furnish.valueOf(data.getParam()[6]), View.valueOf(data.getParam()[7]),
                    Transport.valueOf(data.getParam()[8]), new House(data.getParam()[9],
                    Integer.parseInt(data.getParam()[10]), Long.parseLong(data.getParam()[11]))));
            return ANSI_YELLOW + "Новый элемент был добавлен!\n" + ANSI_RESET;
        } else {
            return ANSI_YELLOW + "Существуют значения id меньше\n" + ANSI_RESET;
        }
    }
}

