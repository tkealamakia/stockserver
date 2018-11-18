package com.tsunazumi.stockserver;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@CrossOrigin(origins="https://localhost:8080")
public class StockController {

    private static int requests = 0;
    private static final double MAX_PRICE = 100.0; // $100.00
    private static final double MAX_PRICE_CHANGE = 0.02; // +/- 2%

    @ControllerAdvice
    static class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
        public JsonpAdvice() {
            super("getStock");
        }
    }

    @RequestMapping("/stockPrices")
    public List<StockPrice> getStock(@RequestParam("q") String stockSymbols) {
        System.out.println("got a request" + ++requests);
        Random rnd = new Random();

        String[] stockSym = stockSymbols.split(" ");
        boolean firstSymbol = true;
        List<StockPrice> list = new ArrayList<>();
        for (String stockSymbol : stockSym) {

            double price = rnd.nextDouble() * MAX_PRICE;
            double change = price * MAX_PRICE_CHANGE * (rnd.nextDouble() * 2f - 1f);
            StockPrice sp = new StockPrice();
            sp.setPrice(price);
            sp.setSymbol(stockSymbol);
            sp.setChange(change);
            list.add(sp);
        }
        return list;
    }

    class CallbackName {
        List<StockPrice> stockList;

        public List<StockPrice> getStockList() {
            return stockList;
        }

        public void setStockList(List<StockPrice> stockList) {
            this.stockList = stockList;
        }
    }
}
