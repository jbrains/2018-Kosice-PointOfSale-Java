package ca.jbrains.pos.test;

import ca.jbrains.java.ReaderBasedTextSource;
import io.vavr.collection.Stream;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

public class ConsumeTextCommandsTest {

    private final BarcodeScannedListener barcodeScannedListener = Mockito.mock(BarcodeScannedListener.class);

    @Test
    public void oneBarcode() throws Exception {
        consumeTextCommandsUsingListener(
                new StringReader(unlines("::barcode::")),
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
                new StringReader(unlines("::barcode 1::", "::barcode 2::", "::barcode 3::")),
                barcodeScannedListener);

        Mockito.verify(barcodeScannedListener).onBarcode("::barcode 1::");
        Mockito.verify(barcodeScannedListener).onBarcode("::barcode 2::");
        Mockito.verify(barcodeScannedListener).onBarcode("::barcode 3::");
        Mockito.verify(barcodeScannedListener, Mockito.atMost(3)).onBarcode(Mockito.anyString());
    }

    // REFACTOR Move to some general-purpose text library
    private static String unlines(String... lines) {
        return Arrays.asList(lines).stream()
                .map(line -> String.format("%s\n", line))
                .reduce("", (s, s2) -> s + s2);
    }

    // SMELL 'consume' seems vague as name.
    // SMELL StringReader is probably too specific
    private void consumeTextCommandsUsingListener(StringReader stringReader, BarcodeScannedListener barcodeScannedListener) throws IOException {
        // REFACTOR Cluster of behavior. Let's separate sanitize() from the rest.
        CommandLexer commandLexer = new RemovingWhitespaceCommandLexer();
        streamLinesFromSource(stringReader)
                .flatMap(commandLexer::tokenize)
                .forEach(barcodeScannedListener::onBarcode);
    }

    // DMZ
    // SMELL Is the something even more generic than Reader?
    // REFACTOR Introduce TextSource interface?
    private Stream<String> streamLinesFromSource(Reader source) {
        return new ReaderBasedTextSource(source).parseIntoLines();
    }
}
