package com.example.springlearnzipkin.controller;

import com.example.springlearnzipkin.exceptions.ItemNotFoundException;
import com.example.springlearnzipkin.model.request.ItemRequestDto;
import com.example.springlearnzipkin.model.response.ItemResponseDto;
import com.example.springlearnzipkin.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/zipkin")
public class ItemController {



    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);



    final ItemService itemService;


    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }




    @PostMapping(path = "/item")
    public ItemResponseDto createItem(@RequestBody ItemRequestDto itemRequestDto){
        return itemService.createItem(itemRequestDto);
    }

    @PutMapping(value = "/item/{id}")
    public ItemResponseDto updateItem(@PathVariable("id") Integer id,@RequestBody ItemRequestDto itemRequestDto) throws ItemNotFoundException {
        return itemService.updateItem(id, itemRequestDto);
    }

    @GetMapping(value = "/item/{id}")
    public ItemResponseDto getItem(@PathVariable("id") Integer id) throws ItemNotFoundException {
        return itemService.getItem(id);
    }



    @DeleteMapping(value = "/item/{id}")
    public void deleteItem(@PathVariable("id") Integer id) throws ItemNotFoundException {
         itemService.deleteItem(id);
    }

    @GetMapping(value = "/item")
    public List<ItemResponseDto> getItems(){
        return itemService.getItems();
    }

/*    @GetMapping("/path1")
    public ResponseEntity path1() {

        logger.info("Incoming request at {} for request /path1 ", applicationName);
        String response = restTemplate.getForObject("http://localhost:9114/api/zipkin/path2", String.class);
        return ResponseEntity.ok("response from /path1 + " + response);
    }

    @GetMapping("/path2")
    public ResponseEntity path2() {
        logger.info("Incoming request at {} at /path2", applicationName);
        return ResponseEntity.ok("response from /path2 ");
    }*/

}
