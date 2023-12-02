import java.util.Objects;
import java.util.Scanner;

public class Options {

    public static void directoryOption(String workingDirectory) {
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                Options:
                1. commit
                2. info <filename>
                3. status
                4. end
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
                        } else {
                            System.out.println("Not commit yet in this directory");
                        }
                        directoryOption(workingDirectory);
                        break;
                    case "end":
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
                } else {
                    System.out.println("Wrong operation.");
                }
                directoryOption(workingDirectory);
                break;
        }


    }
}
