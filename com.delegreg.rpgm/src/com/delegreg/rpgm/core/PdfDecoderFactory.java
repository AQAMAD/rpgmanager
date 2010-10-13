package com.delegreg.rpgm.core;

import java.util.HashMap;

import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;

public class PdfDecoderFactory {
	
	final static HashMap<String, PdfDecoder> instances=new HashMap<String, PdfDecoder>();
	
	public static PdfDecoder getInstance(String filename) throws PdfException{
		if (instances.containsKey(filename)){
			return instances.get(filename);
		}
		else
		{
			PdfDecoder dec=new PdfDecoder(true);
			dec.init(true);
			dec.setExtractionMode(PdfDecoder.TEXT);
			dec.openPdfFile(filename);
			PdfDecoder.setFontReplacements(dec);
			instances.put(filename, dec);
			return dec;
		}
	}
	
	
	
}
