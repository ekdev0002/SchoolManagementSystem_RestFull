package com.app.sms.clientws;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.NotFoundDataException;
import com.app.sms.exceptions.WSException;
import com.app.sms.models.Eleve;
import com.app.sms.models.EleveList;
import com.app.sms.models.Response;
import com.app.sms.utils.Utilitaire;
import com.grak.jaxb.core.JaxBinding;
import com.grak.rest.client.Request;

public class EleveWS  {
	
	private static final String API_URI = Utilitaire.getWebServiceUrl();
	
	public EleveWS() {
		super();
	}
	
	public void create(Eleve eleve) throws JAXBException , IOException, WSException{

		Request request = new Request (API_URI) ;		
		request.path("eleve");		
		request.setParam("nom", eleve.getNom());
		request.setParam("prenom", eleve.getPrenom());
		request.setParam("email", eleve.getEmail());
		request.setParam("telephone", eleve.getTelephone());
		request.setParam("genre", eleve.getGenre());
		request.setParam("path", eleve.getPath());
		request.setParam("login", eleve.getLogin());
		request.setParam("password", eleve.getPassword());
		request.setParam("idClasse", String.valueOf(eleve.getIdClasse()));
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
	
	public void update(Eleve eleve) throws JAXBException , IOException, WSException, NotFoundDataException {
		Request request = new Request (API_URI) ;
		request.path("eleve/"+eleve.getId());
		request.setParam("id", String.valueOf(eleve.getId()));
		request.setParam("nom", eleve.getNom());
		request.setParam("prenom", eleve.getPrenom());
		request.setParam("email", eleve.getEmail());
		request.setParam("telephone", eleve.getTelephone());
		request.setParam("genre", eleve.getGenre());
		request.setParam("path", eleve.getPath());
		request.setParam("login", eleve.getLogin());
		request.setParam("password", eleve.getPassword());
		request.setParam("idClasse", String.valueOf(Utilitaire.findIdClasseByLibelle(eleve.getLibelleClasse())));
		
		
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

	public void delete(int id) throws JAXBException , IOException, WSException {
		Request request = new Request (API_URI) ;
		request.path("eleve/"+id);
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
	
	
	public List<Eleve> list() throws JAXBException , IOException, WSException {
		Request request = new Request (API_URI) ;
		request.path("eleves");
		request.setMethod(Request.Method.get);
		request.send();
		String responseBody = request.getResponseBody();
		EleveList eleveList=null;
		
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			
			jaxb = new JaxBinding(EleveList.class);				
			eleveList = jaxb.unmarshal(responseBody);
			eleveList.display();
		} catch (JAXBException e) {
			Response reponse=null;
			jaxb1 = new JaxBinding(Response.class);
			reponse = jaxb1.unmarshal(responseBody);

		}
        return eleveList.getEleveList();

	}

	public Eleve findEleveByLoginPassword (String login, String password) throws JAXBException , IOException, WSException {
		Request request = new Request (API_URI) ;
		request.path("eleve/"+login+"/"+password);
		request.setMethod(Request.Method.get);
		request.send();
		String responseBody = request.getResponseBody();
		Eleve eleve=null;
		
		JaxBinding jaxb=null;
		
			jaxb = new JaxBinding(Eleve.class);
			eleve = jaxb.unmarshal(responseBody);
			eleve.display();
        return eleve;

	}
	

	public List<Eleve> listByClasse(String idClasse) throws JAXBException , IOException, WSException {
		Request request = new Request (API_URI) ;
		request.path("elevesbyclasse/"+idClasse);
		request.setMethod(Request.Method.get);
		request.send();
		String responseBody = request.getResponseBody();
		EleveList eleveList=null;
		
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(EleveList.class);
			jaxb1 = new JaxBinding(Response.class);
			eleveList = jaxb.unmarshal(responseBody);
			eleveList.display();
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
        return eleveList.getEleveList();       
	}

	public List<Eleve> listByEnseignant(int idEnseignant) throws JAXBException , IOException, WSException {
		Request request = new Request (API_URI) ;
		request.path("elevesbyenseignant/"+idEnseignant);
		request.setMethod(Request.Method.get);
		request.send();
		String responseBody = request.getResponseBody();
		EleveList eleveList=null;
		
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(EleveList.class);
			jaxb1 = new JaxBinding(Response.class);
			eleveList = jaxb.unmarshal(responseBody);
			eleveList.display();
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
        return eleveList.getEleveList();
	}

	public Eleve findById(int idEleve) throws JAXBException, IOException {
		Request request = new Request (API_URI) ;
		request.path("eleve/"+idEleve);
		request.setMethod(Request.Method.get);
		request.send();
		String responseBody = request.getResponseBody();
		Eleve eleve=null;
		
		JaxBinding jaxb=null;	
		jaxb = new JaxBinding(Eleve.class);
		eleve = jaxb.unmarshal(responseBody);
		eleve.display();
        return eleve;
        
	}
}