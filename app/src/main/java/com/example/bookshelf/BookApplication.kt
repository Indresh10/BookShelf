package com.example.bookshelf

import android.app.Application
import com.example.bookshelf.data.AppContainer
import com.example.bookshelf.data.AppNetworkContainer


class BookApplication:Application() {
    lateinit var appContainer:AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppNetworkContainer()
    }
}