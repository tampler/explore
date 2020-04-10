package hello

import com.sksamuel.elastic4s.RefreshPolicy
import com.sksamuel.elastic4s.http.{ ElasticClient, ElasticProperties }
import com.sksamuel.elastic4s.http.Response
import com.sksamuel.elastic4s.http.search.SearchResponse

object ElasticHello extends App {

  // you must import the DSL to use the syntax helpers
  import com.sksamuel.elastic4s.http.ElasticDsl._

  val client = ElasticClient(ElasticProperties("http://localhost:9200"))

  client.execute {
    bulk(
      indexInto("myindex" / "mytype").fields("country" -> "Mongolia", "capital" -> "Ulaanbaatar"),
      indexInto("myindex" / "mytype").fields("country" -> "Namibia", "capital"  -> "Windhoek")
    ).refresh(RefreshPolicy.WaitFor)
  }.await

  val resp0: Response[SearchResponse] = client.execute {
    search("myindex").matchQuery("capital", "ulaanbaatar")
  }.await

  val resp1: Response[SearchResponse] = client.execute {
    search("myindex").matchQuery("capital", "Windhoek")
  }.await

  // prints out the original json
  println(resp0.result.hits.hits.head.sourceAsString)
  println(resp1.result.hits.hits.head.sourceAsString)

  client.close()

}
