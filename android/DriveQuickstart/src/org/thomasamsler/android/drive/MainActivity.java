package org.thomasamsler.android.drive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;

public class MainActivity extends Activity {

	static final String LOG_TAG = "## GD Sample ##";

	static final int REQUEST_ACCOUNT_PICKER = 1;
	static final int REQUEST_AUTHORIZATION = 2;
	static final int CAPTURE_IMAGE = 3;

	static final int GD_IMAGE = 0;
	static final int GD_JSON = 1;

	private static Uri fileUri;
	private static Drive driveService;
	private GoogleAccountCredential googleAccountCredential;
	
	private String mAccountName = null;
	
	/*
	 * Change this variable to either [GD_JSON OR GD_IMAGE]
	 */
	private int gDriveSample = GD_JSON;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		/*
		 * OAuth2 Scope
		 */
		List<String> scopes = new ArrayList<String>();
		scopes.add(DriveScopes.DRIVE);

		/*
		 * Prepare Google Account Credential
		 */
		googleAccountCredential = GoogleAccountCredential.usingOAuth2(this, scopes);

		/*
		 * Start the account picker. The result of this intent is handled in the
		 * "onActivityResult" method.
		 */
		startActivityForResult(googleAccountCredential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
	}

	/*
	 * Helper method that:
	 * 
	 * 1. Sets the Google Account Credential with the account name 2. Gets the
	 * Google Drive service instance 3. Runs either save IMAGE or JSON to Google
	 * Drive.
	 */
	private void getDriveServiceAndRunSample() {

		googleAccountCredential.setSelectedAccountName(mAccountName);
		driveService = getDriveService(googleAccountCredential);

		if (null != driveService) {

			switch (gDriveSample) {

			case GD_IMAGE:
				startCameraIntent();
				break;

			case GD_JSON:
				saveJsonToDrive();
				break;
			}
		}
		else {

			Log.e(LOG_TAG, "ERROR: was not able to get Google Drive Service");
		}
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

		switch (requestCode) {

		case REQUEST_ACCOUNT_PICKER:

			if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {

				mAccountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);

				if (mAccountName != null) {

					Log.i(LOG_TAG, "INFO: account name = " + mAccountName);

					getDriveServiceAndRunSample();
				}
				else {

					Log.e(LOG_TAG, "ERROR: was not able to get account name");
				}
			}

			break;

		case REQUEST_AUTHORIZATION:

			if (resultCode != Activity.RESULT_OK) {

				startActivityForResult(googleAccountCredential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
			}

			break;

		case CAPTURE_IMAGE:

			if (resultCode == Activity.RESULT_OK) {

				saveImageToDrive();
			}

			break;

		default:
			Log.e(LOG_TAG, "ERROR: undefined request code : " + requestCode);
			break;
		}
	}

	private void startCameraIntent() {

		String mediaStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
		fileUri = Uri.fromFile(new java.io.File(mediaStorageDir + java.io.File.separator + "IMG_" + timeStamp + ".jpg"));

		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		startActivityForResult(cameraIntent, CAPTURE_IMAGE);
	}

	private void saveImageToDrive() {

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {

				try {

					// File's binary content
					java.io.File fileContent = new java.io.File(fileUri.getPath());
					FileContent mediaContent = new FileContent("image/jpeg", fileContent);

					// File's metadata.
					File body = new File();
					body.setTitle(fileContent.getName());
					body.setMimeType("image/jpeg");

					File file = driveService.files().insert(body, mediaContent).execute();

					if (null != file) {

						showToast("Photo uploaded: " + file.getTitle());
						Log.i(LOG_TAG, "INFO: Uploaded photo : " + file.getTitle());
					}
					else {

						Log.i(LOG_TAG, "ERROR: Was not able to save image");
					}
				}
				catch (IOException e) {

					e.printStackTrace();
				}
			}
		});
		t.start();
	}

	/*
	 * This method:
	 * 1. Creates a Google Drive folder
	 * 2. Creates a TXT/JSON file and stores the file in the created folder
	 * 3. Reads the TXT/JSON file meta data from Google Drive
	 * 4. Reads the TXT/JSON file content from Google Drive
	 */
	private void saveJsonToDrive() {

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {

				try {

					// Create a folder
					File folderFile = new File();
					folderFile.setTitle("My Folder 123");
					folderFile.setMimeType("application/vnd.google-apps.folder");
					File folderFileResult = driveService.files().insert(folderFile).execute();

					// Printing the folder "file" ID
					Log.i(LOG_TAG, "INFO: folder file id = " + folderFileResult.getId());

					/*
					 * Create some sample JSON
					 */
					String JSON = "{\"name\":\"thomas\", \"interest\":\"android\"}";

					/*
					 * Create JSON file in the above folder
					 */
					ByteArrayContent byteArrayContent = new ByteArrayContent("application/json", JSON.getBytes());
					File jsonFile = new File();
					jsonFile.setTitle("MyJsonFile.txt");
					jsonFile.setMimeType("text/plain");
					jsonFile.setParents(Arrays.asList(new ParentReference().setId(folderFileResult.getId())));
					File jsonFileResult = driveService.files().insert(jsonFile, byteArrayContent).execute();

					// Printing the json "file" ID
					Log.i(LOG_TAG, "INFO: json file id = " + jsonFileResult.getId());

					/*
					 * Access the file meta data
					 */
					File jsonFileFromDrive = driveService.files().get(jsonFileResult.getId()).execute();
					System.out.println("Title: " + jsonFileFromDrive.getTitle());
					System.out.println("Description: " + jsonFileFromDrive.getDescription());
					System.out.println("MIME type: " + jsonFileFromDrive.getMimeType());

					/*
					 * Access file content
					 */
					HttpResponse httpResponse = driveService.getRequestFactory().buildGetRequest(new GenericUrl(jsonFileFromDrive.getDownloadUrl())).execute();
					BufferedReader in = new BufferedReader(new InputStreamReader(httpResponse.getContent()));
					String line = null;
					
					Log.i(LOG_TAG, "INFO: **** File content start ****");
					
					while((line = in.readLine()) != null) {
						
					  Log.i(LOG_TAG, line);
					}
					
					Log.i(LOG_TAG, "INFO: **** File content end ****");

				}
				catch (IOException e) {

					e.printStackTrace();
				}
			}
		});

		t.start();
	}

	private Drive getDriveService(GoogleAccountCredential credential) {

		return new Drive.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential).build();
	}

	public void showToast(final String toast) {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {

				Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
