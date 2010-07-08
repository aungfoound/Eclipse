package foound.com.http;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class http extends Activity {
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		/* We will show the data we read in a TextView. */
		TextView tv = new TextView(this);
		
		/* Will be filled and displayed later. */
		String myString = null;
		try {
			/* Define the URL we want to load data from. */
			URL myURL = new URL(
					"http://api.foound.com/api/login");
			/* Open a connection to that URL. */
			URLConnection ucon = myURL.openConnection();

			/* Define InputStreams to read 
			 * from the URLConnection. */
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			
			/* Read bytes to the Buffer until 
			 * there is nothing more to read(-1). */
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while((current = bis.read()) != -1){
				baf.append((byte)current);
			}

			/* Convert the Bytes read to a String. */
			myString = new String(baf.toByteArray());
		} catch (Exception e) {
			/* On any Error we want to display it. */
			myString = e.getMessage();
		}
		/* Show the String on the GUI. */
		tv.setText(myString);
		this.setContentView(tv);
	}
}