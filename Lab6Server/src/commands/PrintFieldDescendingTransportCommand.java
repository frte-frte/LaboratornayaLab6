package commands;

import application.CollectionManager;
import application.Data;
import data.Flat;
import data.Transport;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrintFieldDescendingTransportCommand extends Command{

    public PrintFieldDescendingTransportCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public String execute(Data data) {
        ArrayDeque<Flat> collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            return "Колекция пуста\n";
        } else {
            List<Transport> transportsType = new ArrayList<>();
            for (Flat flat : collection) {
                transportsType.add(flat.getTransport());
            }
            Collections.sort(transportsType);
            StringBuilder stringBuilder = new StringBuilder();
            for (Transport transport : transportsType) {
                stringBuilder.append(transport).append("\n");
            }
            return stringBuilder.toString();
        }
    }
}
