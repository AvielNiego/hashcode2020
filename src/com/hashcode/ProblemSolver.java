package com.hashcode;

import java.util.*;

import static java.lang.System.currentTimeMillis;

public class ProblemSolver {

    private List<Integer> books;
    private Map<Integer, Integer> libraryScore;
    private InputData inputData;
    private Integer remainingDays;

    public List<LibrarySubmission> solve(InputData inputData) {
        libraryScore = new HashMap<>();
        this.inputData = inputData;
        remainingDays = this.inputData.getDaysForScanning();
        books = inputData.books;


        List<LibrarySubmission> chosenLibs = new ArrayList<>();
        while (remainingDays > 0) {
            long s = currentTimeMillis();
            inputData.libraries.forEach(l -> l.booksIndex.sort(Comparator.comparingInt(o -> books.get(o))));
            long t = currentTimeMillis();
            inputData.libraries.forEach(lib -> libraryScore.put(lib.getLibraryIndex(), getLibraryScore(lib)));
            inputData.libraries.sort(Comparator.comparingInt(this::queryScore).reversed());
            Library chosenLib = inputData.libraries.remove(0);
            chosenLibs.add(new LibrarySubmission(chosenLib.getLibraryIndex(), chosenLib.booksIndex));
            remainingDays -= chosenLib.getSignUpTime();
            System.out.println(t - s);
        }


        return chosenLibs;
    }

    private int queryScore(Library lib) {
        return libraryScore.get(lib.getLibraryIndex());
    }


    private int getLibraryScore(Library lib1) {
        long daysRemaining = Math.max(inputData.getDaysForScanning() - lib1.getSignUpTime(), 0);
        return lib1.getBooksIndex().stream()
                .limit(daysRemaining * lib1.getShipsPerDay())
                .mapToInt(x -> books.get(x))
                .sum();
    }
}
