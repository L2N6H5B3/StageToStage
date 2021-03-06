package stageonjava.util;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import stageonjava.model.DisplayLayouts;

/**
 * @author Luke Bradtke
 * @version 1.3
 * @since 1.0
 */

public class LayoutReader {
	
	// Create variable to hold DisplayLayouts object
	private DisplayLayouts layouts = null;
    
	// Create Objects from XML String
    public DisplayLayouts convertToObjects(String xmlString) {
    	
    	JAXBContext jaxbContext;
    	try {
    	    jaxbContext = JAXBContext.newInstance(DisplayLayouts.class);             
    	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    	    // Create Java Objects using JAXB
    	    layouts = (DisplayLayouts) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
    	} catch (JAXBException e) {
    	    System.out.println("Error creating Java objects from XML...");
    	    return layouts;
    	}
    	return layouts;
    }
}
