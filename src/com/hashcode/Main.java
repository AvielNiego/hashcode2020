package com.hashcode;

public class Main {

    public static void main(String[] args) throws Exception {
        InputData inputData = new ReadInputData("/tmp/hashcode/a_example.txt").parse();
//        var output = new ProblemSolver(inputData).solve();
//        new WriteOutputData("/tmp/t.out").write(output);
        System.out.println(inputData);
    }
}
