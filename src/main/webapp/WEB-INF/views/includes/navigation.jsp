<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!-- Navigation Starts -->
<div class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="/">Spring AppEngine Boilerplate</a>
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="/">Jobs</a></li>
                        <li><a href="/map">Map</a></li>
                        <gae:isLoggedIn>
                            <li><a href="/my-jobs/create">Post Job</a></li>
                            <li><a href="/my-jobs">My Jobs</a></li>
                        </gae:isLoggedIn>
                        <gae:isNotLoggedIn>
                            <li><gae:loginLink destinationUrl="/my-jobs/create">Post Job</gae:loginLink></li>
                        </gae:isNotLoggedIn>
                        <gae:isAdmin>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Administration <b class="caret"></b></a>
                                <ul class="dropdown-menu">
                                    <li><a href="/about">About</a></li>
                                </ul>
                            </li>
                        </gae:isAdmin>
                    </ul>
                    <div class="navbar-right">
                        <gae:isNotLoggedIn>
                            <gae:loginLink destinationUrl="/" class="btn btn-primary navbar-btn">Login</gae:loginLink>
                        </gae:isNotLoggedIn>
                        <gae:isLoggedIn>
                            <gae:logoutLink destinationUrl="/" class="btn btn-default navbar-btn">Logout</gae:logoutLink>
                        </gae:isLoggedIn>
                    </div>
                </div>
            </div>
        </div>
<!-- Navigation Ends -->
