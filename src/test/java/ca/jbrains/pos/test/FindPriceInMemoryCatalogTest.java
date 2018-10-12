package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FindPriceInMemoryCatalogTest {
    @Test
    public void productFound() throws Exception {
        Price matchingPrice = Price.euroCents(700);

        InMemoryCatalog catalog = new InMemoryCatalog(Collections.singletonMap(
                "62746", matchingPrice));

        Assert.assertEquals(matchingPrice, catalog.findPrice("62746"));
    }

    @Test
    public void productFoundAmongMany() throws Exception {
        Price matchingPrice = Price.euroCents(700);

        InMemoryCatalog catalog = new InMemoryCatalog(new HashMap<String, Price>() {{
            put("not 62746", Price.euroCents(-1));
            put("definitely not 62746", Price.euroCents(-2));
            put("62746", matchingPrice);
            put("obviously not 62746, you idiot", Price.euroCents(-3));
        }});

        Assert.assertEquals(matchingPrice, catalog.findPrice("62746"));
    }

    @Test
    public void productNotFound() throws Exception {
        InMemoryCatalog catalog = new InMemoryCatalog(Collections.emptyMap());

        Assert.assertEquals(null, catalog.findPrice("62746"));
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
