# demo-rabbitmq-elastic

docker run -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.6.1

***************************************************************************

docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management


https://gaddings.io/testing-spring-boot-apps-with-rabbitmq-using-testcontainers/


https://github.com/wonwoo/spring-boot-elasticsearch-test/blob/master/src/test/java/com/example/data/DataElasticTestIntegrationTests.java
