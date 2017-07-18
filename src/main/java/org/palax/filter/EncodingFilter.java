package org.palax.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * The {@code EncodingFilter} class implements {@link Filter} and used to set
 * {@code UTF-8} character encoding for all requests
 *
 * @author Taras Palashynskyy
 */
public class EncodingFilter implements Filter {
    private static final String ENCODING = "UTF-8";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding(ENCODING);

        chain.doFilter(request, response);
    }
}
