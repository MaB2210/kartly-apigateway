package com.kartly.api_gateway.config;

import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.stripPrefix;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> authRoute(){
        return route("auth-route")
                .route(path("/api/auth/**"), HandlerFunctions.http())
                .before(stripPrefix(1))
                .filter(lb("user-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceRoute(){
        return route("user-service")
                .route(path("/api/users/**"), HandlerFunctions.http())
                .before(stripPrefix(1))
                .filter(lb("user-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> productServiceRoute(){
        return route("product-service")
                .route(path("/api/products/**"), HandlerFunctions.http())
                .before(stripPrefix(1))
                .filter(lb("product-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute(){
        return route("order-service")
                .route(path("/api/orders/**"), HandlerFunctions.http())
                .before(stripPrefix(1))
                .filter(lb("order-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> oauth2AuthorizationRoute() {
        return route("oauth2-authorization-route")
                .route(path("/oauth2/**"), HandlerFunctions.http())
                .filter(lb("user-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> oauth2CallbackRoute() {
        return route("oauth2-callback-route")
                .route(path("/login/oauth2/**"), HandlerFunctions.http())
                .filter(lb("user-service"))
                .build();
    }
}
