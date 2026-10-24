// This class holds the functions for manipulating character data via CRUD actions, Average Level,
// and reading external text documents to store characters. This class also holds the menu for the application.

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class characterManip
{

    private ArrayList<characterInfo> characters;
    private Scanner input;

    public characterManip()
    {
        characters = new ArrayList<>();
        input = new Scanner(System.in);
    }

    // Application Menu

    public void runApp()
    {
        boolean running = true;

        while (running)
        {
            System.out.println("\n=== CHARACTER SHEET MANAGEMENT ===");
            System.out.println("1. Load Characters from File");
            System.out.println("2. Display All Characters");
            System.out.println("3. Add Character");
            System.out.println("4. Delete Character");
            System.out.println("5. Update Character");
            System.out.println("6. Average Character Level");
            System.out.println("7. Search for a Character by ID");
            System.out.println("0. Exit\n");

            int choice = readInt("Choose an option: ");

            switch (choice)
            {
                case 1:
                    System.out.print("Enter file path: ");
                    loadFromFile(input.nextLine().trim());
                    break;

                case 2:
                    displayAllCharacters();
                    break;

                case 3:
                    addCharacterFromInput();
                    break;

                case 4:
                    deleteCharacterFromInput();
                    break;

                case 5:
                    updateCharacterFromInput();
                    break;

                case 6:
                    System.out.printf("Average level: %.2f%n", averageLevel());
                    break;

                case 7:
                    searchCharacterFromInput();
                    break;

                case 0:
                    running = false;
                    System.out.println("Thank you for using CSApp!");
                    break;

                default:
                    System.out.println("Invalid menu option. Please use the correlated number.");
            }
            System.out.print("\n");
        }
    }

    // Create a Character

    private void addCharacterFromInput()
    {
        int id;

        // Keep asking until the ID is unique
        while (true)
        {
            id = readInt("Character ID: ");

            if (findCharacter(id) == null)
            {
                break;
            }

            System.out.println(
                    "That Character ID already exists. Please try another."
            );
        }

        System.out.print("Character name: ");
        String name = input.nextLine().trim();

        double health = readDouble("Health: ");
        int level = readInt("Level: ");
        double exp = readDouble("Experience: ");
        boolean inspo = readBoolean("Inspired (true/false): ");

        // Validate the character details
        if (name.isEmpty() || health < 0 || level < 1 || exp < 0)
        {
            System.out.println("Invalid character details. Please try again.");
            return;
        }

        characterInfo character = new characterInfo(id, name, health, level, exp, inspo);

        // Add the character to the Array
        if (addCharacter(character))
        {
            System.out.println("Character added successfully!");
        }
    }

    public boolean addCharacter(characterInfo character)
    {
        if (findCharacter(character.getCharID()) != null)
        {
            System.out.println("Character ID already exists.");
            return false;
        }

        characters.add(character);
        return true;
    }

    // Find character from Character ID

    public characterInfo findCharacter(int id)
    {
        for (characterInfo character : characters)
        {
            if (character.getCharID() == id)
            {
                return character;
            }
        }

        return null;
    }

    private void searchCharacterFromInput()
    {
        int id = readInt("Enter Character ID: ");
        characterInfo character = findCharacter(id);

        if (character == null)
        {
            System.out.println("Character not found.");
        }
        else
        {
            displayCharacterDetails(character);
        }
    }

    public void displayAllCharacters()
    {
        if (characters.isEmpty())
        {
            System.out.println("No characters stored.");
            return;
        }

        for (characterInfo character : characters) {
            displayCharacterDetails(character);
        }
    }

    private void displayCharacterDetails(characterInfo character)
    {
        System.out.print("\n");
        System.out.println("ID: " + character.getCharID());
        System.out.println("Name: " + character.getCharName());
        System.out.println("Health: " + character.getCharHealth());
        System.out.println("Level: " + character.getCharLevel());
        System.out.println("Experience: " + character.getCharExp());
        System.out.println("Inspired: " + character.isCharInspo());
        System.out.println("------------------------");
    }

    // Update character information

    private void updateCharacterFromInput()
    {
        // Uses current character ID to select which character to edit
        int id = readInt("Enter the current Character ID you wish edit: ");

        characterInfo character = findCharacter(id);

        if (character == null)
        {
            System.out.println("The character is not found.");
            return;
        }

        // Ensures that the loop only closes when the user selects to return to main menu
        boolean editing = true;

        while (editing)
        {
            System.out.println("\n=== EDIT CHARACTER ===");
            System.out.println("Current ID: " + character.getCharID());
            System.out.println("Character: " + character.getCharName());
            System.out.println("1. Edit Character ID");
            System.out.println("2. Edit Character Name");
            System.out.println("3. Edit Character Health");
            System.out.println("4. Edit Character Level");
            System.out.println("5. Edit Character Experience");
            System.out.println("6. Edit Character Inspiration");
            System.out.println("7. Return to Main Menu");

            int choice = readInt("Choose what detail to edit: ");

            switch (choice)
            {
                case 1:
                    int newID = readInt("Enter new Character ID: ");

                    // This is a check to see whether another character already uses this ID
                    characterInfo existing = findCharacter(newID);

                    if (newID <= 0)
                    {
                        System.out.println("Character ID must be positive.");
                    }
                    else if (existing != null && existing != character)
                    {
                        System.out.println("That ID is already assigned to another character.");
                    }
                    else
                    {
                        character.setCharID(newID);
                        System.out.println("Character ID updated successfully!");
                    }
                    break;

                case 2:
                    System.out.print("Enter new character name: ");
                    String name = input.nextLine().trim();

                    if (!name.isEmpty())
                    {
                        character.setCharName(name);
                        System.out.println("Name updated successfully!");
                    }
                    else
                    {
                        System.out.println("Name cannot be empty.");
                    }
                    break;

                case 3:
                    double health = readDouble("Enter new health: ");

                    if (health >= 0)
                    {
                        character.setCharHealth(health);
                        System.out.println("Health updated successfully!");
                    }
                    else
                    {
                        System.out.println("Health cannot be negative.");
                    }
                    break;

                case 4:
                    int level = readInt("Enter new character level: ");

                    if (level >= 1)
                    {
                        character.setCharLevel(level);
                        System.out.println("Level updated successfully!");
                    }
                    else
                    {
                        System.out.println("Level must be at least 1.");
                    }
                    break;

                case 5:
                    double exp = readDouble("Enter new experience: ");

                    if (exp >= 0)
                    {
                        character.setCharExp(exp);
                        System.out.println("Experience updated successfully!");
                    }
                    else
                    {
                        System.out.println("Experience cannot be negative.");
                    }
                    break;

                case 6:
                    boolean inspo = readBoolean("Inspired? (t/f): ");
                    character.setCharInspo(inspo);
                    System.out.println("Inspiration status updated successfully!");
                    break;

                case 7:
                    editing = false;
                    System.out.println("Returning to the main menu.");
                    break;

                default:
                    System.out.println("Invalid option. Please a valid menu option.");
            }
        }
    }

    public boolean updateCharacter(int oldID, int newID, String name, double health, int level, double exp, boolean inspo)
    {
        characterInfo character = findCharacter(oldID);

        if (character == null)
        {
            System.out.println("Character not found.");
            return false;
        }

        // Validates the new ID
        if (newID <= 0)
        {
            System.out.println("ID must be greater than zero.");
            return false;
        }

        // Prevents duplicate IDs from existing in the same file
        characterInfo existing = findCharacter(newID);

        if (existing != null && existing != character)
        {
            System.out.println("That ID is already in use.");
            return false;
        }

        // Update the character's information
        character.setCharID(newID);
        character.setCharName(name);
        character.setCharHealth(health);
        character.setCharLevel(level);
        character.setCharExp(exp);
        character.setCharInspo(inspo);

        System.out.println("Character updated successfully.");
        return true;
    }

    // Delete a character

    private void deleteCharacterFromInput()
    {
        int id = readInt("Enter ID to delete: ");
        deleteCharacter(id);
    }

    public boolean deleteCharacter(int id)
    {
        characterInfo character = findCharacter(id);

        if (character == null)
        {
            System.out.println("Character not found.");
            return false;
        }

        characters.remove(character);
        System.out.println("Character deleted successfully.");
        return true;
    }

    // Average Level

    public double averageLevel()
    {
        if (characters.isEmpty())
        {
            return 0.0;
        }

        int total = 0;

        for (characterInfo character : characters)
        {
            total += character.getCharLevel();
        }

        return (double) total / characters.size();
    }

    // Load from text file

    public void loadFromFile(String fileName)
    {
        // Allows the user to see how many entries were loaded and skipped over
        int loaded = 0;
        int skipped = 0;

        try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName)))
        {
            // Inserts a blank text line for cleaner spacing
            System.out.print("\n");

            String line;

            while ((line = reader.readLine()) != null)
            {
                if (line.trim().isEmpty())
                {
                    continue;
                }

                try {
                    String[] data = line.split("\\|", -1);

                    if (data.length != 6)
                    {
                        throw new IllegalArgumentException();
                    }

                    int id = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    double health =
                            Double.parseDouble(data[2].trim());
                    int level = Integer.parseInt(data[3].trim());
                    double exp =
                            Double.parseDouble(data[4].trim());
                    String inspoText = data[5].trim();

                    if (name.isEmpty()
                            || !Double.isFinite(health)
                            || !Double.isFinite(exp)
                            || health < 0 || level < 1 || exp < 0
                            || !(inspoText.equalsIgnoreCase("true")
                            || inspoText.equalsIgnoreCase("false")))
                    {
                        throw new IllegalArgumentException();
                    }

                    boolean inspo =
                            Boolean.parseBoolean(inspoText);

                    characterInfo character = new characterInfo(id, name, health, level, exp, inspo);

                    if (addCharacter(character))
                    {
                        loaded++;
                    }
                    else
                    {
                        skipped++;
                    }

                }
                catch (IllegalArgumentException e)
                {
                    System.out.println("Invalid record skipped: " + line);
                    skipped++;
                }
            }

            System.out.println("Loaded: " + loaded);
            System.out.println("Skipped: " + skipped);

        }
        catch (IOException | SecurityException e)
        {
            System.out.println("Unable to read file: " + e.getMessage());
        }
        catch (java.nio.file.InvalidPathException e)
        {
            System.out.println("Invalid file path. Please try again.");
        }
    }

    // These methods ensure that the user can only input the proper data types for each input

    private int readInt(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);

            try
            {
                return Integer.parseInt(input.nextLine().trim());
            }
            catch (NumberFormatException e)
            {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private double readDouble(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);

            try {
                double value =
                        Double.parseDouble(input.nextLine().trim());

                if (Double.isFinite(value))
                {
                    return value;
                }
            }
            catch (NumberFormatException e)
            {
                // Error message will ask user input below
            }

            System.out.println("Please enter a valid number (includes decimals).");
        }
    }

    private boolean readBoolean(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);
            String value = input.nextLine().trim();

            if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("t"))
            {
                return true;
            }

            if (value.equalsIgnoreCase("false") || value.equalsIgnoreCase("f"))
            {
                return false;
            }
            System.out.println("Please enter true or false.");
        }
    }
}
