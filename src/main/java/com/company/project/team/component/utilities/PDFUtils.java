package com.company.project.team.component.utilities;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFUtils {

	public static String getPdfContent(String absoluteFilePath) throws Exception {
		File pdf = new File(absoluteFilePath);
		InputStream in=null;
		try {
			in = new FileInputStream(pdf);
		} catch (FileNotFoundException e) {
			throw new Exception("PDF file not found : " + absoluteFilePath);			
		}
		BufferedInputStream bf = new BufferedInputStream(in);
		PDDocument doc = null;
		try {
			doc = PDDocument.load(bf);
		} catch (IOException e) {
			try {
				in.close();
			} catch (IOException io) {
				throw new Exception("Failed to close InputStream for File : " + absoluteFilePath);
			}
			throw new Exception("Failed to load pdf file : " + absoluteFilePath);
		}
		
		String content = null;
		try {
			content = new PDFTextStripper().getText(doc);
		} catch (IOException e) {
			throw new Exception("Failed to read pdf file : " + absoluteFilePath);			
		}finally{
			try{
				doc.close();
				in.close();
			} catch (IOException e) {
				throw new Exception("Failed to close File : " + absoluteFilePath);
			}
		}		
		return content;
		
	}
}
