package cz.edu.x3m;

import cz.edu.x3m.processing.compilation.ICompilableLanguage;
import cz.edu.x3m.processing.compilation.ICompileResult;
import cz.edu.x3m.processing.compilation.ICompileSetting;
import cz.edu.x3m.processing.compilation.impl.CompileResult;
import cz.edu.x3m.processing.execution.IExecutionResult;
import cz.edu.x3m.processing.execution.IExecutionSetting;
import cz.edu.x3m.processing.execution.impl.ExecutionResult;
import cz.edu.x3m.processing.execution.impl.IOUtils;
import cz.edu.x3m.utils.IniParser;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * Class which will start compilation sh file and then exec sh file
 *
 * @author Jan Hybs
 */
public class JavaLanguage implements ICompilableLanguage {

    private static final String COMPILE_PATH = "javac";
    private static final String EXEC_PATH = "java";
    private static final String EXT_JAVA = ".java";
    private static final String EXT_CLASS = ".class";
    private static final String EXT_INI = ".ini";
    //
    private ICompileSetting compileSettings;
    private String compileErrorPath;
    private String compileMainFileName;
    private String compileSourceDirectory;
    private String compileMainFilePath;
    //
    private IExecutionSetting executionSettings;
    private String executionErrorPath;
    private String executionMainFileName;
    private String executionSourceDirectory;
    private String executionMainFileClass;
    private File executionInfoFile;
    private File compileInfoFile;



    @Override
    public void setCompileSettings (ICompileSetting settings) {
        if (settings == null)
            throw new RuntimeException ("Invalid compile setting");
        this.compileSettings = settings;
    }



    @Override
    public void preCompilation () throws Exception {
        this.compileErrorPath = compileSettings.getErrorPath ();
        this.compileSourceDirectory = compileSettings.getSourceDirectoryPath ();

        // try to find main file
        this.compileMainFileName = compileSettings.getMainFileName ();
        File mainFilePath = findFile (compileMainFileName.concat (EXT_JAVA), new File (compileSourceDirectory));

        //otherwise invalidate pre compilation
        if (mainFilePath == null)
            throw new Exception ("Cannot locate main java file");
        this.compileMainFilePath = mainFilePath.getAbsolutePath ();

        // info file
        this.compileInfoFile = new File (
                compileSourceDirectory
                .concat (File.separator)
                .concat (compileMainFileName)
                .concat (".compile")
                .concat (EXT_INI));
    }



    @Override
    public ICompileResult compile () {
        List<String> command = compileSettings.getCommandBase ();
        // javac -sourcepath SOURCEPATH -d DESTINATION SOURCE
        command.add (COMPILE_PATH);
        command.add ("-sourcepath");
        command.add (compileSourceDirectory);
        command.add ("-d");
        command.add (compileSourceDirectory);
        command.add (compileMainFilePath);



        try {
            System.out.println (command);
            ProcessBuilder processBuilder = new ProcessBuilder (command);
            processBuilder.redirectOutput (compileInfoFile);
            Process executionProcess = processBuilder.start ();
            executionProcess.waitFor ();

            // loads details
            IniParser parser = new IniParser (compileInfoFile);
            
            // parsing
            int runTime = Integer.parseInt (parser.get ("run-time"));
            int memoryAvg = Integer.parseInt (parser.get ("memory-avg"));
            int lineCount = Integer.parseInt (parser.get ("line-count"));
            int exitValue = Integer.parseInt (parser.get ("exit-value"));
            final String error = IOUtils.readAll (compileErrorPath);

            return new CompileResult (exitValue, runTime, memoryAvg, lineCount, error);
        } catch (IOException | InterruptedException e) {
            return new CompileResult (e);
        }
    }



    @Override
    public void postCompilation () throws Exception {
    }



    @Override
    public void setExecutionSettings (IExecutionSetting settings) {
        if (settings == null)
            throw new RuntimeException ("Invalid execution settings");
        this.executionSettings = settings;
    }



    @Override
    public void preExecution () throws Exception {
        this.executionErrorPath = executionSettings.getErrorPath ();
        this.executionSourceDirectory = executionSettings.getSourceDirectoryPath ();


        // try to find main class file
        executionMainFileName = executionSettings.getMainFileName ();
        File mainFilePath = findFile (executionMainFileName.concat (EXT_CLASS), new File (executionSourceDirectory));
        if (mainFilePath == null)
            throw new Exception ("Cannot locate main class file");

        // generate class string
        executionMainFileClass = getClassPath (mainFilePath, new File (executionSourceDirectory));
        if (executionMainFileClass == null)
            throw new Exception ("Cannot locate main class file");

        // info file
        this.executionInfoFile = new File (
                executionSourceDirectory.concat (File.separator).concat (executionMainFileName).concat (".exec").concat (EXT_INI));
    }



    @Override
    public IExecutionResult execute () {
        List<String> command = executionSettings.getCommandBase ();
        // java -classpath CLASSPATH CLASS
        command.add (EXEC_PATH);
        command.add ("-classpath");
        command.add (executionSourceDirectory);
        command.add (executionMainFileClass);

        try {
            System.out.println (command);
            ProcessBuilder processBuilder = new ProcessBuilder (command);
            processBuilder.redirectOutput (executionInfoFile);
            Process executionProcess = processBuilder.start ();
            executionProcess.waitFor ();

            // loads details
            IniParser parser = new IniParser (executionInfoFile);
            
            // parsing
            int runTime = Integer.parseInt (parser.get ("run-time"));
            int memoryAvg = Integer.parseInt (parser.get ("memory-avg"));
            int lineCount = Integer.parseInt (parser.get ("line-count"));
            int exitValue = Integer.parseInt (parser.get ("exit-value"));
            final String error = IOUtils.readAll (executionErrorPath);

            return new ExecutionResult (exitValue, runTime, memoryAvg, lineCount, error);
        } catch (IOException | InterruptedException e) {
            return new ExecutionResult (e);
        }
    }



    @Override
    public void postExecution () throws Exception {
//        if (executionInfoFile != null && executionInfoFile.exists ())
//            executionInfoFile.delete ();
    }



    /**
     * Method finds name with file in given directory and subdirectories
     *
     * @param name filename
     * @param direcrory root directory
     * @return file or null
     */
    protected File findFile (String name, File direcrory) {
        File[] files = direcrory.listFiles ();

        // files first
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory ())
                continue;
            if (file.getName ().equals (name))
                return file;
        }

        // folders
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (!file.isDirectory ())
                continue;

            File result = findFile (name, file);
            if (result != null)
                return result;
        }

        return null;
    }



    /**
     * Method creates dot separated path of given file relative to parent file e.g.
     * <code>cz.edu.x3m.testpackage.ClassName</code>
     *
     * @param childFile
     * @param parentFile
     * @return dot sepatated class path
     */
    protected String getClassPath (File childFile, File parentFile) {
        String relativePath = parentFile.toURI ().relativize (childFile.toURI ()).getPath ();
        relativePath = relativePath.replaceAll ("\\\\|/", ".");
        relativePath = relativePath.replaceAll ("^\\.*", "");
        relativePath = relativePath.replaceAll ("\\.[^\\.]+$", "");
        return relativePath;
    }
}
