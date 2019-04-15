package com.codingwolves.Persistence.Handler;

import com.codingwolves.FileParser.FileTemplate;

import java.util.List;

public interface ModelFileDao {
    String deleteFile(String fileName);
    void updateFile(ModelFile individualFile);
    void addFile(List<FileTemplate> files);
    List<ModelFile> listAllFiles();
    /* ModelFile findPostingList(String word);*/

}
