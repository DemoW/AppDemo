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
    fun testCodeForKt() {
        val kotlinCode1 = KotlinCode("code1")
        kotlinCode1.initKt()
        kotlinCode1.threadName
        //kotlinCode1.getClassName()

        val kotlinCode2 = KotlinCode()

        val car1: Car = Car()
        val car2: Car = Car(listOf("BYD"))
    }

    @Test
    fun testValueAndVariable() {
        val count = 100     // 自动推断为Int类型
        var name = "hello"  // 自动推断为String类型
        name = "world"      // 可重新赋值

        var sex: String     // 声明一个String类型的sex变量
        //println(sex)      // 直接使用未赋值的变量，编译器会报错提示
        sex = "male"

        var number1 = 100
        var number2: Int? = 200
        println("number1 type=${number1::class.java.name}, number2 type=${number2!!::class.java.name}")
    }

    @Test
    fun testKotlinCondition() {
        var count: Int = 100

        val answerString: String = if (count == 42) {
            "I have the answer."
        } else if (count > 35) {
            "The answer is close."
        } else {
            "The answer eludes me."
        }

        println(answerString)

        val result = if (count == 100) "A" else "B"   // 取代Java的?:三元运算符
    }

    @Test
    fun testKotlinFunction() {
        fun stringMapper(str: String, mapper: (String) -> Int): Int {
            return mapper(str)
        }

        // 1. 使用方法引用
        stringMapper("Android", this::getMapper)
        // 2. 使用匿名方法
        stringMapper("Android", { input -> input.length })
        // 3. 匿名函数作为某个函数上定义的最后一个参数
        // 可以在用于调用该函数的圆括号之外传递它
        stringMapper("Android") { input -> input.length }
    }

    fun getMapper(str: String): Int = str.length

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