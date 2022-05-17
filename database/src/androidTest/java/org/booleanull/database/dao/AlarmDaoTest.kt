package org.booleanull.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.booleanull.mock_factory.AlarmTestFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.booleanull.core.entity.Filter
import org.booleanull.database.ApplicationDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AlarmDaoTest {

    private lateinit var database: ApplicationDatabase
    private lateinit var alarmDao: AlarmDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ApplicationDatabase::class.java
        )
            .allowMainThreadQueries()
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()

        alarmDao = database.alarmDao()
    }

    @After
    fun clear() {
        database.close()
    }

    @Test
    fun getNotEmptyAlarmFromDatabase() = runTest {
        // Секция подготовки
        val alarm = AlarmTestFactory.EXAMPLE_ALARM

        // Секция действия
        alarmDao.insert(alarm)

        // Секция проверки
        val alarmsInDatabase = alarmDao.getAlarms()
        assert(alarmsInDatabase.isNotEmpty())
    }

    @Test
    fun getEmptyAlarmsFromDatabase() = runTest {
        val alarmsInDatabase = alarmDao.getAlarms()
        assert(alarmsInDatabase.isEmpty())
    }

    @Test
    fun searchEmptyAlarm() = runTest {
        val searchedAlarm = alarmDao.search(AlarmTestFactory.PACKAGE_NAME)
        assert(searchedAlarm == null)
    }

    @Test
    fun searchNotEmptyAlarm() = runTest {
        val alarm = AlarmTestFactory.EXAMPLE_ALARM
        alarmDao.insert(alarm)

        val searchedAlarm = alarmDao.search(AlarmTestFactory.PACKAGE_NAME)
        assert(searchedAlarm != null)
    }

    @Test
    fun insertAlarmWithFilter() = runTest {
        val alarmWithFilter = AlarmTestFactory.EXAMPLE_ALARM_WITH_FILTER
        alarmDao.insert(alarmWithFilter)

        val alarmsWithFilterInDatabase = alarmDao.getAlarms()
        assert(alarmsWithFilterInDatabase.contains(alarmWithFilter))
    }

    @Test
    fun insertAlarmToDatabase() = runTest {
        val alarm = AlarmTestFactory.EXAMPLE_ALARM
        alarmDao.insert(alarm)

        val alarmsInDatabase = alarmDao.getAlarms().map { it.alarm }
        assert(alarmsInDatabase.contains(alarm))
    }

    @Test
    fun insertFilterToDatabase() = runTest {
        val alarmWithFilter = AlarmTestFactory.EXAMPLE_ALARM_WITH_FILTER
        alarmDao.insert(alarmWithFilter)

        val filterName = "some_filter"
        val filter = Filter(
            packageName = AlarmTestFactory.PACKAGE_NAME,
            filter = filterName
        )
        alarmDao.insert(filter)

        val filtersInDatabase = alarmDao.getAlarms().flatMap { it.filters }
        assert(filtersInDatabase.contains(filter))
    }

    @Test
    fun updateAlarmFromDatabase() = runTest {
        val alarm = AlarmTestFactory.EXAMPLE_ALARM
        alarmDao.insert(alarm)

        val updatedAlarm = alarm.copy(hasAlarm = true)
        alarmDao.insert(updatedAlarm)

        val alarmsInDatabase = alarmDao.getAlarms().map { it.alarm }

        val actualAlarm =
            alarmsInDatabase.firstOrNull { it.packageName == AlarmTestFactory.PACKAGE_NAME }
        assert(actualAlarm?.hasAlarm == true)
    }

    @Test
    fun clearDatabase() = runTest {
        val alarm = AlarmTestFactory.EXAMPLE_ALARM
        alarmDao.insert(alarm)
        alarmDao.clear()

        val alarmsInDatabase = alarmDao.getAlarms().map { it.alarm }
        assert(!alarmsInDatabase.contains(alarm))
    }
}