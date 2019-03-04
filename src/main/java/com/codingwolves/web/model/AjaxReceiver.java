package com.codingwolves.web.model;

import com.codingwolves.web.jsonview.Views;
import com.fasterxml.jackson.annotation.JsonView;

public class AjaxReceiver {
    @JsonView(Views.Public.class)
    String code;
    public void setCode(String code){
        this.code=code;
    }
    public String getCode(){
        return code;
    }
   /* List<String> files;

    public List<String> getFiles (){return files;}
    public void setFiles(List<String>files){this.files=files;}*/
}
