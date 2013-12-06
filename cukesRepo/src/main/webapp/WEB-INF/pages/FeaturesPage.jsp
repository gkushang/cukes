<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<title>Cukes Repo</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
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


.number-of-scenarios {
	color: darkblue;
	text-align: left;
	font-family: Open Sans;
	font-size: 16px;
}

</style>

<script>
function email()
{

    $.ajax({
        url: "/service/email",
        context: "something"
        }).done(function(data) {
            alert(data);
    });

}
</script>




<body>

    <h1 class="header-cukes" id="header">Cukes Repo</h1>

	<h2 class="features-title-1">Features</h2>

        <c:forEach var="feature" items="${features}">
            <a href=${feature.id.concat("/")}>${feature.name} </a>
            <span class="cell">${feature.numberOfScenarios}</span>
            <br>
        </c:forEach>

</body>

<body>
    <h2 class="number-of-scenarios">Total Scenarios: ${totalScenarios}</h2>

      <input type="button" name="email" value="submit" onClick="email()"></input>

       <input type="button" name="email" value="Approve" onClick="email()"></input>



</body>
</html>