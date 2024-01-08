package gambling.applicationServices.frameworkHooks;

import gambling.applicationServices.utils.Transaction;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

class TransactionFilter implements Filter {

    private Transaction transaction;

    @Override public void init(FilterConfig filterConfig) {}

    @Override public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        transaction = Transaction.start();
        req.setAttribute("transaction", transaction);

        chain.doFilter(request, response);
    }

    @Override public void destroy() {
        transaction.complete();
    }
}
