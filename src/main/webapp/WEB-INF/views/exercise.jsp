<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <title>GitRuler - Exercise - <c:out value="${exercise.id}"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css"
          integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/custom.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/github-markdown-css/3.0.1/github-markdown.min.css"/>
</head>

<body>
<!-- header -->

<nav class="navbar sticky-top navbar-dark navbar-expand-md" id="navbar">
    <a class="navbar-brand" href="/">GitRuler</a>
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
         style="background: linear-gradient(to right, #ebfaf8 25%,rgba(255,255,255,0) 100%), url(https://www.tinygraphs.com/isogrids/banner/random/gradient?theme=${exercise.theme}&h=450&xt=35&w=3000&numcolors=4);">
    <div class="d-flex justify-content-start">
        <div class="col-lg-2 d-none d-lg-block" style="max-width: 180px;">
            <img src="http://tinygraphs.com/labs/isogrids/hexa/${exercise.name}?theme=${exercise.theme}&numcolors=4&size=150&fmt=svg"
                 class="img-fluid">
        </div>
        <div class="align-self-center">
            <!-- exercise title -->
            <h1 class="mb-2"><c:out value="${exercise.name}"/></h1>
            <!-- end exercise title -->
        </div>
    </div>
</section>
<!-- end top-box-->

<!--tab-->
<ul class="nav hidden-lg-down shadow-sm" style="padding-left: 10.5%">
    <li class="nav-item">
        <a class="nav-link active" id="instructions-tab" data-toggle="tab" href="#instructions" role="tab"
           aria-controls="instructions" aria-selected="true">Instructions</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" id="attempt-tab" data-toggle="tab" href="#attempt" role="tab"
           aria-controls="attempt" aria-selected="true">Attempts</a>
    </li>
</ul>
<!--end tab-->

<!--bottom-box-->
<section class="bottom-box">
    <div class="tab-content">
        <!--Instructions-->
        <div class="tab-pane fade show active" id="instructions" role="tabpanel" aria-labelledby="instructions-tab">
            <div class="container">
                <div class="row">
                    <!-- markdown -->
                    <div class="col-12 readme">
                        <article class="markdown-body">
                            <!-- buttons box -->
                            <div class="buttons_box">
                                <c:if test="${isSuccessful eq true}">
                                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                                        <strong>Exercise started!</strong> Follow the instructions below.
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                </c:if>
                                <c:if test="${isSubmitSuccessful eq true}">
                                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                                        <strong>Exercise submitted!</strong> Results will be shown in the Attempts tab
                                        shortly.
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                </c:if>
                                <c:if test="${isSubmitSuccessful eq false}">
                                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                        <strong>Whoops! Something went wrong!</strong> Please try again.
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                </c:if>
                                <c:if test="${isSuccessful eq false}">
                                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                        <strong>Exercise already started!</strong> Follow the instructions below.
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                </c:if>
                                <div class="card" id="card">
                                    <c:if test="${isForked == false}">
                                        <span class="h5">Get Started</span>
                                        <span class="text-muted">Click the start button to begin the exercise.</span>
                                        <form action="/exercise/${exercise.id}" method="POST">
                                            <button class="btn bg-info w-100 mt-2" type="submit">Start</button>
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        </form>
                                    </c:if>
                                    <c:if test="${isForked}">
                                        <ol>
                                            <li>
                                                <span class="h6">Clone repository</span><br>
                                                <span class="text-muted">Clone repository and solve the exercise on your local machine.</span>

                                                <div class="input-group mb-3 mt-2">
                                                    <input type="text" class="form-control" id="copy_val" readonly
                                                           value="${repoLink}">
                                                    <div class="input-group-append">
                                                        <button class="btn bg-secondary copy" id="copy" type="button"
                                                                data-clipboard-target="#copy_val"><i
                                                                class="fas fa-copy"></i></button>
                                                    </div>
                                                </div>
                                            </li>
                                            <li>
                                                <span class="h6">Submit solution</span><br>
                                                <span class="text-muted">Click the submit button to submit the solution.</span>

                                                <form action="/exercise/submit/${exercise.id}" method="POST">
                                                    <button class="btn bg-info w-100 mt-2" type="submit">Submit</button>
                                                    <input type="hidden" name="link" value="${repoLink}"/>
                                                    <input type="hidden" name="${_csrf.parameterName}"
                                                           value="${_csrf.token}"/>
                                                </form>
                                            </li>
                                        </ol>
                                    </c:if>
                                </div>
                            </div>
                            <!-- end buttons box -->
                            ${instruction}
                        </article>
                    </div>
                    <!-- end markdown -->
                </div>
            </div>
        </div>
        <!--end Instructions-->
        <!--Feedback-->
        <div class="tab-pane fade show" id="attempt" role="tabpanel" aria-labelledby="attempt-tab">
            <div class="col-12">
                <c:if test="${empty attempts}">
                    <h1 class="text-center mt-5 display-4">No attempts available.</h1>
                </c:if>
                <c:if test="${not empty attempts}">
                    <div class="accordion" id="accordionFeedback">
                        <c:forEach items="${attempts}" var="_attempt" varStatus="loop">
                            <div class="card mb-2">
                                <!-- accordion label -->
                                <div class="card-header d-flex justify-content-between" id="headingOne"
                                     data-toggle="collapse"
                                     data-target="#attempt_${loop.index}"
                                     aria-expanded="${loop.first ? true : false}"
                                     aria-controls="attempt_${loop.index}"
                                     style="background-color: #fff">

                                    <c:set var="index" value="${loop.index - attemptSize}"/>
                                    <div class="p-2">Attempt : ${index < 0 ? -index:index} </div>
                                    <div class="p-2 text-primary">Score : ${_attempt.score}/${exercise.point}</div>

                                </div>
                                <!-- end accordion label -->
                                <!-- accordion content -->
                                <div id="attempt_${loop.index}"
                                     class="collapse <c:if test="${loop.first}">show</c:if>"
                                     aria-labelledby="headingOne"
                                     data-parent="#accordionFeedback"
                                     style="background-color: #282a36"
                                >
                                    <div class="card-body">
                                        <p class="bash">
                                                ${_attempt.feedback}
                                        </p>
                                    </div>
                                </div>
                                <!-- end accordion content -->
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </div>
        <!--end Feedback-->
    </div>
</section>
<!--bottom-box-->

<!-- end body  -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/clipboard.js/1.5.10/clipboard.min.js"></script>
<script>
    $(document).ready(function () {
        $('#copy').tooltip({
            trigger: 'click',
            placement: 'top'
        });

        function setTooltip(btn, message) {
            $(btn).tooltip('hide')
                .attr('data-original-title', message)
                .tooltip('show');
        }

        function hideTooltip(btn) {
            setTimeout(function () {
                $(btn).tooltip('hide');
            }, 1000);
        }

        var clipboard = new Clipboard('#copy');

        clipboard.on('success', function (e) {
            setTooltip(e.trigger, 'Copied!');
            hideTooltip(e.trigger);
        });

        clipboard.on('error', function (e) {
            setTooltip(e.trigger, 'Failed!');
            hideTooltip(e.trigger);
        });
    });
</script>
</body>

</html>
