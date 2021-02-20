package lishui.example.app

import kotlinx.coroutines.*
import lishui.example.app.annotation.AnnotationTest
import lishui.example.app.annotation.DogFeed
import lishui.example.app.coroutine.CoroutineTest
import org.junit.Test
import kotlin.system.measureTimeMillis

/**
 * Created by lishui.lin on 20-10-10
 */
class AppDemoUnitTest {

    @Test
    fun testCoroutine() {
        CoroutineScope(Dispatchers.Default).launch {
            val coroutineTest = CoroutineTest()
            val totalTime = measureTimeMillis {
                val result1 = async { coroutineTest.doDefaultThread1() }
                val result2 = async { coroutineTest.doDefaultThread2() }
                //result1.cancelAndJoin()
                println("Default work result1=" + result1.await() + ", result2=" + result2.await())
            }
            println("testCoroutine total time=$totalTime")
            coroutineTest.doIOThread()
        }

        runBlocking {
            delay(3_000) // wait for test result finished
        }
    }

    @Test
    fun testAnnotation() {
        val annotationTest = AnnotationTest()
        val fields = annotationTest.javaClass.declaredFields
        for (field in fields) {
            field.isAccessible = true
            println("field name = ${field.name}, value = ${field.getInt(annotationTest)}")
            // find DogFeed annotation
            val annotation: DogFeed? =
                field.getAnnotation(DogFeed::class.java)
            annotation?.let {
                println("DogFeed Annotation testValue = ${it.foodValue}")
            }
        }
    }
}