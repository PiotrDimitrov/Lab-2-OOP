import java.io.IOException;
import java.nio.file.*;
public class FileInfoRead {

    public static void printFiles(String directoryPath){
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directoryPath))) {
            System.out.println("Directory " + "\"" + directoryPath + "\"" + " contains files:");
            for (Path path : directoryStream) {
                System.out.println(path.getFileName());
            }
        } catch (IOException | DirectoryIteratorException e) {
            e.printStackTrace();
        }
    }
}
