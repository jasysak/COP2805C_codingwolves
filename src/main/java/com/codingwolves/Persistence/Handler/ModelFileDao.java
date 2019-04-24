package com.codingwolves.Persistence.Handler;

import com.codingwolves.FileParser.FileTemplate;
import java.util.List;

/**
 * This interface is used to implement the abstract methods in a later class.
 * The methods all pertain to persistent data storage like updating, deleting and
 * adding data.
 */
public interface ModelFileDao {
    String deleteFile(String fileName);
    void updateFile(ModelFile individualFile);
    void addFile(List<FileTemplate> files);
    List<ModelFile> listAllFiles();

}
