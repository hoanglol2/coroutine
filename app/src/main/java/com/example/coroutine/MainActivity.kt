package com.example.coroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*runBlocking {
            launch(Dispatchers.IO) {
                Log.d(TAG, doNetWorkAnswer())
            }

            launch(Dispatchers.IO) {
                Log.d(TAG, doNetWorkAnswer())
            }
        }*/

        /*
        * JOB, WAIT, CANCELATION
        * job.join() -> để gọi các công việc vào,
        * job.cancel() -> để hủy các công việc, mặc định gọi trong runBlocking{}, nó sẽ chạy cái đầu tiên rồi hủy.
        * ngoại lệ : chạy các công việc trong vòng lặp for (mặc dù đã cancel ở runBlocking{}, nhưng nó không bị hủy công việc đang chạy
        * vì nó sẽ không có thời gian để kiểm tra.
        * giải pháp: sử dụng isActive để kiểm tra, điều này nói rằng 1 số công việc sẽ khả dụng.
        * */
        /*val job = GlobalScope.launch {
            Log.d(TAG, "Starting long running calculation...")
            for (item in 30..40) {
                if (isActive) {
                    Log.d(TAG, "Result for i = $item : ${fib(item)}")
                }
            }
            Log.d(TAG, "Ending long running calculation...")
        }

        runBlocking {
            job.cancel()
            Log.d(TAG, "Main thread is continuing...")
        }*/

        /*val job = GlobalScope.launch {
            Log.d(TAG, "Starting long runing calculation...")
            withTimeout(3000L) {
                Log.d(TAG, "Hello withTimeout")
            }
        }

        runBlocking {
            job.join()
            Log.d(TAG, "End JOB")
        }*/


        /*Async and Await Coroutine*/
        // Sync -> 6s
        /*GlobalScope.launch {
            val time = measureTimeMillis {
                val answer1 = doNetWorkAnswer()
                val answer2 = doNetWorkAnswer()
                Log.d(TAG, "answer1 is $answer1")
                Log.d(TAG, "answer2 is $answer2")
            }
            Log.d(TAG, "Request took $time ms.")
        }*/

        // Async - C1
        /*GlobalScope.launch {
            val time = measureTimeMillis {
                var answer1: String? = null
                var answer2: String? = null

                val job1 = launch { answer1 = doNetWorkAnswer() }
                val job2 = launch { answer2 = doNetWorkAnswer() }

                job1.join()
                job2.join()

                Log.d(TAG, "answer1 is $answer1")
                Log.d(TAG, "answer2 is $answer2")
            }
            Log.d(TAG, "Request took $time ms.")
        }*/

        // Async - C2
        GlobalScope.launch {
            val time = measureTimeMillis {
                val job1 = async { doNetWorkAnswer() }
                val job2 = async { doNetWorkAnswer() }
                Log.d(TAG, "Answer1 is ${job1.await()}")
                Log.d(TAG, "Answer2 is ${job2.await()}")
            }
            Log.d(TAG, "Request took $time ms.")
        }

    }

    // delay for network call.
    private suspend fun doNetWorkAnswer(): String {
        delay(3000L)
        return "the thread answer"
    }

    // calculation fibonaci
    private fun fib(n: Int): Long {
        return when (n) {
            0 -> 0
            1 -> 1
            else -> {
                fib(n - 1) + fib(n - 2)
            }
        }
    }

}