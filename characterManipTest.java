import org.junit.jupiter.api.Test;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class characterManipTest
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        characterManip manager = new characterManip();

        int choice;

        do
        {
            System.out.println("\n**** CHARACTER DATABASE TESTING ****");
            System.out.println("1. Add Character");
            System.out.println("2. Display All Characters");
            System.out.println("3. Search Character by ID");
            System.out.println("4. Update Character");
            System.out.println("5. Delete Character by ID");
            System.out.println("6. Load Characters from File");
            System.out.println("7. Calculate Average Level");
            System.out.println("8. Exit");
            System.out.print("Choose a test: ");

            choice = readInt(input);

            switch (choice) {

                case 1:
                    // Add character test
                    System.out.println("\n--- ADD CHARACTER TEST ---");

                    System.out.print("Enter character ID: ");
                    int id = readInt(input);

                    System.out.print("Enter character name: ");
                    String name = input.nextLine();

                    System.out.print("Enter health: ");
                    double health = readDouble(input);

                    System.out.print("Enter level: ");
                    int level = readInt(input);

                    System.out.print("Enter experience: ");
                    double exp = readDouble(input);

                    System.out.print("Has inspiration? (true/false OR t/f): ");
                    boolean inspo = readBoolean(input);

                    characterInfo newCharacter = new characterInfo(id, name, health, level, exp, inspo);

                    if (manager.addCharacter(newCharacter))
                    {
                        System.out.println("TEST PASSED: Character added.");
                    }
                    else
                    {
                        System.out.println("TEST FAILED: Character was not added.");
                    }
                    break;

                case 2:
                    // Display all test
                    System.out.println("\n--- DISPLAY ALL TEST ---");
                    manager.displayAllCharacters();
                    break;

                case 3:
                    // Search via Character ID test
                    System.out.println("\n--- SEARCH TEST ---");

                    System.out.print("Enter character ID to search: ");
                    int searchID = readInt(input);

                    characterInfo found =
                            manager.findCharacter(searchID);

                    if (found != null)
                    {
                        System.out.println("TEST PASSED: Character found.");
                        printCharacter(found);
                    }
                    else
                    {
                        System.out.println("TEST RESULT: Character not found.");
                    }
                    break;

                case 4:
                    // Update character info test
                    System.out.println("\n--- UPDATE CHARACTER TEST ---");

                    System.out.print("Enter the CURRENT character ID: ");
                    int oldID = readInt(input);

                    characterInfo character =
                            manager.findCharacter(oldID);

                    if (character == null)
                    {
                        System.out.println("TEST FAILED: Character not found.");
                        break;
                    }

                    System.out.println("\nCurrent character details:");
                    printCharacter(character);

                    System.out.print("\nEnter the NEW character ID: ");
                    int newID = readInt(input);

                    System.out.print("Enter new name: ");
                    String newName = input.nextLine();

                    System.out.print("Enter new health: ");
                    double newHealth = readDouble(input);

                    System.out.print("Enter new level: ");
                    int newLevel = readInt(input);

                    System.out.print("Enter new experience: ");
                    double newExp = readDouble(input);

                    System.out.print("DOes the character have inspiration? (t/f or true/false): ");
                    boolean newInspo = readBoolean(input);

                    boolean updated = manager.updateCharacter(oldID, newID, newName, newHealth, newLevel, newExp, newInspo);

                    if (updated)
                    {
                        System.out.println("TEST PASSED: Character updated.");

                        characterInfo updatedCharacter = manager.findCharacter(newID);

                        if (updatedCharacter != null)
                        {
                            System.out.println("\nUpdated character details:");
                            printCharacter(updatedCharacter);
                        }
                    }
                    else
                    {
                        System.out.println("TEST FAILED: Character was not updated.");
                    }
                    break;

                case 5:
                    // Delete character test
                    System.out.println("\n--- DELETE TEST ---");

                    System.out.print("Enter character ID to delete: ");
                    int deleteID = readInt(input);

                    if (manager.deleteCharacter(deleteID))
                    {
                        System.out.println("TEST PASSED: Character deleted.");
                    }
                    else
                    {
                        System.out.println("TEST FAILED: Character was not found or deleted.");
                    }
                    break;

                case 6:
                    // Load file test
                    System.out.println("\n--- FILE LOADING TEST ---");

                    System.out.print("Enter text file path: ");
                    String filePath = input.nextLine();

                    manager.loadFromFile(filePath);

                    System.out.println("Characters currently stored after file loading:");
                    manager.displayAllCharacters();
                    break;

                case 7:
                    // Average Level test
                    System.out.println("\n--- AVERAGE LEVEL TEST ---");

                    double average = manager.averageLevel();

                    System.out.println("Average character level: " + average);
                    break;

                case 8:
                    System.out.println("Testing finished.");
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1 through 8.");
            }

        }
        while (choice != 8);
        input.close();
    }

    // Prints character information for the tests.
    private static void printCharacter(characterInfo character)
    {
        System.out.println("ID: " + character.getCharID());
        System.out.println("Name: " + character.getCharName());
        System.out.println("Health: " + character.getCharHealth());
        System.out.println("Level: " + character.getCharLevel());
        System.out.println("Experience: " + character.getCharExp());
        System.out.println("Inspiration: " + character.isCharInspo());
    }

    // Input help
    private static int readInt(Scanner input)
    {
        while (true)
        {
            try
            {
                int value = Integer.parseInt(input.nextLine().trim());
                return value;
            }
            catch (NumberFormatException e)
            {
                System.out.print("Enter a valid whole number: ");
            }
        }
    }

    private static double readDouble(Scanner input)
    {
        while (true)
        {
            try
            {
                double value =
                        Double.parseDouble(input.nextLine().trim());

                if (!Double.isFinite(value))
                {
                    System.out.print("Enter a finite number: ");
                    continue;
                }

                return value;
            }
            catch (NumberFormatException e)
            {
                System.out.print("Enter a valid decimal number: ");
            }
        }
    }

    private static boolean readBoolean(Scanner input)
    {
        while (true)
        {
            String value = input.nextLine().trim();

            if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("t"))
            {
                return true;
            }

            if (value.equalsIgnoreCase("false") || value.equalsIgnoreCase("f"))
            {
                return false;
            }

            System.out.print("Enter true or false: ");
        }
    }
}