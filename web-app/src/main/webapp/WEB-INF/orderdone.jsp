<%@ page import="ru.rosbank.javaschool.web.constant.Constants" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Done</title>
    <%@include file="bootstrap-css.jsp" %>
</head>
<body>
<div class="container">
<br>
        <a href="/">Back</a>
<br>
<br>


    Order <%= request.getAttribute(Constants.ORDERID) %> done

</div>
</body>
</html>