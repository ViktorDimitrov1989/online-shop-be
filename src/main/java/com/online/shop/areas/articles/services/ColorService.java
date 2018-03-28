package com.online.shop.areas.articles.services;

import com.online.shop.areas.articles.dto.colors.ColorResponseDto;
import com.online.shop.areas.articles.entities.Color;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ColorService {

    Set<Color> findAllColorsIn(Collection<String> colors);

    List<ColorResponseDto> findAllColors();
}
