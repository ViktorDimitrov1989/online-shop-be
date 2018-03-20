package com.online.shop.serviceImpl;

import com.online.shop.entity.Address;
import com.online.shop.repository.AddressRepository;
import com.online.shop.service.AddressService;
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
