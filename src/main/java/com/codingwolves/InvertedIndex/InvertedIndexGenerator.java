package com.codingwolves.InvertedIndex;

import com.codingwolves.FileParser.FileTemplate;
import java.util.*;

public class InvertedIndexGenerator {
/*

    public static Map<String,Map<String, List<Integer>>> postingsList(List<FileTemplate>Files){
        Map<String,Map<String, List<Integer>>> invertedIndex = new HashMap<>();
        for (FileTemplate individualFile:Files){
            for(int i = 0; i < individualFile.getParsedData().length;i++){
                if(!invertedIndex.containsKey(individualFile.getParsedData()[i])){
                    invertedIndex.put(individualFile.getParsedData()[i],new HashMap<>());
                }
                if(!invertedIndex.get(individualFile.getParsedData()[i]).containsKey(individualFile.getFileName())){
                    invertedIndex.get(individualFile.getParsedData()[i]).put(individualFile.getFileName(),new ArrayList<>());
                }
                if(!invertedIndex.get(individualFile.getParsedData()[i]).get(individualFile.getFileName()).contains(i))
                invertedIndex.get(individualFile.getParsedData()[i]).get(individualFile.getFileName()).add(i);
            }

        }
        //System.out.println(Arrays.asList(invertedIndex));
        return invertedIndex;
    }*/
}
