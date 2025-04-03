package br.edu.ifsp.arqdsw2.microblog.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

@WebServlet("/create")
public class CreateBucketServlet extends HttpServlet {
	private S3Client s3Client;

	@Override
	public void init() {
		s3Client = S3Client.builder().region(Region.SA_EAST_1).credentialsProvider(DefaultCredentialsProvider.create())
				.build();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String bucketName = "joao-andolpho-bucket-1265212";
		CreateBucketRequest req;
		req = CreateBucketRequest.builder().bucket(bucketName).build();
		s3Client.createBucket(req);
		PrintWriter out = response.getWriter();
		out.println("Bucker " + bucketName + " criado com sucesso!");
	}
}
