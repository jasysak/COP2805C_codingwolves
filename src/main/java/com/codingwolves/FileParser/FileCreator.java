package com.codingwolves.FileParser;

import com.codingwolves.web.model.AjaxData;
import java.util.ArrayList;
import java.util.List;

/**
 * This class accepts the data from the client side as is and arranges the data in
 * a way that the server side application can understand.
 * @author Reubin George
 * @version 1.0
 * @since 04/19/2019
 */
public class FileCreator {
    /**
     * The function accepts the data from the client side via class {@link com.codingwolves.web.model.AjaxData}
     * and disseminate the data to the various class on the server side application that use them.
     * @param receivedData Raw data from the client client
     * @param Files List of files created on the server side
     * @throws NullPointerException
     */
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

            PARSED_DATA_CREATION:
            for(int j = arrayIterator; j<lengthTempArray;j++){
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
