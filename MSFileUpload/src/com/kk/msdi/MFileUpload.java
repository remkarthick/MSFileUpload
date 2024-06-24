package com.kk.msdi;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class MFileUpload {
	
	public MFileUpload() {
		LogUtil.initializeLogFile();
	}

	public static void main(String[] args) {

		FileUtil.setStatus(DateUtil.getCurrentDateTime() + " : Program Started...");
		System.out.println(DateUtil.getCurrentDateTime() + " : Program Started...");
		
		AllProperties pu=PropertiesUtil.getProperties();
		
		String USERNAME = pu.USERNAME;
		String PASSWORD = pu.PASSWORD;
		String TARGET_URL = pu.TARGET_URL;
		String TABLE_NAME = pu.TABLE_NAME;
		String TABLE_SYS_ID = pu.TABLE_SYS_ID;
		String LOG_FILE=pu.LOG_FILE;
		String STATUS_LOG_FILE=pu.STATUS_LOG_FILE;
		String FOLDERS_TO_MONITOR=pu.FOLDERS_TO_MONITOR;
		
		Thread sdHook = new ShutdownHook();
		Runtime.getRuntime().addShutdownHook(sdHook);

		String[] filePaths = FOLDERS_TO_MONITOR.split(",");
		try {

			WatchService watchservice = FileSystems.getDefault().newWatchService();

			for (String filePath : filePaths) {
				Path path = Paths.get(filePath);
				path.register(watchservice, StandardWatchEventKinds.ENTRY_CREATE);
			}

			WatchKey key;
			while ((key = watchservice.take()) != null) {

				for (WatchEvent<?> event : key.pollEvents()) {

					Path dir = (Path) key.watchable();
					Path fullFilePath = dir.resolve(event.context().toString());
					String fullFileName = fullFilePath.toString();

					new SNFileUpload(USERNAME,PASSWORD,TARGET_URL,TABLE_NAME,TABLE_SYS_ID).uploadFile(fullFileName);

					/*System.out.println(
					"Event kind:" + event.kind() 
					+": File affected: " + event.context() 
					+ ": File Path: " + fullFilePath);
					*/
				}
				key.reset();
			}
			FileUtil.setStatus(DateUtil.getCurrentDateTime() + " : Program Terminated...");
			System.err.println(DateUtil.getCurrentDateTime() + " : Program Terminated...");
		} catch (Exception e) {

			FileUtil.setStatus(DateUtil.getCurrentDateTime() + " : Program Error...");
			System.err.println(DateUtil.getCurrentDateTime() + " : Program Error...");
			e.printStackTrace();
		}

	}

}

class ShutdownHook extends Thread {
	@Override
	public void run() {
		FileUtil.setStatus(DateUtil.getCurrentDateTime() + " : Program Terminated...");
		System.out.println(DateUtil.getCurrentDateTime() + " : Program Terminated...");

	}

}