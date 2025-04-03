package br.edu.ifsp.arqdsw2.microblog.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.arqdsw2.microblog.model.Post;

public class PostDAO {
	public void salvar(Connection conn, Post post) throws SQLException {
		String sql = "INSERT INTO post (descricao, url_imagem) VALUES (?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, post.getDescricao());
			stmt.setString(2, post.getUrlImagem());
			stmt.executeUpdate();
		}
	}

	public List<Post> listarTodos(Connection conn) throws SQLException {
		List<Post> posts = new ArrayList<>();
		String sql = "SELECT id, descricao, url_imagem, data_postagem " + "FROM post ORDER BY data_postagem DESC";
		try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Post post = new Post(rs.getInt("id"), rs.getString("descricao"), rs.getString("url_imagem"),
						rs.getTimestamp("data_postagem").toLocalDateTime());
				posts.add(post);
			}
		}
		return posts;
	}
}
