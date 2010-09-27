package pinsgatherer.helper;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XMLHelper {
	
	public static Document createNewDocument() {
		DocumentBuilder builder = null;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		Document document = builder.newDocument();

		Element element = document.createElement("pins");
		document.appendChild(element);
		
		return document;
	}
	
	public static void writeXmlFile(Document doc, String filename) {
		try {
			Source source = new DOMSource(doc);

			File file = new File(filename);
			Result result = new StreamResult(file);

			Transformer xformer = TransformerFactory.newInstance()
					.newTransformer();
			xformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	public static Document getDocuemet(File file) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document document = null;
		try {
			db = dbf.newDocumentBuilder();
			document = db.parse(file);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}
}
