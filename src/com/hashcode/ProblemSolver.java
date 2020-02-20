package com.hashcode;

import java.util.Comparator;
import java.util.List;

public class ProblemSolver {

    private List<Integer> books;
    public List<LibrarySubmission> solve(InputData inputData) {
        books = inputData.books;
        inputData.libraries.sort(Comparator.comparingInt(this::getLibraryScore).reversed());
//        inputData.libraries.stream().map(l -> new LibrarySubmission(l.));
        return null;
    }

    private int getLibraryScore(Library lib1) {
        return lib1.getBooks().stream().mapToInt(x -> books.get(x)).sum();
    }
}
