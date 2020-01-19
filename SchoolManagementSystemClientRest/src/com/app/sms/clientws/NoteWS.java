package com.app.sms.clientws;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Devoirs;
import com.app.sms.models.Note;
import com.app.sms.models.Response;
import com.app.sms.utils.Utilitaire;
import com.app.sms.models.NoteList;
import com.grak.jaxb.core.JaxBinding;
import com.grak.rest.client.Request;

public class NoteWS {
	
	private static final String API_URI = Utilitaire.getWebServiceUrl();
	
	public NoteWS() {
		super();
	}
	
	public void create(Note note) throws JAXBException , IOException, WSException {
		Request request = new Request (API_URI) ;		
		request.path("note");
		request.setParam("idDevoir", String.valueOf(note.getIdDevoirs()));
		request.setParam("coef",String.valueOf(note.getCoef()));
		request.setParam("note", String.valueOf(note.getNote()));
		request.setParam("idEleve", String.valueOf(note.getIdEleve()));
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

	
	public List<Note> list() throws JAXBException , IOException, WSException {

		Request request = new Request (API_URI) ;
		request.path("notes");
		request.setMethod(Request.Method.get);
		request.send();
		String responseBody = request.getResponseBody();
		NoteList noteList=null;
		
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(NoteList.class);
			
			noteList = jaxb.unmarshal(responseBody);
			noteList.display();
		} catch (JAXBException e) {
				Response reponse=null;
				e.printStackTrace();
				jaxb1 = new JaxBinding(Response.class);
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
        return noteList.getNoteList();

	}

	
	
	public List<Note> listForEns(int idEns) throws IOException , WSException{
		
		Request request = new Request (API_URI) ;
		request.path("note/enseignant/"+idEns);
		request.setMethod(Request.Method.get);
		request.send();
		
		String responseBody = request.getResponseBody();
			
		NoteList noteList=null;
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(NoteList.class);
			jaxb1 = new JaxBinding(Response.class);
			noteList = jaxb.unmarshal(responseBody);
			noteList.display();
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
        return noteList.getNoteList();
	}

	public List<Note> listForEleve(int idEleve) throws JAXBException , IOException, WSException {

		Request request = new Request (API_URI) ;
		request.path("note/eleve/"+idEleve);
		request.setMethod(Request.Method.get);
		request.send();
		String responseBody = request.getResponseBody();
		NoteList noteList=null;
		
		JaxBinding jaxb=null;
		JaxBinding jaxb1=null;
		
		try {
			jaxb = new JaxBinding(NoteList.class);
			jaxb1 = new JaxBinding(Response.class);
			noteList = jaxb.unmarshal(responseBody);
			noteList.display();
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
        return noteList.getNoteList();

	}

	public List<Note> listByDev(String idDevoir) throws IOException , WSException, JAXBException{
		
		Request request = new Request (API_URI) ;
		request.path("note/devoir/"+idDevoir);
		request.setMethod(Request.Method.get);
		request.send();
		
		String responseBody = request.getResponseBody();
			
		NoteList noteList=null;
		JaxBinding jaxb=null;
		
		jaxb = new JaxBinding(NoteList.class);
		noteList = jaxb.unmarshal(responseBody);
		noteList.display();
		if(noteList!=null)
		{
			return noteList.getNoteList();		
		}
		return null;
		
	}

	
	public List<Note> listByDevs(List<Devoirs> devoirs) throws IOException , WSException, JAXBException{
			
		List<Note> notes=null;
		
		for(Devoirs dev : devoirs)
		{
			notes.addAll(listByDev(String.valueOf(dev.getId())));
		}
		
        return notes;
	}

	
	public void update(Note note) throws JAXBException, IOException, WSException {
		Request request = new Request (API_URI) ;
		request.path("note/"+note.getId());
		request.setParam("coef",String.valueOf(note.getCoef()));
		request.setParam("note", String.valueOf(note.getNote()));
		request.setParam("idEleve", String.valueOf(note.getIdEleve()));
		request.setParam("idDevoir", String.valueOf(note.getIdDevoirs()));
		
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
		request.path("note/"+id);
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

	public Note findByEleve(int idDevoir, int idEleve) throws JAXBException, IOException {
		Request request = new Request (API_URI) ;
		request.path("note/"+idDevoir+"/"+idEleve);
		request.setMethod(Request.Method.get);
		request.send();
		
		String responseBody = request.getResponseBody();
			
		Note note=null;
		JaxBinding jaxb=null;
		
		jaxb = new JaxBinding(Note.class);
		note = jaxb.unmarshal(responseBody);
		note.display();
		if(note!=null)
		{
			return note;		
		}
		return null;
	}
}
	