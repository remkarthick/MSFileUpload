package com.kk.msdi;


import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;


public class SNFileUpload {

	String USERNAME;
	String PASSWORD;
	String TARGET_URL;
	String TABLE_NAME;
	String TABLE_SYS_ID;
	
	
	
	public SNFileUpload(String USERNAME, String PASSWORD, String TARGET_URL,String TABLE_NAME, String TABLE_SYS_ID) {
		this.USERNAME = USERNAME;
		this.PASSWORD = PASSWORD;
		this.TARGET_URL = TARGET_URL;
		this.TABLE_NAME = TABLE_NAME;
		this.TABLE_SYS_ID = TABLE_SYS_ID;
		
	}

	public SNFileUploadResponse uploadFile(String fullFileName) {
		String BASICAUTH = "Basic " + Base64.getEncoder().encodeToString((USERNAME + ":" + PASSWORD).getBytes());
		String BOUNDARY = Long.toHexString(System.currentTimeMillis());
		SNFileUploadResponse snfresponse=null;
		try {

			Path filePath = Paths.get(fullFileName);
			String fileName = filePath.getFileName().toString();
			System.out.println(fileName);

			fileName = URLEncoder.encode(fileName, "UTF-8");

			String queryParams = "?" + "table_name=" + TABLE_NAME + "&table_sys_id=" + TABLE_SYS_ID + "&file_name="
					+ fileName;

			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.header("Content-Type", "multipart/form-data; boundary=" + BOUNDARY)
					.header("Authorization", BASICAUTH).uri(URI.create(TARGET_URL + queryParams))
					.POST(HttpRequest.BodyPublishers.ofFile(filePath)).build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			
			System.out.println("Status code: " + response.statusCode());
			System.out.println("\n Body: " + response.body());
			
			snfresponse=new SNFileUploadResponse(response.statusCode()+"", response.body().toString());
			
			if(response.statusCode()!=201)
			{
				LogUtil.writeToLog(response.body(),"SEVERE");			
			}
						
		} catch (Exception e) {
			LogUtil.writeToLog(e.toString(),"SEVERE");			
		}
		return snfresponse;

	}

}

class SNFileUploadResponse{
	String responseCode;
	String responseBody;
	public SNFileUploadResponse(String responseCode,String responseBody) {
		
		this.responseCode=responseCode;
		this.responseBody=responseBody;
		
	}
}