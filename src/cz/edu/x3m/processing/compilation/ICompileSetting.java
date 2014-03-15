package cz.edu.x3m.processing.compilation;

import cz.edu.x3m.processing.IRunSetting;

/**
 *
 * @author Jan Hybs
 */
public interface ICompileSetting extends IRunSetting {

    public String getMainFileName ();



    public String getSourceDirectoryPath ();
}
