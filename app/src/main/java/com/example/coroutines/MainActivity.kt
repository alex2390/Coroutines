package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    val TAG: String = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e(TAG, "主线程id：${mainLooper.thread.id}")
//        GlobalScope.launch {
//            var token = getToken()
//            var userinfo = getUserinfo(token)
//            setUserinfo(userinfo)
//
//
//        }

        GlobalScope.launch {
            val result1 = GlobalScope.async {
                getResult1()

            }

            val result2 = GlobalScope.async {
                getResult2()

            }

            val result = result1.await() + result2.await()

            Log.d(TAG, "result= $result")


        }

        testrunBlocking()

    }

    val job: Job = GlobalScope.launch {
        delay(6000)
        Log.e(TAG, "协程执行 -- 线程id：${Thread.currentThread().id}")


    }

    private fun testrunBlocking() = runBlocking {
        repeat(8) {
            Log.d(TAG, "协程执行$it 线程id：${Thread.currentThread().id} ")
            delay(100)
        }
    }

    private suspend fun getToken(): String {

        delay(1000)

        return "token"

    }

    private suspend fun getUserinfo(token: String): String {

        delay(2000)

        return "$token-userinfo"
    }


    private suspend fun setUserinfo(userinfo: String) {


        Log.d(TAG, userinfo)
    }

    private suspend fun getResult1(): Int {
        delay(3000)
        return 1
    }

    private suspend fun getResult2(): Int {
        delay(4000)
        return 2
    }
}
