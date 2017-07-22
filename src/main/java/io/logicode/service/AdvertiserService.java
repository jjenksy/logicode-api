package io.logicode.service;


import io.logicode.mapper.AdvertiserMapper;
import io.logicode.model.AdvertiserModel;
import io.logicode.model.Message;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


/**
 * Created by jenksy on 7/7/17.
 */
@Service
public class AdvertiserService {

    private AdvertiserMapper advertiserMapper;

    public AdvertiserService(AdvertiserMapper advertiserMapper) {
        this.advertiserMapper = advertiserMapper;
    }

    public boolean creditChecker(int creditCurrent, int creditToCheck){
        return creditToCheck < creditCurrent;
    }


    public HttpEntity<AdvertiserModel> checkAdvertiser(AdvertiserModel advertiserModel) {
        boolean checker = checkModel(advertiserModel);
        if(checker){
            //all data exists so insert into db
            advertiserMapper.insertAdvertiser(advertiserModel);
            return new ResponseEntity<AdvertiserModel>(advertiserModel, HttpStatus.CREATED);
        }else{
            //return an empty model with error for client
            return new ResponseEntity<>(new AdvertiserModel("Advertiser missing information. Insure name, contact name and credit limit are not missing."), HttpStatus.BAD_REQUEST);
        }


    }

    /**
     * The checkmodel method checks to see if all data exists on the model
     * @param advertiserModel the model to check
     * @return true if data exists and false otherwise
     */
    private boolean checkModel(AdvertiserModel advertiserModel) {

        if(advertiserModel.getContactName() ==null) {
            return false;
        }
        if(advertiserModel.getCreditLimit() == null){
            return false;
        }
        if(advertiserModel.getName() == null){
            return false;
        }

        return true;
    }

    /**
     * This method checks to see if the advertiser exist
     * @param id the id to check
     * @return a ResponseEntity with and advertiser or an error message otherwise
     */
    public HttpEntity<AdvertiserModel> getAdvertiserIfExists(Integer id) {
        AdvertiserModel advertiserModel = advertiserMapper.findByID(id);
        if(advertiserModel == null){
            return new ResponseEntity<AdvertiserModel>(new AdvertiserModel("The advertiser with id of "+ id+ " does not exist."), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<AdvertiserModel>(advertiserModel, HttpStatus.OK);
    }

    /**
     * Delete advertiser but check if it exists before doing so
     * @param id the id of the advertiser to check
     * @return
     */
    public HttpEntity<Message> deleteAdvertiserIfExists(Integer id) {

        AdvertiserModel advertiserModel = advertiserMapper.findByID(id);
        if(advertiserModel == null){
            return new ResponseEntity<Message>(new Message("The advertiser with id of "+ id+ " does not exist."), HttpStatus.NOT_FOUND);
        }
        advertiserMapper.delete(id);
        return new ResponseEntity<Message>(new Message("The advertiser with id of "+ id+ " has been successfully deleted."), HttpStatus.OK);

    }

    /**
     * @putAdvertiserIfExists is the error checking method to insure we have a proper
     * advertiser before we save it to the database
     * @param advertiserModel the new model passed to the use
     * @return the saved model
     */
    public HttpEntity<AdvertiserModel> putAdvertiserIfExists(AdvertiserModel advertiserModel) {
        AdvertiserModel oldAdvertiser = advertiserMapper.findByID(advertiserModel.getId());
        if(oldAdvertiser== null){
            return new ResponseEntity<AdvertiserModel>(new AdvertiserModel("Advertiser with id of "+ advertiserModel.getId()+ " does not exist."), HttpStatus.NOT_FOUND);
        }
        //if all data is not passed only update the data that is passed
        if(!checkModel(advertiserModel)){
            AdvertiserModel finalAdvertiser = updateChangedData(advertiserModel,oldAdvertiser);
            advertiserMapper.update(finalAdvertiser);
            return new ResponseEntity<>(finalAdvertiser, HttpStatus.OK);
        }

        advertiserMapper.update(advertiserModel);
        return new ResponseEntity<>(advertiserModel, HttpStatus.OK);
    }

    /**
     * @updateChangedData allows the user to send partially filled json data to the endpoint
     * and fills any missing data with the old info
     * @param advertiserModel the new adveritiser
     * @param oldAdvertiser the old advertiser info to update
     * @return the final advertiser to save
     */
    private AdvertiserModel updateChangedData(AdvertiserModel advertiserModel, AdvertiserModel oldAdvertiser) {

        if(advertiserModel.getName() == null){
            advertiserModel.setName(oldAdvertiser.getName());
        }
        if (advertiserModel.getContactName() == null){
            advertiserModel.setContactName(oldAdvertiser.getContactName());
        }
        if(advertiserModel.getCreditLimit() == null){
            advertiserModel.setCreditLimit(oldAdvertiser.getCreditLimit());
        }

        return advertiserModel;
    }

    /**
     * @deductCredit deducts an amount of credit for an advertiser if amount is less then the credit
     * then it deducts nothing and returns an error message to the user
     * @param id the id of the user to deduct credit from
     * @param amount the amount of credit to deduct
     * @return the empty object with error message or the object with credit deducted
     */
    public HttpEntity<AdvertiserModel> deductCredit(Integer id, Integer amount) {
        AdvertiserModel advertiserToDeduct = advertiserMapper.findByID(id);
        if(advertiserToDeduct == null){
            return new ResponseEntity<AdvertiserModel>(new AdvertiserModel("Advertiser with id of "+ id+ " does not exist."), HttpStatus.NOT_FOUND);
        }
        int remaining = advertiserToDeduct.getCreditLimit() - amount;

        if(remaining < 0){
            return new ResponseEntity<AdvertiserModel>(new AdvertiserModel("Amount to deduct is more credit then the advertiser has "
                    + advertiserToDeduct.getCreditLimit() +
                    " amount to deduct " +amount +" = " + remaining),
                    HttpStatus.FORBIDDEN);
        }

        advertiserToDeduct.setCreditLimit(remaining);
        advertiserMapper.update(advertiserToDeduct);
        return new ResponseEntity<AdvertiserModel>(advertiserToDeduct, HttpStatus.OK);
    }
}
