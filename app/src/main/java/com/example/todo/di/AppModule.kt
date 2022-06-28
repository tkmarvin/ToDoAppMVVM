package com.example.todo.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.data.TodoDatabase
import com.example.todo.data.TodoRepository
import com.example.todo.data.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//we initialize objects that are required through out the life spans of our application
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesTodoDatabase(app:Application): TodoDatabase{
        return Room.databaseBuilder(app,
            TodoDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(database: TodoDatabase): TodoRepository{
        return TodoRepositoryImpl(database.dao)
    }
}