package org.stormnetdev.before.tests.data.database.sql.insert;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.UUID;
import org.stormnetdev.before.tests.data.database.sql.DBInsertMethods;
import org.stormnetdev.utils.ProjectUtils;


public class AddProject extends DBInsertMethods{

	public UUID projectId;

	public String title;
	public String description;
	public String startDate;
	public String expiresAt;
	public int wddTarget;
	public int wddCollected;
	public int wddMin;
	public int wddMax;
	public int wddTolprate;

	public String accountId;
	public String accountRs;
	public String accountPublicKey;
	public String accountPassphrase;
	public String lptCurrentyId;
	public String shrinkingAddress;
	public int status = 1;
	public BigDecimal destroyedLPTAmount = BigDecimal.ZERO;
	public boolean hidden;

	public void addProject() throws SQLException {
		if (title == null){
			title = "Title" + ProjectUtils.randomString();
		}
		if (description == null){
			description = "Description" + ProjectUtils.randomString();
		}
		if (startDate == null){
			startDate = "now() - interval '2' day";
		}
		if (expiresAt == null){
			expiresAt = "now() + interval '2' day";
		}
		if (wddTarget == 0){
			wddTarget = 10000;
		}
		if (wddCollected == 0){
			wddCollected = 0;
		}
		if (wddMin == 0){
			wddMin = 2;
		}
		if (wddMax == 0){
			wddMax = 20;
		}
		if (wddTolprate == 0){
			wddTolprate = 5000;
		}
		if (accountId == null){
			accountId = ProjectUtils.randomDigitsString(19);
		}
		if (accountRs == null){
			accountRs = ProjectUtils.randomGECAddress();
		}
		if (accountPublicKey == null){
			accountPublicKey = ProjectUtils.randomPublicKey(64);
		}
		if (accountPassphrase == null){
			accountPassphrase = ProjectUtils.randomString();
		}
		if (lptCurrentyId == null){
			lptCurrentyId = "6740677940257569997";
		}
		if (shrinkingAddress == null){
			shrinkingAddress = ProjectUtils.randomGECAddress();
		}
		projectId = addProject(title, description, startDate, expiresAt, wddTarget, wddCollected, wddMin, wddMax,
				wddTolprate, accountId, accountRs, accountPublicKey, accountPassphrase, lptCurrentyId, shrinkingAddress, hidden, destroyedLPTAmount, status);
	}
}