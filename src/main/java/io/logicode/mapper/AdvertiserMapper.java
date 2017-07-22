package io.logicode.mapper;



import io.logicode.model.AdvertiserModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by jenksy on 7/7/17.
 * MyBatis mapper class for the Advertiser model
 */
@Mapper
public interface AdvertiserMapper {
    /**
     * @findByID finds selects the advertiser by there ID
     * @param id the unique id of the advertiser
     * @return the AdvertiserModel
     */
    @Select("SELECT * FROM advertiser WHERE id = #{id}")
    AdvertiserModel findByID(@Param("id") Integer id);

    /**
     * @findByName finds the advertisers by there name
     * @param name the name we are looking for
     * @return a list of advertisers
     */
    @Select("SELECT * FROM advertiser WHERE name = #{name}")
    List<AdvertiserModel> findByName(@Param("name") String name);

    /**
     * @findAll gets all the advertisers from the database
     * @return a list of advertisers
     */
    @Select("SELECT * FROM advertiser")
    List<AdvertiserModel> findAll();

    /**
     * @insertAdvertiser persists a new advertiser to the data base
     * @param advertiserModel the advertiser to persist
     */
    @Insert("insert into advertiser(name,contactName,creditLimit) values(#{name},#{contactName},#{creditLimit})")
    @SelectKey(statement="call identity()", keyProperty="id",
            before=false, resultType=Integer.class)
    void insertAdvertiser(AdvertiserModel advertiserModel);

    /**
     * @delete deletes the advertiser from the database
     * @param id the id of the advertiser to delete
     */
    @Delete("DELETE FROM advertiser WHERE id = #{id}")
    void delete(Integer id);

    /**
     * @update update an existing adveriser by finding the id and updating the params
     * @param advertiserModel the model to update
     */
    @Update("UPDATE advertiser SET name =#{name}, contactName=#{contactName}, creditLimit= #{creditLimit} WHERE id =#{id}")
    void update(AdvertiserModel advertiserModel);
}