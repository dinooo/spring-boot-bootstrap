package com.piksel.movies.resources;

import com.piksel.movies.persistence.AccountDao;
import com.piksel.movies.representation.Account;
import org.springframework.security.config.annotation.web.configurers.RememberMeConfigurer;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
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
    private final HttpServletRequest request;
    @Inject
    public AccountsResource(AccountDao accountDao, HttpServletRequest request){
        this.accountDao = accountDao; this.request = request;
    }

    @GET
    public List<Account> getAll(){
        return this.accountDao.findAll();
    }

    @GET
    @Path("{id}")
    public Account getOne(@PathParam("id") long id){
        Account account = accountDao.findOne(id);
        if(account == null)
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        return account;
    }

    @POST
    public Account save(@Valid Account account) {
        return accountDao.save(account);
    }

    @PUT
    @Path("{id}")
    public Account update(@PathParam("id")long id, @Valid Account account) {
        if(accountDao.findOne(id) == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }else {
            account.setId(id);
            return accountDao.save(account);
        }
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id")long id) {
        Account account = accountDao.findOne(id);
        if(account == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }else {
            accountDao.delete(account);
        }
    }


}
