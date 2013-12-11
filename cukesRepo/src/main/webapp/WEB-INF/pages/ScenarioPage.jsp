<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	font-size: 20px;
}

.scenario-title {
	color: darkgreen;
	text-align: left;
	font-weight:bold;
	font-size: 15px;
}
.scenario-approve {
	color: darkorange;
	text-align: left;
	font-weight:bold;
	font-size: 14px;
}

.scenario-name {
	color: darkgreen;
	text-align: left;
	font-weight:bold;
	font-size: 15px;
}
.step-keyword {
	color: blue;
	text-align: left;
	font-size: 14px;
}

.step-name {
	color: black;
	text-align: left;
	font-size: 14px;
}

.example-keyword {
	color: black;
	font-weight:bold;
	text-align: left;
	font-size: 14px;
}

.example-description {
	color: black;
	text-align: left;
	font-size: 14px;
}

.example-name {
	color: black;
	text-align: left;
	font-size: 14px;
}

.row-description {
	color: black;
	text-align: left;
	font-size: 14px;
}

.row-cell {
	color: black;
	text-align: left;
	font-size: 14px;
}

.comment-heading {
	color: black;
	text-align: left;
	font-size: 14px;
	font-weight:bold;
}

.comment-data {
	color: black;
	text-align: left;
	font-size: 14px;
    font-weight:italic;
}

</style>

<body>

        <h1 class="header-cukes" id="header">Cukes Repo</h1>





        <h2 class="features-title-1">Feature: ${feature.name}</h2>

        <c:forEach var="scenario" items="${scenarios}">
            <span class="scenario-approve">Approved: ${scenario.approved}</span>   <br>
            <span class="scenario-title">${scenario.keyword}</span>
            <span class="scenario-title">: </span>
            <span class="scenario-name"><c:out value = "${scenario.name}" /></span> <br>



            <br>
            <c:forEach var="step" items="${scenario.steps}">
                <span class="step-keyword">${step.keyword}</span>
                <span class="step-name"><c:out value="${step.name}" /></span> <br>
                <c:forEach var="row" items="${step.rows}">
                    <c:forEach var="cell" items="${row.cells}">
                        <span class="step-name">| ${cell} |</span> <br>
                    </c:forEach>
                </c:forEach>

            </c:forEach>



                <c:forEach var="example" items="${scenario.examples}">
                <br>
                    <span class="example-keyword">${example.keyword}</span>
                    <span class="example-keyword">: </span>
                    <span class="example-name">${example.name}</span>
                    <span class="example-description">${example.description}</span>
                    <table>
                        <c:forEach var="row" items="${example.rows}">
                            <tr class="row-description">${example.description}</span>
                                <c:forEach var="cell" items="${row.cells}">
                                    <td class="row-cell">${cell}</span>
                                </c:forEach>
                        </c:forEach>
                     </table>


                </c:forEach>





                <c:forEach var="comment" items="${scenario.comments}">
                    <br> <span class="comment-heading">Comment:</span>
                    <span class="comment-data">"${comment}"</span> <br>
                </c:forEach>
              <FORM ACTION="${scenario.number}/comments" METHOD="POST">
                     <br>
                     <input type="TEXT" style="width:1000px;height:30px;" name=text${scenario.number} value=${scenario.number}>
                     <input type="SUBMIT" value="Add Comment">

                <br><br><br><br>
        </c:forEach>

</body>
</html>