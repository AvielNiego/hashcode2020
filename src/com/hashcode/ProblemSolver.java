package com.hashcode;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.in;

public class ProblemSolver {

    private List<Integer> books;
    private Map<Integer, Integer> libraryScore;
    private InputData inputData;

    public List<LibrarySubmission> solve(InputData inputData) {
        libraryScore = new HashMap<>();
        this.inputData = inputData;
        int totalDays = this.inputData.getDaysForScanning();

        books = inputData.books;

        long s = currentTimeMillis();
        inputData.libraries.forEach(l -> l.booksIndex.sort(Comparator.comparingInt(o -> books.get(o))));
        long t = currentTimeMillis();

        inputData.libraries.forEach(lib -> libraryScore.put(lib.getLibraryIndex(), getLibraryScore(lib)));
        inputData.libraries.sort(Comparator.comparingInt(this::queryScore).reversed());


        System.out.println(t - s);


        return inputData.libraries.stream().map(l -> new LibrarySubmission(l.getLibraryIndex(), l.booksIndex)).collect(Collectors.toList());
    }

    private int queryScore(Library lib) {
        return libraryScore.get(lib.getLibraryIndex());
    }


    private int getLibraryScore(Library lib1) {
        int daysRemaining = inputData.getDaysForScanning() - lib1.getSignUpTime();
        return lib1.getBooksIndex().stream().limit(daysRemaining * lib1.getShipsPerDay()).mapToInt(x -> books.get(x)).sum();
    }
}
