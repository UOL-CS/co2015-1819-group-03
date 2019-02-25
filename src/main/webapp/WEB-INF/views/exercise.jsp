<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <title>GitRuler - Exercise - <c:out value="${exercise.id}"/></title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
    <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"
          integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/custom.css">
</head>

<body>
<!-- header -->

<nav class="navbar sticky-top navbar-dark navbar-expand-md" id="navbar">
    <a class="navbar-brand" href="#">GitRuler</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
            aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse justify-content-between" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <a class="nav-item nav-link active" href="/exercises">Exercises <span class="sr-only">(current)</span></a>
        </div>
    </div>
</nav>

<!-- end header -->

<!-- body  -->

<!--top-box-->
<section class="top-box text-dark"
         style="background: linear-gradient(to right, #ebfaf8 25%,rgba(255,255,255,0) 100%), url(https://www.tinygraphs.com/isogrids/banner/random/gradient?theme=${exercise.theme}&h=450&xt=35&w=3000&numcolors=4);background-attachment: fixed;">
    <div class="d-flex justify-content-start">
        <div class="col-lg-2 d-none d-lg-block" style="max-width: 180px;">
            <img src="http://tinygraphs.com/labs/isogrids/hexa/${exercise.name}?theme=${exercise.theme}&numcolors=4&size=150&fmt=svg"
                 class="img-fluid">
        </div>
        <div class="align-self-center">
            <h1 class="mb-2"><c:out value="${exercise.name}"/></h1>
        </div>
    </div>
</section>
<!-- end top-box-->

<!-- end body  -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.bundle.min.js"
        integrity="sha384-zDnhMsjVZfS3hiP7oCBRmfjkQC4fzxVxFhBx8Hkz2aZX8gEvA/jsP3eXRCvzTofP"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
        integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
        crossorigin="anonymous"></script>
</body>

</html>