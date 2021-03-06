package com.atmim.shopping.repositories;

import com.atmim.shopping.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
