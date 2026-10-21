package com.training;

/*
Region Hierarchy Problem – Find the Smallest Common Region

You're given a world map represented as a hierarchy of regions.
Each list contains a parent region followed by its immediate sub-regions.

Example structure:

    "Earth"
    ├── "North America"
    │   ├── "Mexico"
    │   │   ├── "Oaxaca"
    │   │   └── "Puebla"
    │   ├── "United States"
    │   │   ├── "California"
    │   │   └── "New York"
    │   └── "Canada"
    └── "South America"
        ├── "Argentina"
        └── "Chile"

Your task is to write a function that finds the ** smallest region ** (i.e., closest common parent)
that contains ** two given regions **.

For example:
- smallestCommonRegion("California", "Mexico") → "North America"
- smallestCommonRegion("Chile", "Puebla") → "Earth"
- smallestCommonRegion("Ontario", "Quebec") → "Canada"

Input:
A list of lists where:
- The first element is a **parent region**
- The rest are its **sub-regions**

Goal:
Implement a method that takes this input and two region names,
and returns the **lowest common region** that includes both.

Think of it as finding the lowest common ancestor in a family tree.
*/

import java.util.*;
import java.util.stream.Collectors;

public class SmallestCommonRegion {

    public static String findSmallestRegion(List<List<String>> regions, String region1, String region2) {

        Map<String, String> childsParentMap = new HashMap<>();

        for(List<String> region : regions) {
            String parent = region.get(0);
            for (int i = 1; i < region.size(); i++) {
                childsParentMap.put(region.get(i), parent);
            }
        }

        Set<String> region1AncerstorsList = new HashSet<>();

        while(region1 != null) {
            region1AncerstorsList.add(region1);
            region1 = childsParentMap.get(region1);
        }

        while(region2 != null) {
            if(region1AncerstorsList.contains(region2)) return region2;
            region2 = childsParentMap.get(region2);
        }

        return null;
    }

    public static void main(String[] args) {
        List<List<String>> regions = Arrays.asList(
                Arrays.asList("Earth", "North America", "South America"),
                Arrays.asList("North America", "Mexico", "United States", "Canada"),
                Arrays.asList("South America", "Argentina", "Brazil", "Chile"),
                Arrays.asList("Mexico", "Oaxaca", "Puebla"),
                Arrays.asList("United States", "California", "Wyoming", "New York"),
                Arrays.asList("Canada", "Ontario", "Quebec", "Saskatchewan")
        );

        // Test cases
        System.out.println(findSmallestRegion(regions, "California", "Mexico")); // North America
        System.out.println(findSmallestRegion(regions, "Chile", "Puebla"));      // Earth
        System.out.println(findSmallestRegion(regions, "Ontario", "Quebec"));    // Canada
        System.out.println(findSmallestRegion(regions, "California", "New York")); // United States
        System.out.println(findSmallestRegion(regions, "Oaxaca", "Puebla"));       // Mexico
    }
}
