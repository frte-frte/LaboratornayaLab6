package commands;

import application.CollectionManager;
import application.CommandManager;
import application.Data;
import application.ServerManager;

import java.io.FileReader;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.ArrayList;


public class ExecuteScriptCommand extends Command {

    public ExecuteScriptCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public String execute(Data data, SocketAddress clientAddress) throws IOException {
        String fileName = data.getParam()[0];
        StringBuilder text = new StringBuilder();
        StringBuilder textServer = new StringBuilder();
        if (collectionManager.checkExecuteScript(fileName)) {
            collectionManager.getRecList().clear();
            textServer.append("Обнаружен рекурсивный вызов!");
        } else {
            collectionManager.getRecList().add(fileName);
            try (FileReader reader = new FileReader(fileName)) {
                while (reader.ready()) {
                    int t = reader.read();
                    text.append((char) t);
                }
                reader.close();
                if (!text.isEmpty()) {
                    String[] commandList = text.toString().split("\n");
                    for (String commands : commandList) {
                        String[] command = commands.replace("\r", "").split(" ");
                        ArrayList<String> arrayList = new ArrayList<>();
                        for (String param : command) {
                            arrayList.add(param);
                        }
                        arrayList.remove(0);
                        String[] commandParam = arrayList.toArray(new String[arrayList.size()]);
                        Data serverData = new Data(command[0], commandParam);
                        textServer.append(new CommandManager(serverData).serverWork(collectionManager, clientAddress));
                    }
                }
            }
        }
        return textServer.toString();
    }
}
