<%@ page import="ru.rosbank.javaschool.web.constant.Constants" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Payment</title>
    <%@include file="bootstrap-css.jsp" %>
</head>
<body>
<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="/">Back</a></li>
    </ol>
</nav>

<div class="alert alert-success" role="alert">
    Order <%= request.getAttribute(Constants.ORDERID) %> successfully paid!
</div>

</body>
</html>