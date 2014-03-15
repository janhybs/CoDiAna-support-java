package cz.edu.x3m.processing;

import java.util.List;

/**
 *
 *  @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public interface IRunSetting {

    public String getInputPath ();



    public String getOutputPath ();



    public String getErrorPath ();



    public int getLimitTime ();



    public int getLimitMemory ();



    public List<String> getCommandBase ();
}
