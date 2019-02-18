<%--
  Created by IntelliJ IDEA.
  User: Reubin George
  Date: 2/15/2019
  Time: 1:55 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!---CUSTOM PAGE STATIC RESOURCES SPRING----->
<spring:url value="/resources/css/index-style.css" var="indexstyle" />
<spring:url value="/resources/js/index-JS.js" var="indexjs" />
<spring:url value="/resources/images/wolf.svg" var ="wolfimage" />
<!--------BOOTSTRAP WEBJAR SPRING------------>
<spring:url value="/webjars/bootstrap/4.1.3/js/bootstrap.min.js" var="bootstrapminjs" />
<spring:url value="/webjars/popper.js/1.14.3/umd/popper.min.js" var="popperminjs" />
<spring:url value="/webjars/jquery/3.3.1/jquery.min.js" var="jqueryminjs" />
<spring:url value="/webjars/bootstrap/4.1.3/css/bootstrap.css" var="bootstrapcss" />
<!--------FONTAWESOME WEBJAR SPRING---------->
<spring:url value="/webjars/font-awesome/5.7.1/css/all.min.css" var="fontawesome" />
<!--------DROPZONE WEBJAR SPRING------------->
<spring:url value="/webjars/dropzone/4.3.0/dist/min/dropzone.min.js" var="dropzonejs" />

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0,
         maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Wolfster</title>
    <link rel="icon" href="${wolfimage}"/>
    <!--------BOOTSTRAP-------------->
    <link href="${bootstrapcss}" rel="stylesheet" />
    <script src="${jqueryminjs}"></script>
    <script src="${popperminjs}"></script>
    <script src="${bootstrapminjs}"></script>
    <!----------FONT AWESOME---------->
    <link href="${fontawesome}" rel="stylesheet" />
    <!----------DROPZONE-------------->
    <script src="${dropzonejs}"></script>
    <!--------CUSTOM PAGE ------------>
    <link href="${indexstyle}" rel="stylesheet" />
    <script src="${indexjs}"></script>
</head>
<body>
<div class="image-wrapper">
    <img src="${wolfimage}" alt ="howling wolf">
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
            <div class="modal-header" style="display: block;">
                <button type="button" class="close" data-dismiss="modal">
                    &times;
                </button>
                <h5 class="modal-title" style="display: block;">Admin Panel</h5>
            </div>
            <div class="modal-body">
                <form method="post" id="my-awesome-dropzone" class="dropzone" action="/"
                      enctype="multipart/form-data"></form>
            </div>
            <div class="modal-footer" style="display: block;">
                <button type="button" class="btn btn-outline-secondary" data-dismiss="modal"
                        style="float: right;">Close</button>
                <button type="button" class="btn btn-outline-warning" style="float: right;">
                    Rebuild
                </button>
                <button type="button" class="btn btn-outline-primary" style="text-align: center;">
                    Add Files
                </button>
                <button type="button" class="btn btn-outline-danger" style="float: left;">
                    Reset
                </button>
            </div>
        </div>
    </div>
</div>



