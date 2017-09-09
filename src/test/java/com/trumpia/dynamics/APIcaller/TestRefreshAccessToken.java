package com.trumpia.dynamics.APIcaller;

import static com.trumpia.util.LogUtils.getLogger;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trumpia.dynamics.schema.TestDynamicsSchema;
import com.trumpia.util.Http.HttpRequest;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TestRefreshAccessToken {
	String refreshToken = "AQABAAAAAAA9kTklhVy7SJTGAzR-p1Bcl8fxzcqzJ86soQzO6HFaKRFbWlxEziD0oFFt5me91HenV11FoohDAtoWAnEmEMy5xQq9ScNzzBzWG4_-squanBKAvYzAr3XssGGw73Ha8j93p2DV6uk0N-nta8a8H1yL1O66bR6V8jPlmUcCu3-PD7t4JLxi5Ml5yaRM5ZHbg6fbFQ3bx9wiPMzJSyW7BZr7PiG8ywIQ4XRTV5sCQSrGo5yFIobw16m7BXznKEgFmIAGqixZF0E9ElQ3Dg7YVfWBt7nX-jwVy7Lf2qJ2i4Cr_x1F67_Wc2jhEApZoduqbIRIZ6rQJBUpjbVW1yFTgdB6j6Sxd0qqHBbTGSE_2S5jQB5W3ejFEjZ2ZtliqnfzWkEsbcf_6zcnBldkziQI5DJBWisQyyuQ6QpPzZ6kxhyROLdPvVpTwjEbv_av7Hu1uGeYHKxpRbKXHB7UcHFXE8MSg_DFiuhNVaypjmYyg8piqX0HeBk2moaiKCApuY4Ngn9yox-oNpJDc6mb3Hc9ayTiZaaBVsbej5cndfndBivfWJ_tshnoOWRkaFugmW9JiYuAql-M4zgUZy3rCKkjM9eaNcK_-dg-X5KJyjIvQNTK35sgNF2Usn8Finsd8JqrDgiUpFyx8t7kEtZC4lUO3VkB-bnT1mN2zSID9T4Ji0wN2pJ1HzMJlXX7V9QhGE1NWA1Wm2r1uS14n6hKI1qhnOGfG6yJJVlp89BPpeXR5R9_SBjk5yw7FrSdUee4DInYdG2uwCrsrRFgeGGg8wuESr-vIAA";

	@Test
	public void test() throws Exception {
		RefreshAccessToken refreshAccessToken = new RefreshAccessToken(refreshToken, "https://trumpia.crm.dynamics.com");
		refreshAccessToken.updateToken();
		assertNotNull(refreshAccessToken.accessToken);
		System.out.println("refreshToken: "+refreshAccessToken.refreshToken);
		System.out.println("accessToken: "+refreshAccessToken.accessToken);
	}
}
