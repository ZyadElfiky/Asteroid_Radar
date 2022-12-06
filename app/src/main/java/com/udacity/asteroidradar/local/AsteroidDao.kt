package com.udacity.asteroidradar.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.local.entities.Asteroid
import com.udacity.asteroidradar.network.AsteroidResponse
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.NotNull

@Dao
interface AsteroidDao {

    @Query("select * from asteroid where closeApproachDate >= :startDate and closeApproachDate <= :endDate order by closeApproachDate ASC")
     fun getAsteroidsWithSpecificData(
         startDate: String,
         endDate: String
    ): Flow<List<Asteroid>>

    @Query("SELECT * FROM asteroid ORDER BY closeApproachDate ASC")
     fun getAllAsteroids(): Flow<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAllAsteroid(vararg asteroids: Asteroid)

    @Query("DELETE FROM asteroid WHERE closeApproachDate < :today")
     fun deletePreviousDayAsteroids( @NotNull today: String): Int

}