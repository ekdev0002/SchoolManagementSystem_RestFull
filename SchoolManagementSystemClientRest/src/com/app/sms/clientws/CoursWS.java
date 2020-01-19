package com.app.sms.clientws;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Cours;
import com.app.sms.models.Response;
import com.app.sms.models.CoursList;
import com.app.sms.ui.impl.MainUIApplication;
import com.app.sms.utils.Utilitaire;
import com.grak.jaxb.core.JaxBinding;
import com.grak.rest.client.Request;

public class CoursWS {
	
	private static final String API_URI = Utilitaire.getWebServiceUrl();
	
	public CoursWS() {
		super();
	}
	
	public void create(Cours cours) throws JAXBException , IOException, WSException {
		Request request = new Request (API_URI) ;		
		request.path("cours/"+MainUIApplication.getCurrentUser().getId());		
		request.setParam("idModule", String.valueOf(cours.getIdModule()));
		request.setParam("idClasse", String.valueOf(cours.getIdClasse()));
		request.setParam("dateHeure", cours.getDateHeure());
		request.setParam("description", cours.getDescription());
		request.setParam("duree", String.valueOf(cours.getDuree()));	
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

	public List<Cours> listForEns(int idEns) throws IOException , WSException, JAXBException{
		
		Request request = new Request (API_URI) ;
		request.path("cours/enseignant/"+idEns);
		request.setMethod(Request.Method.get);
		request.send();
		
		String responseBody = request.getResponseBody();
			
		CoursList coursList=null;
		JaxBinding jaxb=null;
		jaxb = new JaxBinding(CoursList.class);
		coursList = jaxb.unmarshal(responseBody);
		coursList.display();

        return (coursList!=null) ? coursList.getCoursList() : null;
	}

	public List<Cours> listForEleve(int idEleve) throws JAXBException , IOException, WSException {

		Request request = new Request (API_URI) ;
		request.path("cours/eleve/"+idEleve);
		request.setMethod(Request.Method.get);
		request.send();
		String responseBody = request.getResponseBody();
		CoursList coursList=null;
		
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(CoursList.class);
			jaxb1 = new JaxBinding(Response.class);
			coursList = jaxb.unmarshal(responseBody);
			coursList.display();
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
        return coursList.getCoursList();

	}


	public void update(Cours cours) throws JAXBException, IOException, WSException {
		Request request = new Request (API_URI) ;
		request.path("cours/"+cours.getId());		
		request.setParam("id", String.valueOf(cours.getId()));
		request.setParam("idModule", String.valueOf(cours.getIdModule()));
		request.setParam("idClasse", String.valueOf(cours.getIdClasse()));
		request.setParam("dateHeure", cours.getDateHeure());
		request.setParam("description", cours.getDescription());
		request.setParam("duree", String.valueOf(cours.getDuree()));
		
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

	public void delete(int id) throws WSException, JAXBException, IOException {
		Request request = new Request (API_URI) ;
		request.path("cours/"+id);
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
