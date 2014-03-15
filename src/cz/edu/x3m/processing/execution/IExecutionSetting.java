package cz.edu.x3m.processing.execution;

import cz.edu.x3m.processing.IRunSetting;

/**
 *
 * @author Jan Hybs
 */
public interface IExecutionSetting extends IRunSetting {

    public String getMainFileName ();



    public String getSourceDirectoryPath ();
}
