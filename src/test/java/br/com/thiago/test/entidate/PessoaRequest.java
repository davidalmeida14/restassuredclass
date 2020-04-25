package br.com.thiago.test.entidate;

public class PessoaRequest {

	private String name;
	private String job;
	
	public PessoaRequest(String name, String job) {
		super();
		this.name = name;
		this.job = job;
	}
	public PessoaRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String nome) {
		this.name = nome;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	
	
}
