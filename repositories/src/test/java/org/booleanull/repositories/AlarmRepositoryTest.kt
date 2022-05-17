package org.booleanull.repositories

import com.booleanull.mock_factory.AlarmTestFactory
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.booleanull.core.functional.Task
import org.booleanull.database.dao.AlarmDao
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class AlarmRepositoryTest {

    companion object {
        private const val SOME_PACKAGE_NAME = "some_package_name"
    }

    private val alarmDao = mock<AlarmDao>()
    private val alarmRepository = AlarmRepositoryImpl(alarmDao)

    @Before
    fun setup(): Unit = runBlocking {
        Mockito.`when`(alarmDao.getAlarms()).thenReturn(
            listOf(AlarmTestFactory.EXAMPLE_ALARM_WITH_FILTER)
        )
        Mockito.`when`(alarmDao.search(AlarmTestFactory.PACKAGE_NAME))
            .thenReturn(AlarmTestFactory.EXAMPLE_ALARM_WITH_FILTER)
        Mockito.`when`(alarmDao.search(SOME_PACKAGE_NAME))
            .thenReturn(null)
    }

    @Test
    fun getAlarms() = runBlocking {
        val actual = alarmRepository.getAlarms()
        val expected = listOf(AlarmTestFactory.EXAMPLE_ALARM_WITH_FILTER)

        assertEquals(actual, expected)
    }

    @Test
    fun searchSuccessAlarm() = runBlocking {
        val actual = alarmRepository.searchAlarm(AlarmTestFactory.PACKAGE_NAME)
        val expected = Task.Success(AlarmTestFactory.EXAMPLE_ALARM_WITH_FILTER)

        assertEquals(actual, expected)
    }

    @Test
    fun searchFailureAlarm() = runBlocking {
        val actual = alarmRepository.searchAlarm(SOME_PACKAGE_NAME)
        assert(actual is Task.Failure)
    }

    @Test
    fun incrementCountSuccessAlarm() = runBlocking {
        alarmRepository.incrementCountAlarm(AlarmTestFactory.PACKAGE_NAME)
        Mockito.verify(alarmDao).search(AlarmTestFactory.PACKAGE_NAME)
        Mockito.verify(alarmDao).insert(AlarmTestFactory.EXAMPLE_ALARM.copy(count = 1))
    }

    @Test
    fun incrementCountFailureAlarm() = runBlocking {
        alarmRepository.incrementCountAlarm(SOME_PACKAGE_NAME)
        Mockito.verify(alarmDao).search(SOME_PACKAGE_NAME)
        Mockito.verify(alarmDao, Mockito.never()).insert(AlarmTestFactory.EXAMPLE_ALARM.copy(count = 1))
    }

    @Test
    fun insertAlarm() = runBlocking {
        alarmRepository.insertAlarm(AlarmTestFactory.EXAMPLE_ALARM_WITH_FILTER)
        Mockito.verify(alarmDao).insert(AlarmTestFactory.EXAMPLE_ALARM_WITH_FILTER)
    }

    @Test
    fun insertFilter() = runBlocking {
        alarmRepository.insertFilter(AlarmTestFactory.EXAMPLE_FILTER)
        Mockito.verify(alarmDao).insert(AlarmTestFactory.EXAMPLE_FILTER)
    }

    @Test
    fun removeFilter() = runBlocking {
        alarmRepository.removeFilter(AlarmTestFactory.EXAMPLE_FILTER)
        Mockito.verify(alarmDao).remove(AlarmTestFactory.EXAMPLE_FILTER)
    }

    @Test
    fun clear() = runBlocking {
        alarmRepository.clear()
        Mockito.verify(alarmDao).clear()
    }
}