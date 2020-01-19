package com.app.sms.clientws;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Classe;
import com.app.sms.models.ClasseList;
import com.app.sms.models.Response;
import com.app.sms.utils.Utilitaire;
import com.grak.jaxb.core.JaxBinding;
import com.grak.rest.client.Request;

public class ClasseWS {
	
	private static final String API_URI = Utilitaire.getWebServiceUrl();
	
	public ClasseWS() {
		super();
	}
	
	public void create(Classe classe) throws JAXBException , IOException, WSException {
		Request request = new Request (API_URI) ;		
		request.path("classe");
		request.setParam("description", classe.getDescription());
		request.setParam("libelle", classe.getLibelle());	
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

	
	public List<Classe> list() throws JAXBException , IOException, WSException {

		Request request = new Request (API_URI) ;
		request.path("classes");
		request.setMethod(Request.Method.get);
		request.send();
		String responseBody = request.getResponseBody();
		ClasseList classeList=null;
		
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(ClasseList.class);
			classeList = jaxb.unmarshal(responseBody);
			classeList.display();
		} catch (JAXBException e) {
			Response reponse=null;
			jaxb1 = new JaxBinding(Response.class);
			reponse = jaxb1.unmarshal(responseBody);
		}
        return classeList.getClasseList();

	}

	
	
	public List<Classe> listForEns(int idEns) throws IOException , WSException, JAXBException{
		
		Request request = new Request (API_URI) ;
		request.path("classe/enseignant/"+idEns);
		request.setMethod(Request.Method.get);
		request.send();
		
		String responseBody = request.getResponseBody();
			
		ClasseList classeList=null;
		JaxBinding jaxb=null;
		
		jaxb = new JaxBinding(ClasseList.class);
		classeList = jaxb.unmarshal(responseBody);
		classeList.display();
		if(classeList!=null)
		{
			return classeList.getClasseList();
		}
		return null;

		
	}

	public int findIdClasseByLibelle(String libelleClasse) throws JAXBException , IOException, WSException {

		Request request = new Request (API_URI) ;
		request.path("classe/libelle/"+libelleClasse);
		request.setMethod(Request.Method.get);
		request.send();
		String responseBody = request.getResponseBody();
		Classe classe=null;
		
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(Classe.class);
			jaxb1 = new JaxBinding(Response.class);
			classe = jaxb.unmarshal(responseBody);
			classe.display();
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
        return classe.getId();

	}


	public void update(Classe classe) throws JAXBException, IOException, WSException {
		Request request = new Request (API_URI) ;	
		request.path("classe/"+String.valueOf(classe.getId()));
		request.setParam("libelle", classe.getLibelle());
		request.setParam("description", classe.getDescription());
		
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
		request.path("classe/"+id);
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
