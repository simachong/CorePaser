package com.cricetulu.core.module;

public class OpenSTM extends AST {

	private String io;
	private String fileName;
	private AST openAst;
	
	public OpenSTM (AST pAst) {
		
		openAst = new AST();
		openAst.setAstName("OPEN");
		
		this.pAst = pAst;
		pAst.getAsts().add(openAst);
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
		return openAst;
	}

	public void setOpenAst(AST openAst) {
		this.openAst = openAst;
	}

}
