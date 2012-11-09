/*
 * Copyright 2012 Thomas Amsler
 * 
 * Author:  Thomas Amsler : tamsler@gmail.com
 * 
 */

package org.gdgsacramento.android.raffle.task;

import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

public class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {

	public static final String METHOD_NAME = "DELETE";

	public String getMethod() {

		return METHOD_NAME;
	}

	public HttpDeleteWithBody(final String uri) {

		super();
		setURI(URI.create(uri));
	}

	public HttpDeleteWithBody(final URI uri) {

		super();
		setURI(uri);
	}

	public HttpDeleteWithBody() {

		super();
	}

}
