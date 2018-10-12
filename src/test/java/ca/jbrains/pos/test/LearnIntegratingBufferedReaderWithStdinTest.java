package ca.jbrains.pos.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LearnIntegratingBufferedReaderWithStdinTest {
    private final InputStream realSystemIn = System.in;

    @Before
    public void setUp() throws Exception {
        System.setIn(new ByteArrayInputStream("".getBytes("UTF-8")));
    }

    @Test
    public void noInputThroughBufferedReader() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        // readLine returns null repeatedly to signal "no more input"
        Assert.assertEquals(null, bufferedReader.readLine());
        Assert.assertEquals(null, bufferedReader.readLine());
        Assert.assertEquals(null, bufferedReader.readLine());
    }

    @After
    public void tearDown() throws Exception {
        System.setIn(realSystemIn);
    }
}
