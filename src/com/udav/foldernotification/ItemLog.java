package com.udav.foldernotification;

class ItemLog {
	private String pathFolder;
	private String fileName;
	private String time;
	private boolean type; //true - add || false - delete
	
	public ItemLog(String pathFolder, String fileName, String time, boolean type){
		this.pathFolder = pathFolder;
		this.fileName = fileName;
		this.time = time;
		this.type = type;
	}
	
	public boolean getType() {
		return type;
	}
	
	public String getFullPath() {
		return pathFolder+"\\"+fileName;
	}
	
	public String getDescription() {
		String strType;
		if (type) strType = "add"; else strType = "delete";
		return getFullPath()+" "+time+" "+strType;
	}
}