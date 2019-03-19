package com.codingwolves.InvertedIndex;

import com.codingwolves.FileParser.FileTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class InvertedIndexGenerator {

    public static void createPostingsList(List<FileTemplate>Files){
        HashMap<String,HashMap<String, List<Integer>>> invertedIndex = new HashMap<>();
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
        System.out.println(Arrays.asList(invertedIndex));
    }
}
