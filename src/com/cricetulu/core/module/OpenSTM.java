package com.cricetulu.core.module;

public class OpenSTM extends AST {

	private String io;
	private String fileName;
	
	public OpenSTM (AST pAst) {
		this.setAstName("OPEN");
		
		this.pAst = pAst;
		pAst.getAsts().add(this);
	}

	public String getIo() {
		return io;
	}

	public void setIo(String io) {
		this.io = io;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public AST getOpenAst() {
		return this;
	}
}
