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

@Component
@Order(2)
public class KeysFilter implements Filter {

    @Autowired
    AccountRepository accountRepository;

    public static final String KEYS = "keys";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String keys = req.getHeader(KEYS);

        if (keys != null && !keys.equals(""))
            req.setAttribute(KEYS, keys);

        chain.doFilter(request, response);
    }
}