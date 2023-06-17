package com.alexandru.SpringBootStore.service;


import com.alexandru.SpringBootStore.model.Address;
import com.alexandru.SpringBootStore.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


    public int updateFirstAddress(String address, long id) {
        return addressRepository.updateFirstAddress(address, id);
    }

    public int updateSecondAddress(Long id, String address) {
        return addressRepository.updateSecondAddress(id, address);
    }

    public int updateCity(long id, String city) {
        return addressRepository.updateCity(id, city);
    }

    public int updateCountry(long id, String country) {
        return addressRepository.updateCountry(id, country);
    }

    public void deleteAddress(long id) {
        addressRepository.deleteById(id);
    }

    public void saveAddress(Address address) {
        addressRepository.save(address);
    }


}
