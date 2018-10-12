package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FindPriceInMemoryCatalogTest extends FindPriceInCatalogContract {

    @Override
    protected Catalog catalogWith(String barcode, Price matchingPrice) {
        return new InMemoryCatalog(new HashMap<String, Price>() {{
            put(String.format("not %s", barcode), Price.euroCents(-1));
            put(String.format("definitely not %s", barcode), Price.euroCents(-2));
            put(barcode, matchingPrice);
            put(String.format("obviously not %s, you idiot", barcode), Price.euroCents(-3));
        }});
    }

    @Override
    protected Catalog catalogWithout(String barcodeToAvoid) {
        return new InMemoryCatalog(new HashMap<String, Price>() {{
            put(String.format("not %s", barcodeToAvoid), Price.euroCents(-1));
            put(String.format("definitely not %s", barcodeToAvoid), Price.euroCents(-2));
            put(String.format("obviously not %s, you idiot", barcodeToAvoid), Price.euroCents(-3));
        }});
    }

    public static class InMemoryCatalog implements Catalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
