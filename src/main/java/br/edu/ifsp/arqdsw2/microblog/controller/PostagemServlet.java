package br.edu.ifsp.arqdsw2.microblog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import br.edu.ifsp.arqdsw2.microblog.model.Post;
import br.edu.ifsp.arqdsw2.microblog.model.dao.PostDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@WebServlet("/postar")
@MultipartConfig
public class PostagemServlet extends HttpServlet {
	private S3Client s3;
	private final PostDAO postDAO = new PostDAO();

	@Override
	public void init() {
		s3 = S3Client.builder().region(Region.SA_EAST_1).credentialsProvider(DefaultCredentialsProvider.create())
				.build();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/postagem.html");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Part imagemPart = request.getPart("imagem");
		String descricao = request.getParameter("descricao");
		String nomeArquivo = UUID.randomUUID() + "-" + imagemPart.getSubmittedFileName();
		try (InputStream inputStream = imagemPart.getInputStream()) {
			PutObjectRequest objectRequest = PutObjectRequest.builder().bucket("nome_unico_global_bucket")
					.key(nomeArquivo).contentType(imagemPart.getContentType()).build();
			s3.putObject(objectRequest,
					software.amazon.awssdk.core.sync.RequestBody.fromInputStream(inputStream, imagemPart.getSize()));
		} catch (S3Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Erro ao fazer upload: " + e.awsErrorDetails().errorMessage());
		}
		Post post = new Post(descricao, nomeArquivo);
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MicroBlog");
			Connection conn = ds.getConnection();
			postDAO.salvar(conn, post);
			conn.close();
			response.sendRedirect("posts");
		} catch (SQLException | NamingException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Erro ao salvar a postagem no banco: " + e.getMessage());
		}
	}
}