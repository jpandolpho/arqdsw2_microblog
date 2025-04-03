<%@ page import="br.edu.ifsp.arqdsw2.microblog.model.Post"%>
<%@ page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Micro Blog</title>
</head>
<body>
	<h2>Postagens Recentes</h2>
	<a href="postagem.html">Criar nova postagem</a>
	<br>
	<br>
	<%
	List<Post> posts = (List<Post>) request.getAttribute("posts");
	if (posts != null) {
		for (Post post : posts) {
	%>
	<div
		style="border: 1px solid #ccc; padding: 10px; margin-bottom: 15px;">
		<p>
			<strong>Data:</strong>
			<%=post.getDataPostagem()%></p>
		<p><%=post.getDescricao()%></p>
		<%
		if (post.getUrlImagem() != null) {
		%>
		<img
			src="<%=request.getAttribute("urlBucket") + post.getUrlImagem()%>"
			alt="Imagem do post" style="max-width: 300px;"><br>
		<%
		}
		%>
	</div>
	<%
	}
	} else {
	%>
	<div
		style="border: 1px solid #ccc; padding: 10px; margin-bottom: 15px;">
		<p>Não existem posts publicados ainda.</p>
	</div>
	<%
}
%>
</body>
</html>