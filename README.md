# demo-rabbitmq-elastic

## RabbitMQ

$ docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

https://gaddings.io/testing-spring-boot-apps-with-rabbitmq-using-testcontainers/

## ElasticSearch

$ docker run -d --name elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.6.1

$ docker run -d --name elastichq -p 5000:5000 elastichq/elasticsearch-hq
$ docker run -d --name elastichq -p 5000:5000 --network elasticsearch -e HQ_DEFAULT_URL='http://elasticsearch:9200' -e HQ_ENABLE_SSL=False elastichq/elasticsearch-hq


https://github.com/wonwoo/spring-boot-elasticsearch-test/blob/master/src/test/java/com/example/data/DataElasticTestIntegrationTests.java


https://piotrminkowski.com/2019/03/29/elasticsearch-with-spring-boot/

https://dzone.com/articles/spring-boot-elasticsearch?fromrel=true

https://www.geekyhacker.com/2019/05/08/integration-test-with-testcontainers-in-java/

https://www.brightmarbles.io/blog/blog-posts/elasticsearch-java-clients-3/

https://www.oodlestechnologies.com/blogs/How-To-Use-Invoke-Elastic-Search-With-SpringBoot-Rest-API/
