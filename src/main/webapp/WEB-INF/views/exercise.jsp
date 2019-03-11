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
</ul>
<!--end tab-->

<!--bottom-box-->
<section class="bottom-box">
    <div class="tab-content">
        <div class="tab-pane fade show active" id="instructions" role="tabpanel" aria-labelledby="instructions-tab">
            <!--Instructions-->
            <div class="container">
                <div class="row">
                    <!-- markdown -->
                    <div class="col-12 readme">
                        <article class="markdown-body">

                            <!-- buttons box -->
                            <div class="buttons_box">
                                <div class="card">
                                    <span class="h5">Get Started</span>
                                    <span class="text-muted">Click the start button to begin the exercise</span>
                                    <button class="btn bg-info w-100 mt-2" type="button">Start</button>
                                </div>
                            </div>
                            <!-- end buttons box -->

                            ${instruction}
                        </article>
                    </div>
                    <!-- end markdown -->

                </div>
            </div>
            <!--end Instructions-->
        </div>
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
    $( document ).ready(function() {
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
            setTimeout(function() {
                $(btn).tooltip('hide');
            }, 1000);
        }

        var clipboard = new Clipboard('#copy');

        clipboard.on('success', function(e) {
            setTooltip(e.trigger, 'Copied!');
            hideTooltip(e.trigger);
        });

        clipboard.on('error', function(e) {
            setTooltip(e.trigger, 'Failed!');
            hideTooltip(e.trigger);
        });
    });
</script>
</body>

</html>
