package com.training;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CryptoCurrencyPriceManagementSystem {

    public static void main (String args[]) throws Exception {

        CryptoCurrencyManager manager = new CryptoCurrencyManager();
        CryptoCurrencyListenerImpl btcListener = new CryptoCurrencyListenerImpl("BTC");

        manager.registerListener(btcListener);

        int threadCount = 10;
        int operationsPerThread = 1000;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        try {
            for(int i = 0; i < operationsPerThread; i++) {
                executor.submit(() -> manager.updatePrice("BTC", "increase", 1, LocalDateTime.now()));
            }            
        } finally {
            executor.shutdown();
            executor.awaitTermination(30, TimeUnit.SECONDS);
        }        

        System.out.println(btcListener.getPrice());


    }

}

class CryptoCurrencyManager {

    Map<String, CryptoCurrencyListener> listeners = new ConcurrentHashMap<>(); 
    
    public void registerListener(CryptoCurrencyListener listener) {
        listeners.put(listener.getCurrencyCode(), listener);
    }

    public void updatePrice(String cryptoCurrency, String action, int amount, LocalDateTime currentTime) {
        CryptoCurrencyListener listener = listeners.get(cryptoCurrency);
        if (listener != null) {
            listener.updatePrice(action, amount, currentTime);
        }
    }
}

interface CryptoCurrencyListener {
    void increase(int amount);
    void decrease(int amount);
    void updatePrice(String action, int amount, LocalDateTime currentTime);
    String getCurrencyCode();
    int getPrice();
    List<String> getCommandHistory();
}

class CryptoCurrencyListenerImpl implements CryptoCurrencyListener {
    private final String currencyCode;
    private final Object lock = new Object();
    private int price;
    private final CopyOnWriteArrayList<PriceChange> history = new CopyOnWriteArrayList<>();

    public CryptoCurrencyListenerImpl(String currencyCode) {
        this.currencyCode = currencyCode;
    }


    @Override
    public void increase(int amount) {
        synchronized(lock) {
            price += amount;
        }
    }

    @Override
    public void decrease(int amount) {
        synchronized(lock) {
            price -= amount;
        }        
    }

    @Override
    public void updatePrice(String action, int amount, LocalDateTime currentTime) {
        synchronized(lock) {
            if("increase".equals(action)) {
                increase(amount);
            } else if ("decrease".equals(action)) {
                decrease(amount);
            }
        }
        
        history.add(new PriceChange(action, amount, currentTime));

    }

    @Override
    public String getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public List<String> getCommandHistory() {
        return history.stream()
                .sorted(Comparator.comparing(PriceChange::getTime))
                .map(change -> change.getAction() + " " + change.getAmount())
                .toList();
    }

    static class PriceChange {
        private String action;
        private int amount;
        private LocalDateTime time;

        PriceChange(String action, int amount, LocalDateTime time) {
            this.action = action;
            this.amount = amount;
            this.time = time;
        }

        public String getAction() { return action; }
        public int getAmount() { return amount; }
        public LocalDateTime getTime() { return time; };
    }
}