package com.web.oneby.Utils;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import java.util.List;
import java.util.Map;

public class SortingUtils {

    public static List<Order> getSoringOrders(Map<String, Direction> sorts){
        return sorts
                .entrySet()
                .stream()
                .map((map) -> new Order(map.getValue(), map.getKey()))
                .toList();
    }

}
