package cc.asako;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        System.out.println("test".replaceAll("\\.[^.]*$", ""));
        System.out.println("test.zip.006".replaceAll("\\.([a-zA-Z]+\\w*|\\w*[a-zA-Z]+)$", ""));
        assertTrue( true );
    }
}
