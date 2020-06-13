package csd.wallet.WebFilters;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Base64;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import csd.wallet.Utils.JWTUtil;

@Component
@Order(1)
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);

		if (authHeader == null)
			res.sendError(401, "Auth needed");

		else {

			StringTokenizer st = new StringTokenizer(authHeader);

			if (st.hasMoreTokens()) {

				String basic = st.nextToken();

				if (basic.equalsIgnoreCase("Basic")) {

					String jwt = new String(Base64.getDecoder().decode(st.nextToken()));

					if (!req.getRequestURI().equals("/login") && !req.getRequestURI().equals("/accounts/create")) {

						try {

							long id = Long.parseLong(JWTUtil.parseJWT(jwt));

							req.setAttribute("id", id);

						} catch (Exception e) {
							res.sendError(401, "Auth Error");
						}

					}

					chain.doFilter(request, response);

				}

			}
		}
	}
}