# Solution Based on Dynamic Imports

A two step solution:

   1. Create a module able to track, dynamically, every exported package using
      the `DynamicImport-Package` directive making, afterwards, its classloader
      visible to Redisson.
   2. Extend Redisson to be able to use the previous classloder.

This solution is based on how Liferay solves the execution of Groovy scripts in the Control Panel: https://github.com/liferay/liferay-portal/blob/master/modules/apps/server/server-admin-web/bnd.bnd Normally is not a recommended design since it breaks the OSGi modularity concept and creates a very heavy class path, but it's very fast to implement and requires few maintenance.

## How to Configure:
How to configure it:

   1. Ensure that the generated `com.liferay.redis.tomcat.client.jar` is placed in `$TOMCAT_HOME/lib/ext`.
   2. Modify the configuration to be able to use the newly created codec: FstLiferayCodec. In a basic default configuration:

```{
    "singleServerConfig":{
      "address": "redis://127.0.0.1:6379"
    },
    "threads":0,
    "nettyThreads":0,
    "transportMode":"NIO",
    "codec":{
    "class":"com.liferay.redis.tomcat.client.redisson.codec.FstLiferayCodec"
    },
    }
```
   3. Deploy in `$LIFERAY_HOME/deploy` the module `com.liferay.redis.tomcat.adapter.jar`

