<%@ page import="ru.rosbank.javaschool.web.constant.Constants" %>
<%@ page import="ru.rosbank.javaschool.web.model.OrderPositionModel" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Order</title>
    <%@include file="bootstrap-css.jsp" %>
</head>

<body>
<br>
<div class="container">
<a href="/"><img src="https://iconizer.net/files/Web_Design_Creatives/orig/Cross-lines.png"
                 width="25" height="25"></a>

<br>
<br>

<form action="<%= request.getContextPath() %>" method="post">
    <div>
        <button class="btn btn-primary" name="action" value="buy">Buy</button>
    </div>
    <br>
    <div class="card-columns">
        <% List<OrderPositionModel> positions = (List<OrderPositionModel>) request.getAttribute(Constants.ORDERITEMS); %>
        <% for (OrderPositionModel model : positions) { %>
        <div class="card" style="width: 18rem;">
            <div class="card-body">
                <h5 class="card-title"><%= model.getProductName() %>
                </h5>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">Price: <%= model.getProductPrice() %> rub.</li>
                    <li class="list-group-item" hidden>
                        <label for="quantity">Quantity: </label>
                        <input type="number" min="0" id="quantity" name="quantity"
                               value="<%= model.getProductQuantity() %>">
                    </li>
                    <li class="list-group-item">Cost: <%= model.getProductPrice() * model.getProductQuantity() %>
                    </li>
                    <input name="model-id" type="hidden" value="<%= model.getId() %>">
                    <input name="id" type="hidden" value="<%= model.getProductId() %>">
                    <input name="category" type="hidden" value="<%= model.getProductCategory() %>">
                    <button class="btn btn-primary" name="action" value="delete<%= model.getId() %>">Delete</button>
                </ul>
            </div>
        </div>
        <% } %>
    </div>
</form>
</div>

</body>
</html>