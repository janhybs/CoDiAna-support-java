package cz.edu.x3m;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class JavaLanguageTest extends JavaLanguage {

    public JavaLanguageTest () {
    }



    @BeforeClass
    public static void setUpClass () {
    }



    @AfterClass
    public static void tearDownClass () {
    }



    @Before
    public void setUp () {
    }



    @After
    public void tearDown () {
    }



    /**
     * Test of postCompilation method, of class JavaLanguage.
     */
    @Test
    public void testPostCompilation () {
//        System.out.println ("postCompilation");
    }



    /**
     * Test of setExecutionSettings method, of class JavaLanguage.
     */
    @Test
    public void testSetExecutionSettings () {
//        System.out.println ("setExecutionSettings");
    }



    /**
     * Test of preExecution method, of class JavaLanguage.
     */
    @Test
    public void testPreExecution () {
//        System.out.println ("preExecution");
    }



    /**
     * Test of execute method, of class JavaLanguage.
     */
    @Test
    public void testExecute () {
//        System.out.println ("execute");
    }



    /**
     * Test of postExecution method, of class JavaLanguage.
     */
    @Test
    public void testPostExecution () {
//        System.out.println ("postExecution");
    }



    @Test
    public void testGetClassPath () {
        System.out.println ("getClassPath");
        File classFile = new File ("c:/xampp/htdocs/moodle/mod/codiana/data/task-0002/user-0004/curr/cz/edu/x3m/utils/Yummy.class");
        File rootFile = new File ("c:/xampp/htdocs/moodle/mod/codiana/data/task-0002/user-0004/curr/");
        String result = this.getClassPath (classFile, rootFile);
        String expected = "cz.edu.x3m.utils.Yummy";
        assertEquals (result, expected);
    }



    @Test
    public void testOverall () {
    }
}