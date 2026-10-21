package com.training;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RealTimeStockMonitor {

    public static void main(String[] args) throws Exception {
        List<Stock> stocks = new ArrayList<>();

        stocks.add(new Stock("AAPL", 100.00, 5.0));
        stocks.add(new Stock("MSFT", 200.00, 3.0));
        stocks.add(new Stock("GOOG", 150.00, 2.0));

        ExecutorService executor = Executors.newFixedThreadPool(stocks.size());

        StockMonitor monitor = new StockMonitor(executor);

        try {
            for (int second = 1; second <= 10; second++) {
                final int currentSecond = second;
                Future<List<String>> future =
                        executor.submit(() ->
                                monitor.monitorStocks(
                                        currentSecond,
                                        stocks.size(),
                                        stocks
                                )
                        );
                List<String> logs = future.get();

                for (String log : logs) {
                    System.out.println(log);
                }

                Thread.sleep(1000);
            }

        } finally {
            executor.shutdown();
        }

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        StockMonitor2 monitor2 = new StockMonitor2();

        //Submit tasks first and then get their results
        List<Future<List<String>>> futures = new ArrayList<>();

        try {

            for (int second = 1; second <= 10; second++) {
                final int currentSecond = second;
                Future<List<String>> future = singleThreadExecutor.submit(() -> 
                    monitor2.monitorStocks(currentSecond, stocks)
                );
                futures.add(future);
                Thread.sleep(10);
            }

            for(Future<List<String>> future : futures) {
                List<String> logs = future.get();
                for (String log : logs) {
                    System.out.println(log);                    
                }
            }
        } finally {
            singleThreadExecutor.shutdown();
        }    
    }
    
}

class Stock {

    private final String name;
    private double price;
    private final double percentageIncrease;

    public Stock(String name, double price, double percentageIncrease) {
        this.name = name;
        this.price = price;
        this.percentageIncrease = percentageIncrease;
    }

    public void updatePrice() {
        price = price + price * (percentageIncrease / 100.0);
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    @Override 
    public String toString() {
        return String.format(Locale.US, "%s : $%.2f", name, price);
    }
}

class StockMonitor {
    private final ExecutorService executorService;

    public StockMonitor(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public synchronized List<String> monitorStocks(int instance, int numberOfStocks, List<Stock> stocks) throws Exception {

        List<String> logs = new ArrayList<>();
        logs.add("Second " + instance + ":");

        List<Callable<Void>> tasks = new ArrayList<>();

        for (Stock stock : stocks) {            
            tasks.add(() -> {
                stock.updatePrice();
                return null;
            });
        }

        executorService.invokeAll(tasks);

        for(Stock stock : stocks) {
            logs.add(stock.toString());
        }
        

        return logs;
    }
}

class StockMonitor2 {
    public synchronized List<String> monitorStocks(int instance, List<Stock> stocks) {
        List<String> logs = new ArrayList<>();
        logs.add("Second " + instance + ":");

        for (Stock stock : stocks) {
            stock.updatePrice();
            logs.add(stock.toString());
        }

        return logs;
    }
}

