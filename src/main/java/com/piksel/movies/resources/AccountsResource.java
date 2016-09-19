package com.piksel.movies.resources;

import com.piksel.movies.persistence.AccountDao;
import com.piksel.movies.representation.Account;
import com.piksel.movies.service.SecurityService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@Component
public class AccountsResource {

    private AccountDao accountDao;
    private final SecurityService securityService;

    @Inject
    public AccountsResource(AccountDao accountDao, SecurityService securityService) {
        this.accountDao = accountDao;
        this.securityService = securityService;
    }

    @GET
    public List<Account> getAll() {
        return this.accountDao.findAll();
    }

    @GET
    @Path("{id}")
    public Account getOne(@PathParam("id") long id) {
        long currentMemberId = securityService.getCurrentMemberId();
        if (currentMemberId != id) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        Account account = accountDao.findOne(id);
        if (account == null)
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        return account;
    }

    @POST
    public Account save(@Valid Account account) {
        return accountDao.save(account);
    }

    @PUT
    @Path("{id}")
    public Account update(@PathParam("id") long id, @Valid Account account) {
        if (accountDao.findOne(id) == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            account.setId(id);
            return accountDao.save(account);
        }
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
        Account account = accountDao.findOne(id);
        if (account == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            accountDao.delete(account);
        }
    }


}
