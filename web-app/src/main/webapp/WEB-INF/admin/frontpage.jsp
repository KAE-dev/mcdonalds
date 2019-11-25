<%@ page import="ru.rosbank.javaschool.web.constant.Constants" %>
<%@ page import="ru.rosbank.javaschool.web.dto.ProductDto" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.rosbank.javaschool.web.dto.DrinkDto" %>
<%@ page import="ru.rosbank.javaschool.web.dto.SandwichDto" %>
<%@ page import="ru.rosbank.javaschool.web.dto.FriesDto" %>
<%@ page import="ru.rosbank.javaschool.web.model.*" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Dashboard</title>
    <%@include file="../bootstrap-css.jsp" %>
</head>
<body>


<li><a href="/">Back</a></li>


<% if (request.getAttribute(Constants.PRODUCT) == null) { %>
<div class="row">
    <div class="card" style="width: 18rem;">
        <div class="card-body">
            <form action="<%= request.getContextPath() %>/admin" method="post">
                <input name="id" type="hidden" value="0">
                <%String newProduct = request.getAttribute(Constants.NEWPRODUCT).toString();%>
                <input name="category" type="hidden" value="<%= newProduct %>">
                <div class="form group">
                    <label for="name">Name</label>
                    <input type="text" id="name" name="name" value="">
                </div>
                <div class="form group">
                    <label for="priceRub">Price (RUB)</label>
                    <input type="number" min="0" id="priceRub" name="priceRub" value="">
                </div>
                <div class="form group">
                    <label for="description">Description</label>
                    <input type="text" id="description" name="description" value="">
                </div>
                <div class="form group">
                    <label for="imageUrl">Image URL</label>
                    <input type="text" id="imageUrl" name="imageUrl" value="">
                </div>
                <% if (newProduct.equals(Constants.FRIES)) { %>
                <label for="size">Size</label>
                <input type="text" id="size" name="size" value="">
                <% } %>
                <% if (newProduct.equals(Constants.DRINKS)) { %>
                <label for="volumeInMilliliters">Volume</label>
                <input type="number" id="volumeInMilliliters" name="volumeInMilliliters" value="0">
                <% } %>
                <button class="btn btn-primary" name="<%= Constants.ACTION %>" value="<%= Constants.ADD %>">Add</button>
            </form>
        </div>
    </div>
</div>
<% } %>


<% if (request.getAttribute(Constants.PRODUCT) != null) { %>
<% ProductModel item = (ProductModel) request.getAttribute(Constants.PRODUCT); %>
<div class="row">
    <div class="card" style="width: 18rem;">
        <div class="card-body">
            <form action="<%= request.getContextPath() %>/admin" method="post">
                <input name="id" type="hidden" value="<%= item.getId() %>">
                <input name="category" type="hidden" value="<%= item.getCategory() %>">
                <div class="form group">
                    <label for="name">Name</label>
                    <input type="text" id="name" name="name" value="<%= item.getName() %>">
                </div>
                <div class="form group">
                    <label for="priceRub">Price (RUB)</label>
                    <input type="number" min="0" id="priceRub" name="priceRub" value="<%= item.getPriceRub() %>">
                </div>
                <div class="form group">
                    <label for="description">Description</label>
                    <input type="text" id="description" name="description" value="<%= item.getDescription() %>">
                </div>
                <div class="form group">
                    <label for="imageUrl">Image URL</label>
                    <input type="text" id="imageUrl" name="imageUrl" value="<%= item.getImageUrl() %>">
                </div>
                <% if (item.getCategory().equals(Constants.FRIES)) { %>
                <label for="size">Size</label>
                <input type="text" id="size" name="size" value="<%= ((FriesModel) item).getSize() %>">
                <% } %>
                <% if (item.getCategory().equals(Constants.DRINKS)) { %>
                <label for="volumeMl">Volume</label>
                <input type="number" id="volumeMl" name="volumeMl" value="<%= ((DrinkModel) item).getVolumeMl() %>">
                <% } %>
                <button class="btn btn-primary" name="<%= Constants.ACTION %>" value="<%= Constants.SAVE %>">Save
                </button>
            </form>
        </div>
    </div>
</div>
<% } %>

<div class="row">
    <% for (ProductModel item : (List<ProductModel>) request.getAttribute(Constants.MODELPRODUCTS)) { %>
    <div class="card" style="width: 18rem;">
        <img src="<%= item.getImageUrl() %>" class="card-img-top" alt="Image: <%= item.getCategory() %>">
        <div class="card-body">
            <ul class="list-group list-group-flush">
                <li class="list-group-item">Category: <%= item.getCategory() %>
                </li>
                <li class="list-group-item">ID: <%= item.getId() %>
                </li>
                <li class="list-group-item">Name: <%= item.getName() %>
                </li>
                <li class="list-group-item">Price: <%= item.getPriceRub() %> rub.</li>
                <li class="list-group-item">Description: <%= item.getDescription() %>
                </li>
                <% if (item.getCategory().equals(Constants.FRIES)) { %>
                <li class="list-group-item">Size: <%= ((FriesModel) item).getSize() %>
                </li>
                <% } %>
                <% if (item.getCategory().equals(Constants.DRINKS)) { %>
                <li class="list-group-item">Volume: <%= ((DrinkModel) item).getVolumeMl() %> ml.</li>
                <% } %>
            </ul>
            <a href="<%= request.getContextPath() %>/admin?id=<%= item.getId() %>&category=<%= item.getCategory() %>&<%= Constants.ACTION %>=<%= Constants.EDIT %>"
               name="<%= Constants.ACTION %>" value="<%= Constants.EDIT %>" class="btn btn-primary">Edit</a>
            <br>
            <a href="<%= request.getContextPath() %>/admin?id=<%= item.getId() %>&category=<%= item.getCategory() %>&<%= Constants.ACTION %>=<%= Constants.DELETE %>"
               name="<%= Constants.ACTION %>" value="<%= Constants.DELETE %>" class="btn btn-primary">Delete</a>
        </div>
    </div>
    <% } %>
</div>

</body>
</html>
