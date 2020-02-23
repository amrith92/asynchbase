#! /bin/bash

mvn deploy -DperformRelease=true -DskipTests -DaltDeploymentRepository=flipkart::default::http://artifactory.fkinternal.com/artifactory/v1.0/artifacts/libs-snapshots-local
