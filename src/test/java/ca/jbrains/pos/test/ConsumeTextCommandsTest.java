package ca.jbrains.pos.test;

import io.vavr.collection.Stream;
import org.junit.Test;
import org.mockito.Mockito;

public class ConsumeTextCommandsTest {
    private final BarcodeScannedListener barcodeScannedListener = Mockito.mock(BarcodeScannedListener.class);

    @Test
    public void oneBarcode() throws Exception {
        Stream<String> commands = tokenize(new RemovingWhitespaceCommandLexer(), Stream.of("::barcode::"));
        interpretCommands(barcodeScannedListener, commands);

        Mockito.verify(barcodeScannedListener).onBarcode("::barcode::");
        // REFACTOR I think this becomes "verify at most n commands (of any kind, let alone 'barcode')".
        Mockito.verify(barcodeScannedListener, Mockito.atMost(1)).onBarcode(Mockito.anyString());
    }

    @Test
    public void noBarcodes() throws Exception {
        Stream<String> commands = tokenize(new RemovingWhitespaceCommandLexer(), Stream.empty());
        interpretCommands(barcodeScannedListener, commands);

        Mockito.verify(barcodeScannedListener, Mockito.never()).onBarcode(Mockito.any());
    }

    @Test
    public void threeBarcodes() throws Exception {
        Stream<String> commands = tokenize(new RemovingWhitespaceCommandLexer(), Stream.of("::barcode 1::", "::barcode 2::", "::barcode 3::"));
        interpretCommands(barcodeScannedListener, commands);

        Mockito.verify(barcodeScannedListener).onBarcode("::barcode 1::");
        Mockito.verify(barcodeScannedListener).onBarcode("::barcode 2::");
        Mockito.verify(barcodeScannedListener).onBarcode("::barcode 3::");
        Mockito.verify(barcodeScannedListener, Mockito.atMost(3)).onBarcode(Mockito.anyString());
    }

    private void interpretCommands(BarcodeScannedListener barcodeScannedListener, Stream<String> commands) {
        commands.forEach(barcodeScannedListener::onBarcode);
    }

    private Stream<String> tokenize(CommandLexer commandLexer, Stream<String> lines) {
        return lines.flatMap(commandLexer::tokenize);
    }
}
