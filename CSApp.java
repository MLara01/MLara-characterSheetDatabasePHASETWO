/*
Name: Morgan Lara
Date Due: 10/19/2026
This project is a tabletop RPG character database where users can create a character, remove a character
from the database, update a character's information, display all saved characters, and return the average level of all
stored characters. The program will also be able to read and store data from an external text document.
*/

public class CSApp
{
    public static void main(String[] args)
    {
        characterManip manager = new characterManip();
        manager.runApp();
    }
}
