package com.web.oneby.commons.Utils;

import com.web.oneby.commons.Enums.Language;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortingUtil {

    public static List<Order> getSortingOrders(Map<String, Direction> filterSorts, int language){
        Map<String, Direction> sorts = new HashMap<>();
        for (Map.Entry<String, Direction> e: filterSorts.entrySet()) {
            switch (e.getKey()) {
                case "name" -> sorts.put("name" + Language.getLanguageById(language).suffix(), e.getValue());
                case "title" -> sorts.put("title" + Language.getLanguageById(language).suffix(), e.getValue());
                case "description" -> sorts.put("description" + Language.getLanguageById(language).suffix(), e.getValue());
                default -> sorts.put(e.getKey(), e.getValue());
            }
        }
        return sorts
                .entrySet()
                .stream()
                .map((map) -> new Order(map.getValue(), map.getKey()))
                .toList();
    }

    public static List<Order> getSortingOrders(Map<String, Direction> filterSorts, Language language){
        Map<String, Direction> sorts = new HashMap<>();
        for (Map.Entry<String, Direction> e: filterSorts.entrySet()) {
            switch (e.getKey()) {
                case "name" -> sorts.put("name" + language.suffix(), e.getValue());
                case "title" -> sorts.put("title" + language.suffix(), e.getValue());
                case "description" -> sorts.put("description" + language.suffix(), e.getValue());
                default -> sorts.put(e.getKey(), e.getValue());
            }
        }
        return sorts
                .entrySet()
                .stream()
                .map((map) -> new Order(map.getValue(), map.getKey()))
                .toList();
    }

}
