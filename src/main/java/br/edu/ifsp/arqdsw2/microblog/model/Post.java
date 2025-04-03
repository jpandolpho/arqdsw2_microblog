package br.edu.ifsp.arqdsw2.microblog.model;

import java.time.LocalDateTime;

public class Post {
	private int id;
	private String descricao;
	private String urlImagem;
	private LocalDateTime dataPostagem;

	public Post(int id, String descricao, String urlImagem, LocalDateTime dataPostagem) {
		this.id = id;
		this.descricao = descricao;
		this.urlImagem = urlImagem;
		this.dataPostagem = dataPostagem;
	}

	public Post(String descricao, String urlImagem) {
		this.id = -1;
		this.descricao = descricao;
		this.urlImagem = urlImagem;
		this.dataPostagem = LocalDateTime.now();
	}
//getters e setters

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}
	
}