package com.example.springlearnzipkin.service;

import com.example.springlearnzipkin.domain.Item;
import com.example.springlearnzipkin.exceptions.ItemNotFoundException;
import com.example.springlearnzipkin.model.request.ItemRequestDto;
import com.example.springlearnzipkin.model.response.ItemResponseDto;
import com.example.springlearnzipkin.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

     Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);


    final ItemRepository itemRepository;



    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemResponseDto createItem(ItemRequestDto itemRequestDto) {
        Item item = new Item(itemRequestDto.getName());

        itemRepository.save(item);

        ItemResponseDto itemResponseDto = new ItemResponseDto();
        if (itemRepository.existsById(item.getId())){
            itemResponseDto.setId(item.getId());
            itemResponseDto.setName(item.getName());

            return itemResponseDto;
        }
        return null;

    }

    @Override
    public ItemResponseDto updateItem(Integer id, ItemRequestDto itemRequestDto) throws ItemNotFoundException {
        if (!itemRepository.existsById(id)) {
            logger.error(String.format("Item's not exist with id : %s", id));
        }else{
            Item existItem=itemRepository.findById(id)
                    .orElseThrow(() -> new ItemNotFoundException(String.format("Item not found. ID: %s", id)));
            logger.info(String.format("Item Exist name: %s",existItem.getName()));
            existItem.setName(itemRequestDto.getName());
            itemRepository.save(existItem);
            logger.info(String.format("Item New name: %s",existItem.getName()));
            ItemResponseDto itemResponseDto = new ItemResponseDto();

                itemResponseDto.setId(existItem.getId());
                itemResponseDto.setName(existItem.getName());

                return itemResponseDto;
        }

        throw new ItemNotFoundException(String.format("Equipment's not exist with id : %s", id));
    }

    @Override
    public ItemResponseDto getItem(Integer id) throws ItemNotFoundException {
         Item existItem=itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(String.format("Item not found. ID: %s", id)));

        ItemResponseDto itemResponseDto = new ItemResponseDto();
        if (itemRepository.existsById(existItem.getId())){
            itemResponseDto.setId(existItem.getId());
            itemResponseDto.setName(existItem.getName());

            return itemResponseDto;
        }
        throw new ItemNotFoundException(String.format("Item's not exist with id : %s", id));
    }

    @Override
    public List<ItemResponseDto> getItems() {

        List<Item> items = itemRepository.findAll();

        List<ItemResponseDto> itemResponseDtos = new ArrayList<>();


        items.stream().forEach(item -> {
            ItemResponseDto itemResponseDto = new ItemResponseDto();
            itemResponseDto.setId(item.getId());
            itemResponseDto.setName(item.getName());

            itemResponseDtos.add(itemResponseDto);
        });


        return itemResponseDtos;
    }
    @Override
    public void deleteItem(Integer id) throws ItemNotFoundException {
        if (!itemRepository.existsById(id)) {

            logger.error(String.format("Item's not exist with id : %s", id));


            throw new ItemNotFoundException(String.format("Item's not exist with id : %s", id));
        }
        else{
            itemRepository.deleteById(id);
            logger.info(String.format("Item's deleted with %s",id));

        }
    }
}
