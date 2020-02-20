package com.hashcode;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        String fileName = "f";
        InputData inputData = new ReadInputData("/tmp/hashcode/" + fileName + ".txt").parse();
        List<LibrarySubmission> output = new ProblemSolver().solve(inputData);
        new WriteOutputData("/tmp/hashcode/out/v2/" + fileName + ".out").write(output);
        System.out.println(new ScoreCalculator().calc(output));
    }
}
