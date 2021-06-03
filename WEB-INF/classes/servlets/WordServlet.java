package servlets;
import java.io.*;
import org.w3c.dom.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import models.*;

@WebServlet("/WordServlet")
public class WordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public WordServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get all parameters
		String lemma = request.getParameter("word");
		String pronunciation = request.getParameter("sound");
		String wordClass = request.getParameter("class");
		
		// Initialize attributes
		Dictionary dictionary = new Dictionary();
		String htmlDictionary = null;
		
		// Do appropriate action
		if (request.getParameter("display") != null || request.getParameter("dump") != null) {
			// Display all words
			dictionary.findAll();
		} else if (request.getParameter("select") != null) {
			// Display certain words
			dictionary.find(lemma, pronunciation, wordClass);
		} else if (request.getParameter("insert") != null) {
			// Insert word into dictionary
			dictionary.addWord(lemma, pronunciation, wordClass);
			dictionary.find(lemma, pronunciation, wordClass); // Display added word
		} else if (request.getParameter("update") != null) {
			// Update word
			dictionary.updateWord(lemma, pronunciation, wordClass);
			dictionary.find(lemma, pronunciation, wordClass); // Display updated word
		} else if (request.getParameter("delete") != null) {
			// Delete word
			dictionary.deleteWord(lemma, pronunciation, wordClass);
			dictionary.findAll(); // Show all other words
		}
		
		// Create XML and XSL sources
		Source xml = new DOMSource(generateXML(dictionary));
		Source xsl = new StreamSource(getServletContext().getResourceAsStream("Dictionary.xsl")); 
		
		if (request.getParameter("dump") != null) xml = new DOMSource(generateXML(dictionary, true)); // If by definition
		
		// Generate HTML table from XML
		try {
			// HTML output
			response.setContentType("text/html");
			StringWriter out = new StringWriter();
			Result html = new StreamResult(out);
			
			// XML to HTML converter
			Transformer transformer = TransformerFactory.newInstance().newTemplates(xsl).newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			// Convert
			transformer.transform(xml, html);
			
			// Set results
			htmlDictionary = out.getBuffer().toString();
		} catch (TransformerException | TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		}
					
		// Return to page
		request.getSession().setAttribute("dictionary", htmlDictionary);
		response.sendRedirect("Home.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private Document generateXML(Dictionary dictionary) {
		Document xml = null;
		
		try {
			xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			
			Element root = xml.createElement("dictionary"); // root element
			xml.appendChild(root);

			for (Word word : dictionary.getEntries()) {
				Element entry = xml.createElement("entry");
				root.appendChild(entry);
				
				Element entryLemma = xml.createElement("lemma");
				entry.appendChild(entryLemma);
				entryLemma.appendChild(xml.createTextNode(word.getLemma()));

				Element entryPronunciation = xml.createElement("pronunciation");
				entry.appendChild(entryPronunciation);
				entryPronunciation.appendChild(xml.createTextNode(word.getSound()));

				Element entryClass = xml.createElement("wordclass");
				entry.appendChild(entryClass);
				entryClass.appendChild(xml.createTextNode(word.getWordClass()));
				
				Element entryDefinitions = xml.createElement("definitions");
				entry.appendChild(entryDefinitions);
				for (Definition definition : word.getSenses()) {
					Text sense;
					if (definition.getNotes() != null && definition.getNotes().isBlank()) {
						sense = xml.createTextNode("(" + definition.getNotes() + ") "
								+ definition.getSense() + "; ");
					} else {
						sense = xml.createTextNode(definition.getSense() + "; ");
					}
					entryDefinitions.appendChild(sense);
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
				
		return xml;
	}
	
	private Document generateXML(Dictionary dictionary, boolean byDefinitions) {
		Document xml = null;
		
		if (byDefinitions = false) {
			return generateXML(dictionary);
		}
		
		try {
			xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			
			Element root = xml.createElement("dictionary"); // root element
			xml.appendChild(root);

			for (Word word : dictionary.getEntries()) {
				for (Definition definition : word.getSenses()) {
					Element entry = xml.createElement("entry");
					root.appendChild(entry);
				
					Element entryLemma = xml.createElement("lemma");
					entry.appendChild(entryLemma);
					entryLemma.appendChild(xml.createTextNode(word.getLemma()));

					Element entryPronunciation = xml.createElement("pronunciation");
					entry.appendChild(entryPronunciation);
					entryPronunciation.appendChild(xml.createTextNode(word.getSound()));

					Element entryClass = xml.createElement("wordclass");
					entry.appendChild(entryClass);
					entryClass.appendChild(xml.createTextNode(word.getWordClass()));
				
					Element entryDefinitions = xml.createElement("definitions");
					entry.appendChild(entryDefinitions);
					
					Text sense;
					if (definition.getNotes() != null && definition.getNotes().isBlank()) {
						sense = xml.createTextNode("(" + definition.getNotes() + ") " + definition.getSense());
					} else {
						sense = xml.createTextNode(definition.getSense());
					}
					
					entryDefinitions.appendChild(sense);
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
				
		return xml;
	}
}
