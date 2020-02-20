package com.hashcode;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.System.currentTimeMillis;

public class ProblemSolver {

    private List<Integer> books;
    private Map<Integer, Integer> libraryScore;

    public List<LibrarySubmission> solve(InputData inputData) {
        books = inputData.books;


        inputData.libraries.forEach(lib -> libraryScore.put(lib.getLibraryIndex(), getLibraryScore(lib)));
        inputData.libraries.sort(Comparator.comparingInt(this::getLibraryScore).reversed());

        long s = currentTimeMillis();
        inputData.libraries.forEach(l -> books.sort(Comparator.comparingInt(o -> books.get(o))));
        long t = currentTimeMillis();
        System.out.println(t - s);


        return inputData.libraries.stream().map(l -> new LibrarySubmission(l.getLibraryIndex(), l.books)).collect(Collectors.toList());
    }

    private int getLibraryScore(Library lib1) {
        return lib1.getBooks().stream().mapToInt(x -> books.get(x)).sum();
    }
}
