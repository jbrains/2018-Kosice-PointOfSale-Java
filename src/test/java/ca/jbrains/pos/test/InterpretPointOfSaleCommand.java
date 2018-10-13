package ca.jbrains.pos.test;

public class InterpretPointOfSaleCommand implements InterpretCommand {
    private BarcodeScannedListener barcodeScannedListener;

    public InterpretPointOfSaleCommand(BarcodeScannedListener barcodeScannedListener) {
        this.barcodeScannedListener = barcodeScannedListener;
    }

    @Override
    public void interpretCommand(String command) {
        barcodeScannedListener.onBarcode(command);
    }
}
