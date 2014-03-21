package cz.edu.x3m.processing.execution;

import cz.edu.x3m.processing.exception.RunException;

/**
 *
 * @author Jan Hybs
 */
public interface IExecutableLanguage {

    void setExecutionSettings (IExecutionSetting settings);



    void preExecution () throws RunException;



    IExecutionResult execute ();



    void postExecution () throws RunException;
}
