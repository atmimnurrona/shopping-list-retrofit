package com.atmim.shopping.services;

import com.atmim.shopping.entities.Item;

import java.util.List;

public interface ItemService {
    public Item save(Item item);
    public Item remove(Integer id);
    public Item findById(Integer id);
    public List<Item> findAll();
}
