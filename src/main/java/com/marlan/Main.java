package com.marlan;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException {
        String dir = "C:\\Photos\\"; // TODO: Refactor to args

        deleteDuplicates(dir); // Deletes files with the same file byte size.
        randomlyRenameFiles(dir); // Uses UUID, there is no handling for duplicate UUID.

        System.out.println("Done");
    }

    private static void deleteDuplicates(String dir) throws IOException {
        File folder = new File(dir);
        HashSet<File> files;

        try {
            files = new HashSet<>(Arrays.asList(Objects.requireNonNull(folder.listFiles())));
        } catch (NullPointerException npe) {
            System.out.println("ERROR: " + npe.getMessage());
            return;
        }

        HashSet<Long> duplicates = new HashSet<>();
        for (File file : files) {
            if (duplicates.contains(Files.size(file.toPath()))) {
                System.out.println("Duplicate deleted: " + file.getName());
                Files.delete(file.toPath());
            } else {
                duplicates.add(Files.size(file.toPath()));
            }
        }
    }

    private static void randomlyRenameFiles(String dir) {
        File folder = new File(dir);
        File[] files = folder.listFiles();

        if (files == null || files.length == 0){
            System.out.println("Folder " + dir + " is empty");
            return;
        }

        HashSet<File> filesSet = new HashSet<>(Arrays.asList(files));
        for (File file : filesSet) {
            String fileExtension = "." + file.getName().substring(file.getName().lastIndexOf(".") + 1);
            boolean successfullyRenamed = file.renameTo(new File(dir + UUID.randomUUID() + fileExtension));
            if (!successfullyRenamed) {
                System.out.println("File " + file.getName() + " was not renamed");
            }
        }
    }
}