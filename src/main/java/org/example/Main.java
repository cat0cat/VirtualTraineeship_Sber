package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.System.out;

public class Main {

    private static final String FILENAME = "Задача ВС Java Сбер.csv";

    public static void main(String[] args) {
        Collection<City> citiesCollect = new ArrayList<>();
        dataHandling(FILENAME, citiesCollect);
        //SortCityByName(citiesCollect);
        //SortCityByDistrictAndName(citiesCollect);
        //findCityByMaxPopulation(citiesCollect);
        findCountCityByRegion(citiesCollect);
    }

    public static void dataHandling(String fileName, Collection<City> citiesCollect) {
        File file = new File(fileName);
        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");
                String[] cities = new String[6];
                System.arraycopy(parts, 0, cities, 0, parts.length);
                City city = new City(cities[1], cities[2], cities[3], Integer.parseInt(cities[4]), cities[5]);
                citiesCollect.add(city);
                //System.out.println(city);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Сортировка списка городов по наименованию в алфавитном порядке по убыванию без учета регистра;
    public static void SortCityByName(Collection<City> citiesCollect) {
        Collection<City> sortNames = citiesCollect.stream()
                .sorted(Comparator.comparing(City::getName, String::compareToIgnoreCase).reversed())
                .toList();
        sortNames.stream().map((s) -> s + "\n").forEach(out::print);
    }

    //Сортировка списка городов по федеральному округу и наименованию города внутри каждого
    // федерального округа в алфавитном порядке по убыванию с учетом регистра;
    public static void SortCityByDistrictAndName(Collection<City> citiesCollect) {
        Collection<City> sortDistrictAndName = citiesCollect.stream()
                .sorted(Comparator.comparing(City::getDistrict).reversed()
                        .thenComparing(Comparator.comparing(City::getName).reversed()))
                .toList();
        sortDistrictAndName.stream().map((s) -> s + "\n").forEach(out::print);
    }

    //поиск города с наибольшим количеством жителей.
    public static void findCityByMaxPopulation(Collection<City> citiesCollect) {
        // необходимо преобразовать список городов в массив.
        // А затем путем перебора массива найти индекс элемента и значение с наибольшим количеством жителей города.
        City[] cityArray = citiesCollect.toArray(new City[0]);
        int maxValue = 0;
        int pos = 0;
        for (int i = 0; i < cityArray.length; i++) {
            if (cityArray[i].getPopulation() > maxValue) {
                maxValue = cityArray[i].getPopulation();
                pos = i;
            }
        }
        out.println("[" + pos + "] " + "= " + maxValue);
    }

    //поиск количества городов в разрезе регионов.
    public static void findCountCityByRegion(Collection<City> citiesCollect) {
        //Необходимо определить количество городов в каждом регионе.
        City[] cityArray = citiesCollect.toArray(new City[0]);
        final Map<String, Integer> result = new HashMap<>();
        for (City city : cityArray) {
            String key = city.getRegion();
            int count = 1;
            if (result.containsKey(key)) {
                count += result.get(key);
            }
            result.put(key, count);
        }
        out.println(result);
    }
}