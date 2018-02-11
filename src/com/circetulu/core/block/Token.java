package com.circetulu.core.block;

public class Token {

	private String tokenName;
	private int tokenID;
	private boolean isKeyword;
	
	public String getTokenName() {
		return tokenName;
	}
	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}
	public int getTokenID() {
		return tokenID;
	}
	public void setTokenID(int tokenID) {
		this.tokenID = tokenID;
	}
	public boolean isKeyword() {
		return isKeyword;
	}
	public void setKeyword(boolean isKeyword) {
		this.isKeyword = isKeyword;
	}
	
	
}
