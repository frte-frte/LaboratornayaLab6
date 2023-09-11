package application;

import data.Flat;

import java.util.ArrayDeque;

public class Server {
    private static Integer PORT;
    private static String filePath;
    CollectionManager collectionManager;

    public static void main(String[] args) {
        try {
            PORT = Integer.parseInt(args[0]);
            filePath = args[1];
            CollectionManager collectionManager = new CollectionManager(args[1]);
            ServerManager server = new ServerManager();
            server.start(collectionManager);

        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println("\u001B[31m" + "Не указан путь до файла. " +
                    "Используйте [java -jar Server.jar PORT /path_to_file] для коректного использования." + "\u001B[31m");
        }
    }

    public static Integer getPORT() {
        return PORT;
    }

    public static String getFilePath() {
        return filePath;
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}