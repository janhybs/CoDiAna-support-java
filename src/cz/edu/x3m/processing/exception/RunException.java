package cz.edu.x3m.processing.exception;

/**
 *
 *  @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class RunException extends Exception {

    public RunException (Throwable cause) {
        super (cause);
    }



    public RunException (String message) {
        super (message);
    }
}
