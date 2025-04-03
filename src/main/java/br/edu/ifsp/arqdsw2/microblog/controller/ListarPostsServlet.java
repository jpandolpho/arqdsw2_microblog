package br.edu.ifsp.arqdsw2.microblog.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import br.edu.ifsp.arqdsw2.microblog.model.Post;
import br.edu.ifsp.arqdsw2.microblog.model.dao.PostDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import software.amazon.awssdk.regions.Region;

@WebServlet("/posts")
public class ListarPostsServlet extends HttpServlet {
	private final PostDAO postDAO = new PostDAO();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MicroBlog");
			Connection conn = ds.getConnection();
			List<Post> posts = postDAO.listarTodos(conn);
			String urlBucket = "https://" + "nome_unico_global_bucket" + ".s3." + Region.SA_EAST_1.toString()
					+ ".amazonaws.com/";
			request.setAttribute("posts", posts);
			request.setAttribute("urlBucket", urlBucket);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			conn.close();
		} catch (Exception e) {
			throw new ServletException("Erro ao listar posts", e);
		}
	}
}