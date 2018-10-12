package ca.jbrains.pos.test;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.StringReader;

public class ConsumeTextCommandsTest {
    @Test
    public void oneBarcode() throws Exception {
        BarcodeScannedListener barcodeScannedListener = Mockito.mock(BarcodeScannedListener.class);

        // SMELL 'consume' seems vague as name.
        consumeTextCommandsUsingListener(
                new StringReader("::barcode::\n"),
                barcodeScannedListener);

        Mockito.verify(barcodeScannedListener).onBarcode("::barcode::");
    }

    // SMELL StringReader is probably too specific
    private void consumeTextCommandsUsingListener(StringReader stringReader, BarcodeScannedListener barcodeScannedListener) throws IOException {
    }
}
