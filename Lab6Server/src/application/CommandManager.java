package application;

import commands.*;
import data.*;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.*;

public class CommandManager {
    private Data data;
    public CommandManager(Data data){
        this.data = data;
    }

    public String serverWork(CollectionManager collectionManager, SocketAddress clientAddress) throws IOException {
        ArrayDeque<Flat> collection = collectionManager.getCollection();
        switch (data.getCommandName()) {
            case ("help"):
                return new HelpCommand(collectionManager).execute();
            case ("info"):
                return new InfoCommand(collectionManager).execute();
            case ("clear"):
                return new ClearCommand(collectionManager).execute();
            case ("show"):
                return new ShowCommand(collectionManager).execute();
            case ("remove_first"):
                return new RemoveFirstCommand(collectionManager).execute();
            case ("remove_head"):
                return new RemoveHeadCommand(collectionManager).execute();
            case ("add"):
                return new AddCommand(collectionManager).execute(data);
            case ("update"):
                return new UpdateCommand(collectionManager).execute(data);
            case ("remove_by_id") :
                return new RemoveByIdCommand(collectionManager).execute(data);
            case ("add_if_min") :
                return new AddIfMinCommand(collectionManager).execute(data);
            case ("filter_by_view") :
                return new FilterByViewCommand(collectionManager).execute(data);
            case ("filter_starts_with_name") :
                return new FilterStartsWithNameCommand(collectionManager).execute(data);
            case ("print_field_descending_transport") :
                return  new PrintFieldDescendingTransportCommand(collectionManager).execute(data);
            case ("execute_script") :
                return new ExecuteScriptCommand(collectionManager).execute(data, clientAddress);
            }
        return null;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
