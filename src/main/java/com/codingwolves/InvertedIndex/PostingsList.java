package com.codingwolves.InvertedIndex;

import com.codingwolves.FileParser.FileTemplate;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

/**
 * The PostingsList class accepts a list of {@link com.codingwolves.FileParser.FileTemplate}
 * and generate an inverted index data structure, The resultant posting list are saved as
 * nested maps for fast search and dissemination operations.
 * @author Reubin George
 * @version 1.0
 * @since 04/19/2019
 */
public class PostingsList {
    private List<FileTemplate> clientFiles;

    /**
     * Default constructor
     */
    private PostingsList(){}

    /**
     * Non-default constructor that accepts a list of {@link com.codingwolves.FileParser.FileTemplate}.
     * @param clientFiles List of files uploaded by the client
     */
    public PostingsList(@NonNull List<FileTemplate>clientFiles){
        super();
        this.clientFiles = clientFiles;
    }

    /**
     * Method return the inverted index created by the classes
     * @return a nested map that contains the postings list
     */
    public Map<String, Map<String, List<Integer>>> getInvertedIndex() {
        return postingsList(this.clientFiles);
    }

    /**
     * This method accepts a list of files and computes the inverted indices.
     * @param Files Files uploaded by the client
     * @return nested maps containing the postings list
     */

    private Map<String,Map<String, List<Integer>>> postingsList(@NonNull List<FileTemplate>Files){
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
