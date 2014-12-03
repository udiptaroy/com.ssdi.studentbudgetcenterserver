package com.ssdi.studentbudgetcentercontroller;

import java.io.File;
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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xml.DatasetReader;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ssdi.studentbudgetcenterTDGateway.TransactionTableDataGateway;
import com.ssdi.studentbudgetcenterentity.CompareObject;
import com.ssdi.studentbudgetcenterentity.Transaction;

public class ReportController {
	
	public String generateBudgetReport(String username){
		try{
		ArrayList<CompareObject> pie1=new ArrayList<CompareObject>();
		ArrayList<CompareObject> pie2=new ArrayList<CompareObject>();
		ArrayList<Transaction> tran=new ArrayList<Transaction>();
		TransactionTableDataGateway ttdg=new TransactionTableDataGateway();
		tran=ttdg.getTransaction(username);
		pie1=ttdg.getOwnTotal(username);
		pie2=ttdg.getOthersTotal(username);
		writeXmlFileBudgetReport(tran,"BudgetReport");
		generatePieChart(pie1,"PieOwn","PieOwn");
		generatePieChart(pie2,"PieOther","PieOther");
		
		
		
		
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
		//writeXmlFileCompReport(tranOwn,"OwnData");
		//writeXmlFileCompReport(tranOthers,"OthersData");
		generatePieChart(tranOwn,"PieOwn","OwnExpense");
		generatePieChart(tranOthers,"PieOther","Others Expense");
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

	        

	        	int i=1;
	        for (Transaction dtl : list) {
	        	Element Details = doc.createElement("Expense");
		        root.appendChild(Details);
		        Attr attribute = doc.createAttribute("id");  
	        	   attribute.setValue(""+i);  
	        	   Details.setAttributeNode(attribute);
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
i++;
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
	            FileWriter fos = new FileWriter("C:/Users/udiptaroy/Desktop/StudentBudgetCenterWorkspace/StudentBudgetCenterWorkspace/com.ssdi.studentbudgetcenterwebclient/WebContent/resources/BudgetReport.xml");
	            StreamResult result = new StreamResult(fos);
	            aTransformer.transform(source, result);
	            fos.close();
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

	        Element root = doc.createElement("PieDataSet");
	        doc.appendChild(root);
	      
	      int i=1;
	             for (CompareObject dtl : list) {
	        	Element Details = doc.createElement("item");
	        	root.appendChild(Details);
	        	  Attr attribute = doc.createAttribute("id");  
	        	   attribute.setValue(""+i);  
	        	   Details.setAttributeNode(attribute);
	               Element cat = doc.createElement("Key");
	            cat.appendChild(doc.createTextNode(String.valueOf(dtl.getBudgetCategory())));
	            Details.appendChild(cat);

	            	            
	            Element amt = doc.createElement("Value");
	            amt.appendChild(doc.createTextNode(String.valueOf(dtl.getAmount())));
	            Details.appendChild(amt);
	            i++;
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
	            FileWriter fos = new FileWriter("../com.ssdi.studentbudgetcenterwebclient/WebContent/resources/"+filename+".xml");
	            StreamResult result = new StreamResult(fos);
	            aTransformer.transform(source, result);
	            fos.close();
	        } catch (IOException e) {

	            e.printStackTrace();
	        }

	    } catch (TransformerException ex) {
	        System.out.println("Error outputting document");

	    } catch (ParserConfigurationException ex) {
	        System.out.println("Error building document");
	    }
	}	
	 public static void generatePieChart(ArrayList<CompareObject> co,String filename,String caption) throws Exception {
		 try{
	        DefaultPieDataset dataSet = new DefaultPieDataset();
	        for(CompareObject c:co){
	        dataSet.setValue(c.getBudgetCategory(), c.getAmount());
	        }
	 
	        JFreeChart chart = ChartFactory.createPieChart(
	                caption, dataSet, true, true, false);
	        int width=640; 
            int height=480;   
            float quality=1; /* Quality factor */
	        
	       // File BarChart=new File("../com.ssdi.studentbudgetcenterwebclient/WebContent/resources/"+filename+".png"); 
	        File BarChart=new File("C:/Users/udiptaroy/Desktop/StudentBudgetCenterWorkspace/StudentBudgetCenterWorkspace/com.ssdi.studentbudgetcenterwebclient/WebContent/resources/"+filename+".png");
	        ChartUtilities.saveChartAsJPEG(BarChart, quality, chart,width,height);
		 }
		 catch(Exception ex){
			 ex.printStackTrace();
		 }
	        
	    }
	 
public static void main(String[] args){
	ReportController rc = new ReportController();
	rc.compareBudgetReport("Soumita2");
}
}
