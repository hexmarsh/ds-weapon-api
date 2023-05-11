package dev.hexmarsh.webcrawl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class WeaponDataBackup {
    public static void main(String[] args) throws FileNotFoundException {
        backupFile("weapons.json", "./src/main/resources/weapons_backup.json");
    }
    public static void backupFile(String location, String destination) throws FileNotFoundException
    {
        Scanner scanner = new Scanner(new FileInputStream(location));
        PrintWriter writer = new PrintWriter(new FileOutputStream(destination));
        long startTime;
        long endTime;
        long elapsedTime;

        startTime = System.currentTimeMillis();
        System.out.println("Starting back up...");

        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            writer.println(line);
            endTime = System.currentTimeMillis();
            elapsedTime = (endTime - startTime) / 1000;
            System.out.print("Backing up..." + elapsedTime + "s" + "\r");
        }
        System.out.println("Successfully backed up " + location);

        writer.close();
        scanner.close();
    }
}

