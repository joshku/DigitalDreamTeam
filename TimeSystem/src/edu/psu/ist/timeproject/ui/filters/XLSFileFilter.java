package edu.psu.ist.timeproject.ui.filters;

import java.io.File;
import java.io.FilenameFilter;


public class XLSFileFilter implements FilenameFilter {

	@Override
	public boolean accept(File file, String name) {
		if (new File(file.getAbsoluteFile() + File.separator + name).isDirectory()) return false; 
		return name.endsWith(".xls") || name.endsWith(".XLS"); 
	}
}