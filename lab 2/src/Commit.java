import java.nio.file.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Stream;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


public class Commit {

    public static void status(String workingDirectory){
        LocalDateTime time = lastCommitTime(workingDirectory);

        try {
            Path directory = Paths.get(workingDirectory);
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory);
            for (Path filePath : directoryStream){
                LocalDateTime fileLastMod = LocalDateTime.ofInstant(Files.getLastModifiedTime(filePath).toInstant(), ZoneId.systemDefault());
                if (time.isBefore(fileLastMod)) {
                    System.out.println(filePath.getFileName() + " Changed");
                } else {
                    System.out.println(filePath.getFileName() + " Not Changed");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static LocalDateTime lastCommitTime(String workingDirectory){
        List<LocalDateTime> time = new ArrayList<>();
        try {
            Files.walk(Paths.get("C:\\Users\\Tatiana\\Desktop\\Lab 2 OOP\\Lab-2-OOP\\lab 2\\Commits")) // path
                    .filter(Files::isRegularFile)
                    .forEach(filePath -> {
                        try {
                            BufferedReader reader = Files.newBufferedReader(filePath);
                            String line1 = reader.readLine();
                            if(line1.equals(workingDirectory)){
                                LocalDateTime commitTime = LocalDateTime.ofInstant(Files.getLastModifiedTime(filePath).toInstant(), ZoneId.systemDefault());
                                time.add(commitTime);
                            }
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return time.get(time.size() - 1);
    }


    public static boolean checkCommit(String workingDirectory) {
        Path directoryPath = Paths.get("C:\\Users\\Tatiana\\Desktop\\Lab 2 OOP\\Lab-2-OOP\\lab 2\\Commits"); //path
        try (Stream<Path> paths = Files.walk(directoryPath)) {
            return paths.filter(Files::isRegularFile)
                    .anyMatch(path -> {
                        try (BufferedReader reader = Files.newBufferedReader(path)) {
                            String firstLine = reader.readLine();
                            return firstLine != null && firstLine.equals(workingDirectory);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return false;
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void createSnapshot(String workingDirectory) {
        long fileCount = 0;
        Path currentWorkingDirectory = Paths.get(System.getProperty("user.dir"));
        Path folderPath = currentWorkingDirectory.resolve("Commits");
        try {
            fileCount = Files.walk(folderPath).filter(Files::isRegularFile).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path filePath = folderPath.resolve(fileCount+".txt");
        try {
            BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            LocalDateTime currentTime = LocalDateTime.now(ZoneId.systemDefault());
            writer.write(workingDirectory);
            writer.newLine();
            writer.write("" + currentTime);
            writer.close();
            System.out.println("Created at: " + currentTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

