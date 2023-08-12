package guru.sfg.brewery.security.annotations.Order;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@PreAuthorize("hasAuthority('order.read') OR " +
        "hasAuthority('customer.order.read') AND " +
        "@beerOrderAuthenticationManager.customerIdMatches(authentication, #customerId)")
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadOrderPermission {
}
