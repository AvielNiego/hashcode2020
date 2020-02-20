package com.hashcode;

import java.util.List;

public class WriteOutputData {
    String filePath;

    public WriteOutputData(String filePath) {
        this.filePath = filePath;
    }

    public void write(List<LibrarySubmission> librarySubmissions) {
        int signUpLibrariesCount = librarySubmissions.size();
    }
}

class LibrarySubmission {
    int libraryIndex;
    List<Integer> booksToSend;

    public LibrarySubmission(int libraryIndex, List<Integer> booksToSend) {
        this.libraryIndex = libraryIndex;
        this.booksToSend = booksToSend;
    }

    public int getLibraryIndex() {
        return libraryIndex;
    }

    public List<Integer> getBooksToSend() {
        return booksToSend;
    }
}
