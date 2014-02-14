package cz.edu.x3m.processing.compilation;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public interface ICompileResult {

    /**
     * Method returns process exit value or -1 if process was not launched
     */
    int getExitValue ();



    /**
     * Method returns Throwable linked to this object or null
     */
    Throwable getThrowable ();



    /**
     * Method returns additional result details
     */
    String getDetails ();



    /**
     * Method returns wheter was compilation successfull
     */
    boolean isSuccessful ();



    /**
     * Method returns wheter was process terminated/interrupted
     */
    boolean isInterrupted ();
}
