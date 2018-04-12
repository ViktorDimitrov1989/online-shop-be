package com.online.shop.areas.articles.serviceImpl;

import com.online.shop.areas.articles.dto.sizes.SizeResponseDto;
import com.online.shop.areas.articles.entities.Size;
import com.online.shop.areas.articles.repositories.SizeRepository;
import com.online.shop.areas.articles.services.SizeService;
import com.online.shop.exception.RequestException;
import com.online.shop.response.ResponseMessageConstants;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SizeServiceImpl implements SizeService{

    private SizeRepository sizeRepository;

    private ModelMapper modelMapper;

    @Autowired
    public SizeServiceImpl(SizeRepository sizeRepository, ModelMapper modelMapper) {
        this.sizeRepository = sizeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<Size> findAllSizesIn(Collection<String> sizes) {
        Set<Size> resp = this.sizeRepository.findAllByNameIn(sizes);

        if(resp == null){
            throw new RequestException(ResponseMessageConstants.INVALID_SIZE,HttpStatus.BAD_REQUEST);
        }

        return resp;
    }

    @Override
    public Set<Size> findAllRawSizes() {
        return new HashSet<>(this.sizeRepository.findAll());
    }

    @Override
    public List<SizeResponseDto> findAllSizes() {
        List<Size> sizes = this.sizeRepository.findAll();

        Type targetListType = new TypeToken<List<SizeResponseDto>>() {}.getType();

        return this.modelMapper.map(sizes, targetListType);
    }

    @Override
    public Size findSizeByName(String size) {
        Size res = this.sizeRepository.findOneByName(size);

        if(res == null){
            throw new RequestException(ResponseMessageConstants.INVALID_SIZE, HttpStatus.BAD_REQUEST);
        }

        return res;
    }
}
