<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!-- Footer -->
<footer>
	<div class="container">
		<div class="row">
			<div class="col-md-6">
				<p>
					&copy; <a href="http://www.hknochi.de">HKnochi</a> - Spring
					AppEnginge Boilerplate
				</p>
			</div>
			<div class="col-md-6 small text-muted text-right">
				<p>
					Application identifier:
					<gae:applicationId />
					<br /> Application version:
					<gae:applicationVersion ignoreMinorVersion="true" />
					<br /> Runtime environment:
					<gae:runtimeEnvironment />
					<br /> Runtime version:
					<gae:runtimeVersion />
					<gae:isLoggedIn>
						<br />
                                User identifier: <gae:user
							property="userId" />
						<br />
                                User email: <gae:user property="email" />
						<br />
                                User nickname: <gae:user
							property="nickname" />
						<br />
						<gae:isAdmin var="isAdmin" />
                                User is administrator: ${isAdmin}
                            </gae:isLoggedIn>
				</p>
			</div>
		</div>
	</div>
</footer>