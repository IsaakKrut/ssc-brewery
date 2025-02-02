package guru.sfg.brewery.security.annotations.Customer;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('customer.update')")
public @interface CustomerUpdatePermission {
}
