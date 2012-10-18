package com.udav.foldernotification;

class ItemLog {
	private String pathFolder;
	private String fileName;
	private String time;
	private boolean typeOperation; //true - add || false - delete
	private boolean folder;
	
	public ItemLog(String pathFolder, String fileName, String time, boolean typeOperation, boolean folder){
		this.pathFolder = pathFolder;
		this.fileName = fileName;
		this.time = time;
		this.typeOperation = typeOperation;
		this.folder = folder;
	}
	
	public boolean getType() {
		return typeOperation;
	}
	
	public boolean isFolder() {
		return folder;
	}
	
	public String getFullPath() {
		return pathFolder+"/"+fileName;
	}
	
	public String getDescription() {
		String strType;
		if (typeOperation) strType = "add"; else strType = "delete";
		return getFullPath()+" "+time+" "+strType;
	}
}