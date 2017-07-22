package io.logicode.controller;



import io.logicode.mapper.AdvertiserMapper;
import io.logicode.model.AdvertiserModel;
import io.logicode.model.Message;
import io.logicode.service.AdvertiserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jenksy on 7/7/17.
 * Rest controller for the advertiser model that exposes endpoints to the client
 */
@RestController
@RequestMapping(value = "/api/advertiser")
public class AdvertiserController {

    private AdvertiserService advertiserService;
    private AdvertiserMapper advertiserMapper;

    /**
     * Constructor for the advertiser to autowire the dependencies
     * since spring 4.3 you no longer need to autowire the controller
     */
    public AdvertiserController(AdvertiserService advertiserService, AdvertiserMapper advertiserMapper) {
        this.advertiserService = advertiserService;
        this.advertiserMapper = advertiserMapper;
    }

    /**
     * @addAd is used to post new advertiser data to the database
     * @param advertiserModel the json model of the advertiser
     */
    @RequestMapping(method = RequestMethod.POST, value = "/newAdvertiser")
    public HttpEntity<AdvertiserModel> addAd(@RequestBody AdvertiserModel advertiserModel){
       return advertiserService.checkAdvertiser(advertiserModel);
    }

    /**
     * @getAd get the advertiser for datastore by id if advertiser not found returns null with
     * a httpstatus code of not found
     * @param id the id of the advertiser to retrieve
     * @return the entity or null
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getAdvertiser/{id}")
    public HttpEntity<AdvertiserModel> getAd(@PathVariable("id")  Integer id){
        return advertiserService.getAdvertiserIfExists(id);
    }

    /**
     * Finds all the advertisers in the database
     * @return the List of advertisers
     */
    @RequestMapping(method = RequestMethod.GET, value = "/findAll")
    public HttpEntity<List<AdvertiserModel>> getAds(){
        return new ResponseEntity<>(advertiserMapper.findAll(), HttpStatus.OK);
    }

    /**
     * @getCredit gets the advertiser from the database and checks to see of the amount is less then there credit limit
     * @param id the id of the advertiser to get
     * @param credit the requested credit to check against
     * @return true if amount is below limit and false otherwise
     */
    @RequestMapping(method = RequestMethod.GET,value = "/checkScore/{id}/{amount}")
    public HttpEntity<Boolean> getCredit(@PathVariable("id") Integer id, @PathVariable("amount") int credit){
        //check wheter the supplied amount is less then credit limit
        return new ResponseEntity<>(advertiserService.creditChecker(advertiserMapper.findByID(id).getCreditLimit(), credit), HttpStatus.OK);
    }

    /**
     * @deleteAdvertiser is endpoint exposed to delete and advertiser be there id
     * @param id the id of the advertiser to delete
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteAdvertiser/{id}")
    public HttpEntity<Message> deleteAdvertiser(@PathVariable("id") Integer id){
        return advertiserService.deleteAdvertiserIfExists(id);
    }

    /**
     * @updateAdvertiser is for updating an existing advertiser
     * if not a values are sent in the model it defaults back to the original values
     * @param advertiserModel the model to update
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/updateAdvertiser")
    public HttpEntity<AdvertiserModel> updateAdvertiser(@RequestBody AdvertiserModel advertiserModel){
       return advertiserService.putAdvertiserIfExists(advertiserModel);
    }

    /**
     *@deductCredit is the endpoint for removing credit from and advertiser
     */
    @RequestMapping(method = RequestMethod.POST, value = "/deductCredit/{id}/{amount}")
    public HttpEntity<AdvertiserModel> deductCredit(@PathVariable("id") Integer id, @PathVariable("amount")Integer amount){
        return advertiserService.deductCredit(id,amount);
    }


}
