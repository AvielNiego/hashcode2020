package com.hashcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ReadInputData {
    private String filePath;

    public ReadInputData(String filePath) throws IOException {
        this.filePath = filePath;
    }

    public InputData parse() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        String firstLine = br.readLine();
        String[] firstLineData = firstLine.split(" ");
        int booksExistsCount = Integer.parseInt(firstLineData[0]);
        int libraryCount = Integer.parseInt(firstLineData[1]);
        int daysForScanning = Integer.parseInt(firstLineData[2]);

        String secondLine = br.readLine();
        List<Integer> books = Arrays.stream(secondLine.split(" ")).map(Integer::parseInt).collect(toList());

        List<Library> libraries = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !line.equals("")) {
            String[] libraryFirstLine = line.split(" ");
            int booksCount = Integer.parseInt(libraryFirstLine[0]);
            int signUpTime = Integer.parseInt(libraryFirstLine[1]);
            int shipsPerDay = Integer.parseInt(libraryFirstLine[2]);
            List<Integer> booksInLibrary = Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt).collect(Collectors.toList());
            libraries.add(new Library(booksCount, signUpTime, shipsPerDay, booksInLibrary, libraries.size()));
        }

        return new InputData(booksExistsCount, libraryCount, daysForScanning, books, libraries);
    }
}

class InputData {
    private int booksExistsCount;
    private int libraryCount;
    private int daysForScanning;

    List<Integer> books;
    List<Library> libraries;

    public InputData(int booksExistsCount, int libraryCount, int daysForScanning, List<Integer> books, List<Library> libraries) {
        this.booksExistsCount = booksExistsCount;
        this.libraryCount = libraryCount;
        this.daysForScanning = daysForScanning;
        this.books = books;
        this.libraries = libraries;
    }

    public int getBooksExistsCount() {
        return booksExistsCount;
    }

    public int getLibraryCount() {
        return libraryCount;
    }

    public int getDaysForScanning() {
        return daysForScanning;
    }

    public List<Integer> getBooks() {
        return books;
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    @Override
    public String toString() {
        return "InputData{" +
                "booksExistsCount=" + booksExistsCount +
                ", libraryCount=" + libraryCount +
                ", daysForScanning=" + daysForScanning +
                ", books=" + books +
                ", libraries=" + libraries +
                '}';
    }
}

class Library {
    private int libraryIndex;
    private int booksCount;
    private int signUpTime;
    private int shipsPerDay;

    List<Integer> booksIndex;

    public Library(int booksCount, int signUpTime, int shipsPerDay, List<Integer> booksIndex, int libraryIndex) {
        this.booksCount = booksCount;
        this.signUpTime = signUpTime;
        this.shipsPerDay = shipsPerDay;
        this.booksIndex = booksIndex;
        this.libraryIndex = libraryIndex;
    }

    public int getBooksCount() {
        return booksCount;
    }

    public int getSignUpTime() {
        return signUpTime;
    }

    public int getShipsPerDay() {
        return shipsPerDay;
    }

    public List<Integer> getBooksIndex() {
        return booksIndex;
    }

    public int getLibraryIndex() {
        return libraryIndex;
    }

    @Override
    public String toString() {
        return "Library{" +
                "booksCount=" + booksCount +
                ", signUpTime=" + signUpTime +
                ", shipsPerDay=" + shipsPerDay +
                ", books=" + booksIndex +
                '}';
    }
}
