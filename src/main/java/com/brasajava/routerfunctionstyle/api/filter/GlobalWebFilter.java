package com.brasajava.routerfunctionstyle.api.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class GlobalWebFilter implements WebFilter {

  @Override
  public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {

    serverWebExchange.getResponse().getHeaders().add("global-web-filter", "global-filter");
    return webFilterChain.filter(serverWebExchange);
  }
}
