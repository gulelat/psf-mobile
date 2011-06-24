package projetosd.android.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import projetosd.android.domain.Option;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class XMLFormParser {

	private static final String XML_TYPE = "tipo";
	public static final String XML_ITENS = "itens";
	public static final String XML_ITEM = "item";
	public static final String XML_MULTIPLE_OPTION = "opcao_multipla";
	public static final String XML_SINGLE_OPTION = "opcao_unica";
	public static final String XML_FORM = "campo";
	private Document doc;
	
	public XMLFormParser(InputStream input) throws ParserConfigurationException, SAXException, IOException {
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    doc = db.parse(new InputSource(input));
	    doc.getDocumentElement().normalize();
	}
	
	public int getFormCount(){
		return doc.getElementsByTagName(XML_FORM).getLength();
	}
	/**
	 * 
	 * @param formId: Numero da pergunta no xml
	 * @param nodeName: Nome do dó que possui o atributo
	 * @param attributeName: Nome do atributo a ser buscado
	 * @param nodeIndex: Indice do nó a ser buscado no caso de haver mais de um nó.
	 * @return Valor do atributo do @nodeIndex-ésimo nó com nome @nodeName do form número @formId
	 */
	public String getAttributeFromNode(int formId,String nodeName,String attributeName,int nodeIndex){
		NodeList formList = doc.getElementsByTagName(XML_FORM);
	    Element formElement = (Element)formList.item(formId);  
	    if(formElement.getAttribute(attributeName)!=""&&XML_FORM.equals(nodeName)){
	    	return formElement.getAttribute(attributeName);
	    }
	    List<Node> nodes = searchNodes(formElement,nodeName);
	    if(nodes.size()<=nodeIndex){
	    	return null;
	    }
	    Element nodeWithAtributte = (Element) nodes.get(nodeIndex);
	    return nodeWithAtributte.getAttribute(attributeName);
	}
	
	private List searchNodes(Node father,String name){
		List<Node> results = new ArrayList<Node>();
		if(father.getNodeName().equals(name)){
			results.add(father);
			return results;
		}
		for (int i = 0; i < father.getChildNodes().getLength(); i++) {
			results.addAll(searchNodes((Node)father.getChildNodes().item(i), name));
		}
		return results;
	}
	

	public String getUniqueNodeValue(int formId, String name) {
		NodeList formList = doc.getElementsByTagName(XML_FORM);
	    Element formElement = (Element)formList.item(formId);   
		NodeList labelList = formElement.getElementsByTagName(name);
		if(labelList.getLength()==0)
			return null;
		labelList = ((Node)labelList.item(0)).getChildNodes();
		if(((Node)labelList.item(0))==null){
			return null;
		}
		return ((Node)labelList.item(0)).getNodeValue();
	}
	

    public List<Option> getAnswerOptions(int j) {
		NodeList formList = doc.getElementsByTagName(XML_FORM);
	    Element formNode = (Element)formList.item(j);
    	String tagName = "";
    	if(getAttributeFromNode(j,XML_FORM,XML_TYPE,0).equals("texto")){
    		return new ArrayList<Option>();
    	}
    	//Node answerNode = formNode.getElementsByTagName(tagName).item(0);
    	Node itens = ((Element)formNode).getElementsByTagName(XML_ITENS).item(0);
    	NodeList list = ((Element)itens).getElementsByTagName(XML_ITEM);
    	List<Option> options = new ArrayList<Option>();
   	 	for (int i = 0; i < list.getLength(); i++) {
   	 		Node nodeOption = list.item(i).getChildNodes().item(0);
   	 		Option option = new Option();
   	 		option.setId(Integer.parseInt(((Element)list.item(i)).getAttribute("ordem")));
   	 		option.setValue(((Element)list.item(i)).getAttribute("valor"));
   	 		option.setLabel(nodeOption.getNodeValue());
   	 		options.add(option);
   	 	}
   	 	return options;    	
	}
	public Class getAnswerComponent(int formId){
		NodeList formList = doc.getElementsByTagName(XML_FORM);
	    Element formElement = (Element)formList.item(formId);
    	if(getAttributeFromNode(formId,XML_FORM,XML_TYPE,0).equals(XML_SINGLE_OPTION)){
    		return RadioButton.class;
    	}else if(getAttributeFromNode(formId,XML_FORM,XML_TYPE,0).equals(XML_MULTIPLE_OPTION)){
    		return CheckBox.class;
    	}else{ //if(getUniqueNodeValue(formElement, "text_input")!=null){
    		return EditText.class;
    	}
	}
    
    public Boolean haveNodeName(Element formElement,String name){
   	 NodeList labelList = formElement.getElementsByTagName(name);
   	 if(labelList.getLength()==0)
   		 return false;
   	 return true;
   }
}
