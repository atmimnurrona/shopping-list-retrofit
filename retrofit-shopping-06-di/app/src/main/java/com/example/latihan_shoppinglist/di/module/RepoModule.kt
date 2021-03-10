package com.example.latihan_shoppinglist.di.module

import com.example.latihan_shoppinglist.data.repository.ItemRepository
import com.example.latihan_shoppinglist.data.repository.ItemRepositoryInterface
import com.example.latihan_shoppinglist.di.qualifier.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {

    @ItemApi
    @Binds
    abstract fun bindsRepositoryApi(itemRepository: ItemRepository): ItemRepositoryInterface

}