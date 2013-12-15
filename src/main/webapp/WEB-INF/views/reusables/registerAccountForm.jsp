<!-- This is file contains common input controls of a registration form -->
<div class="control-group">
	<label for="username" class="control-label">Username: </label>
	<div class="controls">
		<input type="text" id="username" name="username" minLength="5"
			class="input-large required" />
	</div>
</div>
<div class="control-group">
	<label for="email" class="control-label">Email: </label>
	<div class="controls">
		<input type="email" id="email" name="email"
			class="input-large required" />
	</div>
</div>
<div class="control-group">
	<label for="plainPassword" class="control-label">Password: </label>
	<div class="controls">
		<input type="password" id="plainPassword" name="plainPassword"
			minLength="6" class="input-large required" />
	</div>
</div>
<div class="control-group">
	<label for="confirmPassword" class="control-label">Confirm
		Password: </label>
	<div class="controls">
		<input type="password" id="confirmPassword" name="confirmPassword"
			class="input-large required" equalTo="#plainPassword" />
	</div>
</div>
<div class="form-actions">
	<input id="submit_btn" class="btn btn-primary" type="submit"
		value="Register" />&nbsp; <input id="cancel_btn" class="btn"
		type="button" value="Cancel" onclick="history.back()" />
</div>
