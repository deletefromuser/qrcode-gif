package cc.asako;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        System.out.println("test".replaceAll("\\.[^.]*$", ""));
        System.out.println("test.zip.006".replaceAll("\\.([a-zA-Z]+\\w*|\\w*[a-zA-Z]+)$", ""));
        assertTrue(true);
    }

    @Test
    public void test() throws Exception {
        App.main(new String[]{"input - 副本.txt"});
    }
}
