package com.hashcode;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WriteOutputData {
    String filePath;

    public WriteOutputData(String filePath) {
        this.filePath = filePath;
    }

    public void write(List<LibrarySubmission> librarySubmissions) throws IOException {
        String firstLine = String.valueOf(librarySubmissions.size());
        String restOfLines = librarySubmissions.stream()
                .map(l -> l.getLibraryIndex() + " " + l.getBooksToSend().size() + "\n" + l.getBooksToSend().stream().map(String::valueOf).collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));
        Files.write(Paths.get(filePath), Arrays.stream((firstLine + "\n" + restOfLines).split("\n")).collect(Collectors.toList()));
    }
}

class LibrarySubmission {
    int libraryIndex;
    List<Integer> booksToSend;

    public LibrarySubmission(int libraryIndex, List<Integer> booksToSend) {
        this.libraryIndex = libraryIndex;
        this.booksToSend = booksToSend;
    }

    public int getLibraryIndex() {
        return libraryIndex;
    }

    public List<Integer> getBooksToSend() {
        return booksToSend;
    }
}
