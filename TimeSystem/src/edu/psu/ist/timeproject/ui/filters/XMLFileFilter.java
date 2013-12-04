package edu.psu.ist.timeproject.ui.filters;

import java.io.File;
import java.io.FilenameFilter;

public class XMLFileFilter implements FilenameFilter {

	@Override
	public boolean accept(File file, String name) {
		// TODO Auto-generated method stub
		if (new File(file.getAbsoluteFile() + File.separator + name).isDirectory()) return false; 
		return name.endsWith(".xml") || name.endsWith(".XML"); 
	}
}
