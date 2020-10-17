<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Products</title>
    <style>
        td {
            background: #ddd;
            border: 1px solid black;
            padding: 5px;
        }
    </style>
</head>
<body>
<table>
    <tr>
        <th>produkt</th>
        <th>cena (ostatnio uzyskana)</th>
        <th>zamówiono</th>
        <th>brakuje do pełnego zamówienia</th>
        <th>twoje zamówienie</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.name}</td>
            <td>${product.price} za ${product.unit}</td>
            <td>0 ${product.unit}</td>
            <td></td>
            <td>
                <form action="/product/order">
                    <input type="hidden" name="product_id" value="${product.id}">
                    <input type="text" style="width:40px" name="ilosc">${product.unit}&nbsp;&nbsp;<input type="submit"
                                                                                                        value="zamów">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
