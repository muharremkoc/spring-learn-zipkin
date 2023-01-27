package com.example.springlearnzipkin.service;


import com.example.springlearnzipkin.exceptions.ItemNotFoundException;
import com.example.springlearnzipkin.model.request.ItemRequestDto;
import com.example.springlearnzipkin.model.response.ItemResponseDto;

import java.util.List;

public interface ItemService {

    ItemResponseDto createItem(ItemRequestDto itemRequestDto);

    ItemResponseDto updateItem(Integer id,ItemRequestDto itemRequestDto) throws ItemNotFoundException;

    ItemResponseDto getItem(Integer id) throws ItemNotFoundException;

    List<ItemResponseDto> getItems();
    void deleteItem(Integer id) throws ItemNotFoundException;
}
