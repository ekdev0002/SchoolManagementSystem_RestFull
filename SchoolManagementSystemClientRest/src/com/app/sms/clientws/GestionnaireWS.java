package com.app.sms.clientws;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Gestionnaire;
import com.app.sms.models.GestionnaireList;
import com.app.sms.models.Response;
import com.app.sms.utils.Utilitaire;
import com.grak.jaxb.core.JaxBinding;
import com.grak.rest.client.Request;


public class GestionnaireWS {
	
	public GestionnaireWS() {
		super();
	}
	
	private static final String API_URI = Utilitaire.getWebServiceUrl();
	
	public boolean create(Gestionnaire gestionnaire) throws WSException {
    		Request request = new Request (API_URI) ;
    		
    		System.out.print("il est la");
    		request.path("gestionnaire");		
    		request.setParam("nom", gestionnaire.getNom());
    		request.setParam("prenom", gestionnaire.getPrenom());
    		request.setParam("email", gestionnaire.getEmail());
    		request.setParam("telephone", gestionnaire.getTelephone());
    		request.setParam("genre", gestionnaire.getGenre());
    		request.setParam("path", gestionnaire.getPath());
    		request.setParam("login", gestionnaire.getLogin());
    		request.setParam("password", gestionnaire.getPassword());
    		request.setParam("date_naissance",gestionnaire.getDate_naissance());
    		request.setParam("adresse",gestionnaire.getAdresse());
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
        	
        return true;
	}
	
	public List<Gestionnaire> list() throws IOException, WSException {
		Request request = new Request (API_URI) ;
		request.path("gestionnaires");
		request.setMethod(Request.Method.get);
		request.send();
		String responseBody = request.getResponseBody();
		GestionnaireList gestionnaireList=null;
		
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(GestionnaireList.class);
			
			gestionnaireList = jaxb.unmarshal(responseBody);
			gestionnaireList.display();
		} catch (JAXBException e) {
			e.printStackTrace();
				Response reponse=null;
				try {
					jaxb1 = new JaxBinding(Response.class);
					reponse = jaxb1.unmarshal(responseBody);
					if(reponse.getStatus().equals("KO"))
					{
						throw new WSException(reponse.getMessage());
					}

				} catch (JAXBException e1) {
					e1.printStackTrace();
				}			
		}
        return gestionnaireList.getGestionnaireList();       
	}

	public Gestionnaire find(int id) throws IOException {
		Request request = new Request (API_URI) ;
		request.path("gestionnaire/"+id);
		request.setMethod(Request.Method.get);
		request.send();
		String responseBody = request.getResponseBody();
		Gestionnaire gestionnaire=null;
		
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(Gestionnaire.class);
			jaxb1 = new JaxBinding(Response.class);
			gestionnaire = jaxb.unmarshal(responseBody);
			gestionnaire.display();
		} catch (JAXBException e) {
				Response reponse=null;
				try {
					reponse = jaxb1.unmarshal(responseBody);
					if(reponse.getStatus().equals("KO"))
					{
						return null;
					}

				} catch (JAXBException e1) {
					e1.printStackTrace();
				}			
		}
        return gestionnaire;
}	
	
	public void delete(int id) throws JAXBException , IOException, WSException {
		Request request = new Request (API_URI) ;
		request.path("gestionnaire/"+id);
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

	
	public boolean update(Gestionnaire gestionnaire) throws WSException {
		Request request = new Request (API_URI) ;		
		request.path("gestionnaire/"+gestionnaire.getId());		
		request.setParam("nom", gestionnaire.getNom());
		request.setParam("prenom", gestionnaire.getPrenom());
		request.setParam("email", gestionnaire.getEmail());
		request.setParam("telephone", gestionnaire.getTelephone());
		request.setParam("genre", gestionnaire.getGenre());
		request.setParam("path", gestionnaire.getPath());
		request.setParam("login", gestionnaire.getLogin());
		request.setParam("password", gestionnaire.getPassword());
		request.setParam("date_naissance",gestionnaire.getDate_naissance());
		request.setParam("adresse",gestionnaire.getAdresse());
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
    	
    return true;
	}
	
	public Gestionnaire findGestionnaireByLoginPassword (String login, String password) throws JAXBException , IOException, WSException{
		Request request = new Request (API_URI) ;
		request.path("gestionnaire/"+login+"/"+password);
		request.setMethod(Request.Method.get);
		request.send();
		String responseBody = request.getResponseBody();
		GestionnaireList gestionnaireList=null;
		
		JaxBinding jaxb=null;		
		
			jaxb = new JaxBinding(GestionnaireList.class);
			
			gestionnaireList = jaxb.unmarshal(responseBody);
			gestionnaireList.display();

		return gestionnaireList.getGestionnaireList().get(0);
	}
}