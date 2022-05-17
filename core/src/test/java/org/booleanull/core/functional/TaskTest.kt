package org.booleanull.core.functional

import junit.framework.TestCase
import org.junit.Test
import java.lang.Exception

class TaskTest {

    @Test
    fun successFold() {
        val task: Task<Exception, String> = Task.Success("")
        task.fold(
            succeeded = {
                assert(true)
            },
            failed = {
                assert(false)
            }
        )
    }

    @Test
    fun failureFold() {
        val task: Task<Exception, String> = Task.Failure(Exception())
        task.fold(
            succeeded = {
                assert(false)
            },
            failed = {
                assert(true)
            }
        )
    }
}