# APPRECIATE-KUDOS-SERVICE

A core service of APPreciate cloud-native implementation. This microservice implements

## Build
```
./mvnw install
```

Or, for a native package
```
./mvnw install -Pnative
```

## Deploy to OpenShift

### Prepare project
```
oc new-project jee-cloud
```

### OpenShift Maven Plugin
Java app
```
./mvnw package -Dquarkus.kubernetes.deploy=true
```

Or, as a native build
```
./mvnw package -Dquarkus.kubernetes.deploy=true -Pnative
```

### Pure S2I (Native)
```
oc new-app quay.io/quarkus/ubi-quarkus-native-s2i:22.2-java17~https://github.com/ozimakov-rh/jee-cloud-native --context-dir=appreciate-kudos-service --name=appreciate-kudos-service
```

# Connecting to Kafka
[Connection to AMQ Streams cluster](https://access.redhat.com/documentation/en-us/red_hat_amq_streams/2.3/html/getting_started_with_amq_streams_on_openshift/proc-creating-route-str)

