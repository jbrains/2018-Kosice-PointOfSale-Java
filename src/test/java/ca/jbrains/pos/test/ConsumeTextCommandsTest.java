package ca.jbrains.pos.test;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

public class ConsumeTextCommandsTest {

    private final BarcodeScannedListener barcodeScannedListener = Mockito.mock(BarcodeScannedListener.class);

    @Test
    public void oneBarcode() throws Exception {
        consumeTextCommandsUsingListener(
                new StringReader(unlines(Arrays.asList("::barcode::"))),
                barcodeScannedListener);

        Mockito.verify(barcodeScannedListener).onBarcode("::barcode::");
        // REFACTOR I think this becomes "verify at most n commands (of any kind, let alone 'barcode')".
        Mockito.verify(barcodeScannedListener, Mockito.atMost(1)).onBarcode(Mockito.anyString());
    }

    @Test
    public void noBarcodes() throws Exception {
        consumeTextCommandsUsingListener(
                new StringReader(""),
                barcodeScannedListener);

        Mockito.verify(barcodeScannedListener, Mockito.never()).onBarcode(Mockito.any());
    }

    @Test
    public void threeBarcodes() throws Exception {
        consumeTextCommandsUsingListener(
                new StringReader(unlines(Arrays.asList("::barcode 1::", "::barcode 2::", "::barcode 3::"))),
                barcodeScannedListener);

        Mockito.verify(barcodeScannedListener).onBarcode("::barcode 1::");
        Mockito.verify(barcodeScannedListener).onBarcode("::barcode 2::");
        Mockito.verify(barcodeScannedListener).onBarcode("::barcode 3::");
        Mockito.verify(barcodeScannedListener, Mockito.atMost(3)).onBarcode(Mockito.anyString());
    }

    private static String unlines(List<String> lines) {
        return lines.stream()
                .map(line -> String.format("%s\n", line))
                .reduce("", (s, s2) -> s + s2);
    }

    // SMELL 'consume' seems vague as name.
    // SMELL StringReader is probably too specific
    private void consumeTextCommandsUsingListener(StringReader stringReader, BarcodeScannedListener barcodeScannedListener) throws IOException {
        new BufferedReader(stringReader).lines().forEach(barcodeScannedListener::onBarcode);
    }
}
