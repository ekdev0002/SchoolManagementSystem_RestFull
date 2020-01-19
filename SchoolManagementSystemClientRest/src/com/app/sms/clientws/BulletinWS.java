package com.app.sms.clientws;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Bulletin;
import com.app.sms.models.BulletinList;
import com.app.sms.models.Response;
import com.app.sms.utils.Utilitaire;
import com.grak.jaxb.core.JaxBinding;
import com.grak.rest.client.Request;

public class BulletinWS {
	
	private static final String API_URI = Utilitaire.getWebServiceUrl();

	public BulletinWS() {
		super();
	}
	
	public void create(String semestre) {
		Request request = new Request (API_URI) ;		
		request.path("bulletin/"+semestre);		
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
	}

	public List<Bulletin> list() throws IOException, WSException, JAXBException{
		Request request = new Request (API_URI) ;
		request.path("bulletins");
		request.setMethod(Request.Method.get);
		request.send();
		String responseBody = request.getResponseBody();
		BulletinList bulletinList=null;
		
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(BulletinList.class);
			bulletinList = jaxb.unmarshal(responseBody);
			bulletinList.display();
		} catch (JAXBException e) {
				Response reponse=null;
				jaxb1 = new JaxBinding(Response.class);					
				reponse = jaxb1.unmarshal(responseBody);

		}
        return bulletinList.getBulletinList();
	}

	public List<Bulletin> list(int idEleve) throws JAXBException, IOException  {
		Request request = new Request (API_URI) ;
		request.path("bulletin/"+idEleve);
		request.setMethod(Request.Method.get);
		request.send();
		String responseBody = request.getResponseBody();
		BulletinList bulletinList=null;
		
		JaxBinding jaxb=null;
		
		jaxb = new JaxBinding(BulletinList.class);
		bulletinList = jaxb.unmarshal(responseBody);
		bulletinList.display();
        return bulletinList.getBulletinList();
	}
}