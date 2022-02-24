package com.tamzid.fcmdemo.Respontory;

import com.tamzid.fcmdemo.Entity.TokenEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface Token extends CrudRepository<TokenEntity,Integer> {
    @Modifying
    @Query(value = "INSERT INTO tb_token (token) VALUES (:tokens)",nativeQuery = true)
    @Transactional
    void insertToken(@Param("tokens") String tokens);


    @Query(value = "select  tk.token from  tb_token tk ",nativeQuery = true)
    List<String> gettken();


    @Modifying
    @Query(value = "delete from  tb_token t where t.token = :token",nativeQuery = true)
    void DeleteToken(@Param("token")String token);





}
