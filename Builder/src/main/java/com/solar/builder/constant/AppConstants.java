package com.solar.builder.constant;

import java.io.File;

public class AppConstants {

	private String home;
	private String homeForms;
	private String homePdfs;
	
	public AppConstants(String home) {
		this.home = home;
		this.homeForms = home + "templates/";
		this.homePdfs = home + "pdfs/";
		if(! new File(homeForms).exists()){
			new File(homeForms).mkdir();
		}
		if(! new File(homePdfs).exists()){
			new File(homePdfs).mkdir();
		}
	}

	public String getHome() {
		return home;
	}
	public String getHomeForms() {
		return homeForms;
	}
	public String getHomePdfs() {
		return homePdfs;
	}
}
