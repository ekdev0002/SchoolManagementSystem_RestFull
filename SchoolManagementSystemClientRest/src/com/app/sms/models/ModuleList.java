package com.app.sms.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class ModuleList {

	private List<Module> moduleList = new ArrayList<>();

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }
    
    @XmlElement (name="module") 
    @JsonProperty("moduleList")
    public List<Module> getModuleList() {
        return moduleList;
    }
    
    @Override
    public String toString() {
    	String result = "";
    	for (Module module : moduleList) {
    		result += module.toString() + "\n";
    	}    	
    	return result;
    }
    
    public void add(Module module) {
    	moduleList.add(module);
	}

	public void display() {
		for( Module module : moduleList) {
			module.display();
		}
	}
}
