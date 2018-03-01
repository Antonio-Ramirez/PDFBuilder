<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" type="text/css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/static/css/bootstrap-responsive.css" type="text/css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/static/css/style.css" type="text/css" rel="stylesheet">
<title>Forms UI</title>
</head>
<body>
	<div class="container">
		<div class="hero-unit">
			<h1>Forms UI</h1>
			<div class="pull-right">
				Simple Home Solar
			</div>
		</div>
		<div class="row">
			<div class="span12">
				<div id="rootwizard">
					<div class="navbar">
					  <div class="navbar-inner">
					    <div class="container">
					    	<ul class="nav nav-pills">
								<li><a href="#tab_pdfs" data-toggle="tab">Generate PDF </a></li>
								<li><a href="#tab_forms" data-toggle="tab">Download Form </a></li>
							</ul>
						</div>
					  </div>
					</div>
					<div class="tab-content">
						
						<div class="tab-pane" id="tab_pdfs">
							<div class="span8">
								<c:if test="${(response != null) && (response.pdfSummary.errors != null) && (not empty response.pdfSummary.errors)}">
									<div class="alert alert-danger" role="alert">
										<dl class="dl-vertical">
											<dt>Generating errors:</dt>
											<dd>
												<ul class="errors">
													<c:forEach items="${response.pdfSummary.errors}" var="error">
														<li><span class="label label-danger">${error}</span></li>
													</c:forEach>
												</ul>
											</dd>
										</dl>
									</div>
								</c:if>
							
								<form:form method="post" action="build" class="form-horizontal" modelAttribute="fileRequest" >
									<fieldset>
										<legend>Generate PDF</legend>
										<div class="control-group">
											<div class="controls">
												<form:select path="formCustomerId" id="clientSelect">
											    	<form:option value="">Select Customer: </form:option>
											        <c:forEach items="${fileRequest.clients}" var="client">
												        <form:option value="${client.value}">${client.key}</form:option>
										        	</c:forEach>
												</form:select>
											</div>
										</div>
										<div class="control-group">
											<div class="controls">
												<button id="buildbutton" name="buildbutton" type="submit" class="btn btn-primary">Generate PDF</button>
											</div>
										</div>
									</fieldset>
								</form:form>
							</div>
							<div class="span11">
								<div class="response">
									<c:if test="${(response != null) && (response.pdfSummary != null) && (response.pdfSummary.url != null)}">
										<fieldset>
											<legend>Generated PDF</legend>
											<dl class="dl-vertical">
												<dt>PDF:</dt>
												<dd>
													<ul id="pdfs">
														<li>Url: <a href="${response.pdfSummary.url}" target="_blank">${response.pdfSummary.url}</a></li>
														<li>Form: ${response.pdfSummary.id}</li>
													</ul>
												</dd>
											</dl>
										</fieldset>
									</c:if>
								</div>
							</div>
						</div>
						<div class="tab-pane" id="tab_forms">
							<div class="span8">
								<c:if test="${(response != null) && (response.form.errors != null) && (not empty response.form.errors)}">
									<div class="alert alert-danger" role="alert">
										<dl class="dl-vertical">
											<dt>Loading errors:</dt>
											<dd>
												<ul class="errors">
													<c:forEach items="${response.form.errors}" var="error">
														<li><span class="label label-danger">${error}</span></li>
													</c:forEach>
												</ul>
											</dd>
										</dl>
									</div>
								</c:if>
								<form:form method="post" action="submit" class="form-horizontal" modelAttribute="request">
									<fieldset>
										<legend>Download Form</legend>
										<div class="control-group">
											<div class="controls">
												<form:select path="formUrl" id="formSelect">
													<form:option value="">Select Form: </form:option>
													<c:forEach items="${request.options}"  var="option">
													<form:option value="${option.value}">${option.key}</form:option>
										        	</c:forEach>
											    </form:select>
											    <form:label id="url" path=""></form:label>
											</div>
										</div>
										<div class="control-group">
											<div class="controls">
												<button id="downloadbutton" name="downloadbutton" type="submit" class="btn btn-primary">Download Form</button>
											</div>
										</div>
									</fieldset>
								</form:form>
							</div>
							<div class="span11">
								<div class="response">
									<c:if test="${(response != null) && (response.form.fields != null) && (not empty response.form.fields)}">
										<fieldset>
											<legend>Downloaded Form</legend>
											<dl class="dl-vertical">
												<dt>Fields from PDF Form:</dt>
												<dd>
													<div id="formpagewizard">
														<div class="navbar">
														  <div class="navbar-inner">
														    <div class="container">
															<ul class="nav nav-pills">
															<c:forEach items="${response.form.fields}" var="page">
																<li><a href="#tab${page.key}" data-toggle="tab">Page ${page.key} </a></li>
															</c:forEach>
															</ul>
															</div>
														  </div>
														</div>
														<div class="tab-content">
															<c:forEach items="${response.form.fields}" var="page">
															<div class="tab-pane" id="tab${page.key}">
																<table class="table table-bordered table-striped">
																<thead><tr>
																	<th>Field Name</th>
																	<th>Input Field Name</th>
																	<th>Type</th>
																	<th>Possible Value</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${page.value}" var="field">
																	<tr>
																		<td>${field.alias}</td>
																		<td>${field.key}</td>
																		<td>${field.type}</td>
																		<td>${field.value}</td>
																	</tr>
																</c:forEach>
																</tbody>
																</table>
					    									</div>
					    									</c:forEach>
					    									<div style="float:right">
																<input type='button' class='btn button-next' name='next' value='Next' />
															</div>
															<div style="float:left">
																<input type='button' class='btn button-previous' name='previous' value='Previous' />
															</div>
														</div>
													</div>
												</dd>
											</dl>
										</fieldset>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script language="javaScript" src="${pageContext.request.contextPath}/static/js/jquery.min.1.10.2.js"></script>
	<script language="javaScript" src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
	<script language="javaScript" src="${pageContext.request.contextPath}/static/js/bootstrap-datepicker.js"></script>
	<script language="javaScript" src="${pageContext.request.contextPath}/static/js/jquery.bootstrap.wizard.js"></script>
	<script language="javaScript" src="${pageContext.request.contextPath}/static/js/app.js"></script>
	<script>
		$(document).ready(function() {

			$('#rootwizard').bootstrapWizard({'tabClass': 'nav nav-pills'});
			$('#formpagewizard').bootstrapWizard({'nextSelector': '.button-next', 'previousSelector': '.button-previous'});

			$(function() { 
			    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
			        // save the latest tab; use cookies if you like 'em better:
			        localStorage.setItem('lastTab', $(this).attr('href'));
			    });

			    // go to the latest tab, if it exists:
			    var lastTab = localStorage.getItem('lastTab');
			    if (lastTab) {
			        $('[href="' + lastTab + '"]').tab('show');
			    }
			});

			$('#formSelect')
			  .change(function () {
			    var str = "";
			    $('#formSelect option:selected').each(function() {
			       str += $( this ).val() + " ";
			    });
			    $('#url').text( str );
			  })
			  .change();
			  
		});	
	</script>
</body>
</html>
