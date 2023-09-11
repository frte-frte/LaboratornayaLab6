package application;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientManager {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = Client.getPORT();
    private static final int BUFFER_SIZE = 32000;
    private DatagramChannel channel;
    private ByteBuffer sendBuffer;
    private ByteBuffer receiveBuffer;

    public ClientManager() {

        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);

            sendBuffer = ByteBuffer.allocate(BUFFER_SIZE);
            receiveBuffer = ByteBuffer.allocate(BUFFER_SIZE);

            System.out.println("Клиент запущен. Введите команду: (для выхода введите 'exit'):");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() throws IOException, ClassNotFoundException {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String commands = scanner.nextLine().trim();
                String[] command = commands.split(" ");
                Data clientData = new CommandManager(command).commandToObj();
                if (clientData != null) {
                    send(clientData);
                    receive();
                }
            }
        } catch (NoSuchElementException exception) {
            System.out.println("\u001B[33m" + "Введена пустая строка!" + "\u001B[0m");
        }
    }

    public void send(Data clientData) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(clientData);
            oos.flush();

            ByteBuffer sendBuffer = ByteBuffer.wrap(bos.toByteArray());
            channel.send(sendBuffer, new InetSocketAddress("localhost", SERVER_PORT));
        } catch (IOException e) {
            System.out.println("Сервер недоступен");
        }
    }

    public void receive() throws IOException, ClassNotFoundException {
        Selector selector = this.channel.provider().openSelector();

        this.channel.register(selector, SelectionKey.OP_READ);

        while (true) {
            if (selector.select(100000) == 0) {
                throw new SocketTimeoutException("Сервер недоступен");
            }

            Iterator keys = selector.selectedKeys().iterator();

            while (keys.hasNext()) {
                SelectionKey key = (SelectionKey) keys.next();

                if (!key.isValid()) {
                    continue;
                }

                if (key.isReadable()) {
                    receiveBuffer.clear();
                    SocketAddress serverAddress = channel.receive(receiveBuffer);

                    ByteArrayInputStream byteStream = new ByteArrayInputStream(receiveBuffer.array());
                    ObjectInputStream objStream = new ObjectInputStream(byteStream);

                    Data response = (Data) objStream.readObject();
                    System.out.println("Получен ответ от сервера:\n" + response.getParam()[0]);
                    keys.remove();
                    return;
                }
            }
        }
    }

    public ByteBuffer getSendBuffer() {
        return sendBuffer;
    }

    public void setSendBuffer(ByteBuffer sendBuffer) {
        this.sendBuffer = sendBuffer;
    }
}
