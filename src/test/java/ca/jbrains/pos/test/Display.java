package ca.jbrains.pos.test;

public interface Display {
    void displayPrice(Price matchingPrice);

    void displayProductNotFoundMessage(String barcodeNotFound);

    void displayScannedEmptyBarcodeMessage();
}
