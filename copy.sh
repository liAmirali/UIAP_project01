#!/bin/bash

touch "all.java";
> all.java

for i in src/**/*; do
    echo "$i content is:\n"
    cat $i >> all.java;
    
done
