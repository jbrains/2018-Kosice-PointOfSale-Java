package ca.jbrains.pos.test;

public class SellOneItemController {
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
