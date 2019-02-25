<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <title>GitRuler - Exercises</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
    <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"
          integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="css/custom.css">
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
                <a class="nav-item nav-link active" href="#">Exercises<span class="sr-only">(current)</span></a>
            </div>
        </div>
    </nav>

    <!-- end header -->

    <!-- body  -->
    <!--top-box-->
    <section class="top-box text-dark">
        <div class="container-fluid">
            <h1 class="display-4"><strong>Git Exercises</strong></h1>
            <p class="lead"><strong>All the available exercises for you to complete.</strong></p>
        </div>
    </section>
    <!-- end top-box-->

    <!--tab-->
    <ul class="nav hidden-lg-down shadow-sm" style="padding-left: 10.5%">
        <li class="nav-item">
            <a class="nav-link active" id="all-tab" data-toggle="tab" href="#all" role="tab"
                aria-controls="all" aria-selected="true">All</a>
        </li>
    </ul>
    <!--end tab-->

    <!--bottom-box-->
    <section class="bottom-box">
        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade show active" id="exercises" role="tabpanel" aria-labelledby="exercises-tab">
                <!--Exercises-->
                <div class="container mt-3">
                    <div class="list-group">
                    
                      <c:if test="${isEmpty}">
                        <h1 class="text-center mt-5 display-4">No exercises available.</h1>
                      </c:if>

                        <c:forEach items="${exercises}" var="exercise">

                            <a href="/exercise/${exercise.id}"
                                class="list-group-item list-group-item-action flex-column align-items-start shadow-sm">
                                <div class="row">
                                    <div class="col-lg-2 col-md-4 d-none d-sm-block">
                                        <img src="http://tinygraphs.com/labs/isogrids/hexa/${exercise.name}?theme=${exercise.theme}&numcolors=4&size=150&fmt=svg" class="img-fluid">
                                    </div>
                                    <div class="col-lg-7 col-md-6 col-sm-6">
                                        <h4 class="mb-1">
                                            <c:out value="${exercise.name}" />
                                        </h4>
                                        <p class="mb-1">
                                            <c:out value="${exercise.description}" />
                                        </p>
                                    </div>
                                    <div class="col-lg-3 col-md-6 col-sm-12">
                                    <span class="badge badge-primary badge-pill float-right"><c:out
                                            value="${exercise.point}"/> points</span>
                                    </div>
                                </div>
                            </a>


                        </c:forEach>

                    </div>
                </div>
                <!--end Exercises-->
            </div>
        </div>
    </section>
    <!--bottom-box-->
    <!-- end body  -->


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.bundle.min.js"
        integrity="sha384-zDnhMsjVZfS3hiP7oCBRmfjkQC4fzxVxFhBx8Hkz2aZX8gEvA/jsP3eXRCvzTofP"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
        integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
        crossorigin="anonymous"></script>
    <script src="js/custom.js"></script>
</body>

</html>