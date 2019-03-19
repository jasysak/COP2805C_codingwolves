package com.codingwolves.Persistance.Handler;

public interface DataAccessObject {
    void deleteFile(ModelFile individualFile);
    void updateFile(ModelFile individualFile);
    void addFile(ModelFile individualFile);
    ModelFile findPostingList(String word);

}
