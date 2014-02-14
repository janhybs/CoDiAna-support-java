package cz.edu.x3m.processing.compilation.impl;

import cz.edu.x3m.processing.compilation.ICompileResult;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class CompileResult implements ICompileResult {
    
    private Throwable throwable = null;
    private String details = null;
    private int exitValue = -1;



    public CompileResult (int exitValue) {
        this.exitValue = exitValue;
    }



    public CompileResult (Throwable throwable) {
        this.throwable = throwable;
        this.details = throwable.getMessage ();
    }



    @Override
    public int getExitValue () {
        return exitValue;
    }



    @Override
    public Throwable getThrowable () {
        return throwable;
    }



    @Override
    public String getDetails () {
        return details;
    }



    @Override
    public boolean isSuccessful () {
        return exitValue == 0;
    }



    @Override
    public boolean isInterrupted () {
        return exitValue == 1;
    }

    
}
