<%@ page import="ru.rosbank.javaschool.web.constant.Constants" %>
<%@ page import="ru.rosbank.javaschool.web.model.DrinkModel" %>
<%@ page import="ru.rosbank.javaschool.web.model.FriesModel" %>
<%@ page import="ru.rosbank.javaschool.web.model.ProductModel" %>
<%@ page import="ru.rosbank.javaschool.web.model.SandwichModel" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>About product</title>
    <%@include file="bootstrap-css.jsp" %>
</head>
<body>

<% ProductModel model = (ProductModel) request.getAttribute(Constants.PRODUCT); %>

<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="/">Back</a></li>
    </ol>
</nav>

<div align="center">
    <div class="card mb-3" style="max-width: 540px;">
        <div class="row no-gutters">
            <div class="col-md-4">
                <img src="<%= model.getImageUrl() %>" class="card-img" alt="<%= model.getCategory() %>">
            </div>
            <div class="col-md-8">
                <div class="card-body">
                    <h5 class="card-title">
                        <%= model.getName() %>
                    </h5>
                    <p class="card-text"><%= model.getDescription() %></p>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">Category: <%= model.getCategory() %></li>
                    <li class="list-group-item">Price: <%= model.getPriceRub() %> rub.</li>
                    <% if (model.getCategory().equals(Constants.FRENCHFRIES)) { %>
                    <li class="list-group-item">Size: <%= ((FriesModel) model).getSize() %></li>
                    <% } %>
                    <% if (model.getCategory().equals(Constants.DRINKS)) { %>
                    <li class="list-group-item">Volume: <%= ((DrinkModel) model).getVolumeMl() %> ml.</li>
                    <% } %>
                </ul>
            </div>
        </div>
    </div>
</div>

</body>
</html>