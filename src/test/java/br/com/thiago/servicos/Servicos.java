package br.com.thiago.servicos;

public enum Servicos {
	PATH_USERS_ID ("/api/users/{id}"),
	PATH_USERS_PAGE ("/api/users");
	
	private final String valor;
	
	Servicos(String valor){
		this.valor = valor;
	}
	
	public String getValor() {
		return valor;
	}
}
