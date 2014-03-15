package cz.edu.x3m.processing.execution.impl;

import cz.edu.x3m.processing.execution.IExecutionResult;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public final class ExecutionResult implements IExecutionResult {

    private Throwable throwable = null;
    private String error = null;
    private int exitValue = -1;
    private int runTime = -1;
    private int memoryAvg = -1;
    private int lineCount = -1;



    public ExecutionResult (int exitValue, int runTime, int memoryPeak, int lineCount, String error) {
        this.exitValue = exitValue;
        this.runTime = runTime;
        this.memoryAvg = memoryPeak;
        this.lineCount = lineCount;
        this.error = error == null ? "" : error;
    }



    public ExecutionResult (Throwable throwable) {
        this.throwable = throwable;
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
    public String getError () {
        return error;
    }



    @Override
    public int getRunTime () {
        return runTime;
    }



    @Override
    public int getMemoryAvg () {
        return memoryAvg;
    }



    @Override
    public int getLineCount () {
        return lineCount;
    }
}
