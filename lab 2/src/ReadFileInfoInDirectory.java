import java.io.IOException;
import java.nio.file.*;
public class ReadFileInfoInDirectory {

    public static void printFiles(String directoryPath){
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directoryPath))) {
            System.out.println("Working directory " + "\"" + directoryPath + "\"" + " contains:");
            for (Path path : directoryStream) {
                System.out.println(path.getFileName());
            }
        } catch (IOException | DirectoryIteratorException e) {
            e.printStackTrace();
        }
    }
}
