package cz.edu.x3m.processing.compilation;

import cz.edu.x3m.processing.exception.RunException;
import cz.edu.x3m.processing.execution.IExecutableLanguage;

/**
 *
 * @author Jan Hybs
 */
public interface ICompilableLanguage extends IExecutableLanguage {

    void setCompileSettings (ICompileSetting settings);



    void preCompilation () throws RunException;



    ICompileResult compile ();



    void postCompilation () throws RunException;
}
