package com.online.shop.areas.articles.services;

import com.online.shop.areas.articles.dto.sizes.SizeResponseDto;
import com.online.shop.areas.articles.entities.Size;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface SizeService {

    Set<Size> findAllSizesIn(Collection<String> sizes);

    Set<Size> findAllRawSizes();

    List<SizeResponseDto> findAllSizes();
}
