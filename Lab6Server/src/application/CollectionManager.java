package application;

import Parsers.Deserializer.CoordinatesDeserializer;
import Parsers.Deserializer.FlatsDeserializer;
import Parsers.Deserializer.HouseDeserializer;
import Parsers.Deserializer.LocalDateDeserializer;
import Parsers.Serializer.CoordinatesSerializer;
import Parsers.Serializer.FlatSerializer;
import Parsers.Serializer.HouseSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import data.Coordinates;
import data.Flat;
import data.House;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages a collection of objects
 */
public class CollectionManager {
    final String ANSI_YELLOW = "\u001B[33m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_RESET = "\u001B[0m";
    List<String> recList = new ArrayList<>();
    private static ArrayDeque<Flat> collection;
    private final LocalDateTime initializationTime;
    private String filePath;
    private final Gson gsonDeser = new GsonBuilder()
            .registerTypeAdapter(Coordinates.class, new CoordinatesDeserializer())
            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
            .registerTypeAdapter(House.class, new HouseDeserializer())
            .registerTypeAdapter(ArrayDeque.class, new FlatsDeserializer())
            .create();

    private final Gson gsonSer = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(Flat.class, new FlatSerializer())
            .registerTypeAdapter(Coordinates.class, new CoordinatesSerializer())
            .registerTypeAdapter(House.class, new HouseSerializer())
            .create();


    public CollectionManager(String filePath) {
        this.filePath = filePath;
        initializationTime = LocalDateTime.now();
        collection = load();
    }

    public ArrayDeque load() {
        if (filePath.equals("")) {
            return new ArrayDeque<Flat>();
        }
        if (checkPermissionForRead()) {
            try (FileReader reader = new FileReader(filePath)) {
                StringBuilder data = new StringBuilder();
                while (reader.ready()) {
                    int t = reader.read();
                    data.append((char) t);
                }
                if (data.isEmpty()) {
                    System.out.println(ANSI_YELLOW + "Файл пуст!" + ANSI_RESET);
                    return new ArrayDeque<Flat>();
                } else {
                    ArrayDeque<Flat> result = gsonDeser.fromJson(data.toString(), ArrayDeque.class);
                    System.out.println(ANSI_YELLOW + "Файл " + filePath + " успешно загружен!" + ANSI_RESET);
                    for (Flat flat : result) {
                        if (!checkFlat(flat)){
                            System.out.println(ANSI_RED + "Неверные значения!\n" + ANSI_RESET);
                            return new ArrayDeque<>();
                        }
                    }
                    System.out.println(ANSI_YELLOW + "В коллекции находится "
                            + result.size()
                            + " объект(-а/-ов)" + ANSI_RESET);
                    return result;
                }
            } catch (JsonParseException exception) {
                System.out.println(ANSI_RED + "Проблемы с парсингом файла!" + ANSI_RESET);
                return new ArrayDeque<Flat>();
            } catch (IOException exception){
                return new ArrayDeque<Flat>();
            }
        }
        return new ArrayDeque<Flat>();
    }

    public boolean checkFlat(Flat flat) {
        return (flat.getId() > 0 &&
                !flat.getName().trim().isEmpty() &&
                flat.getCoordinates().getX() > -970 &&
                flat.getCoordinates().getY() <= 113 &&
                flat.getCreationDate() != null &&
                flat.getArea() > 0 &&
                flat.getArea() <=904 &&
                flat.getNumberOfRooms() > 0 &&
                flat.getHouse() != null);
    }

    public boolean checkExecuteScript(String filePath) {
        return this.recList.contains(filePath);
    }

    public boolean checkPermissionForRead() {
        File file = new File(filePath);
        return file.exists() && file.canRead();
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String json = gsonSer.toJson(collection);
            writer.write(json);
            System.out.println(ANSI_YELLOW + "Коллекция сохранена!" + ANSI_RESET);
        } catch (IOException exception) {
            System.out.println(ANSI_RED + "Что-то не так с файлом..." + ANSI_RESET);
        }
    }

    public long getMaxId() {
        long counter = Integer.MIN_VALUE;
        for (Flat flat : collection) {
            if (flat.getId() > counter) {
                counter = flat.getId();
            }
        }
        if (counter == Integer.MIN_VALUE) {
            return 0;
        }
        return counter;
    }

    public Long generateId() {
        return getMaxId() + 1;
    }

    public LocalDateTime getInitializationTime(){
        return initializationTime;
    }

    public ArrayDeque<Flat> getCollection() {
        return collection;
    }

    public void setCollection(ArrayDeque<Flat> collection) {
        this.collection = collection;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getRecList() {
        return recList;
    }

    public void setRecList(List<String> recList) {
        this.recList = recList;
    }
}
