package guru.sfg.brewery.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RestRequestParamAuthFilter extends RestAuthFilter{
    public RestRequestParamAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String username = getUsername(request);
        String password = getPassword(request);

        if (username == null){
            username = "";
        }
        if (password == null){
            password="";
        }
        log.debug("Authenticating User: " + username);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        if (!StringUtils.isEmpty(username)) {
            return this.getAuthenticationManager().authenticate(token);
        } else {
            return null;
        }
    }

    private String getUsername(HttpServletRequest request) {
        return request.getParameter("Api-Key");
    }

    private String getPassword(HttpServletRequest request) {
        return request.getParameter("Api-Secret");
    }
}
