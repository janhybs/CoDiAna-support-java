package cz.edu.x3m.processing.execution;

/**
 *
 * @author Jan Hybs
 */
public interface IExecutableLanguage {

    void setExecutionSettings (IExecutionSetting settings);



    void preExecution () throws Exception;



    IExecutionResult execute ();



    void postExecution () throws Exception;
}
