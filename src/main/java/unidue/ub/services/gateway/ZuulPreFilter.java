package unidue.ub.services.gateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;
@Component
public class ZuulPreFilter extends ZuulFilter {

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1; // run before PreDecoration
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    } //use Pre Filter

    @Override
    public boolean shouldFilter() {
        return true; //always apply filter
    }

    /**
     * Replaces the HttpServletRequest with a SanitizedRequest
     * @return null
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        SanitizedRequest sanitizedRequest = new SanitizedRequest(request);
        ctx.setRequest(sanitizedRequest);
        return null;
    }
}
