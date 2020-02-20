package com.hashcode;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        InputData inputData = new ReadInputData("/tmp/hashcode/b_read_on.txt").parse();
        List<LibrarySubmission> output = new ProblemSolver().solve(inputData);
        new WriteOutputData("/tmp/t.out").write(output);
        System.out.println(new ScoreCalculator().calc(output));
    }
}
