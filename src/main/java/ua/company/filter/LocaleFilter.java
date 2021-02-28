package ua.company.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class LocaleFilter implements Filter {
    FilterConfig config;
    private static final Map<String, Locale> locales = new TreeMap<>();

    static {
        locales.put("ru", new Locale("ru", "RU"));
        locales.put("en", new Locale("en", "EN"));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String lang = request.getParameter("lang");
        HttpServletRequest req = (HttpServletRequest) request;

        Locale locale = (Locale) req.getSession().getAttribute("locale");

        if (locale == null) {
            locale = req.getLocale();
        }

        if (lang != null) {
            locale = locales.get(lang);
        }

        req.getSession().setAttribute("locale", locale);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
