package com.online.shop.areas.articles.common;

import com.online.shop.areas.articles.entities.Color;
import com.online.shop.areas.articles.repositories.ColorRepository;
import com.online.shop.areas.articles.repositories.SizeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.util.HashSet;
import java.util.Set;

@Component
public class ArticleConverter extends PropertyEditorSupport {

    private ColorRepository colorRepository;

    private SizeRepository sizeRepository;

    private ModelMapper modelMapper;

    @Autowired
    public ArticleConverter(ColorRepository colorRepository, SizeRepository sizeRepository, ModelMapper modelMapper) {
        this.colorRepository = colorRepository;
        this.sizeRepository = sizeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void setValue(Object value) {

        String[] unparsedColors = (String[]) value;

        Set<Color> colors = new HashSet<>();

        for (String color : unparsedColors) {
            System.out.println(color);
            colors.add(this.colorRepository.findOneByName(color));
        }

        super.setValue(colors);
    }
}
