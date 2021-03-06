package com.atmim.shopping.controllers;

import com.atmim.shopping.entities.Item;
import com.atmim.shopping.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/items")
@RestController
public class ItemController {

    @Autowired
    private ItemService service;

    @PostMapping
    public Item add(@RequestBody Item model) {
        Item item = service.save(model);
        return item;
    }

    @PutMapping("/{id}")
    public Item edit(@PathVariable Integer id, @RequestBody Item model) {
        Item item = service.findById(id);
        model = service.save(model);
        return model;
    }

    @DeleteMapping("/{id}")
    public Item remove(@PathVariable("id") Integer id) {
        Item item = service.remove(id);
        return item;
    }

    @GetMapping("/{id}")
    public Item findById(@PathVariable("id") Integer id) {
        Item item = service.findById(id);
        return item;
    }

    @GetMapping
    public List<Item> findAll() {
        return service.findAll();
    }
}
