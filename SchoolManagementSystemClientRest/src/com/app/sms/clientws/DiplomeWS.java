package com.app.sms.clientws;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Diplome;
import com.app.sms.models.DiplomeList;
import com.app.sms.models.Response;
import com.app.sms.utils.Utilitaire;
import com.grak.jaxb.core.JaxBinding;
import com.grak.rest.client.Request;

public class DiplomeWS  {
	
	private static final String API_URI = Utilitaire.getWebServiceUrl();
	
	public DiplomeWS() {
		super();
	}
	
	public void create(Diplome diplome) throws JAXBException , IOException, WSException {
		Request request = new Request (API_URI) ;	
		request.path("diplome");		
		request.setParam("url", diplome.getUrl());
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
	
	public List<Diplome> list() throws JAXBException , IOException, WSException {
		Request request = new Request (API_URI) ;
		request.path("diplomes");
		request.setMethod(Request.Method.get);
		request.send();
		String responseBody = request.getResponseBody();
		DiplomeList diplomeList=null;
		
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(DiplomeList.class);
			jaxb1 = new JaxBinding(Response.class);
			diplomeList = jaxb.unmarshal(responseBody);
			diplomeList.display();
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
		return diplomeList.getDiplomeList();

	}


	public  boolean update(Diplome diplome) throws JAXBException, IOException, WSException {
		Request request = new Request (API_URI) ;
		request.path("diplome/"+diplome.getId());		
		request.setParam("id", String.valueOf(diplome.getId()));
		request.setParam("url", diplome.getUrl());
		request.setParam("idCandidature", String.valueOf(diplome.getIdCandidature()));
		
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
		return true;			
	}

	public void delete(int id) throws WSException, JAXBException, IOException {
		Request request = new Request (API_URI) ;
		request.path("diplome/"+id);
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
