package com.codingwolves.Persistance.Service;


import com.codingwolves.Persistance.Handler.DataAccessObject;
import com.codingwolves.Persistance.Handler.ModelFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceImplementer implements ServiceInterface {

    DataAccessObject fileDao;

    @Autowired
    public void setFileDao(DataAccessObject fileDao) {
        this.fileDao = fileDao;
    }

    @Override
    public void addFile(ModelFile file) {
        fileDao.addFile(file);
    }

    @Override
    public void updateFile(ModelFile file) {
        fileDao.updateFile(file);
    }

    @Override
    public void deleteFile(ModelFile file) {
        fileDao.deleteFile(file);
    }
}
