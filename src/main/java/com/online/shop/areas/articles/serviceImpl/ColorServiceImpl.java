package com.online.shop.areas.articles.serviceImpl;
import com.online.shop.areas.articles.dto.colors.ColorResponseDto;
import com.online.shop.areas.articles.entities.Color;
import com.online.shop.areas.articles.repositories.ColorRepository;
import com.online.shop.areas.articles.services.ColorService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ColorServiceImpl implements ColorService{

    private ColorRepository colorRepository;

    private ModelMapper modelMapper;

    public ColorServiceImpl(ColorRepository colorRepository, ModelMapper modelMapper) {
        this.colorRepository = colorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<Color> findAllColorsIn(Collection<String> colors) {
        return this.colorRepository.findAllByNameIn(colors);
    }

    @Override
    public Set<Color> findAllRawColors() {
        return new HashSet<>(this.colorRepository.findAll());
    }

    @Override
    public List<ColorResponseDto> findAllColors() {
        List<Color> colors = this.colorRepository.findAll();

        Type targetListType = new TypeToken<List<ColorResponseDto>>() {}.getType();

        return this.modelMapper.map(colors, targetListType);
    }
}
