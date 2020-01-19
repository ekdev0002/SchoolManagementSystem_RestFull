package com.app.sms.clientws;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Candidature;
import com.app.sms.models.CandidatureList;
import com.app.sms.models.Diplome;
import com.app.sms.models.Response;
import com.app.sms.utils.Utilitaire;
import com.grak.jaxb.core.JaxBinding;
import com.grak.rest.client.Request;

public class CandidatureWS  {
	
	private static final String API_URI = Utilitaire.getWebServiceUrl();
	public CandidatureWS() {
		super();
	}
	
	public void create(Candidature candidature) throws JAXBException , IOException, WSException {
		System.out.println(candidature.toString());
		Request request = new Request (API_URI) ;		
		request.path("candidature");
		request.setParam("nom", candidature.getNom());
		request.setParam("prenom", candidature.getPrenom());
		request.setParam("email", candidature.getEmail());
		request.setParam("telephone", candidature.getTelephone());
		request.setParam("commentaires", candidature.getCommentaires());
		request.setParam("state", candidature.getState());
		request.setParam("idEnseignant", String.valueOf(candidature.getIdEnseignant()));
		request.setParam("path", candidature.getPath());
		request.setParam("birthday", candidature.getBirthday());
		request.setParam("genre", candidature.getGenre());
		request.setParam("cv", candidature.getCv());
		
		request.setMethod(Request.Method.post);
		request.sendWithParams();		
		String responseBody = request.getResponseBody();							
		JaxBinding jaxb = new JaxBinding(Response.class);
		Response response = jaxb.unmarshal(responseBody);
		response.display();
		for(Diplome diplome : candidature.getDiplomeList().getDiplomeList())
		{
			DiplomeWS diplomews=new DiplomeWS();
			diplomews.create(diplome);
		}
		if(response.getStatus().equals("KO"))
		{
			throw new WSException(response.getMessage());
		}
		

	}


	public boolean update(Candidature candidature) throws JAXBException, IOException, WSException {
		Request request = new Request (API_URI) ;
		request.path("candidature/"+candidature.getId());		
		request.setParam("id", String.valueOf(candidature.getId()));
		request.setParam("nom", candidature.getNom());
		request.setParam("prenom", candidature.getPrenom());
		request.setParam("email", candidature.getEmail());
		request.setParam("telephone", candidature.getTelephone());
		request.setParam("birthday", candidature.getBirthday());
		request.setParam("commentaires", candidature.getCommentaires());
		request.setParam("state", candidature.getState());
		request.setParam("path", candidature.getPath());
		request.setParam("cv", candidature.getCv());
		request.setParam("genre", candidature.getGenre());
		request.setParam("idEns", String.valueOf(candidature.getIdEnseignant()));
				
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
		return false;
			
					
	}

	public void delete(int id) throws WSException, JAXBException, IOException {
		Request request = new Request (API_URI) ;
		request.path("candidature/"+id);
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

	
	public List<Candidature> listForEns(int idEns) throws IOException , WSException, JAXBException{
		
		Request request = new Request (API_URI) ;
		request.path("candidature/enseignant/"+idEns);
		request.setMethod(Request.Method.get);
		request.send();
		
		String responseBody = request.getResponseBody();
			
		CandidatureList candidatureList=null;
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(CandidatureList.class);
			jaxb1 = new JaxBinding(Response.class);
			candidatureList = jaxb.unmarshal(responseBody);
			candidatureList.display();
		} catch (JAXBException e) {
				Response reponse=null;
				reponse = jaxb1.unmarshal(responseBody);
							
		}
        return candidatureList.getCandidatureList();
	}
	
	
	public List<Candidature> list() throws IOException , WSException, JAXBException{
		
		Request request = new Request (API_URI) ;
		request.path("candidatures");
		request.setMethod(Request.Method.get);
		request.send();
		
		String responseBody = request.getResponseBody();
			
		CandidatureList candidatureList=null;
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(CandidatureList.class);
			jaxb1 = new JaxBinding(Response.class);
			candidatureList = jaxb.unmarshal(responseBody);
			candidatureList.display();
		} catch (JAXBException e) {
			Response reponse=null;
			reponse = jaxb1.unmarshal(responseBody);

		}
        return candidatureList.getCandidatureList();
	}
	
	public Candidature find(int id) throws IOException , WSException{
		
		Request request = new Request (API_URI) ;
		request.path("candidature/"+id);
		request.setMethod(Request.Method.get);
		request.send();
		
		String responseBody = request.getResponseBody();
			
		Candidature candidature=null;
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(Candidature.class);
			jaxb1 = new JaxBinding(Response.class);
			candidature = jaxb.unmarshal(responseBody);
			candidature.display();
		} catch (JAXBException e) {
			e.printStackTrace();
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
        return candidature;
	}

}