package com.hashcode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.currentTimeMillis;

public class ProblemSolver {

    private List<Integer> books;
    private Map<Integer, Double> libraryScore;
    private InputData inputData;
    private Integer remainingDays;
    private Set<Integer> chosenBooks;
    private double avgScorePerDay = 0;

    public List<LibrarySubmission> solve(InputData inputData) {
        libraryScore = new HashMap<>();
        this.inputData = inputData;
        remainingDays = this.inputData.getDaysForScanning();
        books = inputData.books;
        chosenBooks = new HashSet<>();
        avgScorePerDay = inputData.books.stream().mapToDouble(x -> x).sum() / remainingDays;


        List<LibrarySubmission> chosenLibs = new ArrayList<>();
        while (remainingDays > 0 && !inputData.libraries.isEmpty()) {
            System.out.println("Remaining days " + (remainingDays));
            Library chosenLib = getNextLibraryBySortingMethod(inputData.libraries);
            List<Integer> booksUpToRemainingTimeFromLib = getBooksUpToRemainingTime(chosenLib).collect(Collectors.toList());
            chosenBooks.addAll(booksUpToRemainingTimeFromLib);
            chosenLibs.add(new LibrarySubmission(chosenLib.getLibraryIndex(), booksUpToRemainingTimeFromLib));
            remainingDays -= chosenLib.getSignUpTime();
        }


        return chosenLibs;
    }

    private Library getNextLibraryBySortingMethod(List<Library> libraries) {
        libraries.forEach(l -> l.booksIndex.sort(Comparator.comparingInt(o -> books.get(o))));
        libraries.forEach(lib -> libraryScore.put(lib.getLibraryIndex(), getLibraryScore(lib)));
        libraries.sort(Comparator.comparingDouble(this::queryScore).reversed());
        return libraries.remove(0);
    }

    private double queryScore(Library lib) {
        return libraryScore.get(lib.getLibraryIndex());
    }


    private double getLibraryScore(Library lib1) {
        Stream<Integer> booksUpToRemainingTime = getBooksUpToRemainingTime(lib1);
        int libBookSum = booksUpToRemainingTime
                .mapToInt(x -> books.get(x))
                .sum();
        return libBookSum - (lib1.getSignUpTime() * avgScorePerDay);
    }

    private Stream<Integer> getBooksUpToRemainingTime(Library lib1) {
        long daysRemaining = Math.max(inputData.getDaysForScanning() - lib1.getSignUpTime(), 0);
        return lib1.getBooksIndex().stream()
                .filter(bookidx -> !chosenBooks.contains(bookidx))
                .limit(daysRemaining * lib1.getShipsPerDay());
    }
}
