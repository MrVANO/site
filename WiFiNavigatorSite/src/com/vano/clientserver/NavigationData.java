package com.vano.clientserver;

import java.io.Serializable;
import java.util.ArrayList;


public class NavigationData implements Serializable{
	private ArrayList<Integer[]> coordinats;
	private ArrayList<ArrayList<Integer>> signals;
	private ArrayList<String> SSID;
	private String tableName;
	private byte[] placeMap;
	
	public byte[] getPlaceMap() {
		return placeMap;
	}
	public void setPlaceMap(byte[] placeMap) {
		this.placeMap = placeMap;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public ArrayList<Integer[]> getCoordinats() {
		return coordinats;
	}
	public void setCoordinats(ArrayList<Integer[]> coordinats) {
		this.coordinats = coordinats;
	}
	public ArrayList<ArrayList<Integer>> getSignals() {
		return signals;
	}
	public void setSignals(ArrayList<ArrayList<Integer>> signals) {
		this.signals = signals;
	}
	public ArrayList<String> getSSID() {
		return SSID;
	}
	public void setSSID(ArrayList<String> sSID) {
		SSID = sSID;
	}
	
	
}
