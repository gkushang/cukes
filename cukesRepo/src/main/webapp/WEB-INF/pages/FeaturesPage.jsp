<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<title>Cukes Repo</title>

<style>
body {
	background-color: #F6F7F8;
}

.header-cukes {
	color: gray;
	text-align: left;
	font-family: impact;
	font-size: 40px;
}

.home-feature-file-names {
	color: indigo;
	text-align: left;
	font-family: Open Sans;
	font-size: 14px;
}

.features-title-1 {
	color: darkblue;
	text-align: left;
	font-family: Open Sans;
	font-size: 24px;
}

</style>

<body>

    <h1 class="header-cukes" id="header">Cukes Repo</h1>

	<h2 class="features-title-1">Features</h2>

    <c:forEach var="feature" items="${features}">
       <a href=${feature.id.concat("/")}>${feature.name}</a>
       <span>${fn:length(feature.scenarios)}</span>  <br>
    </c:forEach>

</body>
</html>