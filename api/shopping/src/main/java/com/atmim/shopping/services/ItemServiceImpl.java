package com.atmim.shopping.services;

import com.atmim.shopping.entities.Item;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    protected final JpaRepository<Item, Integer> repository;

    public ItemServiceImpl(JpaRepository<Item, Integer> repository) {
        this.repository = repository;
    }

    @Override
    public Item save(Item item) {
        return repository.save(item);
    }

    @Override
    public Item remove(Integer id) {
        Item item = findById(id);
        if (item != null) {
            repository.deleteById(id);
            return item;
        } else {
            return null;
        }
    }

    @Override
    public Item findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Item> findAll() {
        return repository.findAll(Sort.by("id"));
    }
}
