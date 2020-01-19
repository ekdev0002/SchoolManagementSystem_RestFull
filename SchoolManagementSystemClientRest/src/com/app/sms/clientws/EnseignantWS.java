
package com.app.sms.clientws;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Enseignant;
import com.app.sms.models.EnseignantList;
import com.app.sms.models.Response;
import com.app.sms.utils.Utilitaire;
import com.grak.jaxb.core.JaxBinding;
import com.grak.rest.client.Request;

public class EnseignantWS {
	
	private static final String API_URI = Utilitaire.getWebServiceUrl();
	
	public EnseignantWS() {
		super();
	}


	public boolean create(Enseignant enseignant)  {
		Request request = new Request (API_URI) ;		
		request.path("enseignant");		
		request.setParam("nom", enseignant.getNom());
		request.setParam("prenom", enseignant.getPrenom());
		request.setParam("email", enseignant.getEmail());
		request.setParam("telephone", enseignant.getTelephone());
		request.setParam("genre", enseignant.getGenre());
		request.setParam("path", enseignant.getPath());
		request.setParam("login", enseignant.getLogin());
		request.setParam("password", enseignant.getPassword());
		int i = 0;
		for(Integer idClasse : enseignant.getIdClasseList())
		{
			request.setParam("idClasse"+i, idClasse.toString());
		}
		
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
			
		} catch (JAXBException | IOException | WSException e) {
			System.err.println(e.getMessage());
		}
			
		return true;
	}
	
	
	
	public List<Enseignant> list() throws IOException , WSException, JAXBException{
		
		Request request = new Request (API_URI) ;
		request.path("enseignants");
		request.setMethod(Request.Method.get);
		request.send();
		
		String responseBody = request.getResponseBody();
			
		EnseignantList enseignantList=null;
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(EnseignantList.class);
			enseignantList = jaxb.unmarshal(responseBody);
			enseignantList.display();
		} catch (JAXBException e) {
				Response reponse=null;
				jaxb1 = new JaxBinding(Response.class);
				reponse = jaxb1.unmarshal(responseBody);
				}			
        return enseignantList.getEnseignantList();
	}

	public Enseignant find(int id) throws IOException, WSException {
		
		Request request = new Request (API_URI) ;
		request.path("enseignant/"+id);
		request.setMethod(Request.Method.get);
		request.send();
		
		String responseBody = request.getResponseBody();
			
		EnseignantList enseignantList=null;
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(EnseignantList.class);
			jaxb1 = new JaxBinding(Response.class);
			enseignantList = jaxb.unmarshal(responseBody);
			enseignantList.display();
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
        return enseignantList.getEnseignantList().get(0);
	}


	public boolean update(Enseignant enseignant) throws JAXBException, IOException, WSException {
		
		Request request = new Request (API_URI) ;		
		request.path("enseignant/"+enseignant.getId());		
		request.setParam("nom", enseignant.getNom());
		request.setParam("prenom", enseignant.getPrenom());
		request.setParam("email", enseignant.getEmail());
		request.setParam("telephone", enseignant.getTelephone());
		request.setParam("genre", enseignant.getGenre());
		request.setParam("path", enseignant.getPath());
		request.setParam("login", enseignant.getLogin());
		request.setParam("password", enseignant.getPassword());
		int i = 0;
		for(Integer idClasse : enseignant.getIdClasseList())
		{
			request.setParam("idClasse"+i, idClasse.toString());
		}
		
		System.out.println("limage donne"+enseignant.getPath());
		
		request.setMethod(Request.Method.put);
		request.sendWithParams();
		
		String responseBody = request.getResponseBody();
		
	
			JaxBinding jaxb = new JaxBinding(Response.class);
			Response response = jaxb.unmarshal(responseBody);
			System.out.print(response.getStatus());
			response.display();
			if(response.getStatus().equals("KO"))
			{
				throw new WSException(response.getMessage());
			}
	
			
		return true;
	}

	public boolean delete(Integer id) throws JAXBException, IOException, WSException {
		Request request = new Request (API_URI) ;
		request.path("enseignant/"+id);
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
		return true;
	}

	public Enseignant findEnseignantByLoginPassword (String login, String password) throws IOException, WSException, JAXBException {
		
		Request request = new Request (API_URI) ;
		request.path("enseignant/"+login+"/"+password);
		request.setMethod(Request.Method.get);
		request.send();
		
		String responseBody = request.getResponseBody();
			
		EnseignantList enseignantList=null;
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		
			jaxb = new JaxBinding(EnseignantList.class);
	//		jaxb1 = new JaxBinding(Response.class);
			enseignantList = jaxb.unmarshal(responseBody);
			enseignantList.display();
//		} catch (JAXBException e) {
//			e.printStackTrace();
//				Response reponse=null;
//				try {
//					reponse = jaxb1.unmarshal(responseBody);
//					if(reponse.getStatus().equals("KO"))
//					{
//						throw new WSException(reponse.getMessage());
//					}
//
//				} catch (JAXBException e1) {
//					e1.printStackTrace();
//				}			
//		}
        return enseignantList.getEnseignantList().get(0);
	}
}