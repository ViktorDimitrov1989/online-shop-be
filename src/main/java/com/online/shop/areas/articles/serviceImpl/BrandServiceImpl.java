package com.online.shop.areas.articles.serviceImpl;
import com.online.shop.areas.articles.dto.brand.BrandResponseDto;
import com.online.shop.areas.articles.entities.Brand;
import com.online.shop.areas.articles.models.binding.CreateBrandBindingModel;
import com.online.shop.areas.articles.repositories.BrandRepository;
import com.online.shop.areas.articles.services.BrandService;
import com.online.shop.exception.RequestException;
import com.online.shop.response.ResponseMessageConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {

    private BrandRepository brandRepository;

    private ModelMapper modelMapper;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository, ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BrandResponseDto createBrand(CreateBrandBindingModel createBrandBindingModel) {

        if(this.brandRepository.findOneByName(createBrandBindingModel.getName()) != null){
            throw new RequestException(ResponseMessageConstants.EXISTING_BRAND_NAME, HttpStatus.CONFLICT);
        }

        Brand brand = this.modelMapper. map(createBrandBindingModel, Brand.class);

        Brand createdBrand = this.brandRepository.save(brand);

        return this.modelMapper.map(createdBrand, BrandResponseDto.class);
    }

    @Override
    public Brand findBrandByName(String name) {
        Brand res = this.brandRepository.findOneByName(name);

        if(res == null){
            throw new RequestException(ResponseMessageConstants.INVALID_BRAND_NAME ,HttpStatus.BAD_REQUEST);
        }

        return res;
    }
}
