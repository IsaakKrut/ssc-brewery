package guru.sfg.brewery.security.annotations.Brewery;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('brewery.update')")
public @interface BreweryUpdatePermission {
}
