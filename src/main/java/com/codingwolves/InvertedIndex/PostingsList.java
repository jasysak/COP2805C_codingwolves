package com.codingwolves.InvertedIndex;

import com.codingwolves.FileParser.FileTemplate;

import java.util.*;

public class PostingsList {

    private List<FileTemplate> clientFiles;
    /*private Map<String, Map<String, List<Integer>>>
            invertedIndex = postingsList(clientFiles);*/

    private PostingsList(){}
    public PostingsList(List<FileTemplate>clientFiles){
        super();
        this.clientFiles = clientFiles;
    }

    public Map<String, Map<String, List<Integer>>> getInvertedIndex() {
        return postingsList(this.clientFiles);
    }

    private Map<String,Map<String, List<Integer>>> postingsList(List<FileTemplate>Files){
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
        return invertedIndex;
    }


}
