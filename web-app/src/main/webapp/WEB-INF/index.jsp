<%@ page import="ru.rosbank.javaschool.web.constant.Constants" %>
<%@ page import="ru.rosbank.javaschool.web.dto.ProductDto" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.rosbank.javaschool.web.dto.DrinkDto" %>
<%@ page import="ru.rosbank.javaschool.web.dto.SandwichDto" %>
<%@ page import="ru.rosbank.javaschool.web.dto.FriesDto" %>
<%@ page import="ru.rosbank.javaschool.web.model.OrderPositionModel" %>
<%@ page import="ru.rosbank.javaschool.web.service.UserMcDonaldsService" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Menu</title>
    <%@include file="bootstrap-css.jsp" %>
</head>
<body>

<% int orderSize = ((List<OrderPositionModel>) request.getAttribute(Constants.ORDERITEMS)).size(); %>


<button class="btn btn-success" href="<%= request.getContextPath()%>/order" type="button">  Order <%=orderSize%></button>


<div class="row">
    <% for (ProductDto item : (List<ProductDto>) request.getAttribute(Constants.DTOPRODUCTS)) { %>
    <div class="card" style="width: 18rem;">
        <img src="<%= item.getImageUrl() %>" class="card-img-top" alt="<%= item.getCategory() %>">
        <div class="card-body">
            <h5 class="card-title">
                <%
                    StringBuilder sb = new StringBuilder();
                    sb.append(Constants.CATEGORY + "=" + item.getCategory() + "&");
                    sb.append(Constants.ID + "=" + item.getId());
                %>
                <a href="<%= request.getContextPath() %>/detail-information?<%= sb %>"><%= item.getName() %></a>
            </h5>
            <ul class="list-group list-group-flush">
                <li class="list-group-item">Price: <%= item.getPriceRub() %> rub.</li>
            <% if (item.getCategory().equals(Constants.FRENCHFRIES)) { %>
                <li class="list-group-item">Size: <%= ((FriesDto) item).getSize() %></li>
            <% } %>
            <% if (item.getCategory().equals(Constants.DRINKS)) { %>
                <li class="list-group-item">Volume: <%= ((DrinkDto) item).getVolumeMl() %> ml.</li>
            <% } %>
            <form action="<%= request.getContextPath() %>" method="post">
                <input name="id" type="hidden" value="<%= item.getId() %>">
                <input name="category" type="hidden" value="<%= item.getCategory() %>">
                <div class="form group" hidden>
                    <li class="list-group-item">
                    <label for="quantity">Quantity: </label>
                    <input type="number" min="0" id="quantity" name="quantity" value="1">
                    </li>
                </div>
                <button class="btn btn-primary">Add to order</button>
            </form>
            </ul>
        </div>
    </div>
    <% } %>
</div>

</body>
</html>