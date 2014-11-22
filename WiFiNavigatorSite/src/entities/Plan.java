package entities;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Plan implements Serializable{
	private String description;
	
	public Plan(String decription){
		this.description = decription;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
