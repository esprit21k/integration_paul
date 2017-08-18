package com.trumpia.dynamics.APIcaller;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class TestRefreshAccessToken {
	String accessToken;
	

	@Test
	public void test() throws IOException {
		accessToken = RefreshAccessToken.updateAccessToken();
		assertNotNull(accessToken);
	}
}
