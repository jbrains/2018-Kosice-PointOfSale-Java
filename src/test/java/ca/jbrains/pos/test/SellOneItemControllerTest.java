package ca.jbrains.pos.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class SellOneItemControllerTest {
    private Catalog catalog;
    private Display display;
    private SellOneItemController controller;

    @Before
    public void initializeController() throws Exception {
        catalog = Mockito.mock(Catalog.class);
        display = Mockito.mock(Display.class);
        controller = new SellOneItemController(catalog, display);
    }

    @Test
    public void productFound() throws Exception {
        Price matchingPrice = Price.euroCents(450);
        Mockito.when(catalog.findPrice("::any barcode::")).thenReturn(matchingPrice);

        controller.onBarcode("::any barcode::");

        Mockito.verify(display).displayPrice(matchingPrice);
    }

    @Test
    public void productNotFound() throws Exception {
        Mockito.when(catalog.findPrice("::any barcode::")).thenReturn(null);

        controller.onBarcode("::any barcode::");

        Mockito.verify(display).displayProductNotFoundMessage("::any barcode::");
    }

    @Test
    public void emptyBarcode() throws Exception {
        controller.onBarcode("");

        Mockito.verify(display).displayScannedEmptyBarcodeMessage();
    }
}
