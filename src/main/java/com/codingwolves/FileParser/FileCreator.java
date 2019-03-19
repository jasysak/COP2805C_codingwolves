package com.codingwolves.FileParser;

import com.codingwolves.web.model.AjaxData;

import java.util.ArrayList;
import java.util.List;

public class FileCreator {
    public static void generateFileList(AjaxData receivedData, List<FileTemplate> Files)
    throws NullPointerException{
        int numberFilesParsed = receivedData.getFilesParsed();
        String [] flattenedFileName = receivedData.getFileName();
        String [] flattenedParsedData = receivedData.getParsedData();
        String [] flattenedCheckSum = receivedData.getCheckSum();
        String [] flattenedPath = receivedData.getPath();
        int lengthTempArray = flattenedParsedData.length;
        int arrayIterator = 0;
        for (int i =0; i<numberFilesParsed;i++){
            List<String> fileParsedData = new ArrayList<String>();
            PARSED_DATA_CREATION: for(int j = arrayIterator; j<lengthTempArray;j++){
                if(!flattenedParsedData[j].equals("##$@@")){
                    fileParsedData.add(flattenedParsedData[j]);
                    flattenedParsedData[j]="";
                    arrayIterator++;
                }
                else{
                    flattenedParsedData[j]="";
                    arrayIterator++;
                    break PARSED_DATA_CREATION;
                }
            }
            String [] parsedData = fileParsedData.toArray(new
                    String[fileParsedData.size()]);

            Files.add(new FileTemplate(flattenedFileName[i],
                    flattenedCheckSum[i],flattenedPath[i],parsedData));
        }
    }

}
