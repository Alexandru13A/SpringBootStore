package com.alexandru.SpringBootStore.repository;

import com.alexandru.SpringBootStore.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Modifying
    @Query("UPDATE Address a SET a.address1 =: address1 WHERE a.addressId =: addressId")
    int updateFirstAddress(String address, long id);

    @Modifying
    @Query("UPDATE Address a SET a.address2 =: address2 WHERE a.addressId =: addressId")
    int updateSecondAddress(long id, String address);

    @Modifying
    @Query("UPDATE Address a SET a.city =: city WHERE a.addressId =: addressId")
    int updateCity(long id, String city);

    @Modifying
    @Query("UPDATE Address a SET a.country =: country WHERE a.addressId =: addressId")
    int updateCountry(long id, String country);

}
