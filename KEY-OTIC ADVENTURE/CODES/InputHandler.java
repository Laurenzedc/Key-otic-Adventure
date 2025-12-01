import java.util.Locale;
import java.util.Scanner;

public class InputHandler {

    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    /** Read a full line of input (can be empty). */
    public String readString(String prompt) {
        if (prompt != null && !prompt.isBlank()) {
            System.out.println(prompt);
        }
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return "";
    }

    /** Read an integer within [min, max], reprompting until valid. */
    public int readIntRange(String prompt, int min, int max) {
        while (true) {
            System.out.println(prompt);
            String line = readString("");
            try {
                int value = Integer.parseInt(line.trim());
                if (value < min || value > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    /**
     * Read a valid option from the given list.
     * Comparison is case-insensitive; returns the matched option in lower-case.
     */
    public String readValidOption(String prompt, String... validOptions) {
        while (true) {
            System.out.println(prompt);
            String line = readString("").trim().toLowerCase(Locale.ROOT);

            for (String opt : validOptions) {
                if (line.equals(opt.toLowerCase(Locale.ROOT))) {
                    return line;
                }
            }

            System.out.print("Invalid choice. Options: ");
            for (int i = 0; i < validOptions.length; i++) {
                System.out.print(validOptions[i]);
                if (i < validOptions.length - 1) System.out.print(", ");
            }
            System.out.println();
        }
    }

    /** Pause until user presses ENTER. */
    public void waitForEnter() {
        System.out.println();
        System.out.println("(Press ENTER to continue...)");
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }
}
