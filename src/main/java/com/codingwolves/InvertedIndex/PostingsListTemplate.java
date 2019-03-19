package com.codingwolves.InvertedIndex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostingsListTemplate {
    HashMap<String, List<Integer>> postingList;
    Map<String,HashMap<String, List<Integer>>>invertedIndex;

    public HashMap<String, List<Integer>> getPostingList() {
        return postingList;
    }

    public void setPostingList(HashMap<String, List<Integer>> postingList) {
        this.postingList = postingList;
    }

    public Map<String, HashMap<String, List<Integer>>> getInvertedIndex() {
        return invertedIndex;
    }

    public void setInvertedIndex(Map<String, HashMap<String, List<Integer>>> invertedIndex) {
        this.invertedIndex = invertedIndex;
    }
}
