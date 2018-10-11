package ca.jbrains.pos.test;

import org.junit.Test;
import org.mockito.Mockito;

public class SellOneItemControllerTest {
    @Test
    public void productFound() throws Exception {
        Catalog catalog = Mockito.mock(Catalog.class);
        Display display = Mockito.mock(Display.class);
        SellOneItemController controller = new SellOneItemController(catalog, display);

        Price matchingPrice = Price.euroCents(450);
        Mockito.when(catalog.findPrice("12345")).thenReturn(matchingPrice);

        controller.onBarcode("12345");

        Mockito.verify(display).displayPrice(matchingPrice);
    }

    @Test
    public void productNotFound() throws Exception {
        Catalog catalog = Mockito.mock(Catalog.class);
        Display display = Mockito.mock(Display.class);
        SellOneItemController controller = new SellOneItemController(catalog, display);

        Mockito.when(catalog.findPrice("12345")).thenReturn(null);
        controller.onBarcode("12345");

        Mockito.verify(display).displayProductNotFoundMessage("12345");
    }

    public interface Catalog {
        Price findPrice(String barcode);
    }

    public interface Display {
        void displayPrice(Price matchingPrice);

        void displayProductNotFoundMessage(String barcodeNotFound);
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
            Price price = catalog.findPrice(barcode);
            if (price == null) {
                display.displayProductNotFoundMessage(barcode);
            } else {
                display.displayPrice(price);
            }
        }
    }
}
