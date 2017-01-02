package es.edm.filters;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

public class MyBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint{

	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		//Do nothing when there is an error of anthentication
//		HttpServletResponse httpResponse = (HttpServletResponse) response;
//		httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
	}

}
