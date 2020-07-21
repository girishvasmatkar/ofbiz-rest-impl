package org.apache.ofbiz.ws.rs.bind;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class JaxbTest {

	public static void main(String[] args) throws JAXBException {
		// TODO Auto-generated method stub
		
		 File file = new File("sample.rest.xml");
	     JAXBContext jaxbContext = JAXBContext.newInstance(Api.class);
	     Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	     Api api = (Api) unmarshaller.unmarshal(file);
	     System.out.println("API: "+api);
	     System.out.println("Size: "+api.getResource().size());
	     
	     for(Resource rs : api.getResource()) {
	    	 System.out.println("Method: "+rs.getMethod().size());
	    	 System.out.println("Resource: "+rs.getResource().size());
	     }
	     

	}

}
