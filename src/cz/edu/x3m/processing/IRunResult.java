package cz.edu.x3m.processing;

/**
 *
 *  @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public interface IRunResult {

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
    String getError ();



    /**
     * Method return execution run time in ms or -1 on error
     */
    int getRunTime ();



    /**
     * Method return execution memory peak in MB or -1 on error
     */
    int getMemoryAvg ();



    /**
     * Method returns output line count or -1 on error
     */
    int getLineCount ();
}
