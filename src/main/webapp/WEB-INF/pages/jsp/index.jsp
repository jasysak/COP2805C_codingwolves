<%--
  Created by IntelliJ IDEA.
  User: Reubin George
  Date: 2/15/2019
  Time: 1:55 PM
  To change this template use File | Settings | File Templates.
--%>

<%@page session="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!---------SPRING DECLARATIONS------------------->
<spring:url value="" var="" />
<spring:url value="/webjars/bootstrap/4.3.1/css/bootstrap.css" var="bootstrapCSS" />
<spring:url value="webjars/jquery/3.3.1/jquery.min.js" var="jqueryJS" />
<spring:url value="/webjars/popper.js/1.14.3/umd/popper.min.js" var="popperJS" />
<spring:url value="/webjars/bootstrap/4.3.1/js/bootstrap.min.js" var="bootstrapJS" />
<spring:url value="/webjars/font-awesome/5.7.2/css/all.min.css" var="fontawesomeCSS" />
<spring:url value="/webjars/crypto-js/3.1.9/crypto-js.js" var="cryptoJS" />
<spring:url value="/resources/css/index-style.css" var="indexCSS" />
<spring:url value="/resources/js/index-JS.js" var="indexJS" />
<spring:url value="/resources/images/wolf.svg" var="wolfimage" />
<spring:url value="/resources/js/index-JS.js" var="indexJS" />
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0,
         maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Wolfster</title>
    <link rel="icon" href="${wolfimage}"/>
    <c:url var="home" value="/" scope="request" />
    <!--------BOOTSTRAP---------->
    <link href="${bootstrapCSS}" rel="stylesheet"/>
    <script src="${jqueryJS}"></script>
    <script src="${popperJS}"></script>
    <script src="${bootstrapJS}"></script>
    <!-------FONT AWESOME-------->
    <link href="${fontawesomeCSS}" rel="stylesheet" />
    <!------CRYPTO JS------------>
    <script src="${cryptoJS}"></script>
    <!-----CUSTOM PAGE----------->
    <link rel="stylesheet" href="${indexCSS}" />
    <script src="${indexJS}"></script>
</head>
<body>
<div class="image-wrapper">
    <img src="${wolfimage}" alt="howling wolves" />
</div>
<div class="wrapper">
    <div class="input-group mb-3">
        <div class="input-group-prepend">
            <select class="custom-select">
                <option class="custom-option" selected>Auto</option>
                <option class="custom-option" value="1">Boolean</option>
                <option class="custom-option" value="2">Exact Phrase</option>
                <option class="custom-option" value="3">All Phrase</option>
            </select>
            <select id="width_tmp_select">
                <option id="width_tmp_option"></option>
            </select>
        </div>
        <input type="text" class="form-control" placeholder="Enter desired phrase"
               aria-label="Recipient's username" aria-describedby="basic-addon2">
        <div class="input-group-append">
            <button class="btn btn-outline-secondary" type="button" id="adminbtn">
                <i class="fas fa-cog"></i>
            </button>
            <button class="btn btn-outline-primary" type="button" id="searchbtn">
                <i class="fas fa-search"></i>
            </button>
        </div>
    </div>
</div>
</body>
</html>
<div id="searchModal" class="modal fade">
    <div class="modal-dialog">
        <form method="post" id="search_form" enctype="multipart/form-data">
            <div class="modal-content">
                <div class="modal-header" style="display: block;">
                    <button type="button" class="close" data-dismiss="modal">
                        &times;
                    </button>
                    <h5 class="modal-title" style="display: block;">Search Result</h5>
                </div>
                <div class="modal-body">

                </div>
                <div class="modal-footer" style="display: block;">
                    <button type="button" class="btn btn-outline-secondary" data-dismiss="modal"
                            style="float: right;">Close</button>
                </div>
            </div>
        </form>
    </div>
</div>


<div id="adminModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="post" id="fileUpload" class="fileUpload" action="/"
                  enctype="multipart/form-data">
                <div class="modal-header" style="display: block;">
                    <button type="button" class="close" data-dismiss="modal">
                        &times;
                    </button>
                    <h5 class="modal-title" style="display: block;">Admin Panel</h5>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <div class = "custom-file">
                            <input type="file" class="custom-file-input" id="fileSelector" multiple>
                            <label class="custom-file-label" for="inputGroupFile04">Choose file</label>
                        </div>
                    </div>
                    <div class="container">

                    </div>
                </div>
                <div class="modal-footer" style="display: flow-root;">
                    <button type="button" class="btn btn-outline-secondary" data-dismiss="modal"
                            style="float: right;">Close</button>
                    <button type="button" class="btn btn-outline-info" id="upload"
                            style="position: relative;left: 50%;transform: translateX(-125%);">
                        Upload
                    </button>
                    <button type="button" id ="reset" class="btn btn-outline-danger" style="float: left;">
                        Reset
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    /*

*/
</script>