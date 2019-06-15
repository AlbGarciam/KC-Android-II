## Create a custom application

1.  Create a subclass of application (TaskApp in our case) 
2.  Go to manifest 
3.  An application add modifier name to application object

## Add Room

In order to add Room:

### 1. Prepare project
``` 
// Add plugin
apply plugin: 'kotlin-kapt'

// Add dependencies
implementation "androidx.room:room-runtime:2.1.0-rc01"
kapt "androidx.room:room-compiler:2.1.0-rc01"

```

### 2. Create entity
TableName is the name of the table which will be used on TaskEntityDao
``` 
@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val isHighPriority: Boolean,
    val isFinished: Boolean
)
```

### 3. Add DAO

``` 
@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks") // tasks is the name of the table
    suspend fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Long): TaskEntity

    @Insert
    suspend fun insert(taskEntity: TaskEntity)

    @Update
    suspend fun update(taskEntity: TaskEntity)

    @Delete
    suspend fun delete(taskEntity: TaskEntity)

}
```

As we are going to store a non-primitive attribute, we have to convert date to a primitive data. To do that, we create a typeconverter

### 4. Add Database
``` 
// Each entity is a SQL table
@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase {
    
    abstract fun getTaskDao(): TaskDao
    
}
``` 

## Add Koin
1. Add gradle dependency
```
implementation 'org.koin:koin-android:2.0.1'
implementation 'org.koin:koin-android-viewmodel:2.0.1'
```

2. Add the module
```
val appModule = module{
    single {
        Room.databaseBuilder(
            androidContext(),
            TaskDatabase::class.java,
            "tasks.db"
        ).build()
    }

    factory { // We want that DAO is updated every time we access to it
        get<TaskDatabase>().getTaskDao()
    }


    single {
        TaskRepositoryImpl(get()) // this will call the factory
    }
}
```

3. Use Koin
```
// On task app
startKoin {
      androidLogger()
      androidContext(this@TaskApp)
      modules(appModule) // Here we instantiate our database
}
``` 
To customize the application you have to go to Android manifest and set `android:name=".TaskApp"` tag to customize the app. Where TaskApp is the class of your custom class