package com.app.sms.clientws;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Devoirs;
import com.app.sms.models.Response;
import com.app.sms.models.DevoirsList;
import com.app.sms.ui.impl.MainUIApplication;
import com.app.sms.utils.Utilitaire;
import com.grak.jaxb.core.JaxBinding;
import com.grak.rest.client.Request;

public class DevoirsWS {
	
	private static final String API_URI = Utilitaire.getWebServiceUrl();
	
	public DevoirsWS() {
		super();
	}
	
	public void create(Devoirs devoirs) throws JAXBException , IOException, WSException {
		Request request = new Request (API_URI) ;		
		request.path("devoir/"+MainUIApplication.getCurrentUser().getId());		
		request.setParam("idModule", String.valueOf(devoirs.getIdModule()));
		request.setParam("idClasse", String.valueOf(devoirs.getIdClasse()));
		request.setParam("statut", String.valueOf(devoirs.getStatut()));
		request.setParam("dateHeure", devoirs.getDateHeure());
		request.setParam("description", devoirs.getDescription());
		request.setParam("duree", String.valueOf(devoirs.getDuree()));
		request.setParam("coef", String.valueOf(devoirs.getCoef()));
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

	public List<Devoirs> listForEns(int idEns) throws IOException , WSException, JAXBException{
		Request request = new Request (API_URI) ;
		request.path("devoir/enseignant/"+idEns);
		request.setMethod(Request.Method.get);
		request.send();
		
		String responseBody = request.getResponseBody();
			
		DevoirsList devoirsList=null;
		JaxBinding jaxb=null;
		
		jaxb = new JaxBinding(DevoirsList.class);
		devoirsList = jaxb.unmarshal(responseBody);
		devoirsList.display();
        return (devoirsList!=null) ? devoirsList.getDevoirsList() : null ;
	}

	public List<Devoirs> listForEleve(int idEleve) throws JAXBException , IOException, WSException {

		Request request = new Request (API_URI) ;
		request.path("devoir/eleve/"+idEleve);
		request.setMethod(Request.Method.get);
		request.send();
		String responseBody = request.getResponseBody();
		DevoirsList devoirsList=null;
		
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(DevoirsList.class);
			jaxb1 = new JaxBinding(Response.class);
			devoirsList = jaxb.unmarshal(responseBody);
			devoirsList.display();
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
        return devoirsList.getDevoirsList();

	}


	public void update(Devoirs devoirs) throws JAXBException, IOException, WSException {
		Request request = new Request (API_URI) ;
		request.path("devoir/"+devoirs.getId());		
		request.setParam("id", String.valueOf(devoirs.getId()));
		request.setParam("idModule", String.valueOf(devoirs.getIdModule()));
		request.setParam("idClasse", String.valueOf(devoirs.getIdClasse()));
		request.setParam("statut", devoirs.getStatut());
		request.setParam("dateHeure", devoirs.getDateHeure());
		request.setParam("description", devoirs.getDescription());
		request.setParam("duree", String.valueOf(devoirs.getDuree()));
		request.setParam("coef", String.valueOf(devoirs.getCoef()));
		
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
		request.path("devoir/"+id);
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
