package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

public abstract class FindPriceInCatalogContract {
    @Test
    public void productFound() throws Exception {
        Price matchingPrice = Price.euroCents(700);

        Catalog catalog = catalogWith("::known barcode::", matchingPrice);

        Assert.assertEquals(matchingPrice, catalog.findPrice("::known barcode::"));
    }

    protected abstract Catalog catalogWith(String barcode, Price matchingPrice);

    @Test
    public void productNotFound() throws Exception {
        Catalog catalog = catalogWithout("::missing barcode::");

        Assert.assertEquals(null, catalog.findPrice("::missing barcode::"));
    }

    protected abstract Catalog catalogWithout(String barcodeToAvoid);
}
