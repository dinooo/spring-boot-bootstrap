package com.piksel.movies.resources;


import javax.validation.constraints.NotNull;
import javax.ws.rs.Path;

import com.piksel.movies.annotations.PublicResource;
import com.piksel.movies.persistence.AccountDao;
import com.piksel.movies.representation.Account;
import com.piksel.movies.representation.OauthRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/authentication")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@Component
@PublicResource
public class AuthenticationsResource {

    private AccountDao accountDao;

    @Inject
    public AuthenticationsResource(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @POST
    public Account authenticate(@NotNull OauthRequest oauthRequest, @Context HttpServletResponse response) {
        Account account = accountDao.findAccount(oauthRequest);
        if (account == null) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        String sessionCookie = account.getId() + ":" + UUID.randomUUID().toString();
        response.addCookie(new Cookie("session_id", sessionCookie));

        return account;
    }


}
