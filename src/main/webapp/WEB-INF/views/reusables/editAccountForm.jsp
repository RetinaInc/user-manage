<input type="hidden" id="username" name="username"
	value="${account.username}" />
<div class="control-group">
	<label for="email" class="control-label">Email:</label>
	<div class="controls">
		<input type="email" id="email" name="email" value="${account.email}"
			class="input-large required" />
	</div>
</div>
<div class="control-group">
	<label for="plainPassword" class="control-label">Password:</label>
	<div class="controls">
		<input type="password" id="plainPassword" name="plainPassword"
			minLength="6" class="input-large"
			placeholder="leave it blank, if not to change" />
	</div>
</div>
<div class="control-group">
	<label for="confirmPassword" class="control-label">Confirm
		Password:</label>
	<div class="controls">
		<input type="password" id="confirmPassword" name="confirmPassword"
			class="input-large" equalTo="#plainPassword" />
	</div>
</div>

<div class="control-group">
	<label for="birthday" class="control-label">Birthday(YYYY-MM-DD): </label>
	<div class="controls">
		<input id="birthday" name="birthday" type="text" class="input-large" />
	</div>
</div>

<div class="form-actions">
	<input id="submit_btn" class="btn btn-primary" type="submit"
		value="Update" />&nbsp; <input id="cancel_btn" class="btn"
		type="button" value="Cancel" onclick="history.back()" />
</div>
