package lishui.example.app

import kotlinx.coroutines.*
import org.junit.Test
import kotlin.system.measureTimeMillis

/**
 * Created by lishui.lin on 20-10-10
 */
class ExampleUnitTest {

    @Test
    fun testCoroutine() {
        CoroutineScope(Dispatchers.Default).launch {
            val totalTime = measureTimeMillis {
                val result1 = async { doDefaultThread1() }
                val result2 = async { doDefaultThread2() }
                //result1.cancelAndJoin()
                println("Default work result1=" + result1.await() + ", result2=" + result2.await())
            }
            println("testCoroutine total time=$totalTime")
            doIOThread()
        }

        runBlocking {
            delay(3_000) // wait for test result finished
        }
    }

    private suspend fun doDefaultThread1(): String = withContext(Dispatchers.Default) {
        val duration = measureTimeMillis {
            delay(1_000)
        }

        println("doDefaultThread1 spent time=$duration")
        return@withContext "Hello "

    }

    private suspend fun doDefaultThread2(): String = withContext(Dispatchers.Default) {
        val duration = measureTimeMillis {
            delay(1_000)
        }

        println("doDefaultThread2 spent time=$duration")
        return@withContext " world"
    }

    private suspend fun doIOThread() = withContext(Dispatchers.IO) {
        println("doIOThread: " + Thread.currentThread().name)
    }
}