package com.hashcode;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        runOnFileName("a");
        runOnFileName("b");
        runOnFileName("c");
        runOnFileName("d");
        runOnFileName("e");
        runOnFileName("f");
    }

    private static void runOnFileName(String fileName) throws IOException {
        InputData inputData = new ReadInputData("/tmp/hashcode/" + fileName + ".txt").parse();
        List<LibrarySubmission> output = new ProblemSolver().solve(inputData);
        new WriteOutputData("/tmp/hashcode/out/v4/" + fileName + ".out").write(output);
        System.out.println(new ScoreCalculator().calc(output));
    }
}
