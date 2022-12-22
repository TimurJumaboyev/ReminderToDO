package uz.isystem.remindertodo.core.di

import uz.isystem.remindertodo.core.repository.MainRepo
import uz.isystem.remindertodo.core.repository.MainRepoImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {

    @Binds
    @Singleton
    fun getRepo(repo: MainRepoImp): MainRepo

}