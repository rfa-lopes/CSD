package csd.wallet.WebFilters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import csd.wallet.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import csd.wallet.Utils.JWTUtil;

@Component
@Order(1)
public class AuthenticatorFilter implements Filter {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String token = req.getHeader(JWTUtil.HEADER_NAME);

        try {
            Long id = Long.parseLong(JWTUtil.parseJWT(token));
            accountRepository.findById(id).orElseThrow(Exception::new);
            req.setAttribute("id", id);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            req.setAttribute("id", -1);
        }
        chain.doFilter(request, response);
    }
}