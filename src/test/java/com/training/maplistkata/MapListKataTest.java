package com.training.maplistkata;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class MapListKataTest {

    @Test
    public void kata1_mapToFormattedListTest() {
        Map<String, Integer> m = Map.of("E1", 100, "E2", 120);

        List<String> out = MapListKata.mapToFormattedList(m);

        assertEquals(Set.of("id=E1 salary=100", "id=E2 salary=120"), new HashSet<>(out));

    }

    @Test 
    public void kata2_doubledSalariesSorted() {
        Map<String, Integer> m = new HashMap<>();
        m.put("E1", 100);
        m.put("E2", 120);
        m.put("E3", 90);

        assertEquals(List.of(180, 200, 240), MapListKata.kata2_doubledSalariesSorted(m));
    }
}