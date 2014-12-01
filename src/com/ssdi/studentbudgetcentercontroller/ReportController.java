package com.ssdi.studentbudgetcentercontroller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ssdi.studentbudgetcenterTDGateway.TransactionTableDataGateway;
import com.ssdi.studentbudgetcenterentity.CompareObject;
import com.ssdi.studentbudgetcenterentity.Transaction;

public class ReportController {
	
	public String generateBudgetReport(String username){
		try{
		ArrayList<Transaction> tran=new ArrayList<Transaction>();
		TransactionTableDataGateway ttdg=new TransactionTableDataGateway();
		tran=ttdg.getTransaction(username);
		writeXmlFileBudgetReport(tran,"budgetReport");
		return "SUCCESS";
		}
		catch(Exception ex){
			return "ERROR";
		}
		
		}
	public String compareBudgetReport(String username){
		try{
		ArrayList<CompareObject> tranOwn=new ArrayList<CompareObject>();
		ArrayList<CompareObject> tranOthers=new ArrayList<CompareObject>();
		TransactionTableDataGateway ttdg=new TransactionTableDataGateway();
		tranOwn=ttdg.getOwnTotal(username);
		tranOthers=ttdg.getOthersTotal(username);
		writeXmlFileCompReport(tranOwn,"OwnData");
		writeXmlFileCompReport(tranOthers,"OthersData");
		return "SUCCESS";
		}
		catch(Exception ex){
			return "ERROR";
		}
		
		}
		
	
	
	public static void writeXmlFileBudgetReport(ArrayList<Transaction> list, String filename) {

	    try {

	        DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
	        DocumentBuilder build = dFact.newDocumentBuilder();
	        Document doc = build.newDocument();

	        Element root = doc.createElement("BudgetDetails");
	        doc.appendChild(root);

	        


	        for (Transaction dtl : list) {
	        	Element Details = doc.createElement("Expense");
		        root.appendChild(Details);
	            Element item = doc.createElement("Item");
	            item.appendChild(doc.createTextNode(String.valueOf(dtl.getTransactionDesc())));
	            Details.appendChild(item);

	            Element cat = doc.createElement("Category");
	            cat.appendChild(doc.createTextNode(String.valueOf(dtl.getBudgetCategory())));
	            Details.appendChild(cat);

	            Element dte = doc.createElement("Date");
	            dte.appendChild(doc.createTextNode(String.valueOf(dtl.getTransactionDate())));
	            Details.appendChild(dte);
	            
	            Element amt = doc.createElement("Amount");
	            amt.appendChild(doc.createTextNode(String.valueOf(dtl.getTransactionAmt())));
	            Details.appendChild(amt);

	        }

	        // Save the document to the disk file
	        TransformerFactory tranFactory = TransformerFactory.newInstance();
	        Transformer aTransformer = tranFactory.newTransformer();

	        // format the XML nicely
	        aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

	        aTransformer.setOutputProperty(
	                "{http://xml.apache.org/xslt}indent-amount", "4");
	        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

	        DOMSource source = new DOMSource(doc);
	        try {
	            // location and name of XML file you can change as per need
	            FileWriter fos = new FileWriter("./"+filename+".xml");
	            StreamResult result = new StreamResult(fos);
	            aTransformer.transform(source, result);

	        } catch (IOException e) {

	            e.printStackTrace();
	        }

	    } catch (TransformerException ex) {
	        System.out.println("Error outputting document");

	    } catch (ParserConfigurationException ex) {
	        System.out.println("Error building document");
	    }
	}
	public static void writeXmlFileCompReport(ArrayList<CompareObject> list, String filename) {

	    try {

	        DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
	        DocumentBuilder build = dFact.newDocumentBuilder();
	        Document doc = build.newDocument();

	        Element root = doc.createElement("BudgetDetails");
	        doc.appendChild(root);

	        


	        for (CompareObject dtl : list) {
	        	Element Details = doc.createElement("Expense");
		        root.appendChild(Details);
	               Element cat = doc.createElement("Category");
	            cat.appendChild(doc.createTextNode(String.valueOf(dtl.getBudgetCategory())));
	            Details.appendChild(cat);

	            	            
	            Element amt = doc.createElement("Amount");
	            amt.appendChild(doc.createTextNode(String.valueOf(dtl.getAmount())));
	            Details.appendChild(amt);

	        }

	        // Save the document to the disk file
	        TransformerFactory tranFactory = TransformerFactory.newInstance();
	        Transformer aTransformer = tranFactory.newTransformer();

	        // format the XML nicely
	        aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

	        aTransformer.setOutputProperty(
	                "{http://xml.apache.org/xslt}indent-amount", "4");
	        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

	        DOMSource source = new DOMSource(doc);
	        try {
	            // location and name of XML file you can change as per need
	            FileWriter fos = new FileWriter("./"+filename+".xml");
	            StreamResult result = new StreamResult(fos);
	            aTransformer.transform(source, result);

	        } catch (IOException e) {

	            e.printStackTrace();
	        }

	    } catch (TransformerException ex) {
	        System.out.println("Error outputting document");

	    } catch (ParserConfigurationException ex) {
	        System.out.println("Error building document");
	    }
	}	
public static void main(String[] args){
	ReportController rc = new ReportController();
	rc.compareBudgetReport("Soumita3");
}
}
