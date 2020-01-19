package com.app.sms.clientws;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Module;
import com.app.sms.models.ModuleList;
import com.app.sms.models.Response;
import com.app.sms.utils.Utilitaire;
import com.grak.jaxb.core.JaxBinding;
import com.grak.rest.client.Request;

public class ModuleWS {
	
	private static final String API_URI = Utilitaire.getWebServiceUrl();
	
	public ModuleWS() {
		super();
	}
	
	public void create(Module module) throws JAXBException , IOException, WSException {
		Request request = new Request (API_URI) ;		
		request.path("module");
		request.setParam("coefficient",String.valueOf( module.getCoefficient()));
		request.setParam("description", module.getDescription());
		request.setParam("libelle", module.getLibelle());	
		request.setMethod(Request.Method.post);
		request.sendWithParams();	
		String responseBody = request.getResponseBody();
		
		try {
			JaxBinding jaxb = new JaxBinding(Response.class);
			Response response = jaxb.unmarshal(responseBody);
			System.out.print(response.getStatus());
			response.display();
			if(response.getStatus().equals("KO"))
			{
				throw new WSException(response.getMessage());
			}
			
		} catch (JAXBException | IOException e) {
			System.err.println(e.getMessage());
		}

	}

	
	public List<Module> list() throws JAXBException , IOException, WSException {

		Request request = new Request (API_URI) ;
		request.path("modules");
		request.setMethod(Request.Method.get);
		request.send();
		String responseBody = request.getResponseBody();
		ModuleList moduleList=null;
		
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(ModuleList.class);			
			moduleList = jaxb.unmarshal(responseBody);
			moduleList.display();
		} catch (JAXBException e) {
			Response reponse=null;
			jaxb1 = new JaxBinding(Response.class);
			reponse = jaxb1.unmarshal(responseBody);
		}
        return moduleList.getModuleList();

	}

	
	
	public List<Module> listForEns(int idEns) throws IOException , WSException, JAXBException{
		
		Request request = new Request (API_URI) ;
		request.path("module/enseignant/"+idEns);
		request.setMethod(Request.Method.get);
		request.send();
		
		String responseBody = request.getResponseBody();
			
		ModuleList moduleList=null;
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(ModuleList.class);
			
			moduleList = jaxb.unmarshal(responseBody);
			moduleList.display();
		} catch (JAXBException e) {
				Response reponse=null;
				jaxb1 = new JaxBinding(Response.class);
				reponse = jaxb1.unmarshal(responseBody);
		}
		if(moduleList!=null)
		{
        return moduleList.getModuleList();
		}
		else 
		{
			throw new WSException("modules inexistants");
		}
	}

	public List<Module> listForEleve(int idEleve) throws JAXBException , IOException, WSException {

		Request request = new Request (API_URI) ;
		request.path("module/eleve/"+idEleve);
		request.setMethod(Request.Method.get);
		request.send();
		String responseBody = request.getResponseBody();
		ModuleList moduleList=null;
		
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(ModuleList.class);
			jaxb1 = new JaxBinding(Response.class);
			moduleList = jaxb.unmarshal(responseBody);
			moduleList.display();
		} catch (JAXBException e) {
				Response reponse=null;
				try {
					reponse = jaxb1.unmarshal(responseBody);
					if(reponse.getStatus().equals("KO"))
					{
						throw new WSException(reponse.getMessage());
					}

				} catch (JAXBException e1) {
					e1.printStackTrace();
				}			
		}
        return moduleList.getModuleList();

	}


	public void update(Module module, String idClasse, String idEns) throws JAXBException, IOException, WSException {
		Request request = new Request (API_URI) ;
		request.path("module/"+idClasse+"/"+idEns);	
		System.out.println(module.toString()+idClasse+"/"+idEns);
		request.setParam("id", String.valueOf(module.getId()));
		request.setParam("coefficient",String.valueOf( module.getCoefficient()));
		request.setParam("libelle", module.getLibelle());
		request.setParam("description", module.getDescription());
		
		request.setMethod(Request.Method.put);
		request.sendWithParams();
		
		String responseBody = request.getResponseBody();
		
		JaxBinding jaxb = new JaxBinding(Response.class);
		Response response = jaxb.unmarshal(responseBody);
		if(response.getStatus().equals("KO"))
		{
			throw new WSException(response.getMessage());
		}
		response.display();	
					
	}
	
	public void setEnseignantClasse(int idEns, int idClasse,int idModule) throws JAXBException, IOException, WSException {
		
	Request request = new Request (API_URI) ;		
	request.path("module/setenseignant/"+idEns+"/"+idClasse);
	request.setParam("id",String.valueOf(idModule));
	request.setMethod(Request.Method.post);
	request.sendWithParams();	
	String responseBody = request.getResponseBody();
	
	try {
		JaxBinding jaxb = new JaxBinding(Response.class);
		Response response = jaxb.unmarshal(responseBody);
		System.out.print(response.getStatus());
		response.display();
		if(response.getStatus().equals("KO"))
		{
			throw new WSException(response.getMessage());
		}
		
	} catch (JAXBException | IOException e) {
		System.err.println(e.getMessage());
	}
	}


	public void delete(int id) throws WSException, JAXBException, IOException {
		Request request = new Request (API_URI) ;
		request.path("module/"+id);
		request.setMethod(Request.Method.delete);
		request.send();
		String responseBody = request.getResponseBody();
		JaxBinding jaxb = new JaxBinding(Response.class);
		Response response = jaxb.unmarshal(responseBody);
		response.display();
		if(response.getStatus().equals("KO"))
		{
			throw new WSException(response.getMessage());
		}
		response.display();
		
	}
}
