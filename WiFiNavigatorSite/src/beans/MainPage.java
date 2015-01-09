package beans;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;


@ManagedBean
public class MainPage {
	
	private static final int DEFAULT_BUFFER_SIZE = 10240;  
    private String filePath = "D:\\downloads\\Navigator.apk"; //временная мера дожно
	
	public void downloadProgram(ActionEvent actionEvent) throws IOException{
		downLoad();
	}
	
	public void downLoad() throws IOException {  
		           FacesContext context = FacesContext.getCurrentInstance();  
		           HttpServletResponse response = (HttpServletResponse) context  
		                      .getExternalContext().getResponse();  
		            File file = new File(filePath);  
		            if (!file.exists()) {  
		                 response.sendError(HttpServletResponse.SC_NOT_FOUND);  
		                 return;  
		            }  
		            response.reset();  
		            response.setBufferSize(DEFAULT_BUFFER_SIZE);  
		            response.setContentType("application/octet-stream");  
		            response.setHeader("Content-Length", String.valueOf(file.length()));  
		            response.setHeader("Content-Disposition", "attachment;filename=\""  
		                      + file.getName() + "\"");  
		            BufferedInputStream input = null;  
		            BufferedOutputStream output = null;  
		            try {  
		                 input = new BufferedInputStream(new FileInputStream(file),  
		                           DEFAULT_BUFFER_SIZE);  
		                 output = new BufferedOutputStream(response.getOutputStream(),  
		                           DEFAULT_BUFFER_SIZE);  
		                 byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];  
		                 int length;  
		                 while ((length = input.read(buffer)) > 0) {  
		                      output.write(buffer, 0, length);  
		                 }  
		            } finally {  
		                 input.close();  
		                 output.close();  
		            }  
		            context.responseComplete();  
		       }
	
}
