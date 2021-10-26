//
//package com.keelim.free.setting;
//
//import android.content.Context
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.SharedPreferencesMigration
//import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.core.intPreferencesKey
//import androidx.datastore.preferences.core.stringPreferencesKey
//import androidx.datastore.preferences.preferencesDataStore
//import com.keelim.core.setting.AppSettings
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.map
//import kotlinx.serialization.json.Json
//import soup.movie.model.Theater
//import soup.movie.settings.model.AgeFilter
//import soup.movie.settings.model.GenreFilter
//import soup.movie.settings.model.TheaterFilter
//
//class AppSettingsImpl(private val context: Context) : AppSettings {
//
//    private val Context.preferencesName: String
//        get() = packageName + "_preferences"
//
//    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
//        name = context.preferencesName,
//        produceMigrations = { context ->
//            listOf(SharedPreferencesMigration(context, context.preferencesName))
//        }
//    )
//
//    private val theaterFilterKey = intPreferencesKey("theater_filter")
//
//    override suspend fun setTheaterFilter(theaterFilter: TheaterFilter) {
//        context.dataStore.edit { settings ->
//            settings[theaterFilterKey] = theaterFilter.toFlags()
//        }
//    }
//
//    override fun getTheaterFilterFlow(): Flow<TheaterFilter> {
//        return context.dataStore.data.map { preferences ->
//            TheaterFilter(preferences[theaterFilterKey] ?: TheaterFilter.FLAG_THEATER_ALL)
//        }
//    }
//
//    private val ageFilterKey = intPreferencesKey("age_filter")
//
//    override suspend fun setAgeFilter(ageFilter: AgeFilter) {
//        context.dataStore.edit { settings ->
//            settings[ageFilterKey] = ageFilter.toFlags()
//        }
//    }
//
//    override fun getAgeFilterFlow(): Flow<AgeFilter> {
//        return context.dataStore.data.map { preferences ->
//            AgeFilter(preferences[ageFilterKey] ?: AgeFilter.FLAG_AGE_DEFAULT)
//        }
//    }
//
//    private val genreFilterKey = stringPreferencesKey("favorite_genre")
//
//    override suspend fun setGenreFilter(genreFilter: GenreFilter) {
//        context.dataStore.edit { settings ->
//            settings[genreFilterKey] =
//                genreFilter.blacklist.joinToString(separator = SEPARATOR)
//        }
//    }
//
//    override fun getGenreFilterFlow(): Flow<GenreFilter> {
//        return context.dataStore.data.map { preferences ->
//            val genreString = preferences[genreFilterKey].orEmpty()
//            GenreFilter(genreString.split(SEPARATOR).toSet())
//        }
//    }
//
//    private val themeOptionKey = stringPreferencesKey("theme_option")
//
//    override suspend fun setThemeOption(themeOption: String) {
//        context.dataStore.edit { settings ->
//            settings[themeOptionKey] = themeOption
//        }
//    }
//
//    override suspend fun getThemeOption(): String {
//        return getThemeOptionFlow().first()
//    }
//
//    override fun getThemeOptionFlow(): Flow<String> {
//        return context.dataStore.data.map { preferences ->
//            preferences[themeOptionKey].orEmpty()
//        }
//    }
//
//    private val favoriteTheaterListKey = stringPreferencesKey("favorite_theaters")
//
//    override suspend fun setFavoriteTheaterList(list: List<Theater>) {
//        context.dataStore.edit { settings ->
//            settings[favoriteTheaterListKey] = Json.encodeToString(list)
//        }
//    }
//
//    override suspend fun getFavoriteTheaterList(): List<Theater> {
//        return getFavoriteTheaterListFlow().first()
//    }
//
//    override fun getFavoriteTheaterListFlow(): Flow<List<Theater>> {
//        return context.dataStore.data.map { preferences ->
//            val string = preferences[favoriteTheaterListKey]
//            if (string != null) {
//                Json.decodeFromString(string)
//            } else {
//                emptyList()
//            }
//        }
//    }
//
//    companion object {
//
//        private const val SEPARATOR = "|"
//    }
//}