import java.util.Objects;
import java.util.Scanner;

public class Choices {

    public static void directoryOption(String workingDirectory) {
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                
                Actions:
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
                        Commit.createSnapshot(workingDirectory);
                        directoryOption(workingDirectory);
                        break;
                    case "info":
                        FileInfoRead.printFiles(workingDirectory);
                        directoryOption(workingDirectory);
                        break;
                    case "status":
                        if (Commit.checkCommit(workingDirectory)){
                            Commit.status(workingDirectory);
                        } else {
                            System.out.println("No commit in this directory");
                        }
                        directoryOption(workingDirectory);
                        break;
                    case "end":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Error");
                        directoryOption(workingDirectory);
                        break;
                }
                break;
            case 2:
                if (Objects.equals(actionMass[0], "info")){
                    Processing.processFileInfo(workingDirectory, actionMass[1]);
                } else {
                    System.out.println("Error");
                }
                directoryOption(workingDirectory);
                break;
        }


    }
}
