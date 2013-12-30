<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="control-group">
	<label for="username" class="control-label"> 
	<spring:message code="label.username" />
	</label>
	<div class="controls">
		<input type="text" id="username" name="username" minLength="5"
			class="input-large required" />
	</div>
</div>
<div class="control-group">
	<label for="email" class="control-label"> 
	 <spring:message code="label.email" />
	</label>
	<div class="controls">
		<input type="email" id="email" name="email"
			class="input-large required" />
	</div>
</div>
<div class="control-group">
	<label for="plainPassword" class="control-label"> 
	   <spring:message code="label.password" />
	</label>
	<div class="controls">
		<input type="password" id="plainPassword" name="plainPassword"
			minLength="6" class="input-large required" />
	</div>
</div>
<div class="control-group">
	<label for="confirmPassword" class="control-label"> 
	   <spring:message code="label.confirmPassword" />
	</label>
	<div class="controls">
		<input type="password" id="confirmPassword" name="confirmPassword"
			class="input-large required" equalTo="#plainPassword" />
	</div>
</div>
<div class="form-actions">
	<input id="submit_btn" class="btn btn-primary" type="submit"
		value=<spring:message code="button.register"/> />&nbsp; <input id="cancel_btn" class="btn"
		type="button" value=<spring:message code="button.cancel"/> onclick="history.back()" />
</div>
