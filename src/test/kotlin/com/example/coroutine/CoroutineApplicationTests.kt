package com.example.coroutine

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody


@SpringBootTest
class CoroutineApplicationTests {

	@Test
	fun contextLoads() {
	}

	/**
	 * 자바에서도 코루틴은 사용가능하다.
	 * 하지만 Kotlin Coroutines가 Kotlin에 특화되어있다.
	 * Java에서는 Coroutine보다는 Future, CompletableFutre 등을 사용해서 구현하는게 일반적이다.
	 * WebClient의 BodyToMono는 Coroutine을 대체하는가?
	 * 그렇지 않다. bodyToMono(Integer.class) .block();은 동기로 기다리면서 쓰레드가 묶이는 것을 말함.
	 */
	@Test
	fun test ()= runBlocking {
		//val result = fetchData()
		//println(result)

		val result2 = fetchData2()
		println("ddd"+result2)
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

	suspend fun fetchData2(): String {
		val webClient = WebClient.create()
		return webClient.get()
			.uri("https://jsonplaceholder.typicode.com/posts")
			.retrieve()
			//.bodyToMono(String::class.java)
			.awaitBody<String>()
	}

}
