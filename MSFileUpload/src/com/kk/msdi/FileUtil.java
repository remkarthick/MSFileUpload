package com.kk.msdi;

import java.io.File;
import java.io.FileWriter;

public class FileUtil {

	public static void setStatus(String content) {
		try {
			FileWriter fw = new FileWriter(new File("status.txt"), true);
			fw.append(content + "\n");
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}