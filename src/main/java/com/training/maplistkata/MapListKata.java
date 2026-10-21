package com.training.maplistkata;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapListKata {

    public record Employee(String id, String name, String dept, int salary) {}

    private static List<Employee> sampleEmployees () {
        return List.of(
            new Employee("E1", "Ana", "ENG", 100),
            new Employee("E2", "Bob", "ENG",120),
            new Employee("E3", "Caro","HR",90),
            new Employee("E4", "Dan", "HR", 110),
            new Employee("E2", "Bob2", "ENG", 130)
        );
    }

    public static List<String> mapToFormattedList(Map<String,Integer> salaryById) {        
        Map<String, Integer> safe = salaryById == null ? Map.of() : salaryById;

        return safe.entrySet().stream()
            .map(e -> "id=" + e.getKey() + " salary=" + e.getValue())
            .toList();

    }

    public static List<Integer> kata2_doubledSalariesSorted(Map<String, Integer> salariesMap) {
        return salariesMap.values().stream()
            .map(s -> s*2)
            .sorted()
            .toList();            
    }

    public static int simpleArraySum(List<Integer> ar) {
        return ar.stream().reduce(0, (sum,n) -> sum + n);
    }



    
}
