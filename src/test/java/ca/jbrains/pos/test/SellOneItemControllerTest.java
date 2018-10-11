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

    public interface Catalog {
        Price findPrice(String barcode);
    }

    public interface Display {
        void displayPrice(Price matchingPrice);

        void displayProductNotFoundMessage(String barcodeNotFound);

        void displayScannedEmptyBarcodeMessage();
    }

    public static class Price {
        public static Price euroCents(int euroCents) {
            return new Price();
        }
    }

    public static class SellOneItemController {
        private final Catalog catalog;
        private final Display display;

        public SellOneItemController(Catalog catalog, Display display) {
            this.catalog = catalog;
            this.display = display;
        }

        public void onBarcode(String barcode) {
            if ("".equals(barcode)) {
                display.displayScannedEmptyBarcodeMessage();
                return;
            }

            Price price = catalog.findPrice(barcode);
            if (price == null) {
                display.displayProductNotFoundMessage(barcode);
            } else {
                display.displayPrice(price);
            }
        }
    }
}
