import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Processing {

    public static void processFileInfo(String folderPath, String fileName) {
        Path directoryPath = Paths.get(folderPath);
        Path filePath = directoryPath.resolve(fileName);

        try {
            String fileExtension = getFileExtension(fileName);

            if (Files.exists(filePath)) {
                System.out.println("Information about file: " + fileName);

                System.out.println("File Name: " + fileName);
                System.out.println("File Extension: " + fileExtension);
                System.out.println("Created Date: " + Files.getAttribute(filePath, "creationTime"));
                System.out.println("Last Modified Time: " + Files.getLastModifiedTime(filePath));

                if (fileExtension != null) {
                    switch (fileExtension.toLowerCase()) {
                        case "txt":
                            processTextFile(filePath);
                            break;
                        case "png":
                        case "jpg":
                        case "jpeg":
                            processImageFile(filePath);
                            break;
                        case "py":
                        case "java":
                        case "cpp":
                            processProgramFile(filePath);
                            break;
                        default:
                            System.out.println("File type not processed for additional details.");
                            break;
                    }
                }
            } else {
                System.out.println("File doesn't exist!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return null;
    }

    private static void processTextFile(Path filePath) throws IOException {
        long lineCount = Files.lines(filePath).count();
        long wordCount = Files.lines(filePath)
                .flatMap(line -> Stream.of(line.split("\\s+")))
                .count();
        long charCount = Files.size(filePath);
        System.out.println("Text File Details:");
        System.out.println("Number of Lines: " + lineCount);
        System.out.println("Number of Words: " + wordCount);
        System.out.println("Number of Characters: " + charCount);
    }

    private static void processImageFile(Path filePath) throws IOException {
        BufferedImage img = ImageIO.read(filePath.toFile());

        if (img != null) {
            int width = img.getWidth();
            int height = img.getHeight();
            System.out.println("Image File Details:");
            System.out.println("Image Size: " + width + "x" + height);
        } else {
            System.out.println("Cannot read the image file.");
        }
    }

    private static void processProgramFile(Path filePath) throws IOException {
        int lineCount = 0;
        int classCount = 0;
        int methodCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount += 1;

                if (line.contains("class ")) {
                    classCount = classCount + 1;
                } else if (line.contains("void ") || line.contains("int ") ||  line.contains("std::string ") || line.contains("char ") || line.contains("String ")) {
                    methodCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Program File Details:");
        System.out.println("Line Count: " + lineCount);
        System.out.println("Class Count: " + classCount);
        System.out.println("Method Count: " + methodCount);
    }
}
