# Solution Based on Extending Redisson Session Manager

Extends `RedissonSessionManager` to use Liferay Serializer/Deserializer. Liferay serialization handlers are based on the `ClassLoaderPool` that contains every module classloader, which lets it handle every visible class serialization/deserialization. In the end the idea is to leave Liferay handle the inner process since it knows how to reach every classloader.

This solution is based on how Liferay handles PortletSession replication, which somehow it's easier since `PortletSessionImpl` is a class managed by Liferay and can wrap the session every time the constructor is invoked. Probably this solution is better from an overall performance point of view, it matches OSGi principles, but it can be harder to maintain since there's a big coupling between Redisson and Liferay.


## How to Configure:
How to configure it:

   1. Ensure that the generated `com.liferay.redis.redisson.integration.jar` is placed in `$TOMCAT_HOME/lib/ext`.
   2. Change the configuration to use `com.liferay.redis.redisson.integration.tomcat.LiferayRedissonSessionManager` instead of `org.redisson.tomcat.RedissonSessionManager`.
   ```
   <Manager className="com.liferay.redis.redisson.integration.tomcat.LiferayRedissonSessionManager"
  configPath="${catalina.base}/redisson.conf" 
  readMode="REDIS" updateMode="DEFAULT" broadcastSessionEvents="false"/>
  ```
  

