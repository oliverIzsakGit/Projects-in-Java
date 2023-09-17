import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class RestartProgram {
    public static void main(String[] args) throws IOException, URISyntaxException {
        // code here
        restart();
    }

    public static void restart() throws IOException, URISyntaxException {
        String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        File currentJar = new File(RestartProgram.class.getProtectionDomain().getCodeSource().getLocation().toURI());

        /* is it a jar file? */
        if(!currentJar.getName().endsWith(".jar"))
            return;

        /* Build command: java -jar application.jar */
        List<String> command = new ArrayList<String>();
        command.add(javaBin);
        command.add("-jar");
        command.add(currentJar.getPath());

        /* Start new process */
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.start();

        /* Exit current process */
        System.exit(0);
    }
}