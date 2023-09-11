package application;

import java.io.IOException;

public class Client {
    final static String ANSI_GREEN = "\u001B[32m";
    final static String ANSI_RED = "\u001B[31m";
    final static String ANSI_RESET = "\u001B[0m";
    private static int PORT;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        PORT = Integer.parseInt(args[0]);
        try {
            if (PORT < 1023 || PORT > 65535) {
                System.out.println("\u001B[31m" + "Выбран несуществующий или занятый сисемой порт!" + "\u001B[0m");
                System.exit(1);
            }
            ClientManager client = new ClientManager();
            client.start();
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println(ANSI_RED + "Порт для подключения не указан!" + ANSI_RESET);
            System.exit(1);
        }
    }

    public static Integer getPORT() {
        return PORT;
    }
}
