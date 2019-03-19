package com.codingwolves.Persistance.Service;

import com.codingwolves.Persistance.Handler.ModelFile;

public interface ServiceInterface {
    void addFile(ModelFile file);
    void updateFile(ModelFile file);
    void deleteFile(ModelFile file);
}
