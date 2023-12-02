import java.util.Objects;
import java.util.Scanner;

public class Options {

    public static void directoryOption(String workingDirectory) {
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                Options:
                1. Commit (commit)
                2. Info (info | info <file name>)
                3. Status (status)
                4. Quit (quit)
                """);
        String action = sc.nextLine();
        String[] actionMass = action.split("\\s+");
        switch (actionMass.length) {
            case 1:
                switch (actionMass[0]){
                    case "commit":
                        Snapshot.createSnapshot(workingDirectory);
                        directoryOption(workingDirectory);
                        break;
                    case "info":
                        ReadFileInfoInDirectory.printFiles(workingDirectory);
                        directoryOption(workingDirectory);
                        break;
                    case "status":
                        if (Snapshot.checkCommit(workingDirectory)){
                            Snapshot.status(workingDirectory);
                            directoryOption(workingDirectory);
                            break;
                        } else {
                            System.out.println("Not commit yet in this directory");
                            directoryOption(workingDirectory);
                            break;
                        }
                    case "quit":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Wrong operation.");
                        directoryOption(workingDirectory);
                        break;
                }
                break;
            case 2:
                if (Objects.equals(actionMass[0], "info")){
                    FileProcessor.processFileInfo(workingDirectory, actionMass[1]);
                    directoryOption(workingDirectory);
                    break;
                } else {
                    System.out.println("Wrong operation.");
                    directoryOption(workingDirectory);
                    break;
                }
        }


    }
}
