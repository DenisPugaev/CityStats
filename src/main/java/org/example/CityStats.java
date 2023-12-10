package org.example;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class CityStatsApp {
    private static final Map<String, Integer> cityDuplicates = new HashMap<>();
    private static final Map<String, Map<Integer, Integer>> cityBuildingStats = new HashMap<>();
    private List<Item> items;

    public List<Item> getItems() {
        return this.items;
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь до файла или 'exit' для завершения работы:");

        while (scanner.hasNextLine()) {
            String inputLine = scanner.nextLine();
            if ("exit".equalsIgnoreCase(inputLine.trim())) {
                System.out.println("Завершение работы приложения.");
                break;
            } else {
                processFile(inputLine);
            }
            System.out.println("Введите путь до следующего файла или 'exit' для завершения:");
        }
        scanner.close();
    }

    private static void processFile(String filePath) {
        String fileExtension = getFileExtension(filePath);
        if (fileExtension.equals("xml")) {
            analyzeXMLFile(filePath);
        } else if (fileExtension.equals("csv")) {
            analyzeCSVFile(filePath);
        } else {
            System.out.println("Неподдерживаемый формат файла: " + filePath);
        }
    }

    private static String getFileExtension(String filePath) {
        int index = filePath.lastIndexOf('.');
        return filePath.substring(index + 1);
    }

    private static void analyzeXMLFile(String filePath) {
        try {
            File file = new File(filePath);
            JAXBContext jaxbContext = JAXBContext.newInstance(CityStatsApp.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            CityStatsApp cityStatsApp = (CityStatsApp) jaxbUnmarshaller.unmarshal(file);

            for (Item item : cityStatsApp.getItems()) {
                String city = item.getCity();
                int floor = item.getFloor();

                cityDuplicates.put(city, cityDuplicates.getOrDefault(city, 0) + 1);

                Map<Integer, Integer> floorStats = cityBuildingStats.computeIfAbsent(city, k -> new HashMap<>());
                floorStats.put(floor, floorStats.getOrDefault(floor, 0) + 1);
            }

            System.out.println("Статистика дублирующихся записей:");
            cityDuplicates.forEach((city, count) -> System.out.println(city + ": " + count));

            System.out.println("\nСтатистика этажей по городам:");
            cityBuildingStats.forEach((city, stats) -> System.out.println(city + ": " + stats));
        } catch (JAXBException e) {
            System.out.println("Ошибка при чтении файла XML: " + filePath);
            e.printStackTrace();
        }
    }

    private static void analyzeCSVFile(String filePath) {
        try {
            //TODO
            // Логика анализа CSV-файла для поиска дублирующихся записей и статистики
        } catch (Exception e) {
            System.out.println("Ошибка при обработке файла CSV: " + filePath);
            e.printStackTrace();
        }
    }
}
