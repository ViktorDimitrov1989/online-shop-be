package com.online.shop.areas.users.serviceImpl;

import com.online.shop.areas.users.entities.Address;
import com.online.shop.areas.users.repositories.AddressRepository;
import com.online.shop.areas.users.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address save(Address address) {
        return this.addressRepository.save(address);
    }
}
