package com.example.coroutine

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request


@SpringBootTest
class CoroutineApplicationTests {

	@Test
	fun contextLoads() {
	}

	@Test
	fun test ()= runBlocking {
		val result = fetchData()
		println(result)
	}
	suspend fun fetchData(): String = withContext(Dispatchers.IO) {
		val client = OkHttpClient()
		val request = Request.Builder()
			.url("https://jsonplaceholder.typicode.com/posts")
			.build()
		val response = client.newCall(request).execute()
		return@withContext response.body?.string() ?: ""

		//return@withContext response.body()?.string() ?: ""
	}

}
