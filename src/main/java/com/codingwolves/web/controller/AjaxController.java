package com.codingwolves.web.controller;

import com.codingwolves.FileParser.FileCreator;
import com.codingwolves.FileParser.FileTemplate;
import com.codingwolves.web.jsonview.Views;
import com.codingwolves.web.model.AjaxData;
import com.codingwolves.web.model.AjaxReceiver;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AjaxController {
	List<FileTemplate>Files = new ArrayList<>();
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/performMagic")
	public AjaxReceiver getClientData(@RequestBody AjaxData clientData){
		AjaxReceiver result = new AjaxReceiver();
		result.setCode(clientData.toString());
		try {
			FileCreator.generateFileList(clientData,Files);
		}
		catch (Exception e){
			System.out.println("Error found");
		}
		for (FileTemplate eachFile: Files){
			System.out.println(eachFile.getCheckSum());
		}
		return result;
	}
}