package ca.jbrains.pos.test;

import io.vavr.collection.Stream;
import org.junit.Test;
import org.mockito.Mockito;

public class InterpretTextCommandsWithCommandInterpreterTest {
    private final InterpretCommand interpretCommand = Mockito.mock(InterpretCommand.class);

    @Test
    public void oneCommand() throws Exception {
        interpretCommandsWith(Stream.of("::command::"), interpretCommand);

        Mockito.verify(interpretCommand).interpretCommand("::command::");
        // REFACTOR I think this becomes "verify at most n commands (of any kind, let alone 'command')".
        Mockito.verify(interpretCommand, Mockito.atMost(1)).interpretCommand(Mockito.anyString());
    }

    @Test
    public void noCommands() throws Exception {
        interpretCommandsWith(Stream.empty(), interpretCommand);

        Mockito.verify(interpretCommand, Mockito.never()).interpretCommand(Mockito.any());
    }

    @Test
    public void threeCommands() throws Exception {
        interpretCommandsWith(Stream.of(
                "::command 1::", "::command 2::", "::command 3::"),
                interpretCommand);

        Mockito.verify(interpretCommand).interpretCommand("::command 1::");
        Mockito.verify(interpretCommand).interpretCommand("::command 2::");
        Mockito.verify(interpretCommand).interpretCommand("::command 3::");
        Mockito.verify(interpretCommand, Mockito.atMost(3)).interpretCommand(Mockito.anyString());
    }

    // CONTRACT
    // assume that all commands are "valid" for whatever meaning of "valid" matters to you.
    private void interpretCommandsWith(Stream<String> commands, InterpretCommand interpretCommand) {
        commands.forEach(interpretCommand::interpretCommand);
    }
}
