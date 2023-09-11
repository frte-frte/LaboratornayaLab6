package application;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Scanner;

public class ServerManager {
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_RESET = "\u001B[0m";
    private static final int PORT = Server.getPORT();
    private static final int BUFFER_SIZE = 32000;
    private DatagramChannel channel;
    private Selector selector;
    private ByteBuffer receiveBuffer;
    private ByteBuffer sendBuffer;
    InetSocketAddress clientAddress;

    public ServerManager() {
        try {
            channel = DatagramChannel.open();
            channel.socket().bind(new InetSocketAddress(PORT));
            channel.configureBlocking(false);

            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);

            receiveBuffer = ByteBuffer.allocate(BUFFER_SIZE);
            sendBuffer = ByteBuffer.allocate(BUFFER_SIZE);

            System.out.println(ANSI_GREEN + "Сервер запущен! Ожидание подключений..." + ANSI_RESET);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start(CollectionManager collectionManager) {
        try {
            while (true) {
                selector.select();

                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();

                    if (key.isReadable()) {
                        DatagramChannel channel = (DatagramChannel) key.channel();

                        Thread keyBoardThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Scanner scanner = new Scanner(System.in);
                                while (true) {
                                    String input = scanner.nextLine();
                                    switch (input) {
                                        case ("exit"):
                                            System.out.println("Сервер закончил работу");
                                            System.exit(0);
                                        case ("save"):
                                            collectionManager.save();
                                            System.out.println("Данные сохранены");
                                    }
                                }
                            }
                        });
                        keyBoardThread.start();
                        InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(receiveBuffer);
                        Data clientData = receive(clientAddress);
                        Data serverData = new Data("serverData",
                                new String[]{new CommandManager(clientData).serverWork(collectionManager, clientAddress)});
                        send(serverData, clientAddress);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Data receive(SocketAddress clientAddress) {
        try {
            receiveBuffer.clear();
            ByteArrayInputStream bis = new ByteArrayInputStream(receiveBuffer.array());
            ObjectInputStream ois = new ObjectInputStream(bis);
            Data clientData = (Data) ois.readObject();
            System.out.println(ANSI_GREEN + "Получено сообщение от клиента " + ANSI_RESET + clientAddress + ": "
                    + clientData);
            return clientData;
        } catch (IOException exception) {
            System.out.println("Сервер недоступен");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void send(Data serverData, SocketAddress clientAddress) {
        try {
            sendBuffer.clear();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(serverData);
            oos.flush();

            sendBuffer = ByteBuffer.wrap(bos.toByteArray());
            channel.send(sendBuffer, clientAddress);

        } catch (IOException exception) {
            System.out.println("Сервер недоступен");
        }



    }
}
