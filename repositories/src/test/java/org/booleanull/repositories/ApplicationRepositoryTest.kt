package org.booleanull.repositories

import android.content.Intent
import android.content.pm.PackageManager
import com.booleanull.mock_factory.AlarmTestFactory
import com.booleanull.mock_factory.ApplicationTestFactory
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.booleanull.core.functional.Task
import org.booleanull.core.repository.AlarmRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ApplicationRepositoryTest {

    private val alarmRepository = mock<AlarmRepository>()
    private val packageManager = mock<PackageManager>()
    private val applicationRepository = ApplicationRepositoryImpl(packageManager, alarmRepository)

    @Before
    fun setup(): Unit = runBlocking {
        Mockito.`when`(alarmRepository.getAlarms()).thenReturn(
            listOf(AlarmTestFactory.EXAMPLE_ALARM_WITH_FILTER)
        )
        Mockito.`when`(alarmRepository.searchAlarm(AlarmTestFactory.PACKAGE_NAME)).thenReturn(
            Task.Success(AlarmTestFactory.EXAMPLE_ALARM_WITH_FILTER)
        )
        Mockito.`when`(packageManager.getInstalledPackages(PackageManager.GET_META_DATA)).thenReturn(
            listOf(ApplicationTestFactory.EXAMPLE_PACKAGE_INFO)
        )
        Mockito.`when`(packageManager.getLaunchIntentForPackage(AlarmTestFactory.PACKAGE_NAME)).thenReturn(
            Intent()
        )
        Mockito.`when`(packageManager.getApplicationLabel(ApplicationTestFactory.EXAMPLE_PACKAGE_INFO.applicationInfo)).thenReturn(
            "PushAlerts"
        )
        Mockito.`when`(packageManager.getActivityIcon(packageManager.getLaunchIntentForPackage(AlarmTestFactory.PACKAGE_NAME)!!)).thenReturn(
            null
        )
    }

    @Test
    fun getSuccessApplication() = runBlocking {
        val actual = applicationRepository.getApplication(AlarmTestFactory.PACKAGE_NAME)
        val expected = Task.Success(ApplicationTestFactory.EXAMPLE_APPLICATION)
        assertEquals(actual, expected)
    }

    @Test
    fun getFailureApplication() = runBlocking {
        val actual = applicationRepository.getApplication("some_package_name")
        assert(actual is Task.Failure)
    }

    @Test
    fun getApplications() = runBlocking {
        val actual = applicationRepository.getApplicationList()
        val expected = listOf(ApplicationTestFactory.EXAMPLE_APPLICATION)
        assertEquals(actual, expected)
    }

    @Test
    fun searchByNameApplications() = runBlocking {
        val actual = applicationRepository.searchApplicationList(ApplicationTestFactory.APPLICATION_NAME)
        val expected = Task.Success(listOf(ApplicationTestFactory.EXAMPLE_APPLICATION))
        assertEquals(actual, expected)
    }

    @Test
    fun searchByPackageNameApplications() = runBlocking {
        val actual = applicationRepository.searchApplicationList(AlarmTestFactory.PACKAGE_NAME)
        val expected = Task.Success(listOf(ApplicationTestFactory.EXAMPLE_APPLICATION))
        assertEquals(actual, expected)
    }

    @Test
    fun searchFailureApplications() = runBlocking {
        val actual = applicationRepository.searchApplicationList("some_package_name")
        assert(actual is Task.Failure)
    }
}