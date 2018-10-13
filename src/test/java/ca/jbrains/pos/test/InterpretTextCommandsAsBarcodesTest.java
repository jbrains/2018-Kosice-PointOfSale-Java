package ca.jbrains.pos.test;

import io.vavr.collection.Stream;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.function.Consumer;

public class InterpretTextCommandsAsBarcodesTest {
    private final BarcodeScannedListener barcodeScannedListener = Mockito.mock(BarcodeScannedListener.class);

    @Test
    public void oneBarcode() throws Exception {
        interpretCommands(Stream.of("::barcode::"), barcodeScannedListener::onBarcode);

        Mockito.verify(barcodeScannedListener).onBarcode("::barcode::");
        // REFACTOR I think this becomes "verify at most n commands (of any kind, let alone 'barcode')".
        Mockito.verify(barcodeScannedListener, Mockito.atMost(1)).onBarcode(Mockito.anyString());
    }

    @Test
    public void noBarcodes() throws Exception {
        interpretCommands(Stream.empty(), barcodeScannedListener::onBarcode);

        Mockito.verify(barcodeScannedListener, Mockito.never()).onBarcode(Mockito.any());
    }

    @Test
    public void threeBarcodes() throws Exception {
        interpretCommands(Stream.of(
                "::barcode 1::", "::barcode 2::", "::barcode 3::"),
                barcodeScannedListener::onBarcode);

        Mockito.verify(barcodeScannedListener).onBarcode("::barcode 1::");
        Mockito.verify(barcodeScannedListener).onBarcode("::barcode 2::");
        Mockito.verify(barcodeScannedListener).onBarcode("::barcode 3::");
        Mockito.verify(barcodeScannedListener, Mockito.atMost(3)).onBarcode(Mockito.anyString());
    }

    // CONTRACT
    // assume that all commands are "valid" for whatever meaning of "valid" matters to you.
    private void interpretCommands(Stream<String> commands, Consumer<String> interpretCommand) {
        commands.forEach(interpretCommand);
    }
}
