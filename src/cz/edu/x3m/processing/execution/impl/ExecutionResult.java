package cz.edu.x3m.processing.execution.impl;

import cz.edu.x3m.processing.execution.IExecutionResult;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class ExecutionResult implements IExecutionResult {

    private Throwable throwable = null;
    private String details = null;
    private int exitValue = -1;
    private int runTime = -1;
    private int memoryPeak = -1;
    private int lineCount;



    public ExecutionResult (int exitValue, int runTime, int memoryPeak, int lineCount) {
        this.exitValue = exitValue;
        this.runTime = runTime;
        this.memoryPeak = memoryPeak;
        this.lineCount = lineCount;
    }



    public ExecutionResult (Throwable throwable) {
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



    @Override
    public int getRunTime () {
        return runTime;
    }



    @Override
    public int getMemoryPeak () {
        return memoryPeak;
    }



    @Override
    public int getLineCount () {
        return lineCount;
    }
}
