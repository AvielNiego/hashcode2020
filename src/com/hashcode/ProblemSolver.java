package com.hashcode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.currentTimeMillis;

public class ProblemSolver {

    public static final int NEXT_LIBS_WINDOW = 5;
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

        double avgScorePerDayForTopLibs = getAvgScoreForTopLibs(libraries);

        libraries.forEach(lib -> libraryScore.put(lib.getLibraryIndex(), getLibraryScore(lib, avgScorePerDayForTopLibs)));
        return libraries.remove(0);
    }

    private double getAvgScoreForTopLibs(List<Library> libraries) {
        return libraries.stream().limit(NEXT_LIBS_WINDOW).mapToDouble(this::getAvgGainForLibrary).sum() / NEXT_LIBS_WINDOW;
    }

    private int getAvgGainForLibrary(Library lib) {
        return getBooksUpToRemainingTime(lib).mapToInt(x -> books.get(x)).sum() / getRemainingDays(lib);
    }

    private Double getLibraryScore(Library lib, double avgScorePerDayForTopLibs) {
        Stream<Integer> booksUpToRemainingTime = getBooksUpToRemainingTime(lib);
        int libBookSum = booksUpToRemainingTime
                .mapToInt(x -> books.get(x))
                .sum();
        return libBookSum - (lib.getSignUpTime() * avgScorePerDayForTopLibs);
    }

    private double queryScore(Library lib) {
        return libraryScore.get(lib.getLibraryIndex());
    }


    private double getLibraryScore(Library lib) {
        Stream<Integer> booksUpToRemainingTime = getBooksUpToRemainingTime(lib);
        int libBookSum = booksUpToRemainingTime
                .mapToInt(x -> books.get(x))
                .sum();
        return libBookSum;
        // - (lib.getSignUpTime() * avgScorePerDay)
    }

    private Stream<Integer> getBooksUpToRemainingTime(Library lib1) {
        long daysRemaining = getRemainingDays(lib1);
        return lib1.getBooksIndex().stream()
                .filter(bookidx -> !chosenBooks.contains(bookidx))
                .limit(daysRemaining * lib1.getShipsPerDay());
    }

    private int getRemainingDays(Library lib1) {
        return Math.max(inputData.getDaysForScanning() - lib1.getSignUpTime(), 0);
    }
}
